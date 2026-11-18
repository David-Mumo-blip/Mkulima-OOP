/**
 * Model class for one row of the Crops table.
 * dateplanted is kept as text in the format yyyy-MM-dd.
 */
public class Crops {
    private int cropID;
    private String ctype;
    private double qtinkg;
    private String growthstatus;
    private String dateplanted;

    // Used when reading an existing record from the database
    public Crops(int cropID, String ctype, double qtinkg, String growthstatus, String dateplanted) {
        this.cropID = cropID;
        this.ctype = ctype;
        this.qtinkg = qtinkg;
        this.growthstatus = growthstatus;
        this.dateplanted = dateplanted;
    }

    // Used when adding a new record (the database generates the ID)
    public Crops(String ctype, double qtinkg, String growthstatus, String dateplanted) {
        this(0, ctype, qtinkg, growthstatus, dateplanted);
    }

    public int getCropID() { return cropID; }
    public void setCropID(int cropID) { this.cropID = cropID; }

    public String getCtype() { return ctype; }
    public void setCtype(String ctype) { this.ctype = ctype; }

    public double getQtinkg() { return qtinkg; }
    public void setQtinkg(double qtinkg) { this.qtinkg = qtinkg; }

    public String getGrowthstatus() { return growthstatus; }
    public void setGrowthstatus(String growthstatus) { this.growthstatus = growthstatus; }

    public String getDateplanted() { return dateplanted; }
    public void setDateplanted(String dateplanted) { this.dateplanted = dateplanted; }
}