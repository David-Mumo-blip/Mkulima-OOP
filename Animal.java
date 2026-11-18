/**
 * Model class for one row of the Animals table.
 * Field names match the database column names exactly.
 */
public class Animal {
    private int animalID;
    private String gender;
    private int quantity;
    private String atype;
    private String healthstatus;

    // Used when reading an existing record from the database
    public Animal(int animalID, String gender, int quantity, String atype, String healthstatus) {
        this.animalID = animalID;
        this.gender = gender;
        this.quantity = quantity;
        this.atype = atype;
        this.healthstatus = healthstatus;
    }

    // Used when adding a new record (the database generates the ID)
    public Animal(String gender, int quantity, String atype, String healthstatus) {
        this(0, gender, quantity, atype, healthstatus);
    }

    public int getAnimalID() { return animalID; }
    public void setAnimalID(int animalID) { this.animalID = animalID; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getAtype() { return atype; }
    public void setAtype(String atype) { this.atype = atype; }

    public String getHealthstatus() { return healthstatus; }
    public void setHealthstatus(String healthstatus) { this.healthstatus = healthstatus; }
}