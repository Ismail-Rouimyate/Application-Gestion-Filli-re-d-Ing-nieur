package sample;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProfLoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motDePasseField;
    @FXML
    private Label erreur;

    Stage primaryStage;

    // liste de professeurs en mode observable array list
    private ObservableList<Professeur> professeurData = FXCollections.observableArrayList();



    Professeur profConnecte;

    private Stage profLoginStage;
    private boolean okClicked = false;
    private boolean isProfValide = false;
    public Stage dialogStage = new Stage();

    public void setIsProfValide(boolean what){
        this.isProfValide = what;
    }



    /**
     * Initializes the controller class
     * automatically called after
     * the fxml file loaded
     */
    @FXML
    private void initialize(){
    }

    /**
     * Sets the stage of this dialog
     *
     * @param profLoginStage
     */
    public void setProfLoginStage(Stage profLoginStage){
        this.profLoginStage = profLoginStage;
    }

    /**
     * returns true if login clicked
     * @return okClicked
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * called when user clicks login
     */
    @FXML
    private void handleLogin(){
        isInputValid(emailField.getText(),motDePasseField.getText());

        if (isProfValide){
            profAffichage(quelProf(emailField.getText()));
            okClicked = true;
            profLoginStage.close();
        }
        else{
            erreur.setText("Email ou Mot de passe erronés");
            okClicked = false;
        }
    }

     /**
     * validates the user input
     *
     */
     private void isInputValid(String email, String passe){
         // loops through the observable array list to find a match
         //professeurData.add(new Professeur("test", "test"));
         /**
         *  pour tester la fonction de test
          */
         /*Departement dep = new Departement("departement de test");
         Filliere fil = new Filliere("filliere de test");
         Module mod = new Module("module de test");
         Professeur newProf = new Professeur(1,"test","test","moi","moi","specialite",mod,fil,dep);
         professeurData.add(newProf);*/

         /*professeurData.forEach(professeur -> {
             if(professeur.getEmail().equals(email) && professeur.getMotDePasse().equals(passe) ){
                 setIsProfValide(true);
                 profLoginStage.close();
             }
             else{
                 setIsProfValide(false);
             }
         });*/




         ArrayList<String> results = new ArrayList<String>();
         try{
             results=getInfo(email,passe);
         }catch (Exception e){
             System.out.println(e);
         }

         if (results != null){
             String id = results.get(0);
             professeurData.add(new Professeur(Integer.parseInt(id), email, passe, getNomProf(id), getPrenomProf(id), getSpecialite(id), getModule(id), getFilliere(id), getDepartement(id)));
             setIsProfValide(true);
             profLoginStage.close();
         }else{
             setIsProfValide(false);
         }




     }

    /**
    *Opens prof menu
    */
    public void profAffichage(Professeur prof){
        try{

            setProfConnecte(prof);

            //Load the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ProfOverview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Create dialog stage

            dialogStage.setTitle("Planing de : "+prof.getNom()+" "+prof.getPrenom());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProfOverviewController controller = loader.getController();
            controller.setProfOverviewStage(dialogStage);
            controller.setProfLoginController(this);
            controller.initialize();



            // Shows the dialog and waits until the user closes it
            dialogStage.show();
            dialogStage.setOnCloseRequest(e -> {
                e.consume();
                controller.handleDisconnection();
            });



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Professeur quelProf(String email){
        for(Professeur prof : professeurData){
            if(prof.getEmail().equals(email)){
                return prof;
            }
        }
        // serves nothing since we use this methode only if the "if statement" is true on one of the profs
        Professeur profe = new Professeur();
        return profe;
    }

    public void setProfConnecte(Professeur prof){
        this.profConnecte = prof;
    }

    public Professeur getProfConnecte(){
        return this.profConnecte;
    }


    public static ArrayList<String> getInfo(String email, String mdp) throws Exception{
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT id_prof FROM Professeur WHERE email LIKE ? AND mdp = ?");
            statement.setString(1,email);
            statement.setString(2,mdp);
            ResultSet result = statement.executeQuery();


            ArrayList<String> array = new ArrayList<String>();
            while(result.next()){
                System.out.println(result.getString("id_prof"));
                array.add(result.getString("id_prof"));
            }
            return array;
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("rien");
        return null;
    }

    public static String getNomProf(String id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT nom FROM Professeur WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            String nomProf = null;
            while (result.next()){
                nomProf = result.getString("nom");
            }
            return nomProf;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String getPrenomProf(String id){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT prenom FROM Professeur WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            String prenomProf = null;
            while (result.next()){
                prenomProf = result.getString("prenom");
            }
            return prenomProf;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String getDepartement(String id){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT departement FROM Professeur WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            String departementProf = null;
            while (result.next()){
                departementProf = result.getString("departement");
            }
            return departementProf;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String getSpecialite(String id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT specialite FROM Professeur WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            String specialiteProf = null;
            while (result.next()){
                specialiteProf = result.getString("specialite");
            }
            return specialiteProf;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<Filliere> getFilliere(String id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Filiere WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            ObservableList<Filliere> listFilliere = null;
            Filliere filliere = null;
            int id_filliere;
            String intitule;
            String date_acre;
            while (result.next()){
                id_filliere = result.getInt("id_filiere");
                intitule = result.getString("intitule");
                date_acre = result.getString("date_acreditation");
                listFilliere.add(new Filliere(id_filliere, intitule, date_acre));

            }
            return listFilliere;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<Module> getModule (String id){
        try {
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Module WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            ObservableList<Module> listModule = null;
            int id_module;
            String intitule;
            int vhc;
            int vhtd;
            int vhtp;
            int vhap;
            ObservableList<ElementModule> listElem = FXCollections.observableArrayList();
            while (result.next()){
                id_module = result.getInt("id_module");
                intitule = result.getString("intitule");
                vhc = result.getInt("volume_horaire_cours");
                vhtd = result.getInt("volume_horaire_td");
                vhtp = result.getInt("volume_horaire_tp");
                vhap = result.getInt("volume_horaire_ap");
                listElem = getListeElementsForModule(Integer.toString(id_module));
                listModule.add(new Module(id_module, intitule, vhc, vhtd, vhtp, vhap, listElem));
            }
            return listModule;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<ElementModule> getListeElementsForModule(String id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM element_module WHERE id_module = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            int id_elem;
            String intitule;
            int id_prof;
            ObservableList<ElementModule> listElem = null;
            while (result.next()){
                id_elem = result.getInt("id_elem");
                intitule = result.getString("intitule");
                id_prof = result.getInt("id_prof");
                listElem.add(new ElementModule(id_elem, intitule, id_prof, Integer.parseInt(id)));
            }
            return listElem;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<ElementModule> getListeElementsForProf(String id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM element_module WHERE id_prof = ? ");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();

            int id_elem;
            String intitule;
            int id_module;
            ObservableList<ElementModule> listElem = null;
            while (result.next()){
                id_elem = result.getInt("id_elem");
                intitule = result.getString("intitule");
                id_module = result.getInt("id_prof");
                listElem.add(new ElementModule(id_elem, intitule, Integer.parseInt(id), id_module));
            }
            return listElem;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
