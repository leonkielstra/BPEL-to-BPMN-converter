package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;

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
