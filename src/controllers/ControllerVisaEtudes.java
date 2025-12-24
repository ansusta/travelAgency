
package controllers;

import agencevoyage.DataBaseConnection;
import agencevoyage.views.TraitementPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ModelVisaEtudes;


public class ControllerVisaEtudes {
         private TraitementPanel vue;
         public ControllerVisaEtudes(TraitementPanel vue) {
        this.vue = vue;
    }
    
    
           

         public void listVisaEtudes() {
    try {
        List<ModelVisaEtudes> visas = ModelVisaEtudes.getAllVisaEtudes();
        Object[][] clientData = new Object[visas.size()][9];
        for (int i = 0; i < visas.size(); i++) {
            ModelVisaEtudes visa = visas.get(i);
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
      vue.updateVisaEtudesTable(clientData);

        System.out.println("Loaded " + visas.size() + " visas"); // DEBUG
    } catch (SQLException e) {
        e.printStackTrace(); // DEBUG: Check console for errors
    }
}
                  public void addVisaEtudes(ModelVisaEtudes visa) { 
        try {
            ModelVisaEtudes.addVisaEtudes(visa);
            listVisaEtudes();
        } catch (SQLException e) {
            // vue.showMessage("Error adding client: " + e.getMessage());
        }
    }
                                          public void deleteVisaEtudes(Integer id){
       try {
      ModelVisaEtudes.deleteVisaEtudes(id );
    //  vue.showMessage("client updated successfully.");
listVisaEtudes();
    } catch (SQLException e) {
         e.printStackTrace(); 
     //   vue.showMessage("Error deleting client: " + e.getMessage());
    }
  }
                                          
                                          
                                          
                                          
         public void updateVisaEtudes(Integer id ,ModelVisaEtudes visa) {
    try {
        ModelVisaEtudes.updateVisa(id ,visa);
       
      //      vue.showMessage("client updated successfully.");
            listVisaEtudes(); 
        
    } catch (SQLException e) {
        e.printStackTrace(); 
     //   vue.showMessage("Error updating client: " + e.getMessage());
    }
}
    
    
}
