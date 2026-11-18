import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * All Create, Read, Update and Delete operations for Animals, Crops and Machinery.
 * Add, update and delete all go through the one execute() helper below.
 * SQLExceptions are passed up to the GUI, which shows a message to the user.
 */
public class FarmInventoryCRUD {

    // Runs any INSERT / UPDATE / DELETE. The ? placeholders are filled from params,
    // so user input can never change the SQL itself.
    private int execute(String sql, Object... params) throws SQLException {
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        }
    }

    // ---------- ANIMALS ----------
    public void addAnimal(Animal a) throws SQLException {
        execute("INSERT INTO Animals (gender, quantity, atype, healthstatus) VALUES (?, ?, ?, ?)",
                a.getGender(), a.getQuantity(), a.getAtype(), a.getHealthstatus());
    }

    public List<Animal> getAllAnimals() throws SQLException {
        List<Animal> list = new ArrayList<>();
        try (Connection conn = Connect.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Animals ORDER BY animalID")) {
            while (rs.next()) {
                list.add(new Animal(rs.getInt("animalID"), rs.getString("gender"), rs.getInt("quantity"),
                        rs.getString("atype"), rs.getString("healthstatus")));
            }
        }
        return list;
    }

    public void updateAnimal(Animal a) throws SQLException {
        execute("UPDATE Animals SET gender = ?, quantity = ?, atype = ?, healthstatus = ? WHERE animalID = ?",
                a.getGender(), a.getQuantity(), a.getAtype(), a.getHealthstatus(), a.getAnimalID());
    }

    public void deleteAnimal(int id) throws SQLException {
        execute("DELETE FROM Animals WHERE animalID = ?", id);
    }

    // ---------- CROPS (dateplanted must be yyyy-MM-dd) ----------
    public void addCrop(Crops c) throws SQLException {
        execute("INSERT INTO Crops (ctype, qtinkg, growthstatus, dateplanted) VALUES (?, ?, ?, ?)",
                c.getCtype(), c.getQtinkg(), c.getGrowthstatus(), Date.valueOf(c.getDateplanted()));
    }

    public List<Crops> getAllCrops() throws SQLException {
        List<Crops> list = new ArrayList<>();
        try (Connection conn = Connect.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Crops ORDER BY cropID")) {
            while (rs.next()) {
                list.add(new Crops(rs.getInt("cropID"), rs.getString("ctype"), rs.getDouble("qtinkg"),
                        rs.getString("growthstatus"), rs.getString("dateplanted")));
            }
        }
        return list;
    }

    public void updateCrop(Crops c) throws SQLException {
        execute("UPDATE Crops SET ctype = ?, qtinkg = ?, growthstatus = ?, dateplanted = ? WHERE cropID = ?",
                c.getCtype(), c.getQtinkg(), c.getGrowthstatus(), Date.valueOf(c.getDateplanted()), c.getCropID());
    }

    public void deleteCrop(int id) throws SQLException {
        execute("DELETE FROM Crops WHERE cropID = ?", id);
    }

    // ---------- MACHINERY (`condition` is a reserved word, so it needs backticks) ----------
    public void addMachinery(Machinery m) throws SQLException {
        execute("INSERT INTO Machinery (machinetype, mquantity, status, `condition`) VALUES (?, ?, ?, ?)",
                m.getMachinetype(), m.getMquantity(), m.getStatus(), m.getCondition());
    }

    public List<Machinery> getAllMachinery() throws SQLException {
        List<Machinery> list = new ArrayList<>();
        try (Connection conn = Connect.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Machinery ORDER BY machineID")) {
            while (rs.next()) {
                list.add(new Machinery(rs.getInt("machineID"), rs.getString("machinetype"), rs.getInt("mquantity"),
                        rs.getString("status"), rs.getString("condition")));
            }
        }
        return list;
    }

    public void updateMachinery(Machinery m) throws SQLException {
        execute("UPDATE Machinery SET machinetype = ?, mquantity = ?, status = ?, `condition` = ? WHERE machineID = ?",
                m.getMachinetype(), m.getMquantity(), m.getStatus(), m.getCondition(), m.getMachineID());
    }

    public void deleteMachinery(int id) throws SQLException {
        execute("DELETE FROM Machinery WHERE machineID = ?", id);
    }
}