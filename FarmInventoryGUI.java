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
        setTitle("Farm Inventory Manager - Full CRUD");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create tabbed pane for the 3 categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Animals", createAnimalPanel());
        tabbedPane.addTab("Crops", createCropPanel());
        tabbedPane.addTab("Machinery", createMachineryPanel());

        add(tabbedPane);
    }

    // ==================== 1. ANIMAL TAB (CRUD) ====================
    private JPanel createAnimalPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField idField = new JTextField();
        
        String[] genderOptions = {"Male", "Female"};
        JComboBox<String> genderCombo = new JComboBox<>(genderOptions);

        JTextField qtyField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField healthField = new JTextField();
        
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Animal ID:"));      formPanel.add(idField);
        formPanel.add(new JLabel("Gender:"));         formPanel.add(genderCombo);
        formPanel.add(new JLabel("Quantity:"));       formPanel.add(qtyField);
        formPanel.add(new JLabel("Animal Type:"));    formPanel.add(typeField);
        formPanel.add(new JLabel("Health Status:"));  formPanel.add(healthField);
        formPanel.add(addButton);                     formPanel.add(updateButton);
        formPanel.add(deleteButton);                  formPanel.add(new JLabel(""));

        // Table to display data
        String[] columns = {"ID", "Gender", "Quantity", "Type", "Health Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Auto-fill form when clicking a row in the table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                genderCombo.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                qtyField.setText(tableModel.getValueAt(row, 2).toString());
                typeField.setText(tableModel.getValueAt(row, 3).toString());
                healthField.setText(tableModel.getValueAt(row, 4).toString());
                idField.setEnabled(false); // Disable ID editing during update
            }
        });

        // CREATE (Add) Action
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String gender = (String) genderCombo.getSelectedItem();
                int qty = Integer.parseInt(qtyField.getText());
                String type = typeField.getText();
                String health = healthField.getText();

                FarmInventoryCategories.Animal animal = new FarmInventoryCategories.Animal(id, gender, qty, type, health);
                animalList.add(animal);
                tableModel.addRow(new Object[]{id, gender, qty, type, health});
                clearFields(idField, qtyField, typeField, healthField, idField);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // UPDATE Action
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String gender = (String) genderCombo.getSelectedItem();
                    int qty = Integer.parseInt(qtyField.getText());
                    String type = typeField.getText();
                    String health = healthField.getText();

                    // Update Table Model
                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(gender, selectedRow, 1);
                    tableModel.setValueAt(qty, selectedRow, 2);
                    tableModel.setValueAt(type, selectedRow, 3);
                    tableModel.setValueAt(health, selectedRow, 4);

                    // Update ArrayList Object
                    animalList.set(selectedRow, new FarmInventoryCategories.Animal(id, gender, qty, type, health));
                    clearFields(idField, qtyField, typeField, healthField, idField);
                    table.clearSelection();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // DELETE Action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                animalList.remove(selectedRow);
                clearFields(idField, qtyField, typeField, healthField, idField);
                table.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ==================== 2. CROPS TAB (CRUD) ====================
    private JPanel createCropPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField growthField = new JTextField();
        JTextField dateField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Crop ID:"));        formPanel.add(idField);
        formPanel.add(new JLabel("Crop Type:"));      formPanel.add(typeField);
        formPanel.add(new JLabel("Quantity (kg):"));  formPanel.add(qtyField);
        formPanel.add(new JLabel("Growth Status:"));  formPanel.add(growthField);
        formPanel.add(new JLabel("Date Planted:"));   formPanel.add(dateField);
        formPanel.add(addButton);                     formPanel.add(updateButton);
        formPanel.add(deleteButton);                  formPanel.add(new JLabel(""));

        String[] columns = {"ID", "Crop Type", "Qty (kg)", "Growth Status", "Date Planted"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                typeField.setText(tableModel.getValueAt(row, 1).toString());
                qtyField.setText(tableModel.getValueAt(row, 2).toString());
                growthField.setText(tableModel.getValueAt(row, 3).toString());
                dateField.setText(tableModel.getValueAt(row, 4).toString());
                idField.setEnabled(false);
            }
        });

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
                clearFields(idField, typeField, qtyField, growthField, dateField, idField);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String type = typeField.getText();
                    int qty = Integer.parseInt(qtyField.getText());
                    String growth = growthField.getText();
                    String date = dateField.getText();

                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(type, selectedRow, 1);
                    tableModel.setValueAt(qty, selectedRow, 2);
                    tableModel.setValueAt(growth, selectedRow, 3);
                    tableModel.setValueAt(date, selectedRow, 4);

                    cropList.set(selectedRow, new FarmInventoryCategories.Crops(id, type, qty, growth, date));
                    clearFields(idField, typeField, qtyField, growthField, dateField, idField);
                    table.clearSelection();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                cropList.remove(selectedRow);
                clearFields(idField, typeField, qtyField, growthField, dateField, idField);
                table.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ==================== 3. MACHINERY TAB (CRUD) ====================
    private JPanel createMachineryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField qtyField = new JTextField();

        String[] statusOptions = {"Active", "Inactive"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);

        String[] conditionOptions = {"Good", "Broken", "In repair"};
        JComboBox<String> conditionCombo = new JComboBox<>(conditionOptions);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Machine ID:"));     formPanel.add(idField);
        formPanel.add(new JLabel("Machine Type:"));   formPanel.add(typeField);
        formPanel.add(new JLabel("Quantity:"));       formPanel.add(qtyField);
        formPanel.add(new JLabel("Status:"));         formPanel.add(statusCombo);
        formPanel.add(new JLabel("Condition:"));      formPanel.add(conditionCombo);
        formPanel.add(addButton);                     formPanel.add(updateButton);
        formPanel.add(deleteButton);                  formPanel.add(new JLabel(""));

        String[] columns = {"ID", "Machine Type", "Quantity", "Status", "Condition"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                typeField.setText(tableModel.getValueAt(row, 1).toString());
                qtyField.setText(tableModel.getValueAt(row, 2).toString());
                statusCombo.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                conditionCombo.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                idField.setEnabled(false);
            }
        });

        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String type = typeField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                String status = (String) statusCombo.getSelectedItem();
                String condition = (String) conditionCombo.getSelectedItem();

                FarmInventoryCategories.Machinery machine = new FarmInventoryCategories.Machinery(id, type, qty, status, condition);
                machineryList.add(machine);
                tableModel.addRow(new Object[]{id, type, qty, status, condition});
                clearFields(idField, typeField, qtyField, idField);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String type = typeField.getText();
                    int qty = Integer.parseInt(qtyField.getText());
                    String status = (String) statusCombo.getSelectedItem();
                    String condition = (String) conditionCombo.getSelectedItem();

                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(type, selectedRow, 1);
                    tableModel.setValueAt(qty, selectedRow, 2);
                    tableModel.setValueAt(status, selectedRow, 3);
                    tableModel.setValueAt(condition, selectedRow, 4);

                    machineryList.set(selectedRow, new FarmInventoryCategories.Machinery(id, type, qty, status, condition));
                    clearFields(idField, typeField, qtyField, idField);
                    table.clearSelection();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                machineryList.remove(selectedRow);
                clearFields(idField, typeField, qtyField, idField);
                table.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row in the table to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // Helper method to clear fields and re-enable ID field
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
        // The last field passed is always the ID field which we want to re-enable
        fields[fields.length - 1].setEnabled(true);
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FarmInventoryGUI().setVisible(true);
        });
    }
}
