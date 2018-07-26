package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.LoopCharacteristicsImpl;
import org.camunda.bpm.model.bpmn.instance.*;

import java.util.ArrayList;

public class While extends LoopActivity {

    public While() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        FlowNode subProcess;

        if (children.size() != 1 || !(children.get(0) instanceof Scope)) {
            subProcess = builder.createElement(SubProcess.class);
            builder.setCurrentScope(subProcess);

            FlowNode lastNode = builder.createElement(StartEvent.class);
            FlowNode current;
            for (BPELObject child : children) {
                current = child.toBPMN(builder, lastNode);
                builder.createSequenceFlow(lastNode, current);
                lastNode = current;
            }

            FlowNode end = builder.createElement(EndEvent.class);
            builder.createSequenceFlow(lastNode, end);

            builder.setCurrentScope((BpmnModelElementInstance) subProcess.getParentElement());
        } else {
            subProcess = children.get(0).toBPMN(builder, from);
        }

        ExclusiveGateway exclusiveGateway = builder.createElement(ExclusiveGateway.class);
        builder.createSequenceFlow(subProcess, exclusiveGateway);

        builder.prepareConditionalSequenceFlow(condition);
        builder.createSequenceFlow(exclusiveGateway, subProcess);

        return exclusiveGateway;
    }
}
