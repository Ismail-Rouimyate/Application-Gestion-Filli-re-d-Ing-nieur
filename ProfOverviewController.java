package sample;


import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
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
    public Stage seanceStage = new Stage();

    @FXML
    private TextField semainefield;

    private int semaine;

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
    private Label erreurSemaine;
    @FXML
    private Button seance1;
    @FXML
    private Button seance2;
    @FXML
    private Button seance3;
    @FXML
    private Button seance4;
    @FXML
    private Button seance5;
    @FXML
    private Button seance6;
    @FXML
    private Button seance7;
    @FXML
    private Button seance8;
    @FXML
    private Button seance9;
    @FXML
    private Button seance10;
    @FXML
    private Button seance11;
    @FXML
    private Button seance12;
    @FXML
    private Button seance13;
    @FXML
    private Button seance14;
    @FXML
    private Button seance15;
    @FXML
    private Button seance16;
    @FXML
    private Button seance17;
    @FXML
    private Button seance18;
    @FXML
    private Button seance19;
    @FXML
    private Button seance20;
    @FXML
    private Button seance21;
    @FXML
    private Button seance22;
    @FXML
    private Button seance23;
    @FXML
    private Button seance24;

    @FXML
    private Label groupe1;
    @FXML
    private Label groupe2;
    @FXML
    private Label groupe3;
    @FXML
    private Label groupe4;
    @FXML
    private Label groupe5;
    @FXML
    private Label groupe6;
    @FXML
    private Label groupe7;
    @FXML
    private Label groupe8;
    @FXML
    private Label groupe9;
    @FXML
    private Label groupe10;
    @FXML
    private Label groupe11;
    @FXML
    private Label groupe12;
    @FXML
    private Label groupe13;
    @FXML
    private Label groupe14;
    @FXML
    private Label groupe15;
    @FXML
    private Label groupe16;
    @FXML
    private Label groupe17;
    @FXML
    private Label groupe18;
    @FXML
    private Label groupe19;
    @FXML
    private Label groupe20;
    @FXML
    private Label groupe21;
    @FXML
    private Label groupe22;
    @FXML
    private Label groupe23;
    @FXML
    private Label groupe24;

    @FXML
    private Label salle1;
    @FXML
    private Label salle2;
    @FXML
    private Label salle3;
    @FXML
    private Label salle4;
    @FXML
    private Label salle5;
    @FXML
    private Label salle6;
    @FXML
    private Label salle7;
    @FXML
    private Label salle8;
    @FXML
    private Label salle9;
    @FXML
    private Label salle10;
    @FXML
    private Label salle11;
    @FXML
    private Label salle12;
    @FXML
    private Label salle13;
    @FXML
    private Label salle14;
    @FXML
    private Label salle15;
    @FXML
    private Label salle16;
    @FXML
    private Label salle17;
    @FXML
    private Label salle18;
    @FXML
    private Label salle19;
    @FXML
    private Label salle20;
    @FXML
    private Label salle21;
    @FXML
    private Label salle22;
    @FXML
    private Label salle23;
    @FXML
    private Label salle24;




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
    public void setSemaine(int sem){
        this.semaine = sem;
    }

    @FXML
    public void handleSemaine(){
        System.out.print(semainefield.getText());
            try {
                if ((Integer.parseInt(semainefield.getText())) < 53 && (Integer.parseInt(semainefield.getText())) > 0) {
                    erreurSemaine.setText("");
                    this.setSemaine(Integer.parseInt(semainefield.getText()));
                    setElements();
                    setGroupes();
                    setSalles();
                } else {
                    erreurSemaine.setText("Une semaine ne peut être qu'entre 1 et 52");
                }
            }catch(NumberFormatException e){
                erreurSemaine.setText("Vous devez entrer un nombre valide");
            }
    }

    @FXML
    public void handlePlus(){
        semainefield.setText(Integer.toString(semaine+1));
        semaine++;
        handleSemaine();
    }

    @FXML
    public void handleMoins(){
        semainefield.setText(Integer.toString(semaine-1));
        semaine--;
        handleSemaine();
    }

    public String getNomSeance(int numSemaine, int numSeance){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT intitule FROM element_module WHERE id_elem =(SELECT id_elem FROM seance WHERE id_planing = ? AND id_prof = ? AND  nb_seance = ?)");
            statement.setString(1,Integer.toString(numSemaine));
            statement.setString(2,Integer.toString(prof.getId()));
            statement.setString(3,Integer.toString(numSeance));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getString("intitule");
            }
        }catch (Exception e){
            e.printStackTrace();
            return "vide";
        }
        return "vide";
    }

    public void setElements(){
        seance1.setText(getNomSeance(semaine,1));
        seance2.setText(getNomSeance(semaine,2));
        seance3.setText(getNomSeance(semaine,3));
        seance4.setText(getNomSeance(semaine,4));
        seance5.setText(getNomSeance(semaine,5));
        seance6.setText(getNomSeance(semaine,6));
        seance7.setText(getNomSeance(semaine,7));
        seance8.setText(getNomSeance(semaine,8));
        seance9.setText(getNomSeance(semaine,9));
        seance10.setText(getNomSeance(semaine,10));
        seance11.setText(getNomSeance(semaine,11));
        seance12.setText(getNomSeance(semaine,12));
        seance13.setText(getNomSeance(semaine,13));
        seance14.setText(getNomSeance(semaine,14));
        seance15.setText(getNomSeance(semaine,15));
        seance16.setText(getNomSeance(semaine,16));
        seance17.setText(getNomSeance(semaine,17));
        seance18.setText(getNomSeance(semaine,18));
        seance19.setText(getNomSeance(semaine,19));
        seance20.setText(getNomSeance(semaine,20));
        seance21.setText(getNomSeance(semaine,21));
        seance22.setText(getNomSeance(semaine,22));
        seance23.setText(getNomSeance(semaine,23));
        seance24.setText(getNomSeance(semaine,24));
    }

    public String getGroupe(int numSemaine, int numSeance){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT groupe FROM seance WHERE id_planing = ? AND id_prof = ? AND  nb_seance = ?");
            statement.setString(1,Integer.toString(numSemaine));
            statement.setString(2,Integer.toString(prof.getId()));
            statement.setString(3,Integer.toString(numSeance));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getString("groupe");
            }
        }catch (Exception e){
            e.printStackTrace();
            return "vide";
        }
        return "vide";
    }

    public void setGroupes(){
        groupe1.setText(getGroupe(semaine,1));
        groupe2.setText(getGroupe(semaine,2));
        groupe3.setText(getGroupe(semaine,3));
        groupe4.setText(getGroupe(semaine,4));
        groupe5.setText(getGroupe(semaine,5));
        groupe6.setText(getGroupe(semaine,6));
        groupe7.setText(getGroupe(semaine,7));
        groupe8.setText(getGroupe(semaine,8));
        groupe9.setText(getGroupe(semaine,9));
        groupe10.setText(getGroupe(semaine,10));
        groupe11.setText(getGroupe(semaine,11));
        groupe12.setText(getGroupe(semaine,12));
        groupe13.setText(getGroupe(semaine,13));
        groupe14.setText(getGroupe(semaine,14));
        groupe15.setText(getGroupe(semaine,15));
        groupe16.setText(getGroupe(semaine,16));
        groupe17.setText(getGroupe(semaine,17));
        groupe18.setText(getGroupe(semaine,18));
        groupe19.setText(getGroupe(semaine,19));
        groupe20.setText(getGroupe(semaine,20));
        groupe21.setText(getGroupe(semaine,21));
        groupe22.setText(getGroupe(semaine,22));
        groupe23.setText(getGroupe(semaine,23));
        groupe24.setText(getGroupe(semaine,24));
    }

    public String getSalle(int numSemaine, int numSeance){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT salle FROM seance WHERE id_planing = ? AND id_prof = ? AND  nb_seance = ?");
            statement.setString(1,Integer.toString(numSemaine));
            statement.setString(2,Integer.toString(prof.getId()));
            statement.setString(3,Integer.toString(numSeance));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getString("salle");
            }
        }catch (Exception e){
            e.printStackTrace();
            return "vide";
        }
        return "vide";
    }

    public void setSalles(){
        salle1.setText(getSalle(semaine,1));
        salle2.setText(getSalle(semaine,2));
        salle3.setText(getSalle(semaine,3));
        salle4.setText(getSalle(semaine,4));
        salle5.setText(getSalle(semaine,5));
        salle6.setText(getSalle(semaine,6));
        salle7.setText(getSalle(semaine,7));
        salle8.setText(getSalle(semaine,8));
        salle9.setText(getSalle(semaine,9));
        salle10.setText(getSalle(semaine,10));
        salle11.setText(getSalle(semaine,11));
        salle12.setText(getSalle(semaine,12));
        salle13.setText(getSalle(semaine,13));
        salle14.setText(getSalle(semaine,14));
        salle15.setText(getSalle(semaine,15));
        salle16.setText(getSalle(semaine,16));
        salle17.setText(getSalle(semaine,17));
        salle18.setText(getSalle(semaine,18));
        salle19.setText(getSalle(semaine,19));
        salle20.setText(getSalle(semaine,20));
        salle21.setText(getSalle(semaine,21));
        salle22.setText(getSalle(semaine,22));
        salle23.setText(getSalle(semaine,23));
        salle24.setText(getSalle(semaine,24));
    }

    public void showSeanceInfo(int numSeance){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((ProfOverviewController.class.getResource("InfoSeance.fxml")));
            AnchorPane infoSeance = (AnchorPane) loader.load();
            try{
                seanceStage.setTitle("Informations");
                seanceStage.initOwner(profOverviewStage);
                Scene scene = new Scene(infoSeance);
                seanceStage.setScene(scene);
            }catch (IllegalStateException e){
                Scene scene = new Scene(infoSeance);
                seanceStage.setScene(scene);
            }
            InfoSeanceController controller = loader.getController();
            controller.initialize();
            controller.setProfOverviewController(this, numSeance);
            seanceStage.showAndWait();
        }catch (IOException e){
            //Load info Seance
            e.printStackTrace();
        }
    }

    public int getSemaine(){
        return semaine;
    }
    @FXML
    public void showSeanceInfo1(){
        showSeanceInfo(1);
    }
    @FXML
    public void showSeanceInfo2(){
        showSeanceInfo(2);
    }
    @FXML
    public void showSeanceInfo3(){
        showSeanceInfo(3);
    }
    @FXML
    public void showSeanceInfo4(){
        showSeanceInfo(4);
    }
    @FXML
    public void showSeanceInfo5(){
        showSeanceInfo(5);
    }
    @FXML
    public void showSeanceInfo6(){
        showSeanceInfo(6);
    }
    @FXML
    public void showSeanceInfo7(){
        showSeanceInfo(7);
    }
    @FXML
    public void showSeanceInfo8(){
        showSeanceInfo(8);
    }
    @FXML
    public void showSeanceInfo9(){
        showSeanceInfo(9);
    }
    @FXML
    public void showSeanceInfo10(){
        showSeanceInfo(10);
    }
    @FXML
    public void showSeanceInfo11(){
        showSeanceInfo(11);
    }
    @FXML
    public void showSeanceInfo12(){
        showSeanceInfo(12);
    }
    @FXML
    public void showSeanceInfo13(){
        showSeanceInfo(13);
    }
    @FXML
    public void showSeanceInfo14(){
        showSeanceInfo(14);
    }
    @FXML
    public void showSeanceInfo15(){
        showSeanceInfo(15);
    }
    @FXML
    public void showSeanceInfo16(){
        showSeanceInfo(16);
    }
    @FXML
    public void showSeanceInfo17(){
        showSeanceInfo(17);
    }
    @FXML
    public void showSeanceInfo18(){
        showSeanceInfo(18);
    }
    @FXML
    public void showSeanceInfo19(){
        showSeanceInfo(19);
    }
    @FXML
    public void showSeanceInfo20(){
        showSeanceInfo(20);
    }
    @FXML
    public void showSeanceInfo21(){
        showSeanceInfo(21);
    }
    @FXML
    public void showSeanceInfo22(){
        showSeanceInfo(22);
    }
    @FXML
    public void showSeanceInfo23(){
        showSeanceInfo(23);
    }
    @FXML
    public void showSeanceInfo24(){
        showSeanceInfo(24);
    }



}
