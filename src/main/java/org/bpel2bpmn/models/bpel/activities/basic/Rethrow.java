package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.ErrorEventDefinition;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Rethrow extends Activity {

    public Rethrow() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        EndEvent event = builder.createElement(EndEvent.class);
        ErrorEventDefinition error = builder.getModelInstance().newInstance(ErrorEventDefinition.class);
        event.addChildElement(error);

        return event;
    }
}
