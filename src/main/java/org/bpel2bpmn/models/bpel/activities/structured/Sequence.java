package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Sequence extends Activity {

    private ArrayList<BPELObject> children;

    public Sequence() {
        super();
        this.children = new ArrayList<>();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        MappedPair result = new MappedPair();
        FlowNode lastElement = null;

        boolean isFirstChild = true;
        for (BPELObject child : children) {
            // Map child activity to bpmm
            MappedPair mapping = child.toBPMN(builder, lastElement);

            // If a start node is defined and mapping is returned, connect them.
            if (lastElement != null && !mapping.isEmpty()) {
                builder.createSequenceFlow(lastElement, mapping.getStartNode());
            }

            // Set end node of mapping as next element to connect to
            if (!mapping.isEmpty()) {
                lastElement = mapping.getEndNode();
            }

            // Set start node of this sequence
            if (isFirstChild) {
                result.setStartNode(mapping.getStartNode());
                if (result.getStartNode() != null) isFirstChild = false;
            }
        }

        result.setEndNode(lastElement);

        return result;
    }

    @Override
    public void addChild(BPELObject child) throws UnsupportedOperationException {
        this.children.add(child);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getChildren() {
        return this.children;
    }

}
