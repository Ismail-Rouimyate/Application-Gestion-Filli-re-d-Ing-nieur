package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class InfoSeanceController {
    @FXML
    private Label dateLabel;
    @FXML
    private Label heureLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label elementLabel;
    @FXML
    private Label salleLabel;
    @FXML
    private Label groupeLabel;

    private String profId;
    private int semaine;
    private  int numSeance;


    // refrence to the controller of origin
    private ProfOverviewController profOverviewController;

    /**
     * The contructor
     * called before the initialize() method
     */
    public InfoSeanceController(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file is loaded
     */
    @FXML
    public void initialize(){
        // initializes the labels
        dateLabel.setText("");
        heureLabel.setText("");
        typeLabel.setText("");
        elementLabel.setText("");
        salleLabel.setText("");
        groupeLabel.setText("");
    }

    /**
     * Called by the controller of origin
     * to give reference back to itself
     * @param profOverviewController
     */
    public void setProfOverviewController(ProfOverviewController profOverviewController, int numSeance){
        this.profOverviewController = profOverviewController;

        this.profId = profOverviewController.getProfId();
        this.semaine = profOverviewController.getSemaine();
        this.numSeance = numSeance;

        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM seance WHERE id_planing = ? AND id_prof = ? AND  nb_seance = ?");
            statement.setString(1,Integer.toString(semaine));
            statement.setString(2,profId);
            statement.setString(3,Integer.toString(numSeance));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println(result.getString("date"));
                dateLabel.setText(result.getString("date"));
                heureLabel.setText(result.getString("heure_debut"));
                typeLabel.setText(result.getString("type"));
                salleLabel.setText(result.getString("salle"));
                groupeLabel.setText(result.getString("groupe"));
            }
            result.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT intitule FROM element_module WHERE id_elem =(SELECT id_elem FROM seance WHERE id_planing = ? AND id_prof = ? AND  nb_seance = ?)");
            statement.setString(1,Integer.toString(semaine));
            statement.setString(2,profId);
            statement.setString(3,Integer.toString(numSeance));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                elementLabel.setText(result.getString("intitule"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
