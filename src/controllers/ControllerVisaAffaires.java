
package controllers;


import java.sql.SQLException;
import java.util.List; 
import agencevoyage.views.TraitementPanel;
import javax.swing.JOptionPane;
import models.ModelVisa;
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
     
                             
                       public void searchVisaAffaires(String term) {
    try {
        List<ModelVisa> visas = ModelVisaAffaires.searchVisaAffaires(term);

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

        vue.updateVisaAffairesTable(data);

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
