package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;


public class ProfLoginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField motDePasseField;
    @FXML
    private Label erreur;

    Stage primaryStage;

    // liste de professeurs en mode observable array list
    private ObservableList<Professeur> professeurData = FXCollections.observableArrayList();

    Professeur exemple = new Professeur(1,"test","test","nom","prenom","specialite",null,null,null);


    private Stage profLoginStage;
    private boolean okClicked = false;



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
     * @param profLoginStage
     */
    public void setProfLoginStage(Stage profLoginStage){
        this.profLoginStage = profLoginStage;
    }

    /**
     * returns true if login clicked
     * @return okClicked
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * called when user clicks login
     */
    @FXML
    private void handleLogin(){
        if (isInputValid(emailField.getText(),motDePasseField.getText())){
            profAffichage(quelProf(emailField.getText()));
            okClicked = true;
            profLoginStage.close();
        }
        else{
            erreur.setText("Email ou Mot de passe erronés");
            okClicked = false;
        }
    }

     /**
     * validates the user input
     * returns true if valid
     */
     private boolean isInputValid(String email, String passe){
         // loops through the observable array list to find a match
         professeurData.add(exemple);
         /*
         *pour tester la fonction de test
         Professeur newProf = new Professeur(1,"test","test","moi","moi","specialite",null,null,null);
         professeurData.add(newProf);*/

         for(Professeur prof : professeurData){
             if(prof.getEmail() == email && prof.getMotDePasse() == passe){
                 return true;
             }
         }
         return false;
     }

    /**
    *Opens prof menu
    */
    public void profAffichage(Professeur prof){
        try{
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ProfOverview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Create dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Planing de : "+prof.getNom()+" "+prof.getPrenom());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProfOverviewController controller = loader.getController();
            controller.setProfOverviewStage(dialogStage);

            // Shows the dialog and waits until the user closes it
            dialogStage.showAndWait();



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Professeur quelProf(String email){
        for(Professeur prof : professeurData){
            if(prof.getEmail() == email){
                return prof;
            }
        }
        // serves nothing since we use this methode only if the "if statement" is true on one of the profs
        Professeur profe = new Professeur();
        return profe;
    }



}
