package agencevoyage.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class PaiementPanel extends JPanel {
    private final Color PRIMARY_COLOR = new Color(30, 100, 200);
    private final Color SECONDARY_COLOR = new Color(70, 100, 180);
    private final Color BACKGROUND_COLOR = new Color(240, 245, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(59, 89, 152);
    private final Color BUTTON_HOVER = new Color(70, 100, 180);
    private final Color ADD_BUTTON_COLOR = new Color(40, 180, 100);
    private final Color ADD_BUTTON_HOVER = new Color(60, 200, 120);
    private final Color DELETE_BUTTON_COLOR = new Color(220, 80, 70);
    private final Color DELETE_BUTTON_HOVER = new Color(240, 100, 90);
    
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.BOLD, 18);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font SECTION_FONT = new Font("Segoe UI Semibold", Font.BOLD, 16);
    
    // Constrain width for a modern look
    private final int FIELD_WIDTH = 350;

    public PaiementPanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        initUI();
    }

    private void initUI() {
        add(createHeader(), BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout(20, 0));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LEFT: FORM (Scrollable)
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(BACKGROUND_COLOR);

        // --- SECTION 1: PAIEMENT ---
        JPanel pSection = createSectionCard("Détails du Paiement");
        
        pSection.add(createField("Nom Client:"));
        
        // Service Combo
        pSection.add(createComboField("Service:", new String[]{"BILLETS", "ASSURANCE VOYAGE", "RESERVATION HOTEL", "VOYAGE ORGANISÉ", "EXCURSION", "VISA"}));
        
        pSection.add(createField("Somme:"));
        pSection.add(createField("Restant:"));
        
        // État Combo
        pSection.add(createComboField("État:", new String[]{"en_cours", "complet", "annule"}));
        
        // Date Paiement
        pSection.add(createDatePickerField("Date Paiement:"));
        
        formContainer.add(pSection);
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- SECTION 2: REMBOURSEMENT ---
        JPanel rSection = createSectionCard("Remboursement (Optionnel)");
        rSection.add(createField("Somme à rembourser:"));
        
        // Reason Area
        rSection.add(createAreaField("Raison:"));
        
        // Refund Date
        rSection.add(createDatePickerField("Date Remboursement:"));
        
        formContainer.add(rSection);

        // Buttons
        formContainer.add(createActionButtons());

        JScrollPane formScroll = new JScrollPane(formContainer);
        formScroll.setBorder(null);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);
        formScroll.getViewport().setBackground(BACKGROUND_COLOR);

        // RIGHT: TABLE
        JPanel tablePanel = createTable();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formScroll, tablePanel);
        split.setDividerLocation(420);
        split.setDividerSize(5);
        split.setBorder(null);
        
        mainContent.add(split, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    // --- HELPER METHODS FOR CONSTRAINED FIELDS ---

    private JPanel createField(String labelText) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(FIELD_WIDTH, 70));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        l.setForeground(new Color(70, 70, 70));
        
        JTextField t = new JTextField();
        t.setFont(MAIN_FONT);
        t.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 210, 220)), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        
        p.add(l, BorderLayout.NORTH);
        p.add(t, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
    }

    private JPanel createComboField(String labelText, String[] items) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(FIELD_WIDTH, 70));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(MAIN_FONT);
        combo.setBackground(Color.WHITE);
        
        p.add(l, BorderLayout.NORTH);
        p.add(combo, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
    }

    private JPanel createDatePickerField(String labelText) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(FIELD_WIDTH, 70));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        
        DatePicker datePicker = new DatePicker();
        datePicker.getComponentDateTextField().setFont(MAIN_FONT);
        datePicker.getComponentDateTextField().setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)), BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        
        p.add(l, BorderLayout.NORTH);
        p.add(datePicker, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
    }

    private JPanel createAreaField(String labelText) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(FIELD_WIDTH, 120));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        
        JTextArea area = new JTextArea(3, 20);
        area.setFont(MAIN_FONT);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(new LineBorder(new Color(200, 210, 220)));
        
        p.add(l, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
    }

    private JPanel createActionButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(FIELD_WIDTH + 50, 50));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createStyledButton("Ajouter", ADD_BUTTON_COLOR, ADD_BUTTON_HOVER));
        panel.add(createStyledButton("Modifier", BUTTON_COLOR, BUTTON_HOVER));
        panel.add(createStyledButton("Supprimer", DELETE_BUTTON_COLOR, DELETE_BUTTON_HOVER));
        
        return panel;
    }

    // --- EXISTING STYLING METHODS ---

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(100, 80));

        JLabel title = new JLabel("  GESTION DES PAIEMENTS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        headerPanel.add(title, BorderLayout.WEST);
        return headerPanel;
    }

    private JPanel createSectionCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CARD_COLOR);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 230, 240), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SECTION_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        p.add(titleLabel);
        
        return p;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getModel().isRollover() ? hoverColor : bgColor);
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
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private JPanel createTable() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(CARD_COLOR);
        p.setBorder(new LineBorder(new Color(220, 230, 240)));

        String[] cols = {"ID", "Somme", "Restant", "État", "Date", "Remboursé?"};
        DefaultTableModel model = new DefaultTableModel(null, cols);
        JTable table = new JTable(model);
        styleTable(table);
        
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(MAIN_FONT);
        JTableHeader h = table.getTableHeader();
        h.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        h.setBackground(new Color(245, 247, 250));
        h.setPreferredSize(new Dimension(0, 45));
    }
}