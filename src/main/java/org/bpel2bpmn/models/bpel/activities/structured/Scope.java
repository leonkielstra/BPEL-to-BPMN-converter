package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SubProcess;

import java.util.ArrayList;

public class Scope extends Activity {

    private ArrayList<BPELObject> children;

    public Scope() {
        super();
        children = new ArrayList<>();
    }

    public void addChild(BPELObject child) {
        children.add(child);
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        SubProcess subProcess = builder.createElement(SubProcess.class);
        builder.setCurrentScope(subProcess);

        // Map and connect children.
        FlowNode lastElement = null;
        for (BPELObject child : children) {
            MappedPair mapping = child.toBPMN(builder, lastElement);
            if (!mapping.isEmpty()) {
                builder.createSequenceFlow(lastElement, mapping.getStartNode());
                lastElement = mapping.getEndNode();
            }
        }

        // Note: Fault handlers aren't mapped for subprocesses

        builder.setCurrentScope((BpmnModelElementInstance) subProcess.getParentElement());

        return new MappedPair(subProcess);
    }
}
