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



    Professeur profConnecte;

    private Stage profLoginStage;
    private boolean okClicked = false;
    private boolean isProfValide = false;

    public void setIsProfValide(boolean what){
        this.isProfValide = what;
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
        isInputValid(emailField.getText(),motDePasseField.getText());

        if (isProfValide){
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
     *
     */
     private void isInputValid(String email, String passe){
         // loops through the observable array list to find a match
         //professeurData.add(new Professeur("test", "test"));
         /*
         *pour tester la fonction de test*/
         Departement dep = new Departement("departement de test");
         Filliere fil = new Filliere("filliere de test");
         Module mod = new Module("module de test");
         Professeur newProf = new Professeur(1,"test","test","moi","moi","specialite",mod,fil,dep);
         professeurData.add(newProf);


         professeurData.forEach(professeur -> {
             if(professeur.getEmail().equals(email) && professeur.getMotDePasse().equals(passe) ){
                 setIsProfValide(true);
             }
             else{
                 setIsProfValide(false);
             }
         });
     }

    /**
    *Opens prof menu
    */
    public void profAffichage(Professeur prof){
        try{

            setProfConnecte(prof);

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
            controller.setProfLoginController(this);
            controller.setProfOverviewStage(dialogStage);
            controller.initialize();



            // Shows the dialog and waits until the user closes it
            dialogStage.showAndWait();



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Professeur quelProf(String email){
        for(Professeur prof : professeurData){
            if(prof.getEmail().equals(email)){
                return prof;
            }
        }
        // serves nothing since we use this methode only if the "if statement" is true on one of the profs
        Professeur profe = new Professeur();
        return profe;
    }

    public void setProfConnecte(Professeur prof){
        this.profConnecte = prof;
    }

    public Professeur getProfConnecte(){
        return this.profConnecte;
    }



}
