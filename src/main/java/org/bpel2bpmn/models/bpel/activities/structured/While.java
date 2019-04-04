package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.*;

public class While extends LoopActivity {

    public While() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        FlowNode subProcess;

        ExclusiveGateway afterGateway = builder.createElement(ExclusiveGateway.class);
        ExclusiveGateway beforeGateway = builder.createElement(ExclusiveGateway.class);

        if (children.size() != 1 || !(children.get(0) instanceof Scope)) {
            subProcess = builder.createElement(SubProcess.class);
            builder.setCurrentScope(subProcess);

            FlowNode lastNode = builder.createElement(StartEvent.class);
            for (BPELObject child : children) {
                MappedPair mapping = child.toBPMN(builder, lastNode);
                builder.createSequenceFlow(lastNode, mapping.getStartNode());
                lastNode = mapping.getEndNode();
            }

            FlowNode end = builder.createElement(EndEvent.class);
            builder.createSequenceFlow(lastNode, end);

            builder.setCurrentScope((BpmnModelElementInstance) subProcess.getParentElement());
        } else {
            subProcess = children.get(0).toBPMN(builder, beforeGateway).getStartNode();
        }

        builder.prepareConditionalSequenceFlow(condition);
        builder.createSequenceFlow(beforeGateway, subProcess);
        builder.createSequenceFlow(beforeGateway, afterGateway);

        builder.createSequenceFlow(subProcess, afterGateway);

        builder.prepareConditionalSequenceFlow(condition);
        builder.createSequenceFlow(afterGateway, subProcess);

        return new MappedPair(beforeGateway, afterGateway);
    }
}
