package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Professeur;


public class LoginProf {

    @FXML
    private TextField email;
    @FXML
    private TextField motDePasse;
    @FXML
    private Label erreur;

    private Stage loginStage;
    private Professeur prof;
    private boolean okClicked = false;

    /* Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.*/
    @FXML
    private void initialize(){
    }

    /* Sets the stage of the login */
    public void setLoginStage(Stage loginStage){
        this.loginStage = loginStage;
    }

    /* sets the email and pass to compare to */
    public void setProf(Professeur prof){
        this.prof = prof;
        
    }

}
