package accuil;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sourse.classeImple.UserImple;
import sourse.classes.Agent;
import sourse.classes.User;
import sourse.interphaces.IUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.MainController;
import sourse.outils.DB;
import sourse.outils.Outil;


import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static main.MainController.*;

public class ControlAccuil implements Initializable
        {


        @FXML
        private boolean  verification;
        @FXML
        private String login;
        @FXML
        private String pass;
        @FXML
        private String login1;
        @FXML
        private String pass1;
        @FXML
        private Label message;
        @FXML
        private Label messagenom;
        @FXML
        private Label messagePrenom;
        @FXML
        private Label messagefonction;
        @FXML
        private ImageView imageView;
        private Image image;
        private FileChooser fileChooser;
        private File file;
        private Stage stage;
        private FileInputStream fis;
        DB db = new DB();
        Alert alertc = new Alert(Alert.AlertType.CONFIRMATION);
        IUser user =new UserImple();
        @Override
        public void initialize(URL location, ResourceBundle resources) {

           if (caissierparams!=null){
               message.setText("Bien-venu  " + caissierparams.getNom().toUpperCase());
               messagenom.setText(caissierparams.getNom().toUpperCase());
               messagePrenom.setText(caissierparams.getPrenom().toUpperCase());
               messagefonction.setText("CAISSE");
               //pour afficher image
               String sql="SELECT image FROM caissier WHERE  image is not null AND id_caissier= ? ";
               ViewImage(sql,caissierparams.getId());
               //pour nouveau compt propssition de modification de login et pass
               VerificationCaissier(caissierparams.getTel()+"@user.com");
               if (verification==true)
               {
                   alertc.setTitle("alerte");
                   alertc.setHeaderText("Pour la question de securite");
                   alertc.setContentText("Veuillez changer le mot de passe et login");
                   Optional<ButtonType> resultat = alertc.showAndWait();
                   if (resultat.get() == ButtonType.OK)
                   {
                       try {
                           ActionEvent event = null;
                           Outil.loadSub(event, "modification de compte", "/accuil/ModifierCompt.fxml");
                       } catch (Exception ex) {ex.printStackTrace();}
                   }
               }


           }
           if (agentparams!=null){
               message.setText("Bien-venu  "+ agentparams.getNom().toUpperCase());
               messagenom.setText(agentparams.getNom().toUpperCase());
               messagePrenom.setText(agentparams.getPrenom().toUpperCase());
               messagefonction.setText("AGENCE");

               //pour afficher image
               String sql="SELECT image FROM agent WHERE  image is not null AND id_agent= ? ";
               ViewImage(sql,agentparams.getId());

               //pour nouveau compt propssition de modification de login et pass
               VerificationAgent(agentparams.getTel()+"@user.com");
               if (verification==true)
               {
                  alertc.setTitle("alerte");
                   alertc.setHeaderText("Pour la question de securite");
                   alertc.setContentText("Veuillez changer le mot de passe et login");
                   Optional<ButtonType> resultat = alertc.showAndWait();
                   if (resultat.get() == ButtonType.OK)
                   {
                       try {
                           ActionEvent event = null;
                           Outil.loadSub(event, "modification de compte", "/accuil/ModifierCompt.fxml");
                       } catch (Exception ex) {ex.printStackTrace();}
                   }
               }

           }
           if (adminparams!=null){
                        message.setText("Bien-venu  "+ adminparams.getNom().toUpperCase());
                        messagenom.setText(adminparams.getNom().toUpperCase());
                        messagePrenom.setText(adminparams.getPrenom().toUpperCase());
                        messagefonction.setText("ADMINISTRATEUR");

                       //pour afficher image

                        String sql="SELECT image FROM admin WHERE  image is not null AND id= ? ";
                        ViewImage(sql,adminparams.getId());


                       //pour nouveau compt propssition de modification de login et pass
                       VerificationAdmin(adminparams.getTel()+"@user.com");
                       if (verification==true)
                       {
                           alertc.setTitle("alerte");
                           alertc.setHeaderText("Pour la question de securite");
                           alertc.setContentText("Veuillez changer le mot de passe et login");
                           Optional<ButtonType> resultat = alertc.showAndWait();
                           if (resultat.get() == ButtonType.OK)
                           {

                               try {
                                   ActionEvent event = null;
                                   Outil.loadSub(event, "modification de compte", "/accuil/ModifierCompt.fxml");
                               } catch (Exception ex) {ex.printStackTrace();}


                           }
                       }
                    }
        }
        public void ViewImage(String sql, int id) {

                try {
                    db.preparedStatement(sql);
                    db.getPstmt().setInt(1, id);
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
        public void LoadImg(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.gif", "*.jpg"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            fis = new FileInputStream(file);
            if (file != null) {
                System.out.println("" + file.getAbsolutePath());
                image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
                imageView.setPreserveRatio(true);

                if (caissierparams != null) {
                        int id = caissierparams.getId();
                        String sql = "UPDATE caissier SET image= ? WHERE id_caissier = ?";
                        db.preparedStatement(sql);
                        db.getPstmt().setBinaryStream(1, fis, file.length());
                        db.getPstmt().setInt(2, id);
                        db.executUpdate(sql);
                        imageView.setImage(image);

                    }
                if (agentparams != null) {
                        int id = agentparams.getId();
                            String sql = "UPDATE agent SET image= ? WHERE id_agent = ?";
                            db.preparedStatement(sql);
                            db.getPstmt().setBinaryStream(1, fis, file.length());
                            db.getPstmt().setInt(2, id);
                            db.executUpdate(sql);
                            imageView.setImage(image);
                        }
                if (adminparams != null) {
                        int id = adminparams.getId();
                        String sql = "UPDATE admin SET image= ? WHERE id = ?";
                        db.preparedStatement(sql);
                        db.getPstmt().setBinaryStream(1, fis, file.length());
                        db.getPstmt().setInt(2, id);
                        db.executUpdate(sql);
                        imageView.setImage(image);

                    }
            }



        }

            public void deconnection(ActionEvent event){
                try {
                    db.closConnection();
                    caissierparams=null;
                    agentparams=null;
                    adminparams=null;
                   Outil.load(event, "Reconnxion", "/main/main.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Userfxml(ActionEvent event){
                if (adminparams!=null){
                    try {
                        Outil.loadSub(event,"Gestion User", "/user/FUser.fxml");
                    } catch (Exception ex) {ex.printStackTrace();}
                }
                else {
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alerte");
                    alert.setContentText("Vous avez pas le droit de gerer les utilisateurs !!!");
                    alert.showAndWait();
                }
            }
            public void Agentfxml(ActionEvent event){
                if (adminparams!=null){
                    try {
                        Outil.loadSub(event,"Gestion d'agent", "/agent/agent.fxml");
                    } catch (Exception ex) {ex.printStackTrace();}
                }
                else {
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alerte");
                    alert.setContentText("Vous avez pas le droit de gerer les utilisateurs !!!");
                    alert.showAndWait();
                }
            }
            public void Caissefxml(ActionEvent event){
                if (adminparams!=null){
                    try {
                        Outil.loadSub(event,"Gestion de Caissier(e)", "/caisse/caisse.fxml");
                    } catch (Exception ex) {ex.printStackTrace();}
                }
                else {
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alerte");
                    alert.setContentText("Vous avez pas le droit de gerer les utilisateurs !!!");
                    alert.showAndWait();
                }
            }
            public void Adminfxml(ActionEvent event){
                if (adminparams!=null){
                    try {
                        Outil.loadSub(event,"Gestion de Administrateur", "/admin/admin.fxml");
                    } catch (Exception ex) {ex.printStackTrace();}
                }
                else {
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alerte");
                    alert.setContentText("Vous avez pas le droit de gerer les utilisateurs !!!");
                    alert.showAndWait();
                }
            }
            public void VerificationAgent(String login){
                List<User>userList=user.listeUserAg();
                for (User u : userList){
                  if (u.getEmail().contains(login))
                      verification=true;
                }
             }
            public void VerificationCaissier(String login){
                List<User>userListC=user.listeUserC();
                for (User c : userListC) {
                    if (c.getEmail().contains(login))
                        verification = true;
                }
            }
            public void VerificationAdmin(String login){
                List<User>userListA=user.listeUserA();
                for (User a : userListA){
                    if (a.getEmail().contains(login))
                        verification=true;
                }
            }
            public void Locateur(ActionEvent event){
                try {

                    Outil.loadSub(event, "Gestion locateur", "/locateur/Locateur.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Locationfxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Gestion location", "/services/location/Location.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Listelocationfxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Liste de locations valide", "/services/location/Listelocation.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void locationRetourneefxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Liste de locations retournées", "/services/location/LocationRetournee.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Voiturefxml(ActionEvent event){
            try {

                Outil.loadSub(event, "Gestion vehicule", "/services/voiture/Voiture.fxml");
            } catch (Exception ex) {ex.printStackTrace();}
        }
            public void Retourfxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Gestion vehicule", "/services/retour/Retour.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void ListRetourfxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Liste de retoures", "/services/retour/ListRetour.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Facturefxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Liste de factures", "/services/facture/Facture.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Placefxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Gestion de parque(places)", "/services/place/Place.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }
            public void Recufxml(ActionEvent event){
                try {

                    Outil.loadSub(event, "Liste de reçus", "/services/recu/Recu.fxml");
                } catch (Exception ex) {ex.printStackTrace();}
            }




        }
