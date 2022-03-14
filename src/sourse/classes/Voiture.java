package sourse.classes;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Voiture {
    public Voiture()
    {
    }
    private String matrcule;
    private String marque;
    private String model;
    private String vitesse;
    private String couleur;
    private String carburent;
    private String poids;
    private int id;
    private float prix;
    private LocalDate dateMise;
    private Place place = new Place();
    private  int etat,disfonction;
    private Image image;

    public Voiture(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Voiture(String matrcule,int etat,int disfonction, String vitesse, String marque, String model, String couleur, String carburent, String poids, int id, float prix, LocalDate dateMise, Place place) {
        this.matrcule = matrcule;
        this.marque = marque;
        this.model = model;
        this.couleur = couleur;
        this.carburent = carburent;
        this.poids = poids;
        this.id = id;
        this.etat = etat;
        this.disfonction = disfonction;
        this.prix = prix;
        this.dateMise = dateMise;
        this.place = place;
        this.vitesse = vitesse;
    }

    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }
    public int getDisfonction() {
        return disfonction;
    }
    public String getMatrcule() {
        return matrcule;
    }
    public void setDisfonction(int disfonction) { this.disfonction = disfonction;}
    public void setMatrcule(String matrcule) {
        this.matrcule = matrcule;
    }
    public String getVitesse() {
        return vitesse;
    }
    public void setVitesse(String vitesse) {
        this.vitesse = vitesse;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    public String getCarburent() {
        return carburent;
    }
    public void setCarburent(String carburent) {
        this.carburent = carburent;
    }
    public String getPoids() {
        return poids;
    }
    public void setPoids(String poids) {
        this.poids = poids;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getPrix() {
        return prix;
    }
    public void setPrix(float prix) {
        this.prix = prix;
    }
    public LocalDate getDateMise() {
        return dateMise;
    }
    public void setDateMise(LocalDate dateMise) {
        this.dateMise = dateMise;
    }
    public Place getPlace() {
        return place;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return matrcule;
    }
}
