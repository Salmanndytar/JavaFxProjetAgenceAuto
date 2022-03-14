package sourse.classes;

import javafx.scene.image.Image;

public class Agent {
    public Agent()
    {

    }

    private int id;
    private String nom;
    private String prenom;
    private String tel;
    private int etat;
    private Image image;

    public Agent(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Agent(int id,String nom, String prenom, String tel,int etat) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.etat = etat;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(int id) {this.id=id;
    }

    public void setTel(String tel) {this.tel=tel;
    }

    public int getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return nom+" "+prenom;
    }
}
