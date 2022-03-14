package services.location;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sourse.classeImple.*;
import sourse.classes.*;
import sourse.interphaces.*;
import sourse.outils.Outil;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import static main.MainController.*;

public class LocationController implements Initializable {

    @FXML
    private AnchorPane cacher;
    @FXML
    private Button validebt;
    @FXML
    private Button annulerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button imprimerbt;
    @FXML
    private Button modifierbt;
    @FXML
    private Button addlocateur;
    @FXML
    private Button recuvalider;
    @FXML
    private Button plusbt;


    @FXML
    private ComboBox<Voiture> voitureComboBox;
    @FXML
    private ComboBox<Locateur> locateurComboBox;

    @FXML
    private DatePicker dateD;
    @FXML
    private LocalDate dateF;

    @FXML
    private TextField verifiemarticule;
    @FXML
    private int id;
    @FXML
    private TextField nbjour;
    @FXML
    private Label infolocateur;
    @FXML
    private Label recuprixP;
    @FXML
    private Label recuprixR;
    @FXML
    private Label recuprixT;
    @FXML
    private Label recunbjour;
    @FXML
    private String infovoiture;
    @FXML
    private Location clicktable;

    @FXML
    private TableView<Location> tablelocation;
    @FXML
    private TableColumn<Location, Integer> colid;
    @FXML
    private TableColumn<Location, String>colDateD;
    @FXML
    private TableColumn<Location, String>colDateF;
    @FXML
    private TableColumn<Location, String>collocateur;
    @FXML
    private TableColumn<Location, String>colvoiture;
    @FXML
    private TableColumn<Location, String>colagent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateD.setCellValueFactory(new PropertyValueFactory<>("DateD"));
        colDateF.setCellValueFactory(new PropertyValueFactory<>("DateF"));
        collocateur.setCellValueFactory(new PropertyValueFactory<>("locateur"));
        colvoiture.setCellValueFactory(new PropertyValueFactory<>("voiture"));
        colagent.setCellValueFactory(new PropertyValueFactory<>("agents"));

        recuvalider.setDisable(true);
        cacher.setVisible(false);
        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        if (adminparams!=null)
            validebt.setVisible(false);

        if (caissierparams!=null){
            modifierbt.setVisible(false);
            supprimerbt.setVisible(false);
            validebt.setVisible(false);
            annulerbt.setVisible(false);
            addlocateur.setVisible(false);
            imprimerbt.setVisible(false);
            validebt.setVisible(false);
            cacher.setVisible(true);
            addlocateur.setDisable(false);
            plusbt.setVisible(false);
            locateurComboBox.setVisible(false);
            voitureComboBox.setVisible(false);
        }


        ChargeLocation();
        Locateurs();
        Voiture();
    }
    Voiture voiture= new Voiture();
    IVoiture iVoiture = new VoitureImpl();
    Location location = new Location();
    ILocation iLocation =new LocationImpl();
    ILocateur iLocateur = new LocatuerImpl();
    Recu recu = new Recu();
    IRecu iRecu = new RecuImpl();
    public void insertion(ActionEvent event) {
        if(voitureComboBox.getValue() == null ||locateurComboBox.getValue()== null|| nbjour.getText().equals(""))
        { Alerte("Veuillez ramplire tous les champs ");

        }else
        {
            if ( LocalDate.now().isAfter(dateD.getValue())){
                Alerte("la date saisie est invalide!");
            }else {

                location.setId(id);
                location.setDateD(dateD.getValue());
                location.setDateF(dateD.getValue().plusDays(Integer.parseInt(nbjour.getText())));
                location.setVoiture(voitureComboBox.getValue());
                location.setLocateur(locateurComboBox.getValue());
                location.setValide(0);
                location.setAgents(agentparams);

                int ok= iLocation.addLocation(location);
                if (ok!=0 )
                {
                    Addrecu();
                    ModifVoitureEtat1();
                    Actualiser(event);
                    ChargeLocation();
                    Annuler();
                }else Alerte("Operation non effectuee  ");

            }
        }
    }
    public void Annuler(){
        verifiemarticule.setText("");
        voitureComboBox.setValue(null);
        locateurComboBox.setValue(null);
        dateD.setValue(null);
        nbjour.setText("");
        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        if (adminparams==null || caissierparams==null)
            validebt.setVisible(true);

    }
    public void Modifier(ActionEvent event){
        if(voitureComboBox.getValue() == null ||locateurComboBox.getValue()== null)
        { Alerte("Veuillez ramplire tous les champs");;

        }else {
            location.setId(id);
            location.setDateD(dateD.getValue());
            location.setDateF(dateD.getValue().plusDays(Integer.parseInt(nbjour.getText())));
            location.setVoiture(voitureComboBox.getValue());
            location.setLocateur(locateurComboBox.getValue());
            location.setAgents(agentparams);
            boolean oki = AlertConfirmation("Vous etes sure de vouloire modifier ?"); //assuer la modificaton
           if (oki == true) {
               int ok= iLocation.updatLocation(location);  //modifier
                if (ok!=0 ) {
                    Modifrecu();
                    boolean modification = EditMatrVoiture();  //assuere la modification de matricule de voiture
                    if (modification == true){
                         ModifVoitureEtat0();//Libere la voiture//
                         ModifVoitureEtat1();// activer la voiture choisie
                    }

            }
               ChargeLocation();
               Actualiser(event);
               Voiture();
               Annuler();

            }
        }
    }
    public void Supprimer(ActionEvent event) {
        boolean confirmation = AlertConfirmation("Vous etes sure de vouloire supprimer");//assurer la suppression
        if (confirmation == true){
            int oki=iRecu.suppRecu(id);
            if (oki != 0) {
                int ok = iLocation.suppLocation(id);
                if (ok != 0)
                    ModifVoitureEtat0();//Changer l'etat de voiture a 0;
                ChargeLocation();
                Voiture();
                Actualiser(event);
                Annuler();
            }
        }
    }
    public void clockLingne() {
        Location location1= tablelocation.getSelectionModel().getSelectedItem();
        if (location1!=null){
            clicktable = location1;
            recuvalider.setDisable(false);
            modifierbt.setVisible(true);
            supprimerbt.setVisible(true);
            imprimerbt.setVisible(true);


            if (adminparams!=null){
                modifierbt.setVisible(false);
                supprimerbt.setVisible(false);
                imprimerbt.setVisible(false);
            }
            validebt.setVisible(false);
            if (caissierparams!=null) {
                modifierbt.setVisible(false);
                supprimerbt.setVisible(false);
                imprimerbt.setVisible(false);
                validebt.setVisible(false);
            }

            id=location1.getId();
            dateD.setValue(location1.getDateD());
            location.setVoiture(location1.getVoiture());
            voitureComboBox.setValue(location1.getVoiture());
            locateurComboBox.setValue(location1.getLocateur());
            nbjour.setText(String.valueOf(Outil.CalculJours(location1.getDateD(),location1.getDateF())));


            float demiprix = ( location1.getVoiture().getPrix()/ 2 );
            demiprix = demiprix*(Integer.valueOf(nbjour.getText()));
            float prix = ( location1.getVoiture().getPrix() );
            prix = prix*(Integer.valueOf(nbjour.getText()));
            recunbjour.setText(" Nb de jours: " + nbjour.getText() + " J");
            recuprixP.setText(String.valueOf("Prix à payé:  " + demiprix) + " FCFA");
            recuprixR.setText(String.valueOf("Prix restant:  " + demiprix) + " FCFA");
            recuprixT.setText(String.valueOf("Prix total:  " + prix) + " FCFA");
        }

    }
    public void Alerte(String message) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("alerte");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void AlertInfo(String message){

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setContentText(message);
        alert.showAndWait();

    }
    public boolean AlertConfirmation(String message){
        boolean confirmation = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attention");
        alert.setContentText(message);
        Optional<ButtonType> resultat = alert.showAndWait();
        if (resultat.get() == ButtonType.OK)
            confirmation = true;

        return confirmation;

    }
    public boolean EditMatrVoiture(){
        //Voire si la voitue selectionnee dans la table est differente de celui de comboBox
        boolean modfication = false;
        if (tablelocation.getSelectionModel().getSelectedItem().getVoiture()!=voitureComboBox.getValue())
            modfication = true;
        return modfication;
    }
    public void Locateurs(){
        ObservableList<Locateur> locateurObservableList= FXCollections.observableArrayList();
        List<Locateur> locateurListe=iLocateur.listeLocateur();
        for (Locateur l : locateurListe){
            locateurObservableList.add(l);
        }
        locateurComboBox.setItems(locateurObservableList);
    }
    public void ChargeLocation(){
        ObservableList<Location> locationObservableList = FXCollections.observableArrayList();
        List<Location>locationList =iLocation.listeLocationEtat();
        for (Location l : locationList){
            locationObservableList.add(l);
        }
        tablelocation.setItems(locationObservableList);
    }
    public void Voiture(){
        ObservableList<Voiture> voiturelistObservale= FXCollections.observableArrayList();
        List<Voiture> voitureList= iVoiture.listeVoitureEtat();
        for (Voiture v : voitureList){
            voiturelistObservale.add(v);
        }
        voitureComboBox.setItems(voiturelistObservale);
    }
    public void Verifilocateur(){
        List<Locateur> Listelo=iLocateur.listeLocateur();
        for (Locateur l : Listelo){
            if(l.getNumCarte().equals(verifiemarticule.getText())){
                infolocateur.setText("N° : "+l.getNumCarte()+"   -   Nom :"+l.getNom()+"   -    tel: "+l.getTel());
                break;
            }else infolocateur.setText(" Locateur inexistant ou verifier le numero");
        }

    }
    public void Actualiser(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Outil.load(event, "Gestion vehicule", "/services/location/Location.fxml");
        }catch (Exception ex){ex.printStackTrace();}

    }
    public void AddLocateur(ActionEvent event){
        try {
            Outil.loadSub(event, "Gestion locateur", "/locateur/Locateur.fxml");
        }catch (Exception ex){ex.printStackTrace();}

    }
    public void InfoVoiture() throws InterruptedException {

        ObservableList<Voiture> voiturelistObservale = FXCollections.observableArrayList();
        List<Voiture> voitureList = iVoiture.listeVoitureEtat();
        if (voitureComboBox.getValue()==null) {
            infovoiture="Veuillez d'abord selectionner\n une matricule pour plus d'info";
        } else {
            for (Voiture v : voitureList) {
                if (v.getMatrcule().contains(voitureComboBox.getValue().getMatrcule())) {
                    infovoiture="  Poids : " + voitureComboBox.getValue().getPoids() +
                            "\n  Marque :" + voitureComboBox.getValue().getMarque() +
                            "\n  Modele : " + voitureComboBox.getValue().getModel() +
                            "\n  Energie : " + voitureComboBox.getValue().getCarburent();
                    break;
                }
            }
        }
        AlertInfo(infovoiture);
    }
    public void Addrecu(){
        IRecu iRecu = new RecuImpl();
        List<Location> list=iLocation.listeLocationEtat();
        Location idlocation=null;
        for (Location l : list){
            idlocation= l;
        }
        float demiprix = ( voitureComboBox.getValue().getPrix()/ 2 );
        demiprix = demiprix*(Integer.valueOf(nbjour.getText()));
        float prix = ( voitureComboBox.getValue().getPrix() );
        prix = prix*(Integer.valueOf(nbjour.getText()));
        recu.setDuree(Integer.valueOf(nbjour.getText()));
        recu.setMontantRestant(demiprix);
        recu.setMontantVerse(demiprix);
        recu.setLocation(idlocation);
        iRecu.addRecu(recu);
    }
    public void Modifrecu(){

        dateF = dateD.getValue().plusDays(Integer.valueOf(nbjour.getText()));
        float prix = (voitureComboBox.getValue().getPrix());
        prix = prix*(Integer.valueOf(nbjour.getText()));
        float demiprix = prix/2;

        recu.setDuree(Integer.valueOf(nbjour.getText()));
        recu.setMontantRestant(demiprix);
        recu.setMontantVerse(demiprix);
        recu.setLocation(tablelocation.getSelectionModel().getSelectedItem());
        iRecu.updatRecu(recu);
    }
    public void ModifVoitureEtat1(){
        //Rendre l'etat=1 pour la voiture louee
        voiture.setId( voitureComboBox.getValue().getId());
        voiture.setMatrcule(voitureComboBox.getValue().getMatrcule());
        voiture.setPoids(voitureComboBox.getValue().getPoids());
        voiture.setVitesse(voitureComboBox.getValue().getVitesse());
        voiture.setPlace(voitureComboBox.getValue().getPlace());
        voiture.setCarburent(voitureComboBox.getValue().getCarburent());
        voiture.setCouleur(voitureComboBox.getValue().getCouleur());
        voiture.setDateMise(voitureComboBox.getValue().getDateMise());
        voiture.setPrix(voitureComboBox.getValue().getPrix());
        voiture.setMarque(voitureComboBox.getValue().getMarque());
        voiture.setModel(voitureComboBox.getValue().getModel());
        voiture.setEtat(1);
        iVoiture.updatVoiture(voiture);
    }
    public void ModifVoitureEtat0(){
        //rendre l'etat=0 pour la voiture liberee par modification
        voiture.setId( tablelocation.getSelectionModel().getSelectedItem().getVoiture().getId());
        voiture.setMatrcule(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getMatrcule());
        voiture.setPoids(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPoids());
        voiture.setVitesse(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getVitesse());
        voiture.setPlace(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPlace());
        voiture.setCarburent(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getCarburent());
        voiture.setCouleur(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getCouleur());
        voiture.setDateMise(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getDateMise());
        voiture.setPrix(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPrix());
        voiture.setMarque(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getMarque());
        voiture.setModel(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getModel());
        voiture.setEtat(0);
        iVoiture.updatVoiture(voiture);
    }
    public void ValiderLocation(ActionEvent event){
        boolean ok = AlertConfirmation("Vous etes sure de vouloire valider cette location ???");
        location.setId(id);
        location.setValide(1);
        location.setDateV(LocalDate.now());
        location.setCaissier(caissierparams);
        if (ok==true){
            iLocation.ValiderLocation(location);
            Actualiser(event);

        }

    }
}
