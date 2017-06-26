package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminOverviewController {

    private AdminLoginController adminLoginController;

    private ObservableList<Professeur> profData = FXCollections.observableArrayList();
    private ObservableList<Filliere> filliereData = FXCollections.observableArrayList();

    private Stage adminOverviewStage = new Stage();
    private Stage gestionProfStage = new Stage();
    private Stage gestionModuleStage = new Stage();
    private Stage gestionFilliereStage = new Stage();
    private Stage statistiqueStage = new Stage();
    private Stage gestionPlaningStage = new Stage();


    @FXML
    public void initialize(){

    }

    /**
     * Is called by the constructor of origin to give reference back to itself
     *
     * @param adminLoginController
     */
    public void setAdminLoginController(AdminLoginController adminLoginController){
        this.adminLoginController = adminLoginController;
    }

    public void setAdminOverviewStage(Stage adminOverviewStage){
        this.adminOverviewStage = adminOverviewStage;
    }

    public void handleDisconnection(){

        boolean output = AlertBoxController.display("Fermeture","Etes vous sur de vouloir vous déconnecter");
        if(output){ adminLoginController.dialogStage.close(); }

    }

    /**
     * shows the prof overview
     */
    public void showGestionProf(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminOverviewController.class.getResource("GestionProfOverview.fxml"));
            AnchorPane gestionProf = (AnchorPane) loader.load();
            try{
                gestionProfStage.setTitle("Gestion des professeurs");
                gestionProfStage.initOwner(adminOverviewStage);
                Scene scene = new Scene(gestionProf);
                gestionProfStage.setScene(scene);
            }catch (IllegalStateException e){
                Scene scene = new Scene(gestionProf);
                gestionProfStage.setScene(scene);
            }

            // give the controller access to the AdminOverviewController
            GestionProfOverviewController controller = loader.getController();
            controller.setGestionProfStage(gestionProfStage);
            controller.setAdminOverviewController(this);
            controller.initialize();
            gestionProfStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ObservableList<Professeur> getProfData(){
        profData.clear();
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Professeur");
            ResultSet result = statement.executeQuery();


            ObservableList<Module> moduleData = FXCollections.observableArrayList();
            ObservableList<Filliere> filiereData = FXCollections.observableArrayList();
            while (result.next()){
                PreparedStatement statement1 = con.prepareStatement("SELECT * FROM filiere WHERE id_prof = ?");
                statement1.setString(1,Integer.toString(result.getInt(1)));
                ResultSet result1 = statement1.executeQuery();
                while (result1.next()){
                    Filliere filliere = new Filliere(result1.getInt(1),result1.getString(2),result1.getString(3), "","");
                    filiereData.add(filliere);
                }
                PreparedStatement statement2 = con.prepareStatement("SELECT * FROM module WHERE id_prof = ?");
                statement2.setString(1,Integer.toString(result.getInt(1)));
                ResultSet result2 = statement2.executeQuery();
                while (result2.next()){
                    Module module = new Module(result2.getInt(1),result2.getString(2),result2.getInt(3),result2.getInt(4),
                                                result2.getInt(5),result2.getInt(6),null);
                    moduleData.add(module);
                }

                Professeur prof = new Professeur(result.getInt(1),
                                                result.getString("email"),
                                                "vide",
                                                result.getString("nom"),
                                                result.getString("prenom"),
                                                result.getString("specialite"),
                                                moduleData,
                                                filiereData,
                                                result.getString("departement"));
                if (prof.getId() != 0) {
                    profData.add(prof);
                }
            }
            result.close();
            return profData;
        }catch (Exception e){
            e.printStackTrace();
        }
        profData.add(new Professeur("erreur", "erreur"));
        return profData;
    }

    public Stage getAdminOverviewStage(){
        return adminOverviewStage;
    }

    public void showGestionFiliere(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminOverviewController.class.getResource("GestionFilliereOverview.fxml"));
            AnchorPane gestionFiliere = (AnchorPane) loader.load();
            try{
                gestionFilliereStage.setTitle("Gestion des filières");
                gestionFilliereStage.initOwner(adminOverviewStage);
                Scene scene = new Scene(gestionFiliere);
                gestionFilliereStage.setScene(scene);
            }catch (IllegalStateException e){
                Scene scene = new Scene(gestionFiliere);
                gestionFilliereStage.setScene(scene);
            }

            // give the controller access to the AdminOverviewController
            GestionFilliereOverviewController controller = loader.getController();
            controller.setGestionFilliereStage(gestionFilliereStage);
            controller.setAdminOverviewController(this);
            controller.initialize();
            gestionFilliereStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ObservableList<Filliere> getFilliereData(){
        filliereData.clear();
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM filiere");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                PreparedStatement statement1 = con.prepareStatement("SELECT nom, prenom FROM professeur WHERE id_prof = ?");
                statement1.setString(1,Integer.toString(result.getInt(4)));
                ResultSet result1 = statement1.executeQuery();
                while (result1.next()){
                    filliereData.add(new Filliere(   result.getInt(1),
                                                     result.getString(2),
                                                     result.getString(3),
                                            result1.getString(1),result1.getString(2)));
                }
            }
            return filliereData;
        }catch (Exception e){
            e.printStackTrace();
        }
        filliereData.add(new Filliere(0,"erreur","erreur","erreur","erreur"));
        return filliereData;
    }

    //TODO : résoudre les erreurs dans le proffestion pour les modules et filières
}
