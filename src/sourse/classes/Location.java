package sourse.classes;

import java.time.LocalDate;

public class Location {
    public Location()
    {
    }
    private int id;
    private Agent agents = new Agent();
    private Caissier caissier = new Caissier();
    private Locateur locateur = new Locateur();
    private Voiture voiture = new Voiture();
    private LocalDate DateD,DateF,DateV;
    private int valide;

    public Location(int id,int valide, Agent agents, Locateur locateur, Voiture voiture,Caissier caissier, LocalDate dateD, LocalDate dateF,LocalDate dateV) {
        this.id = id;
        this.valide = valide;
        this.agents = agents;
        this.locateur = locateur;
        this.voiture = voiture;
        this.DateD = dateD;
        this.DateF = dateF;
        this.caissier = caissier;
        this.DateV= dateV;
    }

    public int getId() {
        return id;
    }

    public int getValide() {
        return valide;
    }

    public void setValide(int valide) {
        this.valide = valide;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agent getAgents() {
        return agents;
    }

    public void setAgents(Agent agents) {
        this.agents = agents;
    }

    public Locateur getLocateur() {
        return locateur;
    }

    public void setLocateur(Locateur locateur) {
        this.locateur = locateur;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public LocalDate getDateD() {
        return DateD;
    }

    public void setDateD(LocalDate dateD) {
        DateD = dateD;
    }

    public LocalDate getDateF() {
        return DateF;
    }

    public LocalDate getDateV() {
        return DateV;
    }

    public void setDateV(LocalDate dateV) {
        DateV = dateV;
    }

    public void setDateF(LocalDate dateF) {
        DateF = dateF;
    }

    public Caissier getCaissier() {
        return caissier;
    }

    public void setCaissier(Caissier caissier) {
        this.caissier = caissier;
    }

    @Override
    public String toString() {
        return id+"";
    }
}
