package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModuleEditDialogController {

    @FXML
    private TextField intituleField;
    @FXML
    private TextField vhcField;
    @FXML
    private TextField vhtdField;
    @FXML
    private TextField vhtpField;
    @FXML
    private TextField vhapField;
    @FXML
    private TextField element1Field;
    @FXML
    private TextField element2Field;
    @FXML
    private TextField element3Field;
    @FXML
    private TextField nomProfField;
    @FXML
    private TextField prenomProfField;


    Stage dialogStage = new Stage();
    Module module = new Module();

    private boolean okClicked = false;

    public boolean IsOkClicked(){
        return okClicked;
    }

    public void setModule(Module module){
        this.module = module;

        intituleField.setText(module.getNom());
        vhcField.setText(Integer.toString(module.getvhc()));
        vhtdField.setText(Integer.toString(module.getvhtd()));
        vhtpField.setText(Integer.toString(module.getvhtp()));
        vhapField.setText(Integer.toString(module.getvhap()));
        try {
            element1Field.setText(module.listElemProperty().get(0));
        }catch (Exception e){

        }
        try {
            element2Field.setText(module.listElemProperty().get(1));
        }catch (Exception e){

        }
        try {
            element3Field.setText(module.listElemProperty().get(2));
        }catch (Exception e){

        }
        nomProfField.setText(module.getNomProf());
        prenomProfField.setText(module.getPrenomProf());
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
        if (isInputValid()){
            module.setIntitule(intituleField.getText());
            module.setVhc(Integer.parseInt(vhcField.getText()));
            module.setVhtd(Integer.parseInt(vhtdField.getText()));
            module.setVhtp(Integer.parseInt(vhtpField.getText()));
            module.setVhap(Integer.parseInt(vhapField.getText()));
            module.setListElem(element1Field.getText(),element2Field.getText(),element3Field.getText());
            module.setNomProf(nomProfField.getText());
            module.setPrenomProf(prenomProfField.getText());
            if (verifIntitule(intituleField.getText())) {
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("UPDATE module SET volume_horaire_cours = ?, volume_horaire_td = ?, volume_horaire_tp = ?, volume_horaire_ap = ?, id_prof = ? WHERE intitule = ?");
                    statement.setString(1,vhcField.getText());
                    statement.setString(2,vhtdField.getText());
                    statement.setString(3,vhtpField.getText());
                    statement.setString(4,vhapField.getText());
                    statement.setString(5,Integer.toString(getProf(nomProfField.getText(), prenomProfField.getText())));
                    statement.setString(6,intituleField.getText());
                    statement.executeUpdate();
                    module.listElemProperty().forEach(string -> {
                        try {
                            PreparedStatement statement1 = con.prepareStatement("UPDATE element_module SET id_module = ? WHERE intitule = ?");
                            statement1.setString(1,Integer.toString(getIdModule(intituleField.getText())));
                            statement1.setString(2,string);
                            statement1.executeUpdate();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                int maxId = 0;
                try{
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("SELECT max(id_module) FROM module");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                maxId++;
                try{
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("INSERT INTO module VALUES(?,?,?,?,?,?,0,0,?)");
                    statement.setString(1,Integer.toString(maxId));
                    statement.setString(2,intituleField.getText());
                    statement.setString(3,vhcField.getText());
                    statement.setString(4,vhtdField.getText());
                    statement.setString(5,vhtpField.getText());
                    statement.setString(6,vhapField.getText());
                    statement.setString(7,Integer.toString(getProf(nomProfField.getText(), prenomProfField.getText())));
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
        if( intituleField.getText() == null || intituleField.getText().length() == 0){
            errorMessage += "Pas d'intitulé valide! \n";
        }
        if( vhcField.getText() == null || vhcField.getText().length() == 0){
            errorMessage += "Pas de volume horaire valide! \n";
        }
        if( vhtdField.getText() == null || vhtdField.getText().length() == 0){
            errorMessage += "Pas volume horaire valide! \n";
        }
        if( vhtpField.getText() == null || vhtpField.getText().length() == 0){
            errorMessage += "Pas volume horaire valide! \n";
        }
        if( vhapField.getText() == null || vhapField.getText().length() == 0){
            errorMessage += "Pas volume horaire valide! \n";
        }
        if( element1Field.getText() == null || element1Field.getText().length() == 0){
            errorMessage += "Pas d'element 1 valide! \n";
        }
        if( element2Field.getText() == null || element2Field.getText().length() == 0){
            errorMessage += "Pas d'element 2 valide! \n";
        }
        if( element3Field.getText() == null || element3Field.getText().length() == 0){
            errorMessage += "Pas d'element 3 valide! \n";
        }
        if (getProf(nomProfField.getText(),prenomProfField.getText()) == 0){
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
            PreparedStatement statement = con.prepareStatement("SELECT * FROM module WHERE intitule = ?");
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


    private int getIdModule(String intitule){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM module WHERE intitule");
            statement.setString(1,intitule);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return result.getInt("id_module");
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
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
