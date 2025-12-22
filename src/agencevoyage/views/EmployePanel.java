package agencevoyage.views;

import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class EmployePanel extends JPanel {
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

    public EmployePanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        initUI();
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
        JPanel table = createTable();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, table);
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
        JLabel title = new JLabel("  GESTION DES EMPLOYÉS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 0));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 20));

        // Search Field
        JTextField searchField = new JTextField(20);
        searchField.setFont(MAIN_FONT);
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchField.setBackground(Color.WHITE);

        // Search Button
        JButton searchButton = createStyledButton("Rechercher", SECONDARY_COLOR, BUTTON_HOVER);
        searchButton.setPreferredSize(new Dimension(120, 35));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

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
        JLabel formTitle = new JLabel("Informations Employé ");
        formTitle.setFont(TITLE_FONT);
        formTitle.setForeground(PRIMARY_COLOR);
        formTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        formTitle.setMaximumSize(formTitle.getPreferredSize());

        panel.add(formTitle);

        // Standard Text Fields
        panel.add(createField("Nom Employé:"));
        panel.add(createField("Prénom Employé:"));
        panel.add(createField("Num Tél:"));
        
        // Date Embauche
        JPanel embauchePanel = new JPanel(new BorderLayout());
        embauchePanel.setOpaque(false);
        embauchePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel embaucheLabel = new JLabel("Date Embauche:");
        embaucheLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        embaucheLabel.setForeground(new Color(70, 70, 70));
        
        DatePicker dateEmbauchePicker = new DatePicker();
        dateEmbauchePicker.getComponentDateTextField().setFont(MAIN_FONT);
        dateEmbauchePicker.getComponentDateTextField().setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        dateEmbauchePicker.getComponentDateTextField().setBackground(new Color(250, 252, 255));
        
        embauchePanel.add(embaucheLabel, BorderLayout.NORTH);
        embauchePanel.add(dateEmbauchePicker, BorderLayout.CENTER);
        embauchePanel.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        panel.add(embauchePanel);
        
        // Date Sortie
        JPanel sortiePanel = new JPanel(new BorderLayout());
        sortiePanel.setOpaque(false);
        sortiePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel sortieLabel = new JLabel("Date Sortie:");
        sortieLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        sortieLabel.setForeground(new Color(70, 70, 70));
        
        DatePicker dateSortiePicker = new DatePicker();
        dateSortiePicker.getComponentDateTextField().setFont(MAIN_FONT);
        dateSortiePicker.getComponentDateTextField().setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        dateSortiePicker.getComponentDateTextField().setBackground(new Color(250, 252, 255));
        
        sortiePanel.add(sortieLabel, BorderLayout.NORTH);
        sortiePanel.add(dateSortiePicker, BorderLayout.CENTER);
        panel.add(sortiePanel);
        
        panel.add(Box.createVerticalStrut(20));

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Ajouter Button
        JButton addButton = createStyledButton("Ajouter", ADD_BUTTON_COLOR, ADD_BUTTON_HOVER);
        
        // Modifier Button
        JButton modifyButton = createStyledButton("Modifier", BUTTON_COLOR, BUTTON_HOVER);
        
        // Supprimer Button
        JButton deleteButton = createStyledButton("Supprimer", DELETE_BUTTON_COLOR, DELETE_BUTTON_HOVER);

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createField(String labelText) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        l.setForeground(new Color(70, 70, 70));
        
        JTextField t = new JTextField();
        t.setFont(MAIN_FONT);
        t.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        t.setBackground(new Color(250, 252, 255));
        
        p.add(l, BorderLayout.NORTH);
        p.add(t, BorderLayout.CENTER);
        p.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.SOUTH);
        return p;
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

        JLabel tableTitle = new JLabel("Liste des Employés");
        tableTitle.setFont(TITLE_FONT);
        tableTitle.setForeground(PRIMARY_COLOR);

        tableHeader.add(tableTitle, BorderLayout.WEST);
        p.add(tableHeader, BorderLayout.NORTH);

        String[] cols = {"ID", "Nom", "Prénom", "Tél", "Embauche", "Sortie"};
        Object[][] data = {
            {"1", "Martin", "Pierre", "0555 111 222", "2023-01-15", "-"},
            {"2", "Bernard", "Sophie", "0555 333 444", "2022-06-20", "2024-03-10"},
            {"3", "Dubois", "Luc", "0555 555 666", "2024-02-01", "-"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, cols);
        JTable table = new JTable(model);
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
}