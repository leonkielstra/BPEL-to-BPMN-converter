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
        FlowNode lastElement = from;
        MappedPair result = new MappedPair();

        boolean isFirstChild = true;
        for (BPELObject child : children) {
            MappedPair mapping = child.toBPMN(builder, lastElement);
            if (!mapping.isEmpty()) {
                builder.createSequenceFlow(lastElement, mapping.getStartNode());
                lastElement = mapping.getEndNode();
            }

            if (isFirstChild) {
                result.setStartNode(mapping.getStartNode());
                isFirstChild = false;
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
