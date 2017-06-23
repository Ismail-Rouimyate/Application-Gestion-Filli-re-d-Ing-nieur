package sample;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfOverviewController {

    private Stage profOverviewStage;
    private Professeur prof;
    public Stage filiereStage = new Stage();
    public Stage moduleStage = new Stage();

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

    public String getProfId(){
        System.out.print(Integer.toString(prof.getId()));
        return Integer.toString(prof.getId());
    }



    /**
     * Shows the filiere info inside the root layout
     */
    @FXML
    public void showInfoFiliere(){
        try{
            // Load info filiere
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProfOverviewController.class.getResource("InfoFiliere.fxml"));
            AnchorPane infofiliere = (AnchorPane) loader.load();
            try {
                filiereStage.setTitle("Filieres encadrées");
                filiereStage.initOwner(profOverviewStage);
                Scene scene = new Scene(infofiliere);
                filiereStage.setScene(scene);
            }catch (IllegalStateException e){
                Scene scene = new Scene(infofiliere);
                filiereStage.setScene(scene);
            }

            // give the controller access to the profOverviewController
            InfoFiliereController controller = loader.getController();
            controller.setProfOverviewController(this);
            controller.initialize();
            filiereStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show the module info inside the root layout
     */
    @FXML
    public void showInfoModule(){
        try{
            // Load info Module
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((ProfOverviewController.class.getResource("InfoModule.fxml")));
            AnchorPane infomodule = (AnchorPane) loader.load();
            try{
                moduleStage.setTitle(("Modules coordinés"));
                moduleStage.initOwner(profOverviewStage);
                Scene scene = new Scene(infomodule);
                moduleStage.setScene(scene);
            }catch (IllegalStateException e){
                Scene scene = new Scene(infomodule);
                moduleStage.setScene(scene);
            }

            // give the controller access to the profOverviewController
            InfoModuleController controller = loader.getController();
            controller.setProfOverviewController(this);
            controller.initialize();
            moduleStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }




}
