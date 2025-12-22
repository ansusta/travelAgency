
package models;

import java.util.Date;


public class ModelReservationHotel extends ModelTraitement {

    private Date dateDebut;
    private Date dateFin;
    private String nomHotel;
    private String ville;
    private String typeChambre;

    // Getters
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public String getNomHotel() { return nomHotel; }
    public String getVille() { return ville; }
    public String getTypeChambre() { return typeChambre; }

    // Setters
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public void setNomHotel(String nomHotel) { this.nomHotel = nomHotel; }
    public void setVille(String ville) { this.ville = ville; }
    public void setTypeChambre(String typeChambre) { this.typeChambre = typeChambre; }

    public ModelReservationHotel(Integer idTraitement, Date dateT, String Obs, String client ,
                                 String partenariat, String employe, String etat, Double prix) {
        super(idTraitement, dateT, Obs,client ,  partenariat, employe, etat, prix);
    }

    public ModelReservationHotel(Date dateT, String Obs,String client , 
                                 String partenariat, String employe, String etat, Double prix) {
        super(dateT, Obs,client ,  partenariat, employe, etat, prix);
    }
}