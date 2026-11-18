import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Swing GUI (also holds main). Every tab is built by the same buildTab() method:
 * we give it the input fields, the table, and what each button should do.
 * Data is saved in MySQL through FarmInventoryCRUD.
 */
public class FarmInventoryGUI extends JFrame {

    // An action a button performs; it may throw a database error
    private interface Op { void run() throws SQLException; }

    private final FarmInventoryCRUD crud = new FarmInventoryCRUD();
    private final DefaultTableModel animalModel = model("ID", "Gender", "Quantity", "Type", "Health Status");
    private final DefaultTableModel cropModel = model("ID", "Crop Type", "Qty (kg)", "Growth Status", "Date Planted");
    private final DefaultTableModel machineModel = model("ID", "Machine Type", "Quantity", "Status", "Condition");

    public FarmInventoryGUI() {
        setTitle("Mkulima Farm Inventory Manager");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // ---- Animals tab ----
        JTextField aId = idBox(), aQty = new JTextField(), aType = new JTextField(), aHealth = new JTextField();
        JComboBox<String> aGender = new JComboBox<>(new String[]{"Male", "Female"});
        tabs.addTab("Animals", buildTab(animalModel,
            new String[]{"Animal ID (auto)", "Gender", "Quantity", "Animal Type", "Health Status"},
            new JComponent[]{aId, aGender, aQty, aType, aHealth},
            this::loadAnimals,
            () -> crud.addAnimal(new Animal(text(aGender), whole(aQty, "Quantity"), req(aType, "Animal Type"), req(aHealth, "Health Status"))),
            () -> crud.updateAnimal(new Animal(selectedId(aId), text(aGender), whole(aQty, "Quantity"), req(aType, "Animal Type"), req(aHealth, "Health Status"))),
            () -> crud.deleteAnimal(selectedId(aId))));

        // ---- Crops tab ----
        JTextField cId = idBox(), cType = new JTextField(), cQty = new JTextField(), cGrowth = new JTextField(), cDate = new JTextField();
        tabs.addTab("Crops", buildTab(cropModel,
            new String[]{"Crop ID (auto)", "Crop Type", "Quantity (kg)", "Growth Status", "Date Planted (yyyy-MM-dd)"},
            new JComponent[]{cId, cType, cQty, cGrowth, cDate},
            this::loadCrops,
            () -> crud.addCrop(new Crops(req(cType, "Crop Type"), decimal(cQty, "Quantity (kg)"), req(cGrowth, "Growth Status"), date(cDate, "Date Planted"))),
            () -> crud.updateCrop(new Crops(selectedId(cId), req(cType, "Crop Type"), decimal(cQty, "Quantity (kg)"), req(cGrowth, "Growth Status"), date(cDate, "Date Planted"))),
            () -> crud.deleteCrop(selectedId(cId))));

        // ---- Machinery tab (dropdowns only allow the values the database accepts) ----
        JTextField mId = idBox(), mType = new JTextField(), mQty = new JTextField();
        JComboBox<String> mStatus = new JComboBox<>(new String[]{"Active", "Inactive"});
        JComboBox<String> mCondition = new JComboBox<>(new String[]{"Good", "Broken", "In repair"});
        tabs.addTab("Machinery", buildTab(machineModel,
            new String[]{"Machine ID (auto)", "Machine Type", "Quantity", "Status", "Condition"},
            new JComponent[]{mId, mType, mQty, mStatus, mCondition},
            this::loadMachinery,
            () -> crud.addMachinery(new Machinery(req(mType, "Machine Type"), whole(mQty, "Quantity"), text(mStatus), text(mCondition))),
            () -> crud.updateMachinery(new Machinery(selectedId(mId), req(mType, "Machine Type"), whole(mQty, "Quantity"), text(mStatus), text(mCondition))),
            () -> crud.deleteMachinery(selectedId(mId))));

        add(tabs);

        // Show existing records when the window opens
        try {
            loadAnimals();
            loadCrops();
            loadMachinery();
        } catch (SQLException ex) {
            dbError(ex);
        }
    }

    // ==================== BUILDS ONE TAB (form + buttons + table) ====================
    private JPanel buildTab(DefaultTableModel model, String[] labels, JComponent[] fields,
                            Op reload, Op add, Op update, Op delete) {
        JTable table = new JTable(model);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i] + ":"));
            form.add(fields[i]);
        }

        // Empties the form
        Runnable clear = () -> {
            table.clearSelection();
            for (JComponent f : fields) {
                if (f instanceof JTextField) ((JTextField) f).setText("");
                else ((JComboBox<?>) f).setSelectedIndex(0);
            }
        };

        // Clicking a table row copies its values into the form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (e.getValueIsAdjusting() || row < 0) return;
            for (int i = 0; i < fields.length; i++) {
                Object value = model.getValueAt(row, i);
                if (fields[i] instanceof JTextField) ((JTextField) fields[i]).setText(value.toString());
                else ((JComboBox<?>) fields[i]).setSelectedItem(value);
            }
        });

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        addBtn.addActionListener(e -> perform(add, reload, clear, "Record added."));
        updateBtn.addActionListener(e -> perform(update, reload, clear, "Record updated."));
        deleteBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) perform(delete, reload, clear, "Record deleted.");
        });
        clearBtn.addActionListener(e -> clear.run());

        JPanel buttons = new JPanel(new GridLayout(2, 2, 5, 5));
        buttons.add(addBtn); buttons.add(updateBtn); buttons.add(deleteBtn); buttons.add(clearBtn);

        JPanel left = new JPanel(new BorderLayout(5, 15));
        left.add(form, BorderLayout.NORTH);
        left.add(buttons, BorderLayout.CENTER);
        left.setPreferredSize(new Dimension(320, 0));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(left, BorderLayout.WEST);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // Runs a button's action, then refreshes the table. Bad input shows a warning;
    // a database problem shows an error, so the app never crashes.
    private void perform(Op action, Op reload, Runnable clear, String successMessage) {
        try {
            action.run();
            reload.run();
            clear.run();
            JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            dbError(ex);
        }
    }

    private void dbError(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== LOAD TABLES FROM THE DATABASE ====================
    private void loadAnimals() throws SQLException {
        animalModel.setRowCount(0);
        for (Animal a : crud.getAllAnimals())
            animalModel.addRow(new Object[]{a.getAnimalID(), a.getGender(), a.getQuantity(), a.getAtype(), a.getHealthstatus()});
    }

    private void loadCrops() throws SQLException {
        cropModel.setRowCount(0);
        for (Crops c : crud.getAllCrops())
            cropModel.addRow(new Object[]{c.getCropID(), c.getCtype(), c.getQtinkg(), c.getGrowthstatus(), c.getDateplanted()});
    }

    private void loadMachinery() throws SQLException {
        machineModel.setRowCount(0);
        for (Machinery m : crud.getAllMachinery())
            machineModel.addRow(new Object[]{m.getMachineID(), m.getMachinetype(), m.getMquantity(), m.getStatus(), m.getCondition()});
    }

    // ==================== SMALL HELPERS ====================
    private static DefaultTableModel model(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
    }

    private static JTextField idBox() {
        JTextField f = new JTextField();
        f.setEditable(false);
        return f;
    }

    // ==================== VALIDATION (throw IllegalArgumentException with a clear message) ====================
    private String text(JComponent c) {
        return c instanceof JTextField ? ((JTextField) c).getText().trim() : String.valueOf(((JComboBox<?>) c).getSelectedItem());
    }

    private String req(JComponent c, String label) {
        String t = text(c);
        if (t.isEmpty()) throw new IllegalArgumentException(label + " cannot be empty.");
        return t;
    }

    private int whole(JComponent c, String label) {
        try {
            int v = Integer.parseInt(req(c, label));
            if (v < 0) throw new IllegalArgumentException(label + " cannot be negative.");
            return v;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(label + " must be a whole number (e.g. 10).");
        }
    }

    private double decimal(JComponent c, String label) {
        try {
            double v = Double.parseDouble(req(c, label));
            if (v < 0) throw new IllegalArgumentException(label + " cannot be negative.");
            return v;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(label + " must be a number (e.g. 1200.5).");
        }
    }

    private String date(JComponent c, String label) {
        try {
            return LocalDate.parse(req(c, label)).toString(); // only accepts yyyy-MM-dd
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(label + " must be a real date as yyyy-MM-dd (e.g. 2026-11-14).");
        }
    }

    private int selectedId(JTextField idField) {
        if (idField.getText().trim().isEmpty()) throw new IllegalArgumentException("Please select a record from the table first.");
        return Integer.parseInt(idField.getText().trim());
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FarmInventoryGUI().setVisible(true));
    }
}