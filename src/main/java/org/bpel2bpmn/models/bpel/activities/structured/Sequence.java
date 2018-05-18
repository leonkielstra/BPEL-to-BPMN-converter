package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;

import java.util.ArrayList;

public class Sequence extends Activity {

    private ArrayList<Activity> activities;

    public Sequence(String name) {
        super(name);
        this.activities = new ArrayList<>();
    }

    @Override
    public Object toBPMN() {
        return null; // TODO
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }

}
