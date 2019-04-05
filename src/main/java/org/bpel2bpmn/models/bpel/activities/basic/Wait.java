package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.*;

public class Wait extends Activity {

    protected boolean isTimer; /* true in case of a <for> construct, false in case of a <until> construct. */
    protected String timeExpression; /* Represents either the duration or deadline expression */

    public Wait() {
        super();

        this.isTimer = false;
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        IntermediateCatchEvent intermediateEvent = createTimerEvent(builder);

        return new MappedPair(intermediateEvent);
    }

    protected IntermediateCatchEvent createTimerEvent(BPMNBuilder builder) {
        IntermediateCatchEvent intermediateEvent = builder.createElement(IntermediateCatchEvent.class);
        TimerEventDefinition timer = builder.createElement(intermediateEvent, TimerEventDefinition.class);

        if (isTimer) {
            TimeDuration timeDuration = builder.createElement(timer, TimeDuration.class);
            timeDuration.setTextContent(timeExpression);
        } else {
            TimeDate timeDate = builder.createElement(timer, TimeDate.class);
            timeDate.setTextContent(timeExpression);
        }

        return intermediateEvent;
    }

    /*
     * Getters & Setters
     */

    public boolean isTimer() {
        return isTimer;
    }

    public void setTimer(boolean timer) {
        isTimer = timer;
    }

    public String getTimeExpression() {
        return timeExpression;
    }

    public void setTimeExpression(String timeExpression) {
        this.timeExpression = timeExpression;
    }
}
