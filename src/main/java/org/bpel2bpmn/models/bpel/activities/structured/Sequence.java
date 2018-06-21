package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Sequence extends Activity {

    private ArrayList<Activity> activities;

    public Sequence() {
        super();
        this.activities = new ArrayList<>();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return from;
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
