
package controllers;

import agencevoyage.views.TraitementPanel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import models.ModelVisa;
import models.ModelVisaTravail;


public class ControllerVisaTravail {
    
    
    
         private TraitementPanel vue;
         public ControllerVisaTravail(TraitementPanel vue) {
        this.vue = vue;
    }
             public void listVisaTravail() {
    try {
        List<ModelVisaTravail> visas = ModelVisaTravail.getAllVisaTravail();
        Object[][] clientData = new Object[visas.size()][9];
        for (int i = 0; i < visas.size(); i++) {
            ModelVisaTravail visa = visas.get(i);
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
      vue.updateVisaTravailTable(clientData);

        System.out.println("Loaded " + visas.size() + " visas"); // DEBUG
    } catch (SQLException e) {
        e.printStackTrace(); // DEBUG: Check console for errors
    }
}
             
                      public void addVisaTravail(ModelVisaTravail visa) { 
        try {
            ModelVisaTravail.addVisaTravail(visa);
            listVisaTravail();
        } catch (SQLException e) {
            // vue.showMessage("Error adding client: " + e.getMessage());
        }
    }
    
                      
                        public void deleteVisaTravail(Integer id){
       try {
      ModelVisaTravail.deleteVisaTravail(id );
    //  vue.showMessage("client updated successfully.");
listVisaTravail();
    } catch (SQLException e) {
         e.printStackTrace(); 
     //   vue.showMessage("Error deleting client: " + e.getMessage());
    }
  }
  
                        
                        public void updateVisaTravail(Integer id ,ModelVisaTravail visa) {
    try {
        ModelVisaTravail.updateVisa(id ,visa);
       
      //      vue.showMessage("client updated successfully.");
            listVisaTravail(); 
        
    } catch (SQLException e) {
        e.printStackTrace(); 
     //   vue.showMessage("Error updating client: " + e.getMessage());
    }
}
                        
                        
                       public void searchVisaTravail(String term) {
    try {
        List<ModelVisa> visas = ModelVisaTravail.searchVisaTravail(term);

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

        vue.updateVisaTravailTable(data);

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
