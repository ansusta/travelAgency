package models;

import java.util.Date;

public class ModelVoyageOrganise extends ModelTraitement {

    private Date dateD;
    private Date dateF;
    private String destination;
    private String description;

    // Getters
    public Date getDateD() { return dateD; }
    public Date getDateF() { return dateF; }
    public String getDestination() { return destination; }
    public String getDescription() { return description; }

    // Setters
    public void setDateD(Date dateD) { this.dateD = dateD; }
    public void setDateF(Date dateF) { this.dateF = dateF; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDescription(String description) { this.description = description; }

    public ModelVoyageOrganise(Integer idTraitement, Date dateT, String Obs, String client,
                               String partenariat,String employe, String etat, Double prix) {
        super(idTraitement, dateT, Obs, client ,partenariat, employe, etat, prix);
    }

    public ModelVoyageOrganise(Date dateT, String Obs,String client,
                               String partenariat, String employe, String etat, Double prix) {
        super(dateT, Obs, client  , partenariat, employe, etat, prix);
    }
}
