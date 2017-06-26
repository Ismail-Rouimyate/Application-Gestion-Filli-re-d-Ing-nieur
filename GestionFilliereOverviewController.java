package sample;


import javafx.collections.FXCollections;
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

public class GestionFilliereOverviewController {

    @FXML
    private TableView<Filliere> filiereTable = new TableView<>();
    @FXML
    private TableColumn<Filliere, Integer> idColumn;
    @FXML
    private TableColumn<Filliere, String> nomColumn;

    @FXML
    private Label intituleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label profLabel;

    private Stage dialogStage;
    private Stage gestionFilliereStage;

    // reference to the controller of origin
    private AdminOverviewController adminOverviewController;

    public void setGestionFilliereStage(Stage gestionFilliereStage) { this.gestionFilliereStage = gestionFilliereStage; }

    /**
     * The constructor
     * called before the initialize() method
     */
    public GestionFilliereOverviewController(){

    }

    /**
     * Initializes the controller class
     * automatically called after the fxml file is loaded
     */
    @FXML
    public void initialize(){
        // initialize the filiere table with the two columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());

        // Clear filière details
        showFilliereDetails(null);

        // Listen for the selection changes and show the filière details when changed
        filiereTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFilliereDetails(newValue));
    }

    /**
     * Is called by the controller of origin to
     * give reference back to itself
     */
    public void setAdminOverviewController(AdminOverviewController adminOverviewController){
        this.adminOverviewController = adminOverviewController;

        filiereTable.setItems(adminOverviewController.getFilliereData());
    }

    private void showFilliereDetails(Filliere filliere){
        if (filliere != null){
            // fills the labels with the info
            intituleLabel.setText(filliere.getNom());
            dateLabel.setText(filliere.getDate());
            profLabel.setText(filliere.getNomProf()+" "+filliere.getPrenomProf());
        }
        else {
            // filliere is null remove all text
            intituleLabel.setText("");
            dateLabel.setText("");
            profLabel.setText("");
        }
    }

    @FXML
    private void handleDelete(){
        int selectedIndex = filiereTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            ObservableList<Filliere> filiereEdit = filiereTable.getItems();
            deleteFromDatabase(filiereEdit.get(selectedIndex));
            filiereTable.getItems().remove(selectedIndex);
            filiereTable.refresh();
        }
        else{
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Filière selectionnée");
            alert.setContentText("Veuillez selectionner une filière sur la table");

            alert.showAndWait();
        }
    }

    private void deleteFromDatabase(Filliere filliere){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("DELETE FROM filiere WHERE id_filiere = ?");
            statement.setString(1,Integer.toString(filliere.getId_filliere()));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewFiliere(){
        Filliere tempFiliere = new Filliere();
        boolean okClicked = showEditFiliere(tempFiliere);
        if (okClicked){
            filiereTable.refresh();
        }
    }

    @FXML
    private void handleEditFiliere(){
        Filliere selectedFiliere = filiereTable.getSelectionModel().getSelectedItem();
        if (selectedFiliere != null){
            boolean okClicked = showEditFiliere(selectedFiliere);
            if (okClicked){
                filiereTable.refresh();
                showFilliereDetails(selectedFiliere);
            }
        }
        else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Filière selectionnée");
            alert.setContentText("Veuillez selectionner une filière sur la table");

            alert.showAndWait();
        }
    }

    public boolean showEditFiliere(Filliere filliere){
        try {
            // Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GestionProfOverviewController.class.getResource("FilliereEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Filiere");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(gestionFilliereStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // set the prof into the controller
            FilliereEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFilliere(filliere);

            dialogStage.showAndWait();
            return controller.IsOkClicked();

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
