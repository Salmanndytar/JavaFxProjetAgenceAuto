package sourse.classes;

import java.time.LocalDate;

public class Facture {
    private int id;
    private LocalDate date;
    private float penalite;
    private float montant;
    private Retoure retoure = new Retoure();

    public Facture() {
    }

    public Facture(int id, LocalDate date, float penalite, float montant, Retoure retoure) {
        this.id = id;
        this.date = date;
        this.penalite = penalite;
        this.montant = montant;
        this.retoure = retoure;
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

    public float getPenalite() {
        return penalite;
    }

    public void setPenalite(float penalite) {
        this.penalite = penalite;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Retoure getRetoure() {
        return retoure;
    }

    public void setRetoure(Retoure retoure) {
        this.retoure = retoure;
    }
}
