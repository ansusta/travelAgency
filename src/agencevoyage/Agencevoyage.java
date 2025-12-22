
package agencevoyage;

import agencevoyage.views.MainFrame;
import java.sql.Connection;

public class Agencevoyage {


    public static void main(String[] args) {
        
                Connection connection = DataBaseConnection.getConnection(); 
        if (connection == null) {
            System.err.println("Failed to connect to the database. Exiting...");
            return;
        }

         java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
    
}
