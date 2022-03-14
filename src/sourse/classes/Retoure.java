package sourse.classes;

import java.time.LocalDate;

public class Retoure {
    private int id;
    private int valide;
    private LocalDate date;
    Location location = new Location();
    Agent agent = new Agent();
    Caissier caissier = new Caissier();

    public Retoure() {
    }

    public Retoure(int id, int valide,LocalDate date, Location location) {
        this.id = id;
        this.valide = valide;
        this.date = date;
        this.location = location;
    }

    public Retoure(Agent agent, Caissier caissier) {
        this.agent = agent;
        this.caissier = caissier;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Caissier getCaissier() {
        return caissier;
    }

    public void setCaissier(Caissier caissier) {
        this.caissier = caissier;
    }

    public int getValide() {
        return valide;
    }

    public void setValide(int valide) {
        this.valide = valide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return id+"";
    }
}
