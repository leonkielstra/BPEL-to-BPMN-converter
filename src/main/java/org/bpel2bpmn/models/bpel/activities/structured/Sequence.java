package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Sequence extends Activity {

    private ArrayList<BPELObject> children;

    public Sequence() {
        super();
        this.children = new ArrayList<>();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        FlowNode lastElement = from;
        FlowNode currentElement;

        for (BPELObject child : children) {
            if (child instanceof Activity) {
                Activity activity = (Activity) child;
                currentElement = activity.toBPMN(builder, lastElement);
                builder.createSequenceFlow(lastElement, currentElement);
                lastElement = currentElement;
            }
            // TODO: Implement non-activities (e.g. Documentation).
        }

        return lastElement;
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
