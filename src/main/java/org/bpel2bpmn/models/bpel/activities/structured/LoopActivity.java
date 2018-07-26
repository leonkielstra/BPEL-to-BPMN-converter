package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;

import java.util.ArrayList;

public abstract class LoopActivity extends Activity {

    protected ArrayList<BPELObject> children;
    protected String condition;
    protected String expressionLanguage;

    public LoopActivity() {
        children = new ArrayList<>();
    }

    public void addChild(BPELObject child) {
        children.add(child);
    }

    /*
     * Getters & Setters
     */

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setExpressionLanguage(String expLanguage) {
        this.expressionLanguage = expLanguage;
    }
}
