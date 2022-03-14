package services.retour;

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
import java.util.stream.Collectors;

import static main.MainController.*;
import static main.MainController.caissierparams;

public class RetourController implements Initializable {

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
    private Button recuvalider;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private DatePicker dateR;
    @FXML
    private int id;
    @FXML
    private Label recunbjour;
    @FXML
    private Label recuprixR;
    @FXML
    private Label recuprixP;
    @FXML
    private Label recuprixT;
    @FXML
    private Label recupenalite;
    @FXML
    private Label recunbjourDepasse;

    @FXML
    private TableView<Retoure> tableretoure;
    @FXML
    private TableColumn<Location, Integer> colid;
    @FXML
    private TableColumn<Location, String>colDateR;
    @FXML
    private TableColumn<Location, String>collocation;
    @FXML
    private TableColumn<Location, String>colagent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateR.setCellValueFactory(new PropertyValueFactory<>("Date"));
        collocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colagent.setCellValueFactory(new PropertyValueFactory<>("agent"));

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
            imprimerbt.setVisible(false);
            validebt.setVisible(false);
            locationComboBox.setVisible(false);
            cacher.setVisible(true);
        }


        ChargeRetoure();
        ChargeComboLocation();

    }
    Voiture voiture= new Voiture();
    IVoiture iVoiture = new VoitureImpl();

    ILocation iLocation =new LocationImpl();
  Facture facture = new Facture();
  IFacture iFacture = new FactureImpl();
    Retoure retoure = new Retoure();
    IRetour iRetour = new RetourImpl();
    Recu recu = new Recu();
    IRecu iRecu = new RecuImpl();

    public void insertion(ActionEvent event) {
        if(locationComboBox.getValue() == null ||dateR.getValue()== null) {
            Alerte("Veuillez ramplire tous les champs ");

        }else
        {
            if (locationComboBox.getValue().getDateV().isAfter(dateR.getValue())){
                Alerte("La date saisie est incorrecte !");
            } else {
                retoure.setDate(dateR.getValue());
                retoure.setLocation(locationComboBox.getValue());
                retoure.setValide(0);
                retoure.setAgent(agentparams);
                int ok = iRetour.addRetoure(retoure);
                if (ok != 0) {
                    AddValfrecu();
                    ModifVoitureEtat0();
                    ChargeRetoure();
                    Annuler();
                    Actualiser(event);
                }
            }
        }
    }
    public void Annuler(){
        dateR.setValue(null);
        locationComboBox.setValue(null);
        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        if (adminparams==null || caissierparams==null)
            validebt.setVisible(true);

    }
    public void Modifier(ActionEvent event){
        if(locationComboBox.getValue() == null ||dateR.getValue()== null) {
            Alerte("Veuillez ramplire tous les champs");

        }else if (locationComboBox.getValue().getDateV().isAfter(dateR.getValue())){
                Alerte("date invalide !");
            }else {
                retoure.setId(id);
                retoure.setId(id);
                retoure.setDate(dateR.getValue());
                retoure.setLocation(locationComboBox.getValue());
                retoure.setValide(0);
                retoure.setAgent(agentparams);

                int ok= iRetour.updatRetoure(retoure);
                if (ok!=0 ) {
                    boolean matri = ControlMatricule();
                    if (matri != true){
                        ModifVoitureEtat0();
                        ModifVoitureEtat1();
                    }
                    Actualiser(event);
                    ChargeRetoure();
                    ChargeComboLocation();
                    Annuler();
            }else Alerte("Opertion non effectuee");
        }
    }
    public void Supprimer(ActionEvent event) {
        boolean confirmation = AlertConfirmation("Vous etes sure de vouloire supprimer");
        if (confirmation == true){
            Modifrecu();
           int ok= iRetour.suppRetoure(tableretoure.getSelectionModel().getSelectedItem().getLocation().getId());
            if (ok!=0){
                        ModifVoitureEtat1();
                        Actualiser(event);
                        ChargeComboLocation();
                        ChargeRetoure();
                        Annuler();

            }
        }
    }
    public void clockLingne() {
        Retoure retoure1= tableretoure.getSelectionModel().getSelectedItem();
        if (retoure1!=null){
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
                recuvalider.setDisable(false);
                modifierbt.setVisible(false);
                supprimerbt.setVisible(false);
                imprimerbt.setVisible(false);
                validebt.setVisible(false);
            }
            id=retoure1.getId();
            dateR.setValue(retoure1.getDate());
            locationComboBox.setValue(retoure1.getLocation());

            LocalDate datD =retoure1.getLocation().getDateD();
            LocalDate datF = retoure1.getLocation().getDateF();
            int  nbjour=Outil.CalculJours(datD,datF);
            float prix = (retoure1.getLocation().getVoiture().getPrix());
            prix = prix*(nbjour);
            float demiprix = prix/2;
            float penalite =0;
            float prixtotal;
            recuprixP.setText("Montant payé:  " + demiprix + " FCFA");
            recuprixR.setText("Montant restant:  " + demiprix + " FCFA");
            recunbjour.setText("Nb de jours inscrit:  " + nbjour + " J");
            int jrDepasse = Outil.CalculJours(datF,dateR.getValue());

            if (jrDepasse >0){
                penalite = retoure1.getLocation().getVoiture().getPrix()*jrDepasse;
                recupenalite.setText("Penalite:  "+penalite);
                recunbjourDepasse.setText("Nb de jours depasse:  "+jrDepasse+"  J");
            }else recupenalite.setText("Penalite:  0");

            prixtotal =prix+penalite;
            recuprixT.setText("Prix total:  " + prixtotal + " FCFA");


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
    public void ChargeComboLocation(){
        ObservableList<Location> locationObservableList= FXCollections.observableArrayList();
        List<Location> locationListe=iLocation.listeLocation().stream().collect(Collectors.toList());

    for (Location l : locationListe){
            locationObservableList.add(l);
    }
    locationComboBox.setItems(locationObservableList);

    }
    public void ChargeRetoure(){
        ObservableList<Retoure> retoureObservableList = FXCollections.observableArrayList();
        List<Retoure>retoureList =iRetour.listeRetoureEtat();
        for (Retoure r : retoureList){
            retoureObservableList.add(r);
        }
        tableretoure.setItems(retoureObservableList);
    }
    public void Actualiser(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Outil.load(event, "Gestion vehicule", "/services/retour/Retour.fxml");
        }catch (Exception ex){ex.printStackTrace();}

    }
    public void ModifVoitureEtat0(){

       Voiture v =locationComboBox.getValue().getVoiture();
        voiture.setId( v.getId());
        voiture.setMatrcule(v.getMatrcule());
        voiture.setPoids(v.getPoids());
        voiture.setVitesse(v.getVitesse());
        voiture.setPlace(v.getPlace());
        voiture.setCarburent(v.getCarburent());
        voiture.setCouleur(v.getCouleur());
        voiture.setDateMise(v.getDateMise());
        voiture.setPrix(v.getPrix());
        voiture.setMarque(v.getMarque());
        voiture.setModel(v.getModel());
        voiture.setEtat(0);
        iVoiture.updatVoiture(voiture);
    }
    public void ModifVoitureEtat1(){

       Retoure r = tableretoure.getSelectionModel().getSelectedItem();
        voiture.setId( r.getLocation().getVoiture().getId());
        voiture.setMatrcule(r.getLocation().getVoiture().getMatrcule());
        voiture.setPoids(r.getLocation().getVoiture().getPoids());
        voiture.setVitesse(r.getLocation().getVoiture().getVitesse());
        voiture.setPlace(r.getLocation().getVoiture().getPlace());
        voiture.setCarburent(r.getLocation().getVoiture().getCarburent());
        voiture.setCouleur(r.getLocation().getVoiture().getCouleur());
        voiture.setDateMise(r.getLocation().getVoiture().getDateMise());
        voiture.setPrix(r.getLocation().getVoiture().getPrix());
        voiture.setMarque(r.getLocation().getVoiture().getMarque());
        voiture.setModel(r.getLocation().getVoiture().getModel());
        voiture.setEtat(1);
        iVoiture.updatVoiture(voiture);
    }
    public boolean ControlMatricule(){
        boolean ok =false;
        if (locationComboBox.getValue().getVoiture()==tableretoure.getSelectionModel().getSelectedItem().getLocation().getVoiture())
            ok= true;
        return ok;
    }
    public void ValiderLocation(ActionEvent event) {
        boolean ok = AlertConfirmation("Vous etes sure de vouloire valider  ?");
        retoure.setLocation(tableretoure.getSelectionModel().getSelectedItem().getLocation());
        retoure.setValide(1);
        retoure.setCaissier(caissierparams);
        recu.setRetoure(tableretoure.getSelectionModel().getSelectedItem());
        if (ok==true){
            iRetour.ValideRetoure(retoure);
            ModifVoitureEtat0();
            addFacture();
            ChargeRetoure();
            Actualiser(event);
           }

    }//le contenu ce valide retoure
    public  void addFacture(){
        Retoure retoure1 = tableretoure.getSelectionModel().getSelectedItem();
        LocalDate datD =retoure1.getLocation().getDateD();
        LocalDate datF = retoure1.getLocation().getDateF();
        int  nbjour=Outil.CalculJours(datD,datF);
        float prix = (retoure1.getLocation().getVoiture().getPrix());
        prix = prix*(nbjour);
        float penalite =0;
        float prixtotal;
        int jrDepasse = Outil.CalculJours(datF,dateR.getValue());

        if (jrDepasse >0)
            penalite = retoure1.getLocation().getVoiture().getPrix()*jrDepasse;
         prixtotal =prix+penalite;
         facture.setDate(LocalDate.now());
         facture.setMontant(prixtotal);
         facture.setPenalite(penalite);
         facture.setRetoure(retoure1);
         iFacture.addFacture(facture);


    }
    public void AddValfrecu(){
        Retoure idretoure = null;
        List<Retoure>list =iRetour.listeRetoureEtat();
        Location idlocation=null;
        for (Retoure l : list){
            idretoure = l;
        }
        recu.setLocation(locationComboBox.getValue());
        recu.setRetoure(idretoure);
        iRecu.ValideRecu(recu);
    }
    public void Modifrecu() {

        LocalDate dateF = locationComboBox.getValue().getDateF();
        LocalDate dateD = locationComboBox.getValue().getDateD();
        int nbjour = Outil.CalculJours(dateD,dateF);

        float prix = (locationComboBox.getValue().getVoiture().getPrix());
        prix = prix*(nbjour);
        float demiprix = prix/2;

        recu.setDuree(nbjour);
        recu.setMontantRestant(demiprix);
        recu.setMontantVerse(demiprix);
        recu.setLocation(locationComboBox.getValue());
        iRecu.updatRecu(recu);

    }



}
