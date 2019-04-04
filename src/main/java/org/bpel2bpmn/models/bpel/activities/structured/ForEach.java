package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class ForEach extends Activity {

    /* ForEach specific attributes */
    private String counterName;
    private boolean parallel;

    /* ForEach specific elements */
    private String startCounterValue;
    private String finalCounterValue;
    // TODO: Implement scope element

    public ForEach() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
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
}
