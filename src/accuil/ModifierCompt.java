package accuil;

import sourse.classeImple.AdminImpl;
import sourse.classeImple.AgentImpl;
import sourse.classeImple.CaissierImpl;
import sourse.classeImple.UserImple;
import sourse.classes.Admin;
import sourse.classes.Agent;
import sourse.classes.Caissier;
import sourse.classes.User;
import sourse.interphaces.IAdmin;
import sourse.interphaces.IAgent;
import sourse.interphaces.ICaissier;
import sourse.interphaces.IUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.MainController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModifierCompt implements Initializable {
    public AnchorPane fenplus;
    public AnchorPane fenCompt;
    public Button validerplus;
    public Button annulerplus;
    public TextField teltxt;
    public TextField logintxt;
    public TextField prenomtxt;
    public TextField nomtxt;
    public PasswordField pass1txt;
    public PasswordField pass2txt;
    public boolean mail;
   int verificationtel;
   boolean chargerinfo=false;



    public Button plusbt;
    IUser user = new UserImple();
    User usr = new  User();
    Agent agent = new Agent();
    Caissier caissier =new Caissier();
    Admin admin =new Admin();
    IAgent agents=new AgentImpl();
    IAdmin admins=new AdminImpl();
    ICaissier caissiers=new CaissierImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fenplus.setVisible(false);
        plusbt.setVisible(true);
        chargeInfoPlus();

    }
    public void VerificationUser(String login){
        mail=false;
        List<User> userListA=user.listeUserComplet().stream().collect(Collectors.toList());
        for (User u : userListA){
            if (u.getEmail().equals(login))
             { mail= true;}
        }
    }
    public void plus(){
        plusbt.setVisible(false);
        fenplus.setVisible(true);
    }
    public void chargeInfoPlus(){
        chargerinfo=true;
        if(MainController.agentparams!=null)
        {
            nomtxt.setText(MainController.agentparams.getNom());
            prenomtxt.setText(MainController.agentparams.getPrenom());
            teltxt.setText(MainController.agentparams.getTel());
        }
        if(MainController.caissierparams!=null)
        {
            nomtxt.setText(MainController.caissierparams.getNom());
            prenomtxt.setText(MainController.caissierparams.getPrenom());
            teltxt.setText(MainController.caissierparams.getTel());
        }
        if(MainController.adminparams!=null)
        {
            nomtxt.setText(MainController.adminparams.getNom());
            prenomtxt.setText(MainController.adminparams.getPrenom());
            teltxt.setText(MainController.adminparams.getTel());
        }
    }
    public void Quitterplus(){
        chargerinfo=false;
        plusbt.setVisible(true);
        fenplus.setVisible(false);
        pass2txt.setText("");
        pass1txt.setText("");
        logintxt.setText("");
         }
    public void Alerte(String message){
             Alert erreur = new Alert(Alert.AlertType.ERROR);
             erreur.setTitle("Alerte");
             erreur.setHeaderText(message);
             erreur.showAndWait();

         }
    public void Infomessage(String message){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Message");
        info.setHeaderText(message);
        info.showAndWait();
    }
    public void Verificationtel(String tel) {
        verificationtel = 0;
        List<Agent> agentList = agents.listeAgent();
        List<Caissier> caissierList = caissiers.listeCaissier();
        List<Admin> adminList = admins.listeAdmin();

        if (MainController.agentparams != null) {
            if (!MainController.agentparams.getTel().contains(tel)) {
                for (Admin ad : adminList) {
                    if (ad.getTel().contains(tel)) verificationtel += 1;
                }
                for (Caissier ca : caissierList) {
                    if (ca.getTel().contains(tel)) verificationtel += 1;
                }
                for (Agent ag : agentList) {
                    if (ag.getTel().contains(tel)) verificationtel += 1;
                }
            }

        } else if (MainController.adminparams != null) {
            if (!MainController.adminparams.getTel().contains(tel)) {
                for (Agent ag : agentList) {
                    if (ag.getTel().contains(tel)) verificationtel += 1;
                }
                for (Admin ad : adminList) {
                    if (ad.getTel().contains(tel)) verificationtel += 1;
                }
                for (Caissier ca : caissierList) {
                    if (ca.getTel().contains(tel)) verificationtel += 1;
                }
            }
        } else if (MainController.caissierparams != null) {
            if (!MainController.caissierparams.getTel().contains(tel)) {
                for (Agent ag : agentList) {
                    if (ag.getTel().contains(tel)) verificationtel += 1;
                }
                for (Admin ad : adminList) {
                    if (ad.getTel().contains(tel)) verificationtel += 1;
                }
                for (Caissier ca : caissierList) {
                    if (ca.getTel().contains(tel)) verificationtel += 1;
                }
            }
        }
    }
    public void Validation( ActionEvent event){
        int ok = 0;
        int oki = 0;
    if (chargerinfo==true)
         {if (pass2txt.getText().equals("") || pass1txt.getText().equals("")||logintxt.getText().equals(""))Alerte("tous les champs doivent etre remplis");}else oki=1;

         if (!pass2txt.getText().contains(pass1txt.getText()))Alerte("Erreur mot de passe");
        else { VerificationUser(logintxt.getText());
             if (mail == true) { Alerte("Logine : " + logintxt.getText()+ " invalide");
            } else {

                    usr.setId(MainController.userparam.getId());
                    usr.setEmail(logintxt.getText());
                    usr.setPassword(pass1txt.getText());

                    //les information plus

                    if(MainController.agentparams!=null)
                    {
                        agent.setId(MainController.agentparams.getId());
                        agent.setNom(nomtxt.getText());
                        agent.setPrenom(prenomtxt.getText());
                        agent.setTel(teltxt.getText());
                        agent.setEtat(MainController.agentparams.getEtat());

                         if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals("")) { Alerte("Veuillez remplire tous les champs!!!");
                        }else {Verificationtel(teltxt.getText());
                            if (verificationtel==0 ) {
                                oki=agents.updatAgent(agent);
                                ok= user.updatUser(usr);
                            }else Alerte("Numero tétéphone invalide "+verificationtel);
                        }
                    }
                    if(MainController.caissierparams!=null)
                    {
                        caissier.setId(MainController.caissierparams.getId());
                        caissier.setNom(nomtxt.getText());
                        caissier.setPrenom(prenomtxt.getText());
                        caissier.setTel(teltxt.getText());
                        caissier.setEtat(MainController.caissierparams.getEtat());

                        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals("")) { Alerte("Veuillez remplire tous les champs!!!");
                        }else { Verificationtel(teltxt.getText());
                            if (verificationtel==0 ) {
                                oki=caissiers.updatCaissier(caissier);
                                ok= user.updatUser(usr);
                            }else Alerte("Numero tétéphone invalide");
                        }
                    }
                    if(MainController.adminparams!=null) {
                        admin.setId(MainController.adminparams.getId());
                        admin.setNom(nomtxt.getText());
                        admin.setPrenom(prenomtxt.getText());
                        admin.setTel(teltxt.getText());
                        admin.setEtat(MainController.adminparams.getEtat());

                        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals("")) { Alerte("Veuillez remplire tous les champs!!!");
                        }else { Verificationtel(teltxt.getText());
                            if (verificationtel==0 ) {
                                oki=admins.updatAdmin(admin);
                                ok= user.updatUser(usr);
                            }else Alerte("Numero tétéphone invalide");
                        }
                    }



               if (ok!=0 && oki !=0){Infomessage("Modification bien effectuée");
                   ((Node)event.getSource()).getScene().getWindow().hide();

               }
            }
        }
    }
}
