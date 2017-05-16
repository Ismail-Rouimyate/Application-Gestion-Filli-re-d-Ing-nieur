import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import sun.applet.Main;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

    // data as observable list
    private ObservableList<Professeur> professeurData = FXCollections.observableArrayList();
    private ObservableList<Module> moduleData = FXCollections.observableArrayList();
    private ObservableList<Filliere> filliereData = FXCollections.observableArrayList();
    private ObservableList<Departement> departementData = FXCollections.observableArrayList();
    private ObservableList<ElementModule> elementModuleData = FXCollections.observableArrayList();
    private ObservableList<Semestre> semestreData = FXCollections.observableArrayList();
    private ObservableList<Seance> seanceData = FXCollections.observableArrayList();
    private ObservableList<Planing> planingData = FXCollections.observableArrayList();

    // Constructor Professeur
    public MainApp(Professeur prof){
        professeurData.add(prof);
    }

    // Constructor Module
    public MainApp(Module module){
        moduleData.add(module);
    }

    // Constructor Filliere
    public MainApp(Filliere filliere){
        filliereData.add(filliere);
    }

    // Constructor Departement
    public MainApp(Departement departement){
        departementData.add(departement);
    }

    // Constructor ElementModule
    public MainApp(ElementModule element){
        elementModuleData.add(element);
    }

    // Constructor Semestre
    public MainApp(Semestre semestre){
        semestreData.add(semestre);
    }

    // Constructor Seance
    public MainApp(Seance seance){
        seanceData.add(seance);
    }

    // Constructor Planing
    public MainApp(Planing planing){
        planingData.add(planing);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Planing ENSIAS");

        initRootLayout();

        showLogin();
    }

    // initializes root layout
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Shows the login scene inside the root layout
    public void showLogin(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // return the main stage

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
