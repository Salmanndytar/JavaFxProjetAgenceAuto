package sourse.classes;

public class Place {
    private int id;
    private int etat;
    private String code;

    public Place() {
    }

    public Place(int id,int etat, String code) {
        this.id = id;
        this.etat = etat;
        this.code = code;
    }

    public int getId() {
        return id;
    }
    public int getEtat() {
        return etat;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
