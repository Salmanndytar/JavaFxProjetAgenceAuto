package sourse.classes;

import java.time.LocalDate;

public  class User {
    public User()
    {
    }
    private int id;
    private String email;
    private String  password;
    private LocalDate date;
    private Caissier caissier = new Caissier();
    private Agent agent = new Agent();
    private Admin admin = new Admin();

    public User(int id, String email, String password, Caissier caissier, Agent agent, Admin admin,LocalDate date) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.caissier = caissier;
        this.agent = agent;
        this.admin = admin;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Caissier getCaissier() {
        return caissier;
    }

    public void setCaissier(Caissier caissier) {
        this.caissier = caissier;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
