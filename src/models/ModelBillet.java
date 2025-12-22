
package models;

import java.util.Date;

public class ModelBillet extends ModelTraitement {

    private String typeBillet;
    private String destination;
    private Date dateBillet;

    // Getters
    public String getTypeBillet() { return typeBillet; }
    public String getDestination() { return destination; }
    public Date getDateBillet() { return dateBillet; }

    // Setters
    public void setTypeBillet(String typeBillet) { this.typeBillet = typeBillet; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDateBillet(Date dateBillet) { this.dateBillet = dateBillet; }

    public ModelBillet(Integer idTraitement, Date dateT, String Obs,String client ,
                        String partenariat, String employe, String etat, Double prix) {
        super(idTraitement, dateT, Obs,client , partenariat, employe, etat, prix);
    }

    public ModelBillet(Date dateT, String Obs,String client ,
                        String partenariat, String employe, String etat, Double prix) {
        super(dateT, Obs,client , partenariat, employe, etat, prix);
    }
}
