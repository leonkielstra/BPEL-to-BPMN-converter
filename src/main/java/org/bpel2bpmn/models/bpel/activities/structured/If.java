package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class If extends Activity {

    // TODO: Represents the if/elseif/else construct, should still be implemented.

    public If() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, BpmnModelElementInstance from) {
        return null;
    }
}
