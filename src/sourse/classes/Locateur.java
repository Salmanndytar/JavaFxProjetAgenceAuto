package sourse.classes;

public class Locateur {
    public Locateur()
    {
    }
    private String nom,numCarte,tel;
    private int id;

    public Locateur(int id, String nom, String numCarte, String tel) {
        this.id = id;
        this.nom = nom;
        this.numCarte = numCarte;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public String getTel() {
        return tel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return numCarte;
    }
}
