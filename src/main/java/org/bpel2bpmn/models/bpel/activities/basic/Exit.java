package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Exit extends Activity {

    public Exit() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, BpmnModelElementInstance from) {
        return null;
    }

}
