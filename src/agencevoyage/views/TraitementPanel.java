package agencevoyage.views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;


import controllers.*;

public class TraitementPanel extends JPanel {
    private JTextField searchField;
private String currentCategory;

    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Color BUTTON_COLOR = new Color(30, 100, 200);
    private final Color BUTTON_HOVER = new Color(50, 120, 220);
    private final Color ADD_BUTTON_COLOR = new Color(40, 180, 100);
    private final Color ADD_BUTTON_HOVER = new Color(60, 200, 120);
    private final Color DELETE_BUTTON_COLOR = new Color(220, 80, 70);
    private final Color DELETE_BUTTON_HOVER = new Color(240, 100, 90);
    private JButton btnAdd, btnModify, btnDelete, btnSearch, btnAddTreatment, btnreset;
    private Map<String, List<String>> categoryMap;
    private Map<String, JPanel> detailPanels;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private final ControllerVisaAffaires controller;
    private final ControllerVisaEtudes controllerEt;
    private final ControllerVisaTravail controllerTr;
    private final ControllerVisaTouristique controllerTs;
    private final Map<String, JComponent> currentFieldsMap = new HashMap<>();
    private final Map<String, DefaultTableModel> visaTableModels = new HashMap<>();
 private final Color PRIMARY_COLOR = new Color(30, 100, 200);
    private final Color SECONDARY_COLOR = new Color(30, 100, 200);
    private final Color ACCENT_COLOR = new Color(66, 133, 244);
    private final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 14); 
    private String currentVisaKey;
        private int selectedId = -1;

    
    public TraitementPanel() {
        initCategoryMap();
        initUI();
        this.controller = new ControllerVisaAffaires(this);
        this.controllerEt = new ControllerVisaEtudes(this);
        this.controllerTr = new ControllerVisaTravail(this);
        this.controllerTs = new ControllerVisaTouristique(this);
        controllerTr.listVisaTravail();
        controller.listVisaAffaires();
        controllerEt.listVisaEtudes();
        controllerTs.listVisaTouristique();
        
    }
    
    private void initCategoryMap() {
        categoryMap = new LinkedHashMap<>();
        categoryMap.put("TRANSPORT", Arrays.asList("BILLETS"));
        categoryMap.put("ASSURANCE", Arrays.asList("ASSURANCE VOYAGE"));
        categoryMap.put("HÉBERGEMENT", Arrays.asList("RESERVATION HOTEL"));
        categoryMap.put("VOYAGES", Arrays.asList("VOYAGE ORGANISÉ", "EXCURSION"));
        categoryMap.put("VISAS", Arrays.asList("VISA TOURISTIQUE", "VISA ÉTUDES", "VISA TRAVAIL", "VISA AFFAIRES"));
    }
    
    private void initUI() {
   

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
        JPanel categoryPanel = createCategoryPanel();
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        detailPanels = new HashMap<>();
        initDetailPanels();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryPanel, mainPanel);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(3);
        splitPane.setBorder(null);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 20));

         searchField = new JTextField(20);
        searchField.setFont(MAIN_FONT);
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchField.setBackground(Color.WHITE);

        JButton searchButton = createStyledButton("Rechercher", SECONDARY_COLOR, BUTTON_HOVER);
        searchButton.setPreferredSize(new Dimension(120, 35));
        searchButton.addActionListener(e->searchVisa());

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
contentPanel.add(searchPanel, BorderLayout.NORTH);

contentPanel.add(splitPane, BorderLayout.CENTER);

add(contentPanel, BorderLayout.CENTER);
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
    
    
private void initDetailPanels() {

    detailPanels.put("TRANSPORT",
        createCombinedPanel("BILLETS",
            Arrays.asList("Type Billet", "Destination", "Date Billet"),
            "NONE"));

    detailPanels.put("ASSURANCE",
        createCombinedPanel("ASSURANCE VOYAGE",
            Arrays.asList("Type Assurance", "Durée (Jours)"),
            "NONE"));

    detailPanels.put("HÉBERGEMENT",
        createCombinedPanel("RESERVATION HOTEL",
            Arrays.asList("Nom Hôtel", "Ville", "Type Chambre", "Date Début", "Date Fin"),
            "NONE"));

    detailPanels.put("VOYAGES", createVoyagesPanel());

    detailPanels.put("VISAS", createVisasPanel());

    for (Map.Entry<String, JPanel> entry : detailPanels.entrySet()) {
        mainPanel.add(entry.getValue(),
            entry.getKey().replace(" ", "_").toUpperCase());
    }

    mainPanel.add(createEmptyPanel(), "DEFAULT");
    cardLayout.show(mainPanel, "DEFAULT");
}

private JPanel createCombinedPanel(String title, List<String> fields, String key) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);

    panel.add(createDetailHeader(title), BorderLayout.NORTH);

    JPanel formContainer = new JPanel();
    formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
    formContainer.setBackground(BACKGROUND_COLOR);
    formContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    // Specific fields - Passing the KEY
    formContainer.add(createSectionTitle("Détails Spécifiques (" + title + ")"));
    formContainer.add(createFormFields(fields, key)); 

    // General fields - Passing the KEY
    formContainer.add(Box.createRigidArea(new Dimension(0, 15)));
    formContainer.add(createSectionTitle("Information Traitement (Général)"));
    formContainer.add(createFormFields(Arrays.asList(
        "Date Traitement",
        "Client (Nom)",
        "Employé (Nom)",
        "Partenaire (Nom)",
        "Prix",
        "État",
        "Observation"
    ), key));

    formContainer.add(createButtonPanel());

    JScrollPane formScroll = new JScrollPane(formContainer);
    formScroll.setBorder(null);

    JPanel tablePanel = createTablePanel(key);

    JSplitPane split = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        formScroll,
        tablePanel
    );
    split.setDividerLocation(450);
    split.setResizeWeight(0.5);
    split.setBorder(null);

    panel.add(split, BorderLayout.CENTER);
    return panel;
}


private JPanel createVoyagesPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);

    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(MAIN_FONT);

    tabs.addTab("Voyage Organisé",
        createCombinedPanel(
            "VOYAGE ORGANISÉ",
            Arrays.asList("Destination", "Date Début", "Date Fin", "Description"),
            "VOYAGE_ORGANISE"));

    tabs.addTab("Excursion",
        createCombinedPanel(
            "EXCURSION",
            Arrays.asList("Destination", "Date", "Heure Départ"),
            "EXCURSION"));

    panel.add(tabs, BorderLayout.CENTER);
    return panel;
}

public void updateVisaEtudesTable(Object[][] data) {
    DefaultTableModel model = visaTableModels.get("VISA_ETUDES");
    model.setRowCount(0);
    for (Object[] row : data) {
        model.addRow(row);
    }
}

private JPanel createVisasPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);

    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(MAIN_FONT);

    tabs.addTab("Touristique",
        createCombinedPanel("VISA TOURISTIQUE", Arrays.asList("Pays"), "VISA_TOURISTIQUE"));

    tabs.addTab("Études",
        createCombinedPanel("VISA ÉTUDES", Arrays.asList("Pays"), "VISA_ETUDES"));

    tabs.addTab("Travail",
        createCombinedPanel("VISA TRAVAIL", Arrays.asList("Pays"), "VISA_TRAVAIL"));

    tabs.addTab("Affaires",
        createCombinedPanel("VISA AFFAIRES", Arrays.asList("Pays"), "VISA_AFFAIRES"));

    tabs.addChangeListener(e -> {
        int index = tabs.getSelectedIndex();
        currentVisaKey = switch (index) {
            case 0 -> "VISA_TOURISTIQUE";
            case 1 -> "VISA_ETUDES";
            case 2 -> "VISA_TRAVAIL";
            case 3 -> "VISA_AFFAIRES";
            default -> null;
        };
    });

    currentVisaKey = "VISA_TOURISTIQUE"; // default

    panel.add(tabs, BorderLayout.CENTER);
    return panel;
}


    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(100, 70));
        
        JLabel title = new JLabel("GESTION DES TRAITEMENTS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel title = new JLabel("CATÉGORIES");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            panel.add(createCategoryCard(entry.getKey(), entry.getValue()));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createCategoryCard(String mainCategory, List<String> subcategories) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel mainLabel = new JLabel(mainCategory);
        mainLabel.setFont(TITLE_FONT);
        card.add(mainLabel, BorderLayout.CENTER);
        
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentCategory = mainCategory;
                cardLayout.show(mainPanel, mainCategory.replace(" ", "_").toUpperCase());
            }
            public void mouseEntered(MouseEvent e) { card.setBackground(new Color(245, 245, 255)); }
            public void mouseExited(MouseEvent e) { card.setBackground(CARD_COLOR); }
        });
        
        return card;
    }

    private JPanel createDetailHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SECONDARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JButton addButton = new JButton("+ Nouveau");
        addButton.setBackground(ACCENT_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addAction());
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        return header;
    }

    private JPanel createSectionTitle(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(BACKGROUND_COLOR);
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(PRIMARY_COLOR);
        p.add(l);
        return p;
    }

private JPanel createFormFields(List<String> fields, String key) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(CARD_COLOR);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, fields.size() * 50 + 30));

    for (String field : fields) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        JLabel label = new JLabel(field);
        label.setFont(MAIN_FONT);
        label.setPreferredSize(new Dimension(200, 30));
        
        JComponent input;
        if (field.contains("Date")) {
            DatePickerSettings dateSettings = new DatePickerSettings();
            dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
            dateSettings.setAllowKeyboardEditing(false);
            input = new DatePicker(dateSettings);
            ((DatePicker)input).setDateToToday();
        } else if (field.contains("Heure")) {
            TimePicker timePicker = new TimePicker();
            timePicker.setTimeToNow();
            input = timePicker;
        } else if (field.contains("État")) {
            input = new JComboBox<>(new String[]{"En cours", "Complet", "Annulé"});
            input.setFont(MAIN_FONT);
        } else {
            input = new JTextField();
            input.setFont(MAIN_FONT);
        }
        
        // UNIQUE KEY: prefixing with the tab key
        currentFieldsMap.put(key + ":" + field, input);
            
        row.add(label, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        panel.add(row);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    return panel;
}
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton saveBtn = new JButton("modifier");
        saveBtn.setBackground(ACCENT_COLOR);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(TITLE_FONT);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveBtn.addActionListener(e ->modifyVisa());
        
        JButton cancelBtn = new JButton("supprimer");
        cancelBtn.setBackground(new Color(220, 53, 69)); 
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(TITLE_FONT);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelBtn.addActionListener(e -> deleteAction());
      
        
       btnAdd = createStyledButton("Ajouter", ADD_BUTTON_COLOR, ADD_BUTTON_HOVER);
       btnAdd.addActionListener(e -> addVisa());
        
        panel.add(cancelBtn);
        panel.add(saveBtn);
        return panel;
    }

 private JPanel createTablePanel(String key) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(CARD_COLOR);

    String[] columns = {"ID", "Date Traitement", "Client", "Employé", "Partenariat", "État", "Prix", "Pays", "Observation"
    };

    DefaultTableModel model = new DefaultTableModel(null, columns);
    JTable table = new JTable(model);
    table.getColumnModel().removeColumn(table.getColumnModel().getColumn(8));
    table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                    populateFormFromSelectedRow(row);
                }
            }
        });

    visaTableModels.put(key, model); 

    table.setRowHeight(35);
    table.setFont(MAIN_FONT);

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
}

    
 public void updateVisaAffairesTable(Object[][] data) {
    DefaultTableModel model = visaTableModels.get("VISA_AFFAIRES");
    if (model == null) return;

    model.setRowCount(0);
    for (Object[] row : data) {
        model.addRow(row);
    }
}
 
private void populateFormFromSelectedRow(int row) {
    if (currentVisaKey == null) return;

    DefaultTableModel model = visaTableModels.get(currentVisaKey);
    if (model == null) return;

    String prefix = currentVisaKey + ":";

    try {
        DatePicker datePicker =
                (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient =
                (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye =
                (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner =
                (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix =
                (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat =
                (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtPays =
                (JTextField) currentFieldsMap.get(prefix + "Pays");
        JTextField txtObservation = 
                (JTextField) currentFieldsMap.get(prefix + "Observation");

        // Date
        if (model.getValueAt(row, 1) != null) {
            java.sql.Date d = (java.sql.Date) model.getValueAt(row, 1);
            datePicker.setDate(d.toLocalDate());
        }

        txtClient.setText(model.getValueAt(row, 2).toString());
        txtEmploye.setText(model.getValueAt(row, 3).toString());
        txtPartner.setText(model.getValueAt(row, 4).toString());
        comboEtat.setSelectedItem(model.getValueAt(row, 5).toString());
        txtPrix.setText(model.getValueAt(row, 6).toString());
        txtPays.setText(model.getValueAt(row, 7).toString());
        txtObservation.setText(model.getValueAt(row, 8).toString());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Erreur lors du chargement des données : " + e.getMessage()
        );
    }
}



 public void updateVisaTravailTable(Object[][] data) {
    DefaultTableModel model = visaTableModels.get("VISA_TRAVAIL");
    if (model == null) return;

    model.setRowCount(0);
    for (Object[] row : data) {
        model.addRow(row);
    }
}
 public void updateVisaTouristiqueTable(Object[][] data) {
    DefaultTableModel model = visaTableModels.get("VISA_TOURISTIQUE");
    if (model == null) return;
    model.setRowCount(0);
    for (Object[] row : data) {
        model.addRow(row);
    }
}
 
public void addVisaTouristique() {
    String prefix = "VISA_TOURISTIQUE:";
    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        // Validation
        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty() || datePicker.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les champs obligatoires (Client, Pays, Date)!", 
                "Champs Manquants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.Date dateT = java.sql.Date.valueOf(datePicker.getDate()); 
        Double prix = 0.0;
        try { prix = Double.parseDouble(txtPrix.getText().trim()); } catch (Exception e) {}

        models.ModelVisaTouristique visaData = new models.ModelVisaTouristique(
            dateT, txtObs.getText(), txtClient.getText(),
            txtPartner.getText(), txtEmploye.getText(),
            comboEtat.getSelectedItem().toString(), prix, txtPays.getText()
        );

        controllerTs.addVisaTouristique(visaData);
        JOptionPane.showMessageDialog(this, "Visa Touristique enregistré!");
        controllerTs.listVisaTouristique(); 
        clearForm();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        e.printStackTrace();
    }
}

public void addVisaEtudes() {
    String prefix = "VISA_ETUDES:";
    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try { prix = Double.parseDouble(txtPrix.getText().trim()); } catch (Exception ignored) {}

        models.ModelVisaEtudes visa = new models.ModelVisaEtudes(
            java.sql.Date.valueOf(datePicker.getDate()),
            txtObs.getText(),
            txtClient.getText(),
            txtPartner.getText(),
            txtEmploye.getText(),
            comboEtat.getSelectedItem().toString(),
            prix,
            txtPays.getText()
        );

        controllerEt.addVisaEtudes(visa);
        controllerEt.listVisaEtudes();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Études enregistré !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}


public void addVisaTravail() {
    String prefix = "VISA_TRAVAIL:";
    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try { prix = Double.parseDouble(txtPrix.getText().trim()); } catch (Exception ignored) {}

        models.ModelVisaTravail visa = new models.ModelVisaTravail(
            java.sql.Date.valueOf(datePicker.getDate()),
            txtObs.getText(),
            txtClient.getText(),
            txtPartner.getText(),
            txtEmploye.getText(),
            comboEtat.getSelectedItem().toString(),
            prix,
            txtPays.getText()
        );

        controllerTr.addVisaTravail(visa);
        controllerTr.listVisaTravail();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Études enregistré !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}
public void addVisaAffaires() {
    String prefix = "VISA_AFFAIRES:";
    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try { prix = Double.parseDouble(txtPrix.getText().trim()); } catch (Exception ignored) {}

        models.ModelVisaAffaires visa = new models.ModelVisaAffaires(
            java.sql.Date.valueOf(datePicker.getDate()),
            txtObs.getText(),
            txtClient.getText(),
            txtPartner.getText(),
            txtEmploye.getText(),
            comboEtat.getSelectedItem().toString(),
            prix,
            txtPays.getText()
        );

        controller.addVisaAffaires(visa);
        controller.listVisaAffaires();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Études enregistré !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}


public void deleteVisaTravail(){        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un traitement à supprimer!", 
                "Aucun traitement sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce traitement?", 
            "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controllerTr.deleteVisaTravail(selectedId);
            clearForm();
        }}
public void deleteVisaEtudes(){        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un traitement à supprimer!", 
                "Aucun traitement sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce traitement?", 
            "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controllerEt.deleteVisaEtudes(selectedId);
            clearForm();
        }}
public void deleteVisaTouristique(){        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un traitement à supprimer!", 
                "Aucun traitement sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce traitement?", 
            "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controllerTs.deleteVisaTouristique(selectedId);
            clearForm();
        }}

public void deleteVisaAffaires(){        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un traitement à supprimer!", 
                "Aucun traitement sélectionné", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce traitement?", 
            "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteVisaAffaires(selectedId);
            clearForm();
        }}



   public void modifyVisaTouristique(){
    if (selectedId == -1) {
        JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un visa à modifier !",
                "Aucun visa sélectionné",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    String prefix = "VISA_TOURISTIQUE:";

    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try {
            prix = Double.parseDouble(txtPrix.getText().trim());
        } catch (Exception ignored) {}

        models.ModelVisaTouristique visa = new models.ModelVisaTouristique(
                java.sql.Date.valueOf(datePicker.getDate()),
                txtObs.getText(),
                txtClient.getText(),
                txtPartner.getText(),
                txtEmploye.getText(),
                comboEtat.getSelectedItem().toString(),
                prix,
                txtPays.getText()
        );

        controllerTs.updateVisaTouristique(selectedId, visa);
        controllerTs.listVisaTouristique();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Travail modifié avec succès !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }}
public void modifyVisaTravail() {

    if (selectedId == -1) {
        JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un visa à modifier !",
                "Aucun visa sélectionné",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    String prefix = "VISA_TRAVAIL:";

    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try {
            prix = Double.parseDouble(txtPrix.getText().trim());
        } catch (Exception ignored) {}

        models.ModelVisaTravail visa = new models.ModelVisaTravail(
                java.sql.Date.valueOf(datePicker.getDate()),
                txtObs.getText(),
                txtClient.getText(),
                txtPartner.getText(),
                txtEmploye.getText(),
                comboEtat.getSelectedItem().toString(),
                prix,
                txtPays.getText()
        );

        controllerTr.updateVisaTravail(selectedId, visa);
        controllerTr.listVisaTravail();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Travail modifié avec succès !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}

         public void modifyVisaEtudes(){
             
    if (selectedId == -1) {
        JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un visa à modifier !",
                "Aucun visa sélectionné",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    String prefix = "VISA_ETUDES:";

    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try {
            prix = Double.parseDouble(txtPrix.getText().trim());
        } catch (Exception ignored) {}

        models.ModelVisaEtudes visa = new models.ModelVisaEtudes(
                java.sql.Date.valueOf(datePicker.getDate()),
                txtObs.getText(),
                txtClient.getText(),
                txtPartner.getText(),
                txtEmploye.getText(),
                comboEtat.getSelectedItem().toString(),
                prix,
                txtPays.getText()
        );

        controllerEt.updateVisaEtudes(selectedId, visa);
        controllerEt.listVisaEtudes();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Travail modifié avec succès !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
         }
            public void modifyVisaAffaires(){
                                
    if (selectedId == -1) {
        JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un visa à modifier !",
                "Aucun visa sélectionné",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    String prefix = "VISA_AFFAIRES:";

    try {
        DatePicker datePicker = (DatePicker) currentFieldsMap.get(prefix + "Date Traitement");
        JTextField txtClient = (JTextField) currentFieldsMap.get(prefix + "Client (Nom)");
        JTextField txtEmploye = (JTextField) currentFieldsMap.get(prefix + "Employé (Nom)");
        JTextField txtPartner = (JTextField) currentFieldsMap.get(prefix + "Partenaire (Nom)");
        JTextField txtPrix = (JTextField) currentFieldsMap.get(prefix + "Prix");
        JComboBox<?> comboEtat = (JComboBox<?>) currentFieldsMap.get(prefix + "État");
        JTextField txtObs = (JTextField) currentFieldsMap.get(prefix + "Observation");
        JTextField txtPays = (JTextField) currentFieldsMap.get(prefix + "Pays");

        if (txtClient.getText().trim().isEmpty() || txtPays.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
            return;
        }

        Double prix = 0.0;
        try {
            prix = Double.parseDouble(txtPrix.getText().trim());
        } catch (Exception ignored) {}

        models.ModelVisaAffaires visa = new models.ModelVisaAffaires(
                java.sql.Date.valueOf(datePicker.getDate()),
                txtObs.getText(),
                txtClient.getText(),
                txtPartner.getText(),
                txtEmploye.getText(),
                comboEtat.getSelectedItem().toString(),
                prix,
                txtPays.getText()
        );

        controller.updateVisaAffaires(selectedId, visa);
        controller.listVisaAffaires();
        clearForm();

        JOptionPane.showMessageDialog(this, "Visa Travail modifié avec succès !");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
            
            }
            
public void searchVisaTravail() {
    String searchTerm = searchField.getText().trim();

    if (!searchTerm.isEmpty()) {
        controllerTr.searchVisaTravail(searchTerm);
    } else {
        controllerTr.listVisaTravail();
    }
}

           public void searchVisaTouristique(){    String searchTerm = searchField.getText().trim();

    if (!searchTerm.isEmpty()) {
        controllerTs.searchVisaTouristique(searchTerm);
    } else {
        controllerTs.listVisaTouristique();
    }}
           public void searchVisaAffaires(){   
               String searchTerm = searchField.getText().trim();

    if (!searchTerm.isEmpty()) {
        controller.searchVisaAffaires(searchTerm);
    } else {
        controller.listVisaAffaires();
    }}
           public void searchVisaEtudes(){ 
               String searchTerm = searchField.getText().trim();

    if (!searchTerm.isEmpty()) {
        controllerEt.searchVisaEtudes(searchTerm);
    } else {
        controllerEt.listVisaEtudes();
    }}
            
            
            
            



    private JPanel createEmptyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        JLabel label = new JLabel("Sélectionnez une catégorie pour afficher les détails");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(150, 150, 150));
        panel.add(label);
        return panel;
    }
public void clearForm() {
    for (Map.Entry<String, JComponent> entry : currentFieldsMap.entrySet()) {
        JComponent comp = entry.getValue();
        // Only clear if the field is visible on screen
        if (comp.isShowing()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            } else if (comp instanceof DatePicker) {
                ((DatePicker) comp).clear();
            } else if (comp instanceof TimePicker) {
                ((TimePicker) comp).clear();
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).setSelectedIndex(0);
            }
        }
    }
}
    
    public void setClientAndCategory(int clientId, String clientFullName, String category) {
    cardLayout.show(mainPanel, category.replace(" ", "_").toUpperCase());
    
    JOptionPane.showMessageDialog(this, "Nouveau traitement pour: " + clientFullName);
}
    public void addVisa() {
    switch (currentVisaKey) {
        case "VISA_TOURISTIQUE" -> addVisaTouristique();
        case "VISA_ETUDES"     -> addVisaEtudes();
        case "VISA_TRAVAIL"    -> addVisaTravail();
        case "VISA_AFFAIRES"   -> addVisaAffaires();
        default -> JOptionPane.showMessageDialog(this, "Type de visa inconnu");
    }
}
    
        public void deleteVisa() {
    switch (currentVisaKey) {
        case "VISA_TOURISTIQUE" -> deleteVisaTouristique();
        case "VISA_ETUDES"     -> deleteVisaEtudes();
        case "VISA_TRAVAIL"    -> deleteVisaTravail();
        case "VISA_AFFAIRES"   -> deleteVisaAffaires();
        default -> JOptionPane.showMessageDialog(this, "Type de visa inconnu");
    }
}
        
            public void modifyVisa() {
    switch (currentVisaKey) {
       case "VISA_TOURISTIQUE" ->modifyVisaTouristique();
       case "VISA_ETUDES"     -> modifyVisaEtudes();
        case "VISA_TRAVAIL"    -> modifyVisaTravail();
        case "VISA_AFFAIRES"   -> modifyVisaAffaires();
        default -> JOptionPane.showMessageDialog(this, "Type de visa inconnu");
        
    }
}
            
            public void searchVisa(){
                
                    switch (currentVisaKey) {
       case "VISA_TOURISTIQUE" ->searchVisaTouristique();
       case "VISA_ETUDES"     -> searchVisaEtudes();
        case "VISA_TRAVAIL"    -> searchVisaTravail();
        case "VISA_AFFAIRES"   -> searchVisaAffaires();
        default -> JOptionPane.showMessageDialog(this, "Type de visa inconnu");
            }
            
            }
            
            

            public void addTransport(){}
            public void addAssurance(){}
            public void addHebergement(){}
            public void addVoyage(){}
            
            public void deleteTransport(){}
            public void deleteAssurance(){}
            public void deleteHebergement(){}
            public void deleteVoyage(){}
            
            public void deleteAction() {
    switch (currentCategory) {
        case "VISAS" -> deleteVisa();
        case "TRANSPORT" -> deleteTransport();
        case "ASSURANCE" -> deleteAssurance();
        case "HÉBERGEMENT" -> deleteHebergement();
        case "VOYAGES" -> deleteVoyage();
    }
}

            
            public void addAction() {
    switch (currentCategory) {
        case "VISAS" -> addVisa();
        case "TRANSPORT" -> addTransport();
        case "ASSURANCE" -> addAssurance();
        case "HÉBERGEMENT" -> addHebergement();
        case "VOYAGES" -> addVoyage();
        default -> JOptionPane.showMessageDialog(this, "Catégorie inconnue");
    }
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            JFrame frame = new JFrame("Gestion Agence");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 850);
            frame.setLocationRelativeTo(null);
            frame.add(new TraitementPanel());
            frame.setVisible(true);
        });
    }
    
    
    
}