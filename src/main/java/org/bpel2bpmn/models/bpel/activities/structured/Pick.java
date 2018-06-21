package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Pick extends Activity {

    private boolean createInstance;

    // TODO: Implement onAlarm and onMessage

    public Pick(boolean createInstance) {
        super();
        this.createInstance = createInstance;
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, BpmnModelElementInstance from) {
        return null;
    }

    public boolean shouldCreateInstance() {
        return createInstance;
    }

    public void setCreateInstance(boolean createInstance) {
        this.createInstance = createInstance;
    }
}
