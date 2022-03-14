package sourse.classes;

public class Recu {
    private int id;
    private int duree;
    private Location location = new Location();
    private Retoure retoure = new Retoure();
    private float montantVerse, montantRestant;


    public Recu() {
    }

    public Recu(int id,int duree, Location location, Retoure retoure) {
        this.id = id;
        this.duree = duree;
        this.location = location;
        this.retoure = retoure;
    }

    public int getId() {
        return id;
    }
    public int getDuree() {
        return duree;
    }

    public Recu(float montantVerse, float montantRestant) {
        this.montantVerse = montantVerse;
        this.montantRestant = montantRestant;
}

    public float getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(float montantVerse) {
        this.montantVerse = montantVerse;
    }

    public float getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(float ontantRestant) {
        this.montantRestant = ontantRestant;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Retoure getRetoure() {
        return retoure;
    }

    public void setRetoure(Retoure retoure) {
        this.retoure = retoure;
    }
}
