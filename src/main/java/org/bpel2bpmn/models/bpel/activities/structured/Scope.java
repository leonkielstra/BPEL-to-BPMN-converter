package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Scope extends Activity {

    private ArrayList<BPELObject> children;

    public Scope() {
        super();
        children = new ArrayList<>();
    }

    public void addChild(BPELObject child) {
        children.add(child);
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
    }
}
