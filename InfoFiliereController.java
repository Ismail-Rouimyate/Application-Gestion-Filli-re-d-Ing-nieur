package sample;


import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InfoFiliereController {
    @FXML
    private TableView<Filliere>  filiereTable;
    @FXML
    private TableColumn<Filliere, String > intituleColumn;
    @FXML
    private TableColumn<Filliere, String> dateColumn;
    @FXML
    private TableColumn<Filliere, Integer> idColumn;

    private String profId;

    final ObservableList<Filliere> filiereData = FXCollections.observableArrayList();


    // Reference to the controller of origin
    private ProfOverviewController profOverviewController;

    /**
     * The constructor
     * called before the initialize() method
     */
    public InfoFiliereController(){
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after FXML file had been loaded
     */
    @FXML
    public void initialize(){
        // Initialize the table with the infos of filière
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        intituleColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateaccrProperty());
    }

    /**
     * Called by the controller of origin
     * to give reference back to itself
     * @param profOverviewController
     */
    public void setProfOverviewController(ProfOverviewController profOverviewController){
        this.profOverviewController = profOverviewController;

        profId = profOverviewController.getProfId();
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Filiere WHERE id_prof = ? ");
            statement.setString(1,profId);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                filiereData.add(new Filliere(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3)
                ));
                filiereTable.setItems(filiereData);
            }
            result.close();
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
