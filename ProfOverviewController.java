package sample;


import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

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
    @FXML
    private TextField semaine;

    public void setProf(Professeur prof){
        this.prof = prof;
    }

    public Professeur getProf(){
        return this.prof;
    }

    /*public void initialize(){
        professeur.setText(prof.getNom()+" "+prof.getPrenom());
        //departement.setText(prof.departementProperty().toString());
    }*/

    public void setProfOverviewStage(Stage profOverviewStage){
        this.profOverviewStage = profOverviewStage;

    }

}
