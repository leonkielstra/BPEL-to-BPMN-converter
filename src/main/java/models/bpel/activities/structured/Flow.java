package models.bpel.activities.structured;

import models.bpel.activities.Activity;

import java.util.ArrayList;

public class Flow extends Activity {

    // TODO: Implement Links
    private ArrayList<Activity> activities;

    public Flow(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<Activity> getActivities() {
        return activities;
    }
}
