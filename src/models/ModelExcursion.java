
package models;

import java.time.LocalTime;
import java.util.Date;

public class ModelExcursion extends ModelTraitement {

    private Date dateEx;
    private LocalTime heureDepart;
    private String destination;

    // Getters
    public Date getDateEx() { return dateEx; }
    public LocalTime getHeureDepart() { return heureDepart; }
    public String getDestination() { return destination; }

    // Setters
    public void setDateEx(Date dateEx) { this.dateEx = dateEx; }
    public void setHeureDepart(LocalTime heureDepart) { this.heureDepart = heureDepart; }
    public void setDestination(String destination) { this.destination = destination; }

    public ModelExcursion(Integer idTraitement, Date dateT, String Obs,String client ,
                          String partenariat, String employe, String etat, Double prix) {
        super(idTraitement, dateT, Obs, client , partenariat, employe, etat, prix);
    }

    public ModelExcursion(Date dateT, String Obs,String client ,
                          String partenariat, String employe, String etat, Double prix) {
        super(dateT, Obs,client , partenariat, employe, etat, prix);
    }
}
