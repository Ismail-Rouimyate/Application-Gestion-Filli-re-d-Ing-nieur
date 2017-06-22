package sample;


import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.Label;

public class ProfOverviewController {

    private Stage profOverviewStage;
    private Professeur prof;

    @FXML
    private Label professeur;
    @FXML
    private Label departement;
    @FXML
    private Label filliere;
    @FXML
    private Label module;
    @FXML
    private Label specialite;


    //reference to profLoginController
    private ProfLoginController profLoginController;

    /**
     * The contructor
     * called before the initialize method
     */
    // NULL


    public void initialize(){
        showProfDetails(prof);

    }

    /**
     *  Is called by the previous controller to give a reference back to itself
     *
     *  @param profLoginController
     */
    public void setProfLoginController(ProfLoginController profLoginController){
        this.profLoginController = profLoginController;

        this.prof = profLoginController.getProfConnecte();
    }


    public void setProfOverviewStage(Stage profOverviewStage){
        this.profOverviewStage = profOverviewStage;

    }

    private void showProfDetails(Professeur prof){
        //profOverviewStage.setOnCloseRequest(e -> handleDisconnection());

        if(prof != null){
            // fills the labels with info from professeur object
            professeur.setText(prof.getNom()+" "+prof.getPrenom());
            departement.setText(prof.getDepratement());
            specialite.setText(prof.specialiteProperty().getValue());
        }
        else {
            // prof is null, remove all text
            professeur.setText("inexistant");
            departement.setText("inexistant");
            specialite.setText("inexistant");
        }
    }

    /**
     * handles when the user wants to close the stage either with the red cross or the disconnect button
     */
    @FXML
    public void handleDisconnection(){

        boolean output = AlertBoxController.display("Fermeture","Etes vous sur de vouloir vous déconnecter");
        if(output == true){ profLoginController.dialogStage.close(); }

    }

}
