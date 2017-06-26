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


public class GestionProfOverviewController {

    @FXML
    private TableView<Professeur> professeurTable = new TableView<>();
    @FXML
    private TableColumn<Professeur, String> nomColumn;
    @FXML
    private TableColumn<Professeur, String> prenomColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label specialiteLabel;
    @FXML
    private Label departementLabel;

    @FXML
    private ListView<String> moduleMenu = new ListView<>();
    @FXML
    private ListView<String> filiereMenu = new ListView<>();

    private Stage dialogStage;
    private Stage gestionProfStage;


    // reference to the controller of origin
    private AdminOverviewController adminOverviewController;



    public void setGestionProfStage(Stage gestionProfStage){
        this.gestionProfStage = gestionProfStage;
    }
    /**
     * The constructor
     * called before the initialize() method
     */
    public GestionProfOverviewController(){

    }

    /**
     * Initializes the controller class
     * automatically called after the fxml file is loaded
     */
    @FXML
    public void initialize(){
        // initialize the prof table with the two columns
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());

        // Clear person details
        showProfDetails(null);

        // Listen for the selection changes and show the person details when changed
        professeurTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProfDetails(newValue));

    }

    /**
     * Is called by the controller of origin to
     * give reference back to itself
     *
     * @param prof
     */
    private void showProfDetails(Professeur prof){
        if(prof != null){
            // fills the labels with the info
            idLabel.setText(Integer.toString(prof.getId()));
            emailLabel.setText(prof.getEmail());
            nomLabel.setText(prof.getNom());
            prenomLabel.setText(prof.getPrenom());
            specialiteLabel.setText(prof.getSpecialite());
            departementLabel.setText(prof.getDepratement());



            ObservableList<String> modules = FXCollections.observableArrayList();
            ObservableList<String> filieres = FXCollections.observableArrayList();
            modules.clear();
            filieres.clear();
            prof.getModule().forEach((Module module) -> modules.add(module.getNom()));
            prof.getFilliere().forEach((Filliere filiere) -> filieres.add(filiere.getNom()));
            moduleMenu.setItems(modules);
            filiereMenu.setItems(filieres);

            /*prof.getModule().forEach((Module module) -> moduleMenu.getItems().add(module.getNom()));
            prof.getFilliere().forEach((Filliere filliere) -> filiereMenu.getItems().add(filliere.getNom()));*/
        }
        else{
            // prof is null remove all text
            idLabel.setText("");
            emailLabel.setText("");
            nomLabel.setText("");
            prenomLabel.setText("");
            specialiteLabel.setText("");
            departementLabel.setText("");
        }
    }

    public void setAdminOverviewController(AdminOverviewController adminOverviewController){
        this.adminOverviewController = adminOverviewController;

        professeurTable.setItems(adminOverviewController.getProfData());
    }


    @FXML
    private void handledelete(){
        int selectedIndex = professeurTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            // Listen for the selection changes and show the person details when changed
            ObservableList<Professeur> profedit = professeurTable.getItems();
            deleteFromDatabase(profedit.get(selectedIndex));
            /*professeurTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> deleteFromDatabase(newValue));*/
            professeurTable.getItems().remove(selectedIndex);
            professeurTable.refresh();

        }
        else{
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminOverviewController.getAdminOverviewStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de Professeur selectionné");
            alert.setContentText("Veuillez selectionner un professeur sur la table");

            alert.showAndWait();
        }
    }

    private void deleteFromDatabase(Professeur prof){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("UPDATE filiere SET id_prof = 0 WHERE id_prof = ?");
            statement.setString(1,Integer.toString(prof.getId()));
            PreparedStatement statement1 = con.prepareStatement("UPDATE module SET id_prof = 0 WHERE id_prof = ?");
            statement1.setString(1, Integer.toString(prof.getId()));
            PreparedStatement statement2 = con.prepareStatement("DELETE FROM professeur WHERE id_prof = ?");
            statement2.setString(1,Integer.toString(prof.getId()));
            statement.executeUpdate();
            statement1.executeUpdate();
            statement2.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewProf(){
        Professeur tempProf = new Professeur();
        boolean okClicked = showProfEditDialog(tempProf);
        if (okClicked){
            professeurTable.refresh();
        }
    }


    @FXML
    private void handleEditProf(){
        Professeur selectedProf = professeurTable.getSelectionModel().getSelectedItem();
        if (selectedProf != null){
            boolean okClicked = showProfEditDialog(selectedProf);
            if (okClicked){
                professeurTable.refresh();
                showProfDetails(selectedProf);
            }
        }
        else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(gestionProfStage);
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Pas de professeur selectionné");
            alert.setContentText("Veuillez sélectionner un professeur de la table");

            alert.showAndWait();
        }
    }


    public boolean showProfEditDialog(Professeur prof){
        try {
            // Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GestionProfOverviewController.class.getResource("ProfEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Professeur");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(gestionProfStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // set the prof into the controller
            ProfEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProfesseur(prof);

            dialogStage.showAndWait();
            return controller.IsOkClicked();

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
