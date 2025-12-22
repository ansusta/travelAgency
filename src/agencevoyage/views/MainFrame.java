package agencevoyage.views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private JPanel cards;
    private HashMap<String, JButton> menuButtons; // store buttons to highlight

    public MainFrame() {
        setTitle("Travel Agency App");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainContainer = new JPanel(new BorderLayout());

        // Side menu
        JPanel menuPanel = new JPanel();
        
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(30, 100, 200)); 
        menuPanel.setPreferredSize(new Dimension(200, 0));

        // Create menu buttons and store them in a map
        menuButtons = new HashMap<>();
        JButton homeButton = createMenuButton("Home", "HOME");
        JButton clientButton = createMenuButton("Clients", "CLIENT");
        JButton traitementButton = createMenuButton("Traitement", "TRAITEMENT");
        JButton employeButton = createMenuButton("employe", "EMPLOYE");
        JButton partenariatButton = createMenuButton("partenariat", "PARTENARIAT");
        JButton paiementButton = createMenuButton("paiement", "PAIEMENT");
        JButton contratButton = createMenuButton("contrat", "CONTRAT");
        
        

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(homeButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(clientButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(traitementButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(employeButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(partenariatButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(paiementButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(contratButton);

        cards = new JPanel(new CardLayout());
cards = new JPanel(new CardLayout());
JPanel homePanel = new JPanel();
homePanel.add(new JLabel("Home Screen"));
ClientPanel clientPanel = new ClientPanel();
TraitementPanel traitementPanel = new TraitementPanel();
PartenariatPanel partenariatPanel = new PartenariatPanel();
EmployePanel employePanel = new EmployePanel();
PaiementPanel paiementPanel=new PaiementPanel();
ContratPanel contratPanel=new ContratPanel();



cards.add(homePanel, "HOME");
cards.add(clientPanel, "CLIENT");
cards.add(traitementPanel, "TRAITEMENT");
cards.add(employePanel, "EMPLOYE");
cards.add(partenariatPanel, "PARTENARIAT");
cards.add(paiementPanel, "PAIEMENT");
cards.add(contratPanel, "CONTRAT");


        mainContainer.add(menuPanel, BorderLayout.WEST);
        mainContainer.add(cards, BorderLayout.CENTER);
        add(mainContainer);

        // Show initial card
        showCard("HOME");
    }

    // Helper to create a modern button
    private JButton createMenuButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> showCard(cardName));
        menuButtons.put(cardName, btn);
        return btn;
    }

    // Switch panel and highlight active menu button
    private void showCard(String name) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);

        // Reset all buttons to default color
        for (JButton btn : menuButtons.values()) {
            btn.setBackground(new Color(30, 100, 200));
        }
        // Highlight active button
        JButton active = menuButtons.get(name);
        active.setBackground(new Color(0, 120, 215)); // blue highlight
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
