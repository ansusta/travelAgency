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
import java.sql.*;




class ModelTraitement {

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



public class ModelVisa extends ModelTraitement {

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
    
    private static Integer getIdFromName(Connection conn, String type, String fullName) throws SQLException {
        if (fullName == null || fullName.trim().isEmpty()) return null;
        
        String[] parts = fullName.split(" ", 2);
        String nom = parts[0];
        String prenom = (parts.length > 1) ? parts[1] : "";

        String sql = "";
        switch (type) {
            case "client":
                sql = "SELECT idClient FROM client WHERE nomClient = ? AND prenom = ?";
                break;
            case "employe":
                sql = "SELECT idEmploye FROM employe WHERE nomEmploye = ? AND prenomEmploye = ?";
                break;
            case "partenariat":
                sql = "SELECT idPartenariat FROM partenariats WHERE nom = ? AND prenom = ?";
                break;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null; 
    }
  
    
    private static String mapEtat(String uiEtat) {
    switch (uiEtat) {
        case "En cours": return "en_cours";
        case "Complet": return "complet";
        case "Annulé": return "annule";
        default: return null;
    }
}
    
    public static void updateVisa(Integer id, ModelVisa data) throws SQLException {
    Connection connection = DataBaseConnection.getConnection();

    Integer clientId = getIdFromName(connection, "client", data.getClient());
    Integer employeId = getIdFromName(connection, "employe", data.getEmploye());
    Integer partnerId = getIdFromName(connection, "partenariat", data.getPartenariat());

    String sqlTraitement =
        "UPDATE traitement SET date=?, etatTraitement=?, idClient=?, idEmploye=?, idPartenariat=?, prix=?, observation=? " +
        "WHERE idTraitement=?";

    String sqlVisa =
        "UPDATE visa SET pays=? WHERE idVisa=?";

    try {
        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement(sqlTraitement)) {
            stmt.setDate(1, new java.sql.Date(data.getDateT().getTime()));
            stmt.setString(2, mapEtat(data.getEtat()));
            stmt.setObject(3, clientId, Types.INTEGER);
            stmt.setObject(4, employeId, Types.INTEGER);
            stmt.setObject(5, partnerId, Types.INTEGER);
            stmt.setDouble(6, data.getPrix());
            stmt.setString(7, data.getObs());
            stmt.setInt(8, id);
            stmt.executeUpdate();
        }

        try (PreparedStatement stmt = connection.prepareStatement(sqlVisa)) {
            stmt.setString(1, data.getPays());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }

        connection.commit();
    } catch (SQLException e) {
        connection.rollback();
        throw e;
    } finally {
        connection.setAutoCommit(true);
    }
}

}





