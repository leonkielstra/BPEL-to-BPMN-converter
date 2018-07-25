package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class OnAlarm extends Wait {

    private ArrayList<BPELObject> children;

    public OnAlarm() {
        super();
        children = new ArrayList<>();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
    }

    /*
     * Getters & Setters
     */

    @Override
    public void addChild(BPELObject child) throws UnsupportedOperationException {
        children.add(child);
    }

}
