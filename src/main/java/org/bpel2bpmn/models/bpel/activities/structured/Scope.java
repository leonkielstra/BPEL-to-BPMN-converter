package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Scope extends Activity {
    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
    }
    // TODO: Implement this. A scope is like subprocess, need to investigate if it can extend a Process.
}
