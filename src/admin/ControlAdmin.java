package admin;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainController;
import sourse.classeImple.AdminImpl;
import sourse.classeImple.AgentImpl;
import sourse.classeImple.CaissierImpl;
import sourse.classes.Admin;
import sourse.classes.Agent;
import sourse.classes.Caissier;
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
import sourse.outils.DB;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControlAdmin implements Initializable {
    @FXML
    private int verificationtel ;
    public String telSelect="";
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
    private TableView<Admin> tableagent;
    @FXML
    private TableColumn<Agent, Integer> colonneid;
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

        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);



    }
    DB db =new DB();
    Admin admin = new Admin();
    IAdmin admins = new AdminImpl();
    IAgent agents = new AgentImpl();
    ICaissier caissiers = new CaissierImpl();
    public void Insert(ActionEvent event) {

        admin.setNom(nomtxt.getText());
        admin.setPrenom(prenomtxt.getText());
        admin.setTel(teltxt.getText());
        admin.setEtat(0);
        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerte");
            alert.setContentText("Veuillez remplire tous les chemps");
            alert.showAndWait();
        }
       else {
              Verification(teltxt.getText());
             if (verificationtel==0) {
               admins.addAdmin(admin);

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
        imageView.setImage(null);
        validerbt.setVisible(true);

    }
    public void Modifier(ActionEvent event){

        admin.setId(idtxt);
        admin.setNom(nomtxt.getText());
        admin.setPrenom(prenomtxt.getText());
        admin.setTel(teltxt.getText());

        admin.setEtat(tableagent.getSelectionModel().getSelectedItem().getEtat());
        if(nomtxt.getText().equals("")|| prenomtxt.getText().equals("")||teltxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerte");
            alert.setContentText("Veuillez remplire tous les chemps");
            alert.showAndWait();
        }
        else {
            Verification(teltxt.getText());
            if (verificationtel==0) {
                admins.updatAdmin(admin);
                imageView.setImage(image);
                Annuler(event);
            }else Alerte("numero téléphone invalide");
        }
    }
    public void Supprimer(ActionEvent event){
        boolean ok =AlertConf("Confirmer vous de le supprimer ?");
        if (ok == true){
            boolean controle = ControlSupp();
            Alerte(controle+"");
            if (controle == true)
                admins.suppAdmin(idtxt);
            else Alerte("Impossible de supprimer la session ouverte !");
        }
    }
    public void load(){
        List<Admin> adminList = admins.listeAdmin();
        ObservableList<Admin> adminObservableList=FXCollections.observableArrayList();
        for (Admin a : adminList){
            adminObservableList.add(a);
        }
        tableagent.setItems(adminObservableList);
    }

    public void clockLingne() {
        Admin admin=tableagent.getSelectionModel().getSelectedItem();
        if (admin!=null){
        imageView.setImage(null);
        modifierbt.setVisible(true);
        supprimerbt.setVisible(true);
        imprimerbt.setVisible(true);
        validerbt.setVisible(false);
        idtxt=admin.getId();
        nomtxt.setText(admin.getNom());
        prenomtxt.setText(admin.getPrenom());
        teltxt.setText(admin.getTel());
        telSelect=admin.getTel();
        ViewImage();

        }

    }

    public void Verification(String tel){


        verificationtel=0;
        List<Agent> agentList=agents.listeAgent();
        List<Caissier> caissierList = caissiers.listeCaissier();
        List<Admin> adminList = admins.listeAdmin();
        if (!telSelect.equals(tel)){
            for (Agent ag : agentList) { if (ag.getTel().contains(tel)) verificationtel += 1; }
            for (Admin ad : adminList) { if (ad.getTel().contains(tel)) verificationtel += 1; }
            for (Caissier ca : caissierList) { if (ca.getTel().contains(tel)) verificationtel += 1; }}

        if (verificationtel!=0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("alerte");
            alert.setContentText("numero téléphone invalide");
            alert.showAndWait();
        }

    }
    public void Alerte(String message) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("alerte");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public boolean ControlSupp(){
        boolean ok =true;

        Admin ad = tableagent.getSelectionModel().getSelectedItem();
            if ( ad.getId() == MainController.adminparams.getId())
                ok=false;
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
    public void ViewImage(){
        String sql="SELECT image FROM admin WHERE  image is not null AND id = '"+idtxt+"'";
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
                admin.setImage(image);
                imageView.setImage(admin.getImage());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void LoadImg(ActionEvent event) throws IOException, SQLException, ClassNotFoundException
    {
        Admin admin=tableagent.getSelectionModel().getSelectedItem();
        if (admin!=null){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.gif", "*.jpg"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        fis = new FileInputStream(file);
        if (file != null) {
            System.out.println("" + file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setPreserveRatio(true);
            String sql = "UPDATE admin SET image= ? WHERE id  = ?";
            db.preparedStatement(sql);
            db.getPstmt().setBinaryStream(1, fis, file.length());
            db.getPstmt().setInt(2, idtxt);
            db.executUpdate(sql);
        }
        }else {
            Alerte("Veuillez selectionner un user pour charger une image");
        }
    }
}
