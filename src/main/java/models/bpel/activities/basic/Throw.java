package models.bpel.activities.basic;

import models.bpel.activities.Activity;

public class Throw extends Activity {

    /* Throw specific attributes */
    private String faultName;
    private String faultVariable;

    public Throw(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null; // TODO
    }

    /*
     * Getters & Setters
     */

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getFaultVariable() {
        return faultVariable;
    }

    public void setFaultVariable(String faultVariable) {
        this.faultVariable = faultVariable;
    }
}
