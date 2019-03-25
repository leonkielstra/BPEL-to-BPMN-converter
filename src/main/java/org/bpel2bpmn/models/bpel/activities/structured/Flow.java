package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.generic.Documentation;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Flow extends Activity {

    private ArrayList<String> links;
    private ArrayList<Activity> activities;

    public Flow() {
        links = new ArrayList<>();
        activities = new ArrayList<>();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        // TODO: Implement
        return from;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void addLink(String name) {
        this.links.add(name);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
}
