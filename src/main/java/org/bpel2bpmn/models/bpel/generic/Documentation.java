package org.bpel2bpmn.models.bpel.generic;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Documentation extends BPELObject {

    private String content;

    public Documentation(String content) {
        this.content = content;
    }

    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
        org.camunda.bpm.model.bpmn.instance.Documentation bpmnDoc = builder.createElement(org.camunda.bpm.model.bpmn.instance.Documentation.class);
        bpmnDoc.setTextContent(content);

        return new MappedPair();
    }

    /*
     * Getters & Setters
     */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
