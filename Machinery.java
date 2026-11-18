/**
 * Model class for one row of the Machinery table.
 * status is "Active" or "Inactive".
 * condition is "Good", "Broken" or "In repair".
 */
public class Machinery {
    private int machineID;
    private String machinetype;
    private int mquantity;
    private String status;
    private String condition;

    // Used when reading an existing record from the database
    public Machinery(int machineID, String machinetype, int mquantity, String status, String condition) {
        this.machineID = machineID;
        this.machinetype = machinetype;
        this.mquantity = mquantity;
        this.status = status;
        this.condition = condition;
    }

    // Used when adding a new record (the database generates the ID)
    public Machinery(String machinetype, int mquantity, String status, String condition) {
        this(0, machinetype, mquantity, status, condition);
    }

    public int getMachineID() { return machineID; }
    public void setMachineID(int machineID) { this.machineID = machineID; }

    public String getMachinetype() { return machinetype; }
    public void setMachinetype(String machinetype) { this.machinetype = machinetype; }

    public int getMquantity() { return mquantity; }
    public void setMquantity(int mquantity) { this.mquantity = mquantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}