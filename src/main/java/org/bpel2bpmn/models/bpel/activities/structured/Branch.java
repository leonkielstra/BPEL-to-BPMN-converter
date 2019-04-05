package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Branch extends Activity {

    private ArrayList<BPELObject> children;
    private String expressionLanguage;
    private String condition;

    public Branch() {
        this.children = new ArrayList<>();
    }

    public void addChild(BPELObject child) {
        children.add(child);
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        MappedPair result = new MappedPair();
        FlowNode lastElement = null;

        boolean isFirstChild = true;
        for (BPELObject child : children) {
            builder.prepareConditionalSequenceFlow(condition);

            MappedPair mapping = child.toBPMN(builder, lastElement);
            if (!mapping.isEmpty()) {
                builder.createSequenceFlow(lastElement, mapping.getStartNode());
                lastElement = mapping.getEndNode();
            }

            if (isFirstChild && !mapping.isEmpty()) {
                result.setStartNode(mapping.getStartNode());
                isFirstChild = false;
            }
        }

        result.setEndNode(lastElement);

        return result;
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getChildren() {
        return children;
    }

    public String getExpressionLanguage() {
        return expressionLanguage;
    }

    public void setExpressionLanguage(String expressionLanguage) {
        this.expressionLanguage = expressionLanguage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
