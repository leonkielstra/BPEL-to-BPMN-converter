package models.bpel.activities.structured;

import models.bpel.activities.Activity;

import java.util.ArrayList;

public class While extends Activity {

    private String condition;
    private Activity activities;

    public While(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null;
    }

    /*
     * Getters & Setters
     */

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Activity getActivities() {
        return activities;
    }

    public void setActivities(Activity activities) {
        this.activities = activities;
    }
}
