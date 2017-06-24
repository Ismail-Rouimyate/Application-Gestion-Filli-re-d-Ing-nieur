package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminOverviewController {

    private AdminLoginController adminLoginController;

    private Stage adminOverviewStage;
    private Stage gestionProfStage;
    private Stage gestionModuleStage;
    private Stage gestionFilliereStage;
    private Stage statistiqueStage;
    private Stage gestionPlaningStage;


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
        if(output == true){ adminLoginController.dialogStage.close(); }

    }

}
