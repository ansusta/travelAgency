
package models;

import agencevoyage.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelVisaEtudes extends ModelVisa{

    public ModelVisaEtudes(Integer idTraitement, Date dateT, String Obs,String client , String partenariat, String employe, String etat, Double prix, String pays) {
        super(idTraitement, dateT, Obs,client , partenariat, employe, etat, prix, pays);
    }
    
            public ModelVisaEtudes(Date dateT, String Obs ,String client , String partenariat, String employe, String etat, Double prix, String pays) {
        super(dateT, Obs, client , partenariat, employe, etat, prix, pays);
    }
            
            
                   
public static List<ModelVisaEtudes> getAllVisaEtudes() throws SQLException {
    Connection connection = DataBaseConnection.getConnection();

    String sql = "SELECT t.idTraitement, t.date, t.observation, CONCAT(c.nomClient, ' ', c.prenom) AS nomclient, CONCAT(e.nomEmploye, ' ', e.prenomEmploye) AS nomemploye, CONCAT(p.nom, ' ', p.prenom) AS nompartenariat, t.etatTraitement, t.prix, v.pays FROM traitement t JOIN visa v ON v.idVisa = t.idTraitement JOIN visa_etudes va ON va.idVisa = v.idVisa JOIN client c ON c.idClient = t.idClient JOIN employe e ON e.idEmploye=t.idEmploye LEFT JOIN partenariats p ON p.idPartenariat = t.idPartenariat;";
    List<ModelVisaEtudes> visas = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            visas.add(new ModelVisaEtudes(
                rs.getInt("idTraitement"),
                rs.getDate("date"),
                rs.getString("observation"),
                rs.getString("nomclient"),
                rs.getString("nompartenariat"),
                rs.getString("nomemploye"),
                rs.getString("etatTraitement"),
                rs.getDouble("prix"),
                rs.getString("pays")
            ));
        }
    }

    return visas;
}


    
    
 public static void addVisaEtudes(ModelVisa data) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        
        Integer clientId = getIdFromName(connection, "client", data.getClient());
        Integer employeId = getIdFromName(connection, "employe", data.getEmploye());
        Integer partnerId = getIdFromName(connection, "partenariat", data.getPartenariat());

        String sqlTraitement = "INSERT INTO traitement (date, etatTraitement, idClient, idEmploye, idPartenariat, prix, observation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlVisa = "INSERT INTO visa (idVisa, pays) VALUES (?, ?)";
        String sqlVisaTouristique = "INSERT INTO visa_etudes (idVisa) VALUES (?)";

        try {
            connection.setAutoCommit(false);
            int generatedId = -1;

            try (PreparedStatement stmt = connection.prepareStatement(sqlTraitement, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, new java.sql.Date(data.getDateT().getTime()));
                stmt.setString(2, mapEtat(data.getEtat()));

                
                if (clientId != null) stmt.setInt(3, clientId); else stmt.setNull(3, Types.INTEGER);
                if (employeId != null) stmt.setInt(4, employeId); else stmt.setNull(4, Types.INTEGER);
                if (partnerId != null) stmt.setInt(5, partnerId); else stmt.setNull(5, Types.INTEGER);
                
                stmt.setDouble(6, data.getPrix());
                stmt.setString(7, data.getObs());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) generatedId = rs.getInt(1);
                }
            }

            if (generatedId == -1) throw new SQLException("Failed to retrieve ID from Traitement.");

            try (PreparedStatement stmt = connection.prepareStatement(sqlVisa)) {
                stmt.setInt(1, generatedId);
                stmt.setString(2, data.getPays());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlVisaTouristique)) {
                stmt.setInt(1, generatedId);
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
    
    
        public static void deleteVisaEtudes(Integer id) throws SQLException {
    Connection connection = DataBaseConnection.getConnection();

    String sql1 = "DELETE FROM visa_etudes WHERE idVisa = ?";
    String sql2 = "DELETE FROM visa WHERE idVisa = ?";
    String sql3 = "DELETE FROM traitement WHERE idTraitement = ?";

    try {
        connection.setAutoCommit(false); // start transaction

        try (PreparedStatement stmt1 = connection.prepareStatement(sql1);
             PreparedStatement stmt2 = connection.prepareStatement(sql2);
             PreparedStatement stmt3 = connection.prepareStatement(sql3)) {

            stmt1.setInt(1, id);
            stmt1.executeUpdate();

            stmt2.setInt(1, id);
            stmt2.executeUpdate();

            stmt3.setInt(1, id);
            stmt3.executeUpdate();
        }

        connection.commit(); // success
    } catch (SQLException e) {
        connection.rollback(); // rollback on error
        throw e;
    } finally {
        connection.setAutoCommit(true);
    }
}
}
