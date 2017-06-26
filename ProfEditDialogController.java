package sample;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ProfEditDialogController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField specialiteField;
    @FXML
    private TextField departementField;
    @FXML
    private PasswordField motDePasseField;
    @FXML
    private PasswordField confirmationField;
    @FXML
    private TextField emailField;


    Stage dialogStage = new Stage();
    Professeur prof = new Professeur();

    private boolean okClicked = false;

    public boolean IsOkClicked(){
        return okClicked;
    }

    public void setProfesseur(Professeur prof){
        this.prof = prof;

        nomField.setText(prof.getNom());
        prenomField.setText(prof.getPrenom());
        specialiteField.setText(prof.getSpecialite());
        departementField.setText(prof.getDepratement());
        emailField.setText(prof.getEmail());
        motDePasseField.setText(prof.getMotDePasse());
        confirmationField.setText(prof.getMotDePasse());

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
     * called when the user clicks on OK
     */
    @FXML
    public void handleOk(){
        if(isInputValid()){
            prof.setNom(nomField.getText());
            prof.setPrenom(prenomField.getText());
            prof.setEmail(emailField.getText());
            prof.setSpecialite(specialiteField.getText());
            prof.setDepartement(departementField.getText());
            prof.setMotDePasse(motDePasseField.getText());
            if (verifEmail(emailField.getText())){
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("UPDATE professeur SET nom = ? , prenom = ? , specialite = ? , departement = ? , mdp = ? WHERE email = ?");
                    statement.setString(1,nomField.getText());
                    statement.setString(2,prenomField.getText());
                    statement.setString(3,specialiteField.getText());
                    statement.setString(4,departementField.getText());
                    statement.setString(5,motDePasseField.getText());
                    statement.setString(6,emailField.getText());
                    statement.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                int maxId = 0;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("SELECT max(id_prof) FROM professeur");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()){
                        maxId = resultSet.getInt(1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                maxId++;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("INSERT INTO professeur Values (?,?,?,?,?,?,?)");
                    statement.setString(1,Integer.toString(maxId));
                    statement.setString(2,nomField.getText());
                    statement.setString(3,prenomField.getText());
                    statement.setString(4,specialiteField.getText());
                    statement.setString(5,departementField.getText());
                    statement.setString(6,emailField.getText());
                    statement.setString(7,motDePasseField.getText());
                    statement.executeUpdate();
                }catch (Exception e){
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

        if (emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "Pas d'email valide! \n";
        }
        if (nomField.getText() == null || nomField.getText().length() == 0){
            errorMessage += "Pas de nom valide! \n";
        }
        if (prenomField.getText() == null || prenomField.getText().length() == 0){
            errorMessage += "Pas de prenom valide! \n";
        }
        if (specialiteField.getText() == null || specialiteField.getText().length() == 0){
            errorMessage += "Pas de specialité valide! \n";
        }
        if (departementField.getText() == null || departementField.getText().length() == 0){
            errorMessage += "Pas de département valide! \n";
        }
        if (motDePasseField.getText() == null || motDePasseField.getText().length() == 0){
            errorMessage += "Pas de mot de passe valide! \n";
        }
        if (!motDePasseField.getText().equals(confirmationField.getText())){
            errorMessage += "Mots de passe différents! \n";
        }
        /*if (verifEmail(emailField.getText())){
            errorMessage += "Cet email est déja utilisé! \n";
        }*/

        if (errorMessage.length() == 0){
            return true;
        }
        else{
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


    private boolean verifEmail(String email){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM professeur WHERE email = ?");
            statement.setString(1,email);
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



}
