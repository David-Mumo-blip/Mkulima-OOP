public class FarmInventoryCategories {

    public static class Animal {
        private int animalID;
        private String gender;
        private int quantity;
        private String atype;
        private String healthstatus;

        public Animal(int animalID, String gender, int quantity, String atype, String healthstatus) {
            this.animalID = animalID;
            this.gender = gender;
            this.quantity = quantity;
            this.atype = atype;
            this.healthstatus = healthstatus;
        }

        // Setters and Getters
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

    public static class Crops {
        private int cropID;
        private String ctype;
        private int qtinkg;
        private String growthstatus;
        private String dateplanted; 

        public Crops(int cropID, String ctype, int qtinkg, String growthstatus, String dateplanted) {
            this.cropID = cropID;
            this.ctype = ctype;
            this.qtinkg = qtinkg;
            this.growthstatus = growthstatus;
            this.dateplanted = dateplanted;
        }

        // Setters and Getters
        public int getCropID() { return cropID; }
        public void setCropID(int cropID) { this.cropID = cropID; }

        public String getCtype() { return ctype; }
        public void setCtype(String ctype) { this.ctype = ctype; }

        public int getQtinkg() { return qtinkg; }
        public void setQtinkg(int qtinkg) { this.qtinkg = qtinkg; }

        public String getGrowthstatus() { return growthstatus; }
        public void setGrowthstatus(String growthstatus) { this.growthstatus = growthstatus; }

        public String getDateplanted() { return dateplanted; }
        public void setDateplanted(String dateplanted) { this.dateplanted = dateplanted; }
    }

    public static class Machinery {
        private int machineID;
        private String machinetype;
        private int mquantity;
        private String status;
        private String condition; 

        public Machinery(int machineID, String machinetype, int mquantity, String status, String condition) {
            this.machineID = machineID;
            this.machinetype = machinetype;
            this.mquantity = mquantity;
            this.status = status;
            this.condition = condition;
        }

        // Setters and Getters
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
}