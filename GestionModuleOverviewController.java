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

public class GestionModuleOverviewController {

    @FXML
    private TableView<Module> moduleTable = new TableView<>();
    @FXML
    private TableColumn<Module, Integer> idColumn;
    @FXML
    private TableColumn<Module, String> intituleColumn;

    @FXML
    private Label intituleLabel;
    @FXML
    private Label vhcLabel;
    @FXML
    private Label vhtdLabel;
    @FXML
    private Label vhtpLabel;
    @FXML
    private Label vhapLabel;
    @FXML
    private Label element1Label;
    @FXML
    private Label element2Label;
    @FXML
    private Label element3Label;
    @FXML
    private Label profLabel;

    private Stage dialogStage;
    private Stage gestionModuleStage;

    // reference to the controller of origin
    private AdminOverviewController adminOverviewController;

    public void setGestionModuleStage (Stage gestionModuleStage) { this.gestionModuleStage = gestionModuleStage; }

    /**
     * The constructor
     * called before the initialize() method
     */
    public GestionModuleOverviewController(){

    }

    /**
     * Initializes the controller class
     * automatically called after the fxml file is loaded
     */
    @FXML
    public void initialize(){
        // initialize the module with two columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        intituleColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());

        // clear module details
        showModuleDetails(null);

        // Listen to the selection changes and show the module details when changed
        moduleTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showModuleDetails(newValue));
    }

    /**
     * Is called by the controller of origin to
     * give reference back to itself
     */
    public void setAdminOverviewController(AdminOverviewController adminOverviewController){
        this.adminOverviewController = adminOverviewController;

        moduleTable.setItems(adminOverviewController.getModuleData());
    }

    private void showModuleDetails(Module module){
        if (module != null){
            // fills the details with info
            intituleLabel.setText(module.getNom());
            vhcLabel.setText(Integer.toString(module.getvhc()));
            vhtdLabel.setText(Integer.toString(module.getvhtd()));
            vhtpLabel.setText(Integer.toString(module.getvhtp()));
            vhapLabel.setText(Integer.toString(module.getvhap()));
            try {
                element1Label.setText(module.listElemProperty().get(0));
            }catch (IndexOutOfBoundsException e){

            }
            try {
                element2Label.setText(module.listElemProperty().get(1));
            }catch (IndexOutOfBoundsException e){

            }
            try {
                element3Label.setText(module.listElemProperty().get(2));
            }catch (IndexOutOfBoundsException e){

            }
            profLabel.setText(module.getNomProf()+" "+module.getPrenomProf());
        }
        else {
            // module is null remove all text
            intituleLabel.setText("");
            vhcLabel.setText("");
            vhtdLabel.setText("");
            vhtpLabel.setText("");
            vhapLabel.setText("");
            element1Label.setText("");
            element2Label.setText("");
            element3Label.setText("");
            profLabel.setText("");
        }
    }

    @FXML
    private void handleDelete(){
        int selectedIndex = moduleTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            ObservableList<Module> moduleEdit = moduleTable.getItems();
            deleteFromDatabase(moduleEdit.get(selectedIndex));
            moduleTable.getItems().remove(selectedIndex);
            moduleTable.refresh();
        }
        else{
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Module selectionné");
            alert.setContentText("Veuillez selectionner un module sur la table");

            alert.showAndWait();
        }
    }

    private void deleteFromDatabase(Module module){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("DELETE FROM module WHERE id_module = ?");
            statement.setString(1,Integer.toString(module.getId()));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNew(){
        Module tempModule = new Module();
        boolean okClicked = showEditModule(tempModule);
        if(okClicked){
            moduleTable.refresh();
        }
    }

    @FXML
    private void handleEdit(){
        Module selectedModule = moduleTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null){
            boolean okClicked = showEditModule(selectedModule);
            if (okClicked){
                moduleTable.refresh();
                showModuleDetails(selectedModule);
            }
        }
        else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Module selectionné");
            alert.setContentText("Veuillez selectionner un module sur la table");

            alert.showAndWait();
        }
    }

    public boolean showEditModule(Module module){
        try{
            // Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GestionModuleOverviewController.class.getResource("ModuleEditdialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Module");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(gestionModuleStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // set the module into the controller
            ModuleEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setModule(module);

            dialogStage.showAndWait();
            return controller.IsOkClicked();

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
