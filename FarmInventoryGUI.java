import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FarmInventoryGUI extends JFrame {

    // Lists to store the objects in memory
    private ArrayList<FarmInventoryCategories.Animal> animalList = new ArrayList<>();
    private ArrayList<FarmInventoryCategories.Crops> cropList = new ArrayList<>();
    private ArrayList<FarmInventoryCategories.Machinery> machineryList = new ArrayList<>();

    public FarmInventoryGUI() {
        setTitle("Farm Inventory Manager");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create tabbed pane for the 3 categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Animals", createAnimalPanel());
        tabbedPane.addTab("Crops", createCropPanel());
        tabbedPane.addTab("Machinery", createMachineryPanel());

        add(tabbedPane);
    }

    // ==================== 1. ANIMAL TAB ====================
    private JPanel createAnimalPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField healthField = new JTextField();
        JButton addButton = new JButton("Add Animal");

        formPanel.add(new JLabel("Animal ID:"));      formPanel.add(idField);
        formPanel.add(new JLabel("Gender:"));         formPanel.add(genderField);
        formPanel.add(new JLabel("Quantity:"));       formPanel.add(qtyField);
        formPanel.add(new JLabel("Animal Type:"));    formPanel.add(typeField);
        formPanel.add(new JLabel("Health Status:"));  formPanel.add(healthField);
        formPanel.add(new JLabel(""));                formPanel.add(addButton);

        // Table to display data
        String[] columns = {"ID", "Gender", "Quantity", "Type", "Health Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Button Action
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String gender = genderField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                String type = typeField.getText();
                String health = healthField.getText();

                // Create object and add to list
                FarmInventoryCategories.Animal animal = new FarmInventoryCategories.Animal(id, gender, qty, type, health);
                animalList.add(animal);

                // Update table & clear fields
                tableModel.addRow(new Object[]{id, gender, qty, type, health});
                idField.setText(""); genderField.setText(""); qtyField.setText("");
                typeField.setText(""); healthField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ==================== 2. CROPS TAB ====================
    private JPanel createCropPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField growthField = new JTextField();
        JTextField dateField = new JTextField();
        JButton addButton = new JButton("Add Crop");

        formPanel.add(new JLabel("Crop ID:"));        formPanel.add(idField);
        formPanel.add(new JLabel("Crop Type:"));      formPanel.add(typeField);
        formPanel.add(new JLabel("Quantity (kg):"));  formPanel.add(qtyField);
        formPanel.add(new JLabel("Growth Status:"));  formPanel.add(growthField);
        formPanel.add(new JLabel("Date Planted:"));   formPanel.add(dateField);
        formPanel.add(new JLabel(""));                formPanel.add(addButton);

        // Table to display data
        String[] columns = {"ID", "Crop Type", "Qty (kg)", "Growth Status", "Date Planted"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Button Action
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String type = typeField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                String growth = growthField.getText();
                String date = dateField.getText();

                FarmInventoryCategories.Crops crop = new FarmInventoryCategories.Crops(id, type, qty, growth, date);
                cropList.add(crop);

                tableModel.addRow(new Object[]{id, type, qty, growth, date});
                idField.setText(""); typeField.setText(""); qtyField.setText("");
                growthField.setText(""); dateField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ==================== 3. MACHINERY TAB (WITH DROPDOWNS) ====================
    private JPanel createMachineryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField qtyField = new JTextField();

        // Dropdown 
        String[] statusOptions = {"Active", "Inactive"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);

        String[] conditionOptions = {"Good", "Broken", "In repair"};
        JComboBox<String> conditionCombo = new JComboBox<>(conditionOptions);

        JButton addButton = new JButton("Add Machinery");

        formPanel.add(new JLabel("Machine ID:"));     formPanel.add(idField);
        formPanel.add(new JLabel("Machine Type:"));   formPanel.add(typeField);
        formPanel.add(new JLabel("Quantity:"));       formPanel.add(qtyField);
        formPanel.add(new JLabel("Status:"));         formPanel.add(statusCombo);
        formPanel.add(new JLabel("Condition:"));      formPanel.add(conditionCombo);
        formPanel.add(new JLabel(""));                formPanel.add(addButton);

        // Tabl to display data
        String[] columns = {"ID", "Machine Type", "Quantity", "Status", "Condition"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Button Action
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String type = typeField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                
                // Get selected values from the dropdown menus
                String status = (String) statusCombo.getSelectedItem();
                String condition = (String) conditionCombo.getSelectedItem();

                FarmInventoryCategories.Machinery machine = new FarmInventoryCategories.Machinery(id, type, qty, status, condition);
                machineryList.add(machine);

                tableModel.addRow(new Object[]{id, type, qty, status, condition});
                
                // Clear text fields
                idField.setText(""); typeField.setText(""); qtyField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FarmInventoryGUI().setVisible(true);
        });
    }
}