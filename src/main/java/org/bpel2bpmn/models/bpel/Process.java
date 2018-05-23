package org.bpel2bpmn.models.bpel;

import java.util.ArrayList;

public class Process extends BPELObject {

    private ArrayList<BPELObject> activities;

    public Process() {
        this.activities = new ArrayList<>();
    }

    @Override
    public void addActivity(BPELObject activity) {
        this.activities.add(activity);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getActivities() {
        return activities;
    }
}
