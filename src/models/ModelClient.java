package models;

import agencevoyage.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ModelClient {
     private Integer id;
    private String nom;
    private String prenom;
    private String numTel;
    private int fidelite;
    
    public ModelClient(Integer id,String nom, String prenom, String numTel, int fidelite ){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.fidelite = fidelite;
    }
        public ModelClient(String nom, String prenom, String numTel, int fidelite ){
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.fidelite = fidelite;
    }
        
        
        
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getNumTel() {
        return numTel;
    }
    public Integer getId(){
    return id;
}
    public int getFidelite(){
        return fidelite;
    }
    
    
    
    public static List<ModelClient> searchClients(String term) throws SQLException {
    Connection connection = DataBaseConnection.getConnection();
    String sql = "SELECT * FROM client WHERE nomClient LIKE ? OR prenom LIKE ? OR numTel LIKE ?";
    List<ModelClient> clients = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        String searchPattern = "%" + term + "%";
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);
        stmt.setString(3, searchPattern);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(new ModelClient(
                    rs.getInt("idClient"),
                    rs.getString("nomClient"),
                    rs.getString("prenom"),
                    rs.getString("numTel"),
                    rs.getInt("fidelite")
                ));
            }
        }
    }
    return clients;
}
    
    
         public static void addClient( ModelClient client) throws SQLException {
             Connection connection = DataBaseConnection.getConnection();
        String sql = "INSERT INTO client (nomClient, prenom, numTel,fidelite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.nom);
            stmt.setString(2, client.prenom);
            stmt.setString(3, client.numTel);
            stmt.setInt(4, client.fidelite);
            stmt.executeUpdate();
        }
    }
             public static void deleteClient( Integer id) throws SQLException {
                    Connection connection = DataBaseConnection.getConnection();
        String sql = "DELETE FROM client WHERE idClient=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
    }
             
             
        public static List<ModelClient> getAllClients() throws SQLException {
            Connection connection = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM client";
        List<ModelClient> clients = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(new ModelClient(
                        rs.getInt("idClient"),
                        rs.getString("nomClient"),
                        rs.getString("prenom"),
                         rs.getString("numTel"),
                        rs.getInt("fidelite")
                ));
            }
        }
        return clients;
    } 
    
    
       
            
   
      public static boolean updateClient(Integer id, String updatedName, String updatedSurname, String updatedPhone , int updatedFidelite) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
    String query = "UPDATE client SET nomClient = ?, prenom = ?, numTel = ?, fidelite = ? WHERE idClient=?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, updatedName);
        preparedStatement.setString(2, updatedSurname);
        preparedStatement.setString(3, updatedPhone);
        preparedStatement.setInt(4, updatedFidelite);
        preparedStatement.setInt(5, id);
        int rowsUpdated = preparedStatement.executeUpdate();
         return rowsUpdated > 0;
    }
}
    
    
        
        
    
}
