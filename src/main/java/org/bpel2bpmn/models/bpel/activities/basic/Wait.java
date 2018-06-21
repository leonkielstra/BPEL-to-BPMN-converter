package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Wait extends Activity {

    private boolean isTimer; /* true in case of a <for> construct, false in case of a <until> construct. */
    private String timeExpression; /* Represents either the duration or deadline expression */

    public Wait(boolean isTimer, String timeExpression) {
        super();

        this.isTimer = isTimer;
        this.timeExpression = timeExpression;
    }

    /*
     * Getters & Setters
     */

    public boolean isTimer() {
        return isTimer;
    }

    public String getTimeExpression() {
        return timeExpression;
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, BpmnModelElementInstance from) {
        return null;
    }
}
