package main;

import sourse.classes.Admin;
import sourse.classes.Caissier;
import sourse.classes.User;
import sourse.classes.Agent;
import sourse.interphaces.IUser;
import sourse.classeImple.UserImple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sourse.outils.Outil;

public class MainController {
    @FXML
    private TextField emailtxt;
    @FXML
    private PasswordField passwordtxt;
    private IUser useres = new UserImple();
    public static Agent agentparams;
    public static  Admin adminparams;
    public  static Caissier caissierparams;
    public static User userparam;
    public static String userparams;
    public static String nomparams;
    public static String prenomparams;
    public static String foctionparams;


    public void  getLogin(ActionEvent event){
        String email =emailtxt.getText();
        String password= passwordtxt.getText();
        if (email.equals("") || password.equals(""))
        {
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerte");
        alert.setContentText("Veuiller remplire tous les chemps !!!");
        alert.showAndWait();
        }else {
           Agent agents = new Agent();
           User usr=useres.getConnection(email,password);
            if (usr !=null){
                userparam=usr;
               if (usr.getAdmin()!=null){
                   adminparams=usr.getAdmin(); }
               if (usr.getAgent()!=null){
                   agentparams=usr.getAgent();}
               if (usr.getCaissier()!=null){
                   caissierparams=usr.getCaissier(); }
                try {
                    Outil.load(event, "Bien venu", "/accuil/Accuil.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }else {

               Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerte");
                alert.setContentText("Login ou mot de passe incorrecte!!!");
                alert.showAndWait();
            }
        }
    }
}
