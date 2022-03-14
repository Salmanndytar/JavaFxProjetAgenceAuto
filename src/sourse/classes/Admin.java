package sourse.classes;

import javafx.scene.image.Image;

public class Admin {
    private int id,etat;
    private String nom,prenom,tel;
    private Image image;

    public Admin() {
    }

    public Admin(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Admin(int id, String nom, int etat) {
        this.id = id;
        this.nom = nom;
        this.etat = etat;
    }

    public Admin(String prenom, String tel) {
        this.prenom = prenom;
        this.tel = tel;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return id+" "+nom+" "+prenom;
    }
}
