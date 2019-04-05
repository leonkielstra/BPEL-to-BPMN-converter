package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.basic.Receive;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;

import java.util.ArrayList;

public class OnMessage extends Receive {

    private ArrayList<BPELObject> children;

    public OnMessage() {
        super();
        children = new ArrayList<>();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        IntermediateCatchEvent messageEvent = createMessageEvent(builder);

        FlowNode lastNode = messageEvent;
        for (BPELObject child : children) {
            MappedPair mapping = child.toBPMN(builder, lastNode);
            if (!mapping.isEmpty()) {
                builder.createSequenceFlow(lastNode, mapping.getStartNode());
                lastNode = mapping.getEndNode();
            }
        }

        return new MappedPair(messageEvent, lastNode);
    }

    /*
     * Getters & Setters
     */

    @Override
    public void addChild(BPELObject child) throws UnsupportedOperationException {
        children.add(child);
    }
}
