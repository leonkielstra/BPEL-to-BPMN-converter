package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.TerminateEventDefinition;

public class Exit extends Activity {

    public Exit() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
        EndEvent endEvent = builder.createElement(EndEvent.class);
        builder.createElement(endEvent, TerminateEventDefinition.class);

        return new MappedPair(endEvent);
    }

}
