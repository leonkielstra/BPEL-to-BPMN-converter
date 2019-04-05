package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class ForEach extends Activity {

    /* ForEach specific attributes */
    private String counterName;
    private boolean parallel;

    /* ForEach specific elements */
    private int startCounterValue;
    private int finalCounterValue;

    public ForEach() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        throw new BPELConversionException("There is no mapping defined from the 'forEach' activity to a BPMN construct.", "forEach");
    }

    /*
     * Getters & Setters
     */

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public int getStartCounterValue() {
        return startCounterValue;
    }

    public void setStartCounterValue(int startCounterValue) {
        this.startCounterValue = startCounterValue;
    }

    public int getFinalCounterValue() {
        return finalCounterValue;
    }

    public void setFinalCounterValue(int finalCounterValue) {
        this.finalCounterValue = finalCounterValue;
    }
}
