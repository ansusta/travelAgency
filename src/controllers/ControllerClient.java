package controllers;

import java.sql.SQLException;
import java.util.List; // ADD THIS IMPORT
import agencevoyage.views.ClientPanel;
import javax.swing.JOptionPane;
import models.ModelClient;

public class ControllerClient {
    private ClientPanel vue;
    
    public ControllerClient(ClientPanel vue) {
        this.vue = vue;
    }
    
    public void addClient(ModelClient client) { 
        try {
            ModelClient.addClient(client);
            listClients(); // This will refresh the table
        } catch (SQLException e) {
            // vue.showMessage("Error adding client: " + e.getMessage());
        }
    }
    
public void listClients() {
    try {
        List<ModelClient> clients = ModelClient.getAllClients();
        Object[][] clientData = new Object[clients.size()][5];
        for (int i = 0; i < clients.size(); i++) {
            ModelClient client = clients.get(i);
            clientData[i][0] = client.getId();           // ID
            clientData[i][1] = client.getNom();          // Nom
            clientData[i][2] = client.getPrenom();       // Prénom
            clientData[i][3] = client.getNumTel();       // FIXED: was getTel()
            clientData[i][4] = client.getFidelite();     // Niveau Fidélité
        }
        vue.updateClientTable(clientData);
        System.out.println("Loaded " + clients.size() + " clients"); // DEBUG
    } catch (SQLException e) {
        e.printStackTrace(); // DEBUG: Check console for errors
    }
}

public void updateClient(Integer id , String updatedName, String updatedSurname, String updatedNumTel, int updatedFidelite) {
    try {
        ModelClient.updateClient(id,updatedName,updatedSurname,updatedNumTel,updatedFidelite);
       
      //      vue.showMessage("client updated successfully.");
            listClients(); 
        
    } catch (SQLException e) {
        e.printStackTrace(); 
     //   vue.showMessage("Error updating client: " + e.getMessage());
    }
}

  public void deleteClient(Integer id){
       try {
      ModelClient.deleteClient(id );
    //  vue.showMessage("client updated successfully.");
listClients();
    } catch (SQLException e) {
         e.printStackTrace(); 
     //   vue.showMessage("Error deleting client: " + e.getMessage());
    }
  }
  
  
    public void searchClient(String term) {
    try {
        List<ModelClient> clients = ModelClient.searchClients(term);

        Object[][] data = new Object[clients.size()][5];
        for (int i = 0; i < clients.size(); i++) {
            ModelClient c = clients.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getNom();
            data[i][2] = c.getPrenom();
            data[i][3] = c.getNumTel();
            data[i][4] = c.getFidelite();
        }

        vue.updateClientTable(data); // 🔥 THIS WAS MISSING

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(vue,
                "Erreur lors de la recherche",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
    


}
