package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.basic.Receive;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class OnMessage extends Receive {

    private ArrayList<BPELObject> children;

    public OnMessage() {
        super();
        children = new ArrayList<>();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
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
