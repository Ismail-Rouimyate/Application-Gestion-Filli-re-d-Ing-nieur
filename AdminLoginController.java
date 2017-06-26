package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminLoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motDePasseField;
    @FXML
    private Label erreur;

    Stage primaryStage;

    private Stage adminLoginStage;
    private boolean okClicked = false;
    private boolean isAdminValide = false;
    public Stage dialogStage = new Stage();

    public void setIsAdminValide(boolean what){
        this.isAdminValide = what;
    }

    /**
     * Initializes the controller class
     * automatically called after
     * the fxml file loaded
     */
    @FXML
    private void initialize(){
    }

    /**
     * Sets the stage of this dialog
     *
     * @param adminLoginStage
     */
    public void setAdminLoginStage(Stage adminLoginStage){
        this.adminLoginStage = adminLoginStage;
    }

    /**
     * returns true if login clicked
     * @return okClicked
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     *called when the user clicks login
     */
    @FXML
    private void handleLogin(){
        if(isInputValide(emailField.getText(),motDePasseField.getText())){
            adminAffichage();
            okClicked = true;
            adminLoginStage.close();
        }
        else{
            erreur.setText("Email ou Mot de passe erronés");
            okClicked = false;
        }
    }


    /**
     * validates the user input
     */
    private boolean isInputValide(String email, String mdp){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT id_admin FROM Admin WHERE email = ? AND mot_de_passe = ?");
            statement.setString(1,email);
            statement.setString(2,mdp);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                System.out.println(result.getString("id_admin"));
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("rien");
        return false;
    }

    private void adminAffichage(){
        try{

            //Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("AdminOverview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Create dialog stage

            dialogStage.setTitle("Administration");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AdminOverviewController controller = loader.getController();
            controller.setAdminOverviewStage(dialogStage);
            controller.setAdminLoginController(this);
            controller.initialize();



            // Shows the dialog and waits until the user closes it
            dialogStage.show();
            dialogStage.setOnCloseRequest(e -> {
                e.consume();
                controller.handleDisconnection();
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
