package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Empty extends Activity {

    public Empty() {
        super();
    }

    /**
     * This activity is skipped in the mapping.
     * @return previous activity (such that it can be mapped to the next activity).
     */
    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return from;
    }
}
