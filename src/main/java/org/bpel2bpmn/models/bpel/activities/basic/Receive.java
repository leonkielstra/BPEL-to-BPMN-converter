package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

// TODO: implement
public class Receive extends Activity {

    public Receive() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
    }
}
