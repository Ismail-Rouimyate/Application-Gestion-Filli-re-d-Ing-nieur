package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InfoModuleController {
    @FXML
    private TableView<Module> moduleTable;
    @FXML
    private TableColumn<Module, String> intituleColumn;
    @FXML
    private TableColumn<Module, Integer> vhcColumn;
    @FXML
    private TableColumn<Module, Integer> vhtdColumn;
    @FXML
    private TableColumn<Module, Integer> vhtpColumn;
    @FXML
    private TableColumn<Module, Integer> vhapColumn;
    @FXML
    private TableColumn<Module, String> elementColumn;

    private String profId;

    final ObservableList<String> listElem = FXCollections.observableArrayList();


    final ObservableList<Module> moduleData = FXCollections.observableArrayList();

    // reference to the controller of origin
    private ProfOverviewController profOverviewController;

    /**
     * The contructor
     * called before the initialize() method
     */
    public InfoModuleController(){
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXMl file had been loaded
     */
    @FXML
    public void initialize(){
        // initialize the table with the infos of module
        intituleColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());
        vhcColumn.setCellValueFactory(cellData -> cellData.getValue().vhcProperty().asObject());
        vhtdColumn.setCellValueFactory(cellData -> cellData.getValue().vhtdProperty().asObject());
        vhtpColumn.setCellValueFactory(cellData -> cellData.getValue().vhtpProperty().asObject());
        vhapColumn.setCellValueFactory(cellData -> cellData.getValue().vhapProperty().asObject());
    }

    /**
     * Called by the controller of origin
     * to give refrence back to itself
     * @param profOverviewController .
     */
    public void setProfOverviewController(ProfOverviewController profOverviewController){
        this.profOverviewController = profOverviewController;

        profId = profOverviewController.getProfId();
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Module WHERE id_prof = ?");
            statement.setString(1,profId);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                moduleData.add(new Module(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getInt(4),
                        result.getInt(5),
                        result.getInt(6),
                        listElem.get(0), listElem.get(1), listElem.get(2), "", ""
                ));
                moduleTable.setItems(moduleData);
            }
            result.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
