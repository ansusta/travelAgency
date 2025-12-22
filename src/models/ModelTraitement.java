package models;

import java.util.Date;
import java.time.LocalTime;
import agencevoyage.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class ModelTraitement {

    private Integer idTraitement;
    private Date dateT;
    private String Obs;
    private String client;
    private String partenariat;
    private String employe;
    private String etat;
    private Double prix;

    // Getters
    public String getClient(){return client;}
    public Integer getIdTraitement() { return idTraitement; }
    public Date getDateT() { return dateT; }
    public String getObs() { return Obs; }
    public String getPartenariat() { return partenariat; }
    public String getEmploye() { return employe; }
    public String getEtat() { return etat; }
    public Double getPrix() { return prix; }

    // Setters
    public void setClient(String client){this.client=client;}
    public void setIdTraitement(Integer idTraitement) { this.idTraitement = idTraitement; }
    public void setDateT(Date dateT) { this.dateT = dateT; }
    public void setObs(String obs) { this.Obs = obs; }
    public void setPartenariat(String partenariat) { this.partenariat = partenariat; }
    public void setEmploye(String employe) { this.employe = employe; }
    public void setEtat(String etat) { this.etat = etat; }
    public void setPrix(Double prix) { this.prix = prix; }

    // Constructors
    public ModelTraitement(Integer idTraitement, Date dateT, String Obs,String client,
                           String partenariat, String employe, String etat, Double prix) {
        this.idTraitement = idTraitement;
        this.dateT = dateT;
        this.Obs = Obs;
        this.client=client;
        this.partenariat = partenariat;
        this.employe = employe;
        this.etat = etat;
        this.prix = prix;
    }

    public ModelTraitement(Date dateT, String Obs, String client, 
                           String partenariat, String employe, String etat, Double prix) {
        
        this.dateT = dateT;
        this.Obs = Obs;
        this.client=client;
        this.partenariat = partenariat;
        this.employe = employe;
        this.etat = etat;
        this.prix = prix;
    }
}



class ModelVisa extends ModelTraitement {

    private String pays;

    // Getter & Setter
    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public ModelVisa(Integer idTraitement, Date dateT, String Obs,String client,
                     String partenariat, String employe, String etat, Double prix, String pays) {
        super(idTraitement, dateT, Obs, client, partenariat, employe, etat, prix);
        this.pays = pays;
    }

    public ModelVisa(Date dateT, String Obs, String client , 
                     String partenariat, String employe, String etat, Double prix, String pays) {
        super(dateT, Obs, client, partenariat, employe, etat, prix);
        this.pays = pays;
    }
}





