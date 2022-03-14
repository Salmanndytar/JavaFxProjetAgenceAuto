package user;
import sourse.classeImple.*;
import sourse.classes.*;
import sourse.interphaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.MainController;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControlUser implements Initializable {
    @FXML
    private int idtxt;
    @FXML
    private Label  titreListe;
    @FXML
    private Label  titreListeInactive;
    @FXML
    private Label  libelleActive;
    @FXML
    private Label  libelleInactive;

    boolean ad=false;
    boolean ca=false;
    boolean ag=false;

    boolean adInactive=false;
    boolean caInactive=false;
    boolean agInactive=false;

    @FXML
    private Button activerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button annulerbt;

    //tabe agent inactiv
    @FXML
    private TableView<Agent> tablAgentInactive;
    @FXML
    private TableColumn<Agent,Integer> colonneidAgI;
    @FXML
    private TableColumn<Agent, String> colonnenomAgI;
    @FXML
    private TableColumn<Agent, String> colonneprenomAgI;
    @FXML
    private TableColumn<Agent, String> colonnetelAgI;

    //tabe admin inactiv
    @FXML
    private TableView<Admin> tablAdminInactive;
    @FXML
    private TableColumn<Admin,Integer> colonneidAdI;
    @FXML
    private TableColumn<Admin, String> colonnenomAdI;
    @FXML
    private TableColumn<Admin, String> colonneprenomAdI;
    @FXML
    private TableColumn<Admin, String> colonnetelAdI;

    //table caissier inactive
    @FXML
    private TableView<Caissier> tablCaissierInactive;

    @FXML
    private TableColumn<Caissier,Integer> colonneidCaI;
    @FXML
    private TableColumn<Caissier, String> colonnenomCaI;
    @FXML
    private TableColumn<Caissier, String> colonneprenomCaI;
    @FXML
    private TableColumn<Caissier, String> colonnetelCaI;

    //table agent ativ
    @FXML
    private TableView<User> tableAgent;
    @FXML
    private TableColumn<User,Integer> colonneid;
    @FXML
    private TableColumn<User, String> columnNom;
    @FXML
    private TableColumn<User, String> colonnedate;

    //table caissier ativ
    @FXML
    private TableView<User> tableCaissier;
    @FXML
    private TableColumn<User, Integer> colonneidc;
    @FXML
    private TableColumn<User, String> colonenomc;
    @FXML
    private TableColumn<User, String> colonnedatec;

    //table admin ativ
    @FXML
    private TableView<User> tableadmin;
    @FXML
    private TableColumn<User, Integer> colonneida;
    @FXML
    private TableColumn<User, String> colonenoma;
    @FXML
    private TableColumn<User, String> colonnedatea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            tableadmin.setVisible(false);
            tableCaissier.setVisible(false);
            tableAgent.setVisible(false);
            libelleActive.setVisible(true);

            tablAdminInactive.setVisible(false);
            tablAgentInactive.setVisible(false);
            tablCaissierInactive.setVisible(false);
            libelleInactive.setVisible(true);

            supprimerbt.setVisible(false);
            activerbt.setVisible(false);



            colonneid.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnNom.setCellValueFactory(new PropertyValueFactory<>("agent"));
            colonnedate.setCellValueFactory(new PropertyValueFactory<>("date"));
            ChargeTableAgent();

            colonneidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonenomc.setCellValueFactory(new PropertyValueFactory<>("caissier"));
            colonnedatec.setCellValueFactory(new PropertyValueFactory<>("date"));
            ChargeTableCaissier();

            colonneida.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonenoma.setCellValueFactory(new PropertyValueFactory<>("admin"));
            colonnedatea.setCellValueFactory(new PropertyValueFactory<>("date"));
            ChargeTableAdmin();


            //chargement de tables inactive
            colonneidAgI.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonnenomAgI.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colonneprenomAgI.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colonnetelAgI.setCellValueFactory(new PropertyValueFactory<>("tel"));
            ChargeTableAgentInactive();

            colonneidAdI.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonnenomAdI.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colonneprenomAdI.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colonnetelAdI.setCellValueFactory(new PropertyValueFactory<>("tel"));
            ChargeTableAdminInactive();

            colonneidCaI.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonnenomCaI.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colonneprenomCaI.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colonnetelCaI.setCellValueFactory(new PropertyValueFactory<>("tel"));
            ChargeTableCaissierInactive();


    }
    User user = new User();
    IUser users = new UserImple();
    IAdmin admin = new AdminImpl();
    IAgent agent =new AgentImpl();
    ICaissier caissier= new CaissierImpl();
    Admin admins ;
    Agent agents;
    Caissier caissiers;
    User agentuser;
    User adminUser;
    User caissierUsur;
    ILocation iLocation = new LocationImpl();

    Alert alert = new Alert(Alert.AlertType.ERROR);
    Alert alerts = new Alert(Alert.AlertType.INFORMATION);
    Alert alertc = new Alert(Alert.AlertType.CONFIRMATION);

    public void Insert(ActionEvent event) {

        alertc.setTitle("alerte");
        alertc.setHeaderText("Confirmez vous cette operation");
        alertc.setContentText("");
        Optional<ButtonType> resultat = alertc.showAndWait();

        if (resultat.get() == ButtonType.OK)
        {
            int ok = 0;
            user.setPassword("1234");
            user.setDate(LocalDate.now());
            if (admins != null)
            {
                user.setAdmin(admins);
                user.setEmail(admins.getTel()+"@user.com");
                ok = users.adduserAd(user);
                if (ok != 0)
                {
                    Admin admin1 = new Admin();
                    admin1.setId(admins.getId());
                    admin1.setNom(admins.getNom());
                    admin1.setPrenom(admins.getPrenom());
                    admin1.setTel(admins.getTel());
                    admin1.setEtat(1);
                    admin.updatAdmin(admin1);
                }

            } else if (caissiers != null)
            {
                user.setCaissier(caissiers);
                user.setEmail(caissiers.getTel()+"@user.com");
                ok = users.adduserCa(user);
                if (ok != 0) {
                    Caissier caissier1 = new Caissier();
                    caissier1.setId(caissiers.getId());
                    caissier1.setNom(caissiers.getNom());
                    caissier1.setPrenom(caissiers.getPrenom());
                    caissier1.setTel(caissiers.getTel());
                    caissier1.setEtat(1);
                    caissier.updatCaissier(caissier1);
                }
            } else if (agents != null)
            {
                user.setAgent(agents);
                user.setEmail(agents.getTel()+"@user.com");
                ok = users.adduserAg(user);

                if (ok != 0)
                {
                    Agent agent1 = new Agent();
                    agent1.setId(agents.getId());
                    agent1.setNom(agents.getNom());
                    agent1.setPrenom(agents.getPrenom());
                    agent1.setTel(agents.getTel());
                    agent1.setEtat(1);
                    agent.updatAgent(agent1);
                }
            }

            if (ok != 0)
            {
                ChargeTableAgent();
                ChargeTableAdmin();
                ChargeTableCaissier();
                ChargeTableAgentInactive();
                ChargeTableAdminInactive();
                ChargeTableCaissierInactive();
                Annuler(event);
            } else
                {
                alert.setTitle("Alerte");
                alert.setContentText("Operation non effectuee selectionner un user pour activer");
                alert.showAndWait();
            }
        }else activerbt.setVisible(false);
    }
    public void Annuler(ActionEvent event){
    supprimerbt.setVisible(false);
    activerbt.setVisible(false);
    agents=null;
    admins=null;
    caissiers=null;
    //validerbt.setVisible(false);

    }
    public void Supprimer(ActionEvent event) {
        alertc.setTitle("alerte");
        alertc.setHeaderText("Confirmez vous cette operation");
        alertc.setContentText("");
        Optional<ButtonType> resultat = alertc.showAndWait();

              if (resultat.get() == ButtonType.OK)
              {
                  if(+idtxt== MainController.userparam.getId())
                  {   alert.setTitle("Alerte");
                      alert.setContentText("Imposiple de desactiver la session ouverte");
                      alert.showAndWait();
                  }else{
                      int ok = users.suppUser(idtxt);

                      if (ok != 0)
                      {
                          if (agentuser!=null )
                          {
                              Agent agent1 = new Agent();
                              agent1.setId(agentuser.getAgent().getId());
                              agent1.setNom(agentuser.getAgent().getNom());
                              agent1.setPrenom(agentuser.getAgent().getPrenom());
                              agent1.setTel(agentuser.getAgent().getTel());
                              agent1.setEtat(0);
                              agent.updatAgent(agent1);
                          }
                          else if (caissierUsur!=null )
                          {
                              Caissier caissier1 = new Caissier();
                              caissier1.setId(caissierUsur.getCaissier().getId());
                              caissier1.setNom(caissierUsur.getCaissier().getNom());
                              caissier1.setPrenom(caissierUsur.getCaissier().getPrenom());
                              caissier1.setTel(caissierUsur.getCaissier().getTel());
                              caissier1.setEtat(0);
                              caissier.updatCaissier(caissier1);
                          }
                          else if (adminUser!=null )
                          {
                              Admin admin1 = new Admin();
                              admin1.setId(adminUser.getAdmin().getId());
                              admin1.setNom(adminUser.getAdmin().getNom());
                              admin1.setPrenom(adminUser.getAdmin().getPrenom());
                              admin1.setTel(adminUser.getAdmin().getTel());
                              admin1.setEtat(0);
                              admin.updatAdmin(admin1);
                          }
                          ChargeTableCaissier();
                          ChargeTableAdmin();
                          ChargeTableAgent();
                          ChargeTableCaissierInactive();
                          ChargeTableAdminInactive();
                          ChargeTableAgentInactive();
                          Annuler(event);
                      }

                  }
              }else supprimerbt.setVisible(false);
          }

    public void ChargeTableAgent(){
        List<User>userList=users.listeUserAg();
        ObservableList<User>userObservableListAg=FXCollections.observableArrayList();

        for (User u : userList){
            userObservableListAg.add(u);
        }
        tableAgent.setItems(userObservableListAg);
        if (ag==true)
        AffichetableAg();

    }
    public void ChargeTableCaissier(){
        List<User>userList=users.listeUserC();
        ObservableList<User>userObservableListCa=FXCollections.observableArrayList();
        for (User u : userList){
            userObservableListCa.add(u);
        }
        tableCaissier.setItems(userObservableListCa);
        if (ca==true)
         AffichetableCa();
    }
    public void ChargeTableAdmin(){
        List<User>userList=users.listeUserA();
        ObservableList<User>userObservableListAd=FXCollections.observableArrayList();
        for (User u : userList){ userObservableListAd.add(u);}
        tableadmin.setItems(userObservableListAd);
        if (ad==true)
        AffichetableAd();


    }

    public void ChargeTableAdminInactive(){

            ObservableList<Admin>adminsobservable= FXCollections.observableArrayList();
            List<Admin>adminList=admin.listeAdminEtat();
            for (Admin a : adminList){
                adminsobservable.add(a);
            }
            tablAdminInactive.setItems(adminsobservable);
            if (adInactive==true)
             AffichetableAdInactive();

        }
    public  void  ChargeTableAgentInactive(){
            ObservableList<Agent>agentObservableList= FXCollections.observableArrayList();
            List<Agent>agentList=agent.listeAgentEtatNonActive();

            for (Agent a : agentList){
                agentObservableList.add(a);
            }
            tablAgentInactive.setItems(agentObservableList);
        if (agInactive==true)
            AffichetableAgInactive();
        }
    public void ChargeTableCaissierInactive(){
            ObservableList<Caissier>caissierObservableList= FXCollections.observableArrayList();
            List<Caissier>caissierList=caissier.listeCaissierEtat();
            for (Caissier c : caissierList){
                caissierObservableList.add(c);
            }
            tablCaissierInactive.setItems(caissierObservableList);
        if (caInactive==true)
            AffichetableCaInactive();
        }

    public void clockTabAg() {
        User user=tableAgent.getSelectionModel().getSelectedItem();
        if (user!=null){
        agentuser=user;
        idtxt=user.getId();
        supprimerbt.setVisible(true);
        activerbt.setVisible(false);}
    }
    public void clockTabCa() {
        User usr=tableCaissier.getSelectionModel().getSelectedItem();
       if (usr!=null){
        idtxt=usr.getId();
        caissierUsur=usr;
        supprimerbt.setVisible(true);
        activerbt.setVisible(false);
       }
    }
    public void clockTabAd() {
        User usr=tableadmin.getSelectionModel().getSelectedItem();
        if (usr!=null){
        idtxt=usr.getId();
        adminUser=usr;
        supprimerbt.setVisible(true);
        activerbt.setVisible(false);
        }
    }

    public void clockTabAgInacive() {
        Agent agent=tablAgentInactive.getSelectionModel().getSelectedItem();
       if (agent!=null){
        agents=agent;
        idtxt=agent.getId();
        supprimerbt.setVisible(false);
        activerbt.setVisible(true);
       }
    }
    public void clockTabCaInacive() {
        Caissier caissier=tablCaissierInactive.getSelectionModel().getSelectedItem();
        if (caissier!=null){
        caissiers=caissier;
        supprimerbt.setVisible(false);
        activerbt.setVisible(true);
        }
    }
    public void clockTabAdInacive() {
        Admin  admin=tablAdminInactive.getSelectionModel().getSelectedItem();
        if (admin!=null){
        admins=admin;
        supprimerbt.setVisible(false);
        activerbt.setVisible(true);}
    }

    public void AffichetableAd(){
        tableadmin.setVisible(true);
        tableCaissier.setVisible(false);
        tableAgent.setVisible(false);
        libelleActive.setVisible(false);
        ad=true;
        titreListe.setText("Liste de user Admin active");}
    public void AffichetableCa(){
        tableCaissier.setVisible(true);
        tableAgent.setVisible(false);
        tableadmin.setVisible(false);
        libelleActive.setVisible(false);
        ca=true;
        titreListe.setText("Liste de user Caissier active");}
    public void AffichetableAg(){
        tableAgent.setVisible(true);
        tableCaissier.setVisible(false);
        tableadmin.setVisible(false);
        libelleActive.setVisible(false);
        ag=true;
        titreListe.setText("Liste de user Agent active");}

    public void AffichetableAdInactive(){
        tablAdminInactive.setVisible(true);
        tablCaissierInactive.setVisible(false);
        tablAgentInactive.setVisible(false);
        libelleInactive.setVisible(false);
        adInactive=true;
        titreListeInactive.setText("Liste de user Admin inaactive");}
    public void AffichetableCaInactive(){
        tablCaissierInactive.setVisible(true);
        tablAgentInactive.setVisible(false);
        tablAdminInactive.setVisible(false);
        libelleInactive.setVisible(false);
        caInactive=true;
        titreListeInactive.setText("Liste de user Caissier inactive");}
    public void AffichetableAgInactive(){
        tablAgentInactive.setVisible(true);
        tablCaissierInactive.setVisible(false);
        tablAdminInactive.setVisible(false);
        libelleInactive.setVisible(false);
        agInactive=true;
        titreListeInactive.setText("Liste de user Agent inactive");}

}
