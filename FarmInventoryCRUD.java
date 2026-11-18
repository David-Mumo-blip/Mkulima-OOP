import java.sql.*;

public class FarmInventoryCRUD {
    public static void main(String[] args) throws SQLException {
        String url = "";
        String user = "";
        String password = "";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();

        // 1. CREATE (INSERT) 
        
        // Insert Animal
        int AnimalsRows = stmt.executeUpdate(
            "INSERT INTO Animals (animalID, gender, quantity, atype, healthstatus) VALUES ('1', 'Female', 10, 'Cows', 'Sick')"
        );
        System.out.println(AnimalsRows + " animal rows added.");

        // Insert Crop
        int CropsRows = stmt.executeUpdate(
            "INSERT INTO Crops (cropID, ctype, qtinkg, growthstatus, dateplanted) VALUES ('1', 'Wheat', 1200.5, 'seedling', '14/11/26')"
        );
        System.out.println(CropsRows + " crop rows added.");

        // Insert Machinery
        int MachineryRows = stmt.executeUpdate(
            "INSERT INTO Machinery (machineID, machinetype, mquantity, status, condition) VALUES ('1', 'Harvester', 'Inactive', 'Good')"
        );
        System.out.println(MachineryRows + " machinery rows added.");


        // 2. READ (SELECT) 
        
        ResultSet rsAnimals = stmt.executeQuery("SELECT * FROM Animals");
        while (rsAnimals.next()) {
            String id = rsAnimals.getString("animalID");
            String gender = rsAnimals.getString("gender");
            int quantity = rsAnimals.getInt("quantity");
            String type = rsAnimals.getString("atype");
            String healthStatus = rsAnimals.getString("healthstatus");
            System.out.println(id  +","+ type + ","+ gender + ", " + quantity + ", " + healthStatus);
        }
        rsAnimals.close();


        //  3. UPDATE 
        
        int updateRows = stmt.executeUpdate(
            "UPDATE animals SET healthstatus = 'Excellent', quantity = 12 WHERE animalID = '1'"
        );
        System.out.println("\n" + updateRows + " animal rows updated.");


        //  4. DELETE 
        
        int deleteRows = stmt.executeUpdate(
            "DELETE FROM machinery WHERE machineID = '1'"
        );
        System.out.println(deleteRows + " machinery rows deleted.");


        //  CLOSE CONNECTIONS 
        stmt.close();
        conn.close();
    }
}