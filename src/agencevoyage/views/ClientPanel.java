package agencevoyage.views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import controllers.ControllerClient;
import models.ModelClient;

public class ClientPanel extends JPanel {
    private ControllerClient controller;
    private JTextField txtNom, txtPrenom, txtTel, txtSearch;
    private JSpinner fidelitySpinner;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnAdd, btnModify, btnDelete, btnSearch, btnAddTreatment, btnreset;
    private int selectedClientId = -1;
    // Track selected client for modify/delete

    // Colors and fonts (unchanged)
    private final Color PRIMARY_COLOR = new Color(30, 100, 200);
    private final Color SECONDARY_COLOR = new Color(70, 130, 240);
    private final Color BACKGROUND_COLOR = new Color(240, 245, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(30, 100, 200);
    private final Color BUTTON_HOVER = new Color(50, 120, 220);
    private final Color ADD_BUTTON_COLOR = new Color(40, 180, 100);
    private final Color ADD_BUTTON_HOVER = new Color(60, 200, 120);
    private final Color DELETE_BUTTON_COLOR = new Color(220, 80, 70);
    private final Color DELETE_BUTTON_HOVER = new Color(240, 100, 90);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.BOLD, 18);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    public ClientPanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        controller = new ControllerClient(this); // Fix: assign to field
        initUI();
        controller.listClients(); // Now uses the assigned controller
    }

    private void initUI() {
        // Header with search
        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);

        // Content Container
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BACKGROUND_COLOR);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form (Left)
        JPanel form = createForm();
        
        // Table (Right)
        JPanel tablePanel = createTable();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, tablePanel);
        split.setDividerLocation(380);
        split.setDividerSize(5);
        split.setBorder(null);

        content.add(split, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(100, 90));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Title
        JLabel title = new JLabel("  GESTION DES CLIENTS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 0));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 20));

        // Search Field - NOW ASSIGNED TO FIELD
        txtSearch = new JTextField(20);
        txtSearch.setFont(MAIN_FONT);
        txtSearch.setPreferredSize(new Dimension(250, 35));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setBackground(Color.WHITE);

        // Search Button - NOW ASSIGNED TO FIELD
        btnSearch = createStyledButton("Rechercher", SECONDARY_COLOR, BUTTON_HOVER);
        btnSearch.setPreferredSize(new Dimension(120, 35));
        btnSearch.addActionListener(e -> performSearch());

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 230, 240), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // Form Title
        JLabel formTitle = new JLabel("Informations Client");
        formTitle.setFont(TITLE_FONT);
        formTitle.setForeground(PRIMARY_COLOR);
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(formTitle);

        // NOW CREATE AND ASSIGN TEXT FIELDS TO INSTANCE FIELDS
        panel.add(createField("Nom Client:", txtNom = new JTextField()));
        panel.add(createField("Prénom:", txtPrenom = new JTextField()));
        panel.add(createField("Num Tél:", txtTel = new JTextField()));
        
        // Fidelity Spinner - NOW ASSIGNED TO FIELD
        JPanel levelRow = createFidelityRow();
        panel.add(levelRow);
        panel.add(Box.createVerticalStrut(20));

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Buttons with ActionListeners
        btnAdd = createStyledButton("Ajouter", ADD_BUTTON_COLOR, ADD_BUTTON_HOVER);
        btnAdd.addActionListener(e -> addClient());
        
        btnModify = createStyledButton("Modifier", BUTTON_COLOR, BUTTON_HOVER);
        btnModify.addActionListener(e -> modifyClient());
        
        btnDelete = createStyledButton("Supprimer", DELETE_BUTTON_COLOR, DELETE_BUTTON_HOVER);
       btnDelete.addActionListener(e -> deleteClient());
    
       //i will create a reset form button 

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnModify);
        buttonPanel.add(btnDelete);
        

        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(15));

       JPanel treatmentResetPanel = new JPanel(new GridLayout(1, 2, 10, 0));
treatmentResetPanel.setOpaque(false);
treatmentResetPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
treatmentResetPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

btnAddTreatment = createStyledButton("Ajouter un Traitement",
        new Color(100, 150, 220), new Color(120, 170, 240));
btnAddTreatment.addActionListener(e ->addTreat());

btnreset = createStyledButton("Réinitialiser", BUTTON_COLOR, BUTTON_HOVER);
   btnreset.addActionListener(e ->resetForm());


treatmentResetPanel.add(btnAddTreatment);
treatmentResetPanel.add(btnreset);


panel.add(treatmentResetPanel);

        return panel;
    }

    private JPanel createField(String labelText, JTextField textField) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        l.setForeground(new Color(70, 70, 70));
        
        textField.setFont(MAIN_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setBackground(new Color(250, 252, 255));
        
        p.add(l, BorderLayout.NORTH);
        p.add(textField, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
    }

    private JPanel createFidelityRow() {
        JPanel levelRow = new JPanel(new BorderLayout());
        levelRow.setOpaque(false);
        levelRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel levelLabel = new JLabel("Niveau de Fidélité :");
        levelLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        levelLabel.setForeground(new Color(70, 70, 70));
        
        // Spinner - NOW ASSIGNED TO FIELD
        SpinnerModel model = new SpinnerNumberModel(0, 0, 10, 1);
        fidelitySpinner = new JSpinner(model);
        fidelitySpinner.setFont(MAIN_FONT);
        fidelitySpinner.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        levelRow.add(levelLabel, BorderLayout.NORTH);
        levelRow.add(fidelitySpinner, BorderLayout.CENTER);
        levelRow.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.SOUTH);
        
        return levelRow;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(hoverColor);
                } else {
                    g.setColor(bgColor);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        return button;
    }

    private JPanel createTable() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(CARD_COLOR);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 230, 240), 1, true),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Table Header Panel
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setBackground(CARD_COLOR);
        tableHeader.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(230, 235, 240)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel tableTitle = new JLabel("Liste des Clients");
        tableTitle.setFont(TITLE_FONT);
        tableTitle.setForeground(PRIMARY_COLOR);

        tableHeader.add(tableTitle, BorderLayout.WEST);
        p.add(tableHeader, BorderLayout.NORTH);

        // DYNAMIC TABLE MODEL
        String[] cols = {"ID", "Nom", "Prénom", "Téléphone", "Niveau Fidélité"};
        tableModel = new DefaultTableModel(new Object[0][], cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        table = new JTable(tableModel);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    selectedClientId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    populateFormFromSelectedRow(row);
                }
            }
        });
        styleTable(table);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(248, 250, 252));
        scroll.setViewportBorder(BorderFactory.createEmptyBorder());
        p.add(scroll, BorderLayout.CENTER);

        return p;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(MAIN_FONT);
        table.setGridColor(new Color(235, 240, 245));
        table.setSelectionBackground(new Color(225, 235, 250));
        table.setSelectionForeground(new Color(30, 30, 30));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setBorder(BorderFactory.createEmptyBorder());
        
        // Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (!isSelected) {
                    if (row % 2 == 0) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(new Color(248, 250, 252));
                    }
                }
                return this;
            }
        });
        
        JTableHeader h = table.getTableHeader();
        h.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        h.setBackground(new Color(245, 247, 250));
        h.setForeground(new Color(70, 70, 70));
        h.setPreferredSize(new Dimension(0, 45));
        h.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 2, 0, new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        h.setReorderingAllowed(false);
    }

    // PUBLIC METHODS FOR CONTROLLER CALLBACKS
    public void updateClientTable(Object[][] clients) {
        tableModel.setRowCount(0); // Clear existing data
        for (Object[] client : clients) {
            tableModel.addRow(client);
        }
    }

    public void clearForm() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtTel.setText("");
        fidelitySpinner.setValue(0);
        selectedClientId = -1;
    }

    // ACTION METHODS
    private void addClient() {
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String tel = txtTel.getText().trim();
        int fidelite = (Integer) fidelitySpinner.getValue();
        
        if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires!", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ModelClient client = new ModelClient(nom, prenom, tel,fidelite );
        controller.addClient(client);
        clearForm();
    }

    private void modifyClient() {
        if (selectedClientId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à modifier!", 
                "Aucun client sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String tel = txtTel.getText().trim();
        int fidelite = (Integer) fidelitySpinner.getValue();
        
        controller.updateClient(selectedClientId, nom, prenom, tel, fidelite);
        clearForm();
    }

    private void deleteClient() {
        if (selectedClientId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer!", 
                "Aucun client sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce client?", 
            "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteClient(selectedClientId);
            clearForm();
        }
    }

    private void performSearch() {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            controller.searchClient(searchTerm);
        } else {
            controller.listClients();
        }
    } 
    
    private void resetForm(){
         String s=("");
            txtNom.setText(s);  
            txtPrenom.setText(s);
        
            txtTel.setText(s);
    }
    
private void addTreat() {
    if (selectedClientId == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Veuillez sélectionner un client !",
                "Aucun client sélectionné",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Available treatment categories
    String[] categories = {
            "TRANSPORT",
            "ASSURANCE",
            "HÉBERGEMENT",
            "VOYAGES",
            "VISAS"
    };

    // Ask user to choose a category
    String selectedCategory = (String) JOptionPane.showInputDialog(
            this,
            "Choisissez le type de traitement :",
            "Ajouter un Traitement",
            JOptionPane.QUESTION_MESSAGE,
            null,
            categories,
            categories[0]
    );

    // User cancelled
    if (selectedCategory == null) {
        return;
    }

    // For now: confirmation (later you connect this to TraitementPanel / controller)
    JOptionPane.showMessageDialog(
            this,
            "Traitement \"" + selectedCategory + "\" pour le client ID "
                    + selectedClientId + " prêt à être ajouté.",
            "Traitement",
            JOptionPane.INFORMATION_MESSAGE
    );

    // TODO later:
    // controller.openTraitement(selectedClientId, selectedCategory);
}


    private void populateFormFromSelectedRow(int row) {
        txtNom.setText(tableModel.getValueAt(row, 1).toString());
        txtPrenom.setText(tableModel.getValueAt(row, 2).toString());
        txtTel.setText(tableModel.getValueAt(row, 3).toString());
        fidelitySpinner.setValue(Integer.parseInt(tableModel.getValueAt(row, 4).toString()));
    }

    // GETTERS FOR CONTROLLER (if needed)
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }
}



//to do

//reset button