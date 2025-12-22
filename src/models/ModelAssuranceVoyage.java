
package models;

import java.util.Date;

class ModelAssuranceVoyage extends ModelTraitement {

    private String typeAssurance;
    private int duree;

    // Getters
    public String getTypeAssurance() { return typeAssurance; }
    public int getDuree() { return duree; }

    // Setters
    public void setTypeAssurance(String typeAssurance) { this.typeAssurance = typeAssurance; }
    public void setDuree(int duree) { this.duree = duree; }

    public ModelAssuranceVoyage(Integer idTraitement, Date dateT, String Obs,String client ,
                                String partenariat, String employe, String etat, Double prix) {
        super(idTraitement, dateT, Obs,client , partenariat, employe, etat, prix);
    }

    public ModelAssuranceVoyage(Date dateT, String Obs,String client ,
                                String partenariat, String employe, String etat, Double prix) {
        super(dateT, Obs,client , partenariat, employe, etat, prix);
    }
}