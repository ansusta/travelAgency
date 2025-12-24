
package controllers;

import agencevoyage.views.TraitementPanel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import models.ModelVisa;
import models.ModelVisaTouristique;


public class ControllerVisaTouristique {
    
      private TraitementPanel vue;
         public ControllerVisaTouristique(TraitementPanel vue) {
        this.vue = vue;
    }
    
     public void listVisaTouristique() {
    try {
        List<ModelVisaTouristique> visas = ModelVisaTouristique.getAllVisaTouristique();
        Object[][] clientData = new Object[visas.size()][9];
        for (int i = 0; i < visas.size(); i++) {
            ModelVisaTouristique visa = visas.get(i);
            clientData[i][0] = visa.getIdTraitement();  
            clientData[i][1] = visa.getDateT();          // Nom
            clientData[i][2] = visa.getClient();       // Prénom
            clientData[i][3] = visa.getEmploye();       // FIXED: was getTel()
             clientData[i][4] = visa.getPartenariat(); 
            clientData[i][5] = visa.getEtat();     // Niveau Fidélité
            clientData[i][6] = visa.getPrix(); 
            clientData[i][7] = visa.getPays(); 
                clientData[i][8] = visa.getObs(); 
        }
      vue.updateVisaTouristiqueTable(clientData);

        System.out.println("Loaded " + visas.size() + " visas"); // DEBUG
    } catch (SQLException e) {
        e.printStackTrace(); // DEBUG: Check console for errors
    }
}
     
         public void addVisaTouristique(ModelVisaTouristique visa) { 
        try {
            ModelVisaTouristique.addVisaTouristique(visa);
            listVisaTouristique();
        } catch (SQLException e) {
            // vue.showMessage("Error adding client: " + e.getMessage());
        }
    }
         
                                 public void deleteVisaTouristique(Integer id){
       try {
      ModelVisaTouristique.deleteVisaTouristique(id );
    //  vue.showMessage("client updated successfully.");
listVisaTouristique();
    } catch (SQLException e) {
         e.printStackTrace(); 
     //   vue.showMessage("Error deleting client: " + e.getMessage());
    }
  }
    
                                 
                                                         public void updateVisaTouristique(Integer id ,ModelVisaTouristique visa) {
    try {
        ModelVisaTouristique.updateVisa(id ,visa);
       
      //      vue.showMessage("client updated successfully.");
            listVisaTouristique(); 
        
    } catch (SQLException e) {
        e.printStackTrace(); 
     //   vue.showMessage("Error updating client: " + e.getMessage());
    }
}
    
                                                         
                                                                                 
                       public void searchVisaTouristique(String term) {
    try {
        List<ModelVisa> visas = ModelVisaTouristique.searchVisaTouristique(term);

        Object[][] data = new Object[visas.size()][9];

        for (int i = 0; i < visas.size(); i++) {
            ModelVisa v = visas.get(i);

            data[i][0] = v.getIdTraitement();
            data[i][1] = v.getDateT();
            data[i][2] = v.getClient();
            data[i][3] = v.getEmploye();
            data[i][4] = v.getPartenariat();
            data[i][5] = v.getEtat();
            data[i][6] = v.getPrix();
            data[i][7] = v.getPays();
            data[i][8] = v.getObs();
        }

        vue.updateVisaTouristiqueTable(data);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            vue,
            "Erreur lors de la recherche",
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

}
