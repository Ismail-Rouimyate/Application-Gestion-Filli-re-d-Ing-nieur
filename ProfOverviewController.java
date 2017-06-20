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
        if(prof != null){
            // fills the labels with info from professeur object
            professeur.setText(prof.getNom()+" "+prof.getPrenom());
            departement.setText(prof.departementProperty().getValue().getNom());
            filliere.setText(prof.filliereProperty().getValue().getNom());
            module.setText(prof.moduleProperty().getValue().getNom());
            specialite.setText(prof.specialiteProperty().getValue());
        }
        else {
            // prof is null, remove all text
            professeur.setText("inexistant");
            departement.setText("inexistant");
            filliere.setText("inexistant");
            module.setText("inexistant");
            specialite.setText("inexistant");
        }
    }

}
