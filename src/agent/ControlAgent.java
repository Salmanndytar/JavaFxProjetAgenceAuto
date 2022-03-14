package agent;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sourse.classeImple.AdminImpl;
import sourse.classeImple.AgentImpl;
import sourse.classeImple.CaissierImpl;
import sourse.classeImple.LocationImpl;
import sourse.classes.Admin;
import sourse.classes.Agent;
import sourse.classes.Caissier;
import sourse.classes.Location;
import sourse.interphaces.IAdmin;
import sourse.interphaces.IAgent;
import sourse.interphaces.ICaissier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.interphaces.ILocation;
import sourse.outils.DB;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControlAgent implements Initializable {
    @FXML
   private int verificationtel;
    String telSelect="";
    @FXML
    private int idtxt;
    @FXML
    private TextField nomtxt;
    @FXML
    private TextField prenomtxt;
    @FXML
    private TextField teltxt;
    @FXML
    private Button validerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button annulerbt;
    @FXML
    private Button modifierbt;
    @FXML
    private Button imprimerbt;

    @FXML
    private Button imagebt;
    @FXML
    private ImageView imageView;
    private Image image;
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private FileInputStream fis;

    @FXML
    private TableView<Agent> tableagent;
    @FXML
    private TableColumn<Agent, Integer>colonneid;
    @FXML
    private TableColumn<Agent, String>colonnenom;
    @FXML
    private TableColumn<Agent, String>colonneprenom;
    @FXML
    private TableColumn<Agent, String>colonnetel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    colonneid.setCellValueFactory(new PropertyValueFactory<Agent,Integer>("id"));
    colonnenom.setCellValueFactory(new  PropertyValueFactory<Agent, String>("nom"));
    colonneprenom.setCellValueFactory(new PropertyValueFactory<Agent, String>("prenom"));
    colonnetel.setCellValueFactory(new PropertyValueFactory<Agent, String>("tel"));
    load();
    modifierbt.setVisible(false);
    supprimerbt.setVisible(false);
    imprimerbt.setVisible(false);



    }
    Agent agent = new Agent();
    IAgent agents = new AgentImpl();
    ICaissier caissiers =new CaissierImpl();
    IAdmin admins = new AdminImpl();
    ILocation iLocation = new LocationImpl();
    DB db = new DB();

    public void Insert(ActionEvent event) {
        agent.setNom(nomtxt.getText());
        agent.setPrenom(prenomtxt.getText());
        agent.setTel(teltxt.getText());
        agent.setEtat(0);

        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerte");
            alert.setContentText("Veuillez remplire tous les chemps");
            alert.showAndWait();
        }else
            {
                Verification(teltxt.getText());
             if (verificationtel==0 )
                {
                    agents.addAgent(agent);
                    load();
                    Annuler(event);
                }else Alerte("numero téléphone invalide");
            }
        }
    public void Annuler(ActionEvent event){
        nomtxt.setText("");
        prenomtxt.setText("");
        teltxt.setText("");
        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);

        validerbt.setVisible(true);

    }
    public void Modifier(ActionEvent event){
        agent.setId(idtxt);
        agent.setNom(nomtxt.getText());
        agent.setPrenom(prenomtxt.getText());
        agent.setTel(teltxt.getText());
        agent.setEtat(tableagent.getSelectionModel().getSelectedItem().getEtat());

        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerte");
            alert.setContentText("Veuillez remplire tous les chemps");
            alert.showAndWait();
        }else
        {
            Verification(teltxt.getText());
            if (verificationtel==0 )
            {
                agents.updatAgent(agent);
                load();
                Annuler(event);
            }else Alerte("numero téléphone invalide");
        }
    }
    public void Supprimer(ActionEvent event){
        boolean ok = AlertConf(" Vous etes sure de vouloir supprimer ?");
        if (ok ==true) {
            boolean possible = ControlSupp();
            if (possible == true)
                agents.suppAgent(idtxt);
            else Alerte(" Désolé\n cet utilisateur detien des informations importentes !");
        }
     }
    public void clockLingne() {
        Agent agent=tableagent.getSelectionModel().getSelectedItem();
      if (agent!=null){
        modifierbt.setVisible(true);
        supprimerbt.setVisible(true);
        imprimerbt.setVisible(true);
        validerbt.setVisible(false);
        idtxt=agent.getId();
        nomtxt.setText(agent.getNom());
        prenomtxt.setText(agent.getPrenom());
        teltxt.setText(agent.getTel());
        telSelect=agent.getTel();
        ViewImage();
      }
    }
    public void Verification(String tel){
            verificationtel=0;
            List<Agent> agentList=agents.listeAgent();
            List<Caissier> caissierList = caissiers.listeCaissier();
            List<Admin> adminList = admins.listeAdmin();
            if (!telSelect.equals(tel)){
                for (Agent ag : agentList) { if (ag.getTel().contains(tel)) verificationtel += 100; }
                for (Admin ad : adminList) { if (ad.getTel().contains(tel)) verificationtel += 1; }
                for (Caissier ca : caissierList) { if (ca.getTel().contains(tel)) verificationtel += 1; }}
            }
    public void Alerte(String message) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("alerte");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public boolean ControlSupp(){
        boolean ok =true;
        List<Location> loc = iLocation.listeLocationEtat();// list de location vallidee
        List<Location> loca = iLocation.listeLocationRetour();// list de location retournee
        Agent ag = tableagent.getSelectionModel().getSelectedItem();
        for (Location l : loc){
            if ( l.getAgents().getId() == ag.getId())
                ok=false;
            break;
        }
        for (Location l : loca){
            if ( l.getAgents().getId() == ag.getId())
                ok=false;
            break;
        }
        return ok;
    }
    public  boolean AlertConf(String message){
        boolean ok = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            ok = true;
        return  ok;
    }
    public void load(){
        List<Agent>agentList = agents.listeAgent();
        ObservableList<Agent>observableList= FXCollections.observableArrayList();
        for (Agent a : agentList){
            observableList.add(a);
        }
        tableagent.setItems(observableList);
    }
    public void ViewImage(){
        String sql="SELECT image FROM agent WHERE  image is not null AND id_agent = '"+idtxt+"'";
        try {
            db.preparedStatement(sql);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size = is.read(contents)) != -1) {
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg",imageView.getFitWidth(),imageView.getFitHeight(),true,true);
                imageView.setImage(image);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void LoadImg(ActionEvent event) throws IOException, SQLException, ClassNotFoundException
    {
        Agent agent=tableagent.getSelectionModel().getSelectedItem();
        if (agent!=null){
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.gif", "*.jpg"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            fis = new FileInputStream(file);
            if (file != null) {
                System.out.println("" + file.getAbsolutePath());
                image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
                imageView.setPreserveRatio(true);
                String sql = "UPDATE agent SET image= ? WHERE id_agent  = ?";
                db.preparedStatement(sql);
                db.getPstmt().setBinaryStream(1, fis, file.length());
                db.getPstmt().setInt(2, idtxt);
                db.executUpdate(sql);
                imageView.setImage(image);
            }
        }else {
            Alerte("Veuillez selectionner un user pour charger une image");
        }
    }


}
