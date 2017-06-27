package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GestionPlaningOverviewController {
    @FXML
    private  TableView<Seance> seanceTable = new TableView<>();
    @FXML
    private TableColumn<Seance, Integer> semaineColumn;
    @FXML
    private TableColumn<Seance,String> dateColumn;
    @FXML
    private TableColumn<Seance, String> heureColumn;
    @FXML
    private TableColumn<Seance, String> groupeColumn;
    @FXML
    private TableColumn<Seance, String> nomProfColumn;
    @FXML
    private TableColumn<Seance, String> prenomProfColumn;
    @FXML
    private TableColumn<Seance, String> elemColumn;
    @FXML
    private TableColumn<Seance, String> typeColumn;
    @FXML
    private TableColumn<Seance, String> salleColumn;

    private Stage dialogStage;
    private Stage gestionPlaningStage;

    // reference to the controller of origin
    private AdminOverviewController adminOverviewController;

    public void setGestionPlaningStage (Stage gestionPlaningStage){ this.gestionPlaningStage = gestionPlaningStage; }

    /**
     * The constructor
     * called before the initialize() method
     */
    public GestionPlaningOverviewController(){

    }

    /**
     * Initializes the controller class
     * automatically called after the fxml file is loaded
     */
    @FXML
    public void initialize(){
        // initializes the columns
        semaineColumn.setCellValueFactory(cellData -> cellData.getValue().numPlaningProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        heureColumn.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
        groupeColumn.setCellValueFactory(cellData -> cellData.getValue().groupeProperty());
        nomProfColumn.setCellValueFactory(cellData -> cellData.getValue().nomProfProperty());
        prenomProfColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProfProperty());
        elemColumn.setCellValueFactory(cellData -> cellData.getValue().elementProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        salleColumn.setCellValueFactory(cellData -> cellData.getValue().salleProperty());
    }

    /**
     * Is called by the controller of origin to
     * give reference back to itself
     */
    public void setAdminOverviewController(AdminOverviewController adminOverviewController){
        this.adminOverviewController = adminOverviewController;

        seanceTable.setItems(adminOverviewController.getSeanceData());
    }

    @FXML
    private void handleDelete(){
        int selectedIndex = seanceTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            ObservableList<Seance> seanceEdit = seanceTable.getItems();
            deleteFromDatabase(seanceEdit.get(selectedIndex));
            seanceTable.getItems().remove(selectedIndex);
            seanceTable.refresh();
        }
        else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Séance selectionnée");
            alert.setContentText("Veuillez selectionner une seance sur la table");

            alert.showAndWait();
        }
    }

    private void deleteFromDatabase(Seance seance){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("DELETE FROM seance WHERE id_seance = ?");
            statement.setString(1,Integer.toString(seance.getIdSeance()));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewSeance(){
        Seance tempSeance = new Seance();
        boolean okClicked = showEditSeance(tempSeance);
        if (okClicked){
            seanceTable.refresh();
        }
        seanceTable.refresh();
    }

    @FXML
    private void handleEditSeance(){
        Seance selectedSeance = seanceTable.getSelectionModel().getSelectedItem();
        if (selectedSeance != null){
            boolean okClicked = showEditSeance(selectedSeance);
            if (okClicked){
                seanceTable.refresh();
            }
        }
        else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Séance selectionnée");
            alert.setContentText("Veuillez selectionner une seance sur la table");

            alert.showAndWait();
        }
        seanceTable.refresh();
    }

    public boolean showEditSeance(Seance seance){
        try {
            // Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GestionPlaningOverviewController.class.getResource("PlaningEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Seance");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(gestionPlaningStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // set the filiere into the controller
            PlaningEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSeance(seance);

            dialogStage.showAndWait();
            return controller.IsOkClicked();

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }





}
