package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ProfLoginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField motDePasseField;
    @FXML
    private Label erreur;

    // liste de professeurs en mode observable array list
    private ObservableList<Professeur> professeurData = FXCollections.observableArrayList();


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
     * @return
     */
    public boolean isOkClicked(){
        return isOkClicked();
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
        // TODO : créer une méthode pour ouvrir le menu du professeur
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
