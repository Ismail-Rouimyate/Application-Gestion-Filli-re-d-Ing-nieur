package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FilliereEditDialogController {

    @FXML
    private TextField intituleField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;

    Stage dialogStage = new Stage();
    Filliere filliere = new Filliere();

    private boolean okClicked = false;

    public boolean IsOkClicked(){
        return okClicked;
    }

    public void setFilliere (Filliere filliere){
        this.filliere = filliere;

        intituleField.setText(filliere.getNom());
        dateField.setText(filliere.getDate());
        nomField.setText(filliere.getNomProf());
        prenomField.setText(filliere.getPrenomProf());

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Called when the user clicks ok
     */
    @FXML
    private void handleOk(){
        if (isInputValid()) {
            filliere.setIntitule(intituleField.getText());
            filliere.setDateaccr(dateField.getText());
            filliere.setNomProf(nomField.getText());
            filliere.setPrenomProf(prenomField.getText());
            if (verifIntitule(intituleField.getText())) {
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("UPDATE filiere SET  date_acreditation = ? , id_prof = ? WHERE intitule = ?");
                    statement.setString(1, dateField.getText());
                    statement.setString(2, Integer.toString(getProf(nomField.getText(), prenomField.getText())));
                    statement.setString(3, intituleField.getText());
                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                int maxId = 0;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("SELECT max(id_filiere) FROM filiere");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                maxId++;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("INSERT INTO filiere VALUES (?,?,?,?)");
                    statement.setString(1,Integer.toString(maxId));
                    statement.setString(2,intituleField.getText());
                    statement.setString(3,dateField.getText());
                    statement.setString(4,Integer.toString(getProf(nomField.getText(), prenomField.getText())));
                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancle(){ dialogStage.close(); }

    private boolean isInputValid(){
        String errorMessage = "";

        if (intituleField.getText() == null || intituleField.getText().length() == 0){
            errorMessage += "Pas d'intitulé valide! \n";
        }
        if (dateField.getText() == null ||dateField.getText().length() == 0){
            errorMessage += "Pas de date valide! \n";
        }
        if (getProf(nomField.getText(),prenomField.getText()) == 0){
            errorMessage += "Pas de professeur valide! \n";
        }

        if (errorMessage.length() == 0){
            return true;
        }
        else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private boolean verifIntitule(String intitule){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM filiere WHERE intitule = ?");
            statement.setString(1,intitule);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private int getProf(String nom, String prenom){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM professeur WHERE nom = ? AND prenom = ?");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return result.getInt("id_prof");
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

}
