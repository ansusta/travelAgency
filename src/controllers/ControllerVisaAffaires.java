
package controllers;


import java.sql.SQLException;
import java.util.List; 
import agencevoyage.views.TraitementPanel;
import javax.swing.JOptionPane;
import models.ModelVisaAffaires;

public class ControllerVisaAffaires {
     private TraitementPanel vue;
         public ControllerVisaAffaires(TraitementPanel vue) {
        this.vue = vue;
    }
         
         public void listVisaAffaires() {
    try {
        List<ModelVisaAffaires> visas = ModelVisaAffaires.getAllVisaAffaires();
        Object[][] clientData = new Object[visas.size()][9];
        for (int i = 0; i < visas.size(); i++) {
            ModelVisaAffaires visa = visas.get(i);
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
       vue.updateVisaAffairesTable(clientData);
        System.out.println("Loaded " + visas.size() + " visas"); // DEBUG
    } catch (SQLException e) {
        e.printStackTrace(); // DEBUG: Check console for errors
    }
}
         
                  public void addVisaAffaires(ModelVisaAffaires visa) { 
        try {
            ModelVisaAffaires.addVisaAffaires(visa);
            listVisaAffaires();
        } catch (SQLException e) {
            // vue.showMessage("Error adding client: " + e.getMessage());
        }
    }
    
  public void deleteVisaAffaires(Integer id){
       try {
      ModelVisaAffaires.deleteVisaAffaires(id );
    //  vue.showMessage("client updated successfully.");
listVisaAffaires();
    } catch (SQLException e) {
         e.printStackTrace(); 
     //   vue.showMessage("Error deleting client: " + e.getMessage());
    }
  }
  
  
     public void updateVisaAffaires(Integer id ,ModelVisaAffaires visa) {
    try {
        ModelVisaAffaires.updateVisa(id ,visa);
       
      //      vue.showMessage("client updated successfully.");
            listVisaAffaires(); 
        
    } catch (SQLException e) {
        e.printStackTrace(); 
     //   vue.showMessage("Error updating client: " + e.getMessage());
    }
}
    
}
