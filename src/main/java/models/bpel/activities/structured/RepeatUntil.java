package models.bpel.activities.structured;

import models.bpel.activities.Activity;

public class RepeatUntil extends Activity {

    private String condition;
    private String activity;

    public RepeatUntil(String name) {
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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
