package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
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
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        FlowNode lastElement = from;
        FlowNode currentElement;

        for (BPELObject child : children) {
            builder.prepareConditionalSequenceFlow(condition);

            if (child instanceof Activity) {
                Activity activity = (Activity) child;
                currentElement = activity.toBPMN(builder, lastElement);
                builder.createSequenceFlow(lastElement, currentElement);
                lastElement = currentElement;
            }
        }

        return lastElement;
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
