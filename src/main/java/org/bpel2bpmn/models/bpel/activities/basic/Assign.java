package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Assign extends Activity {

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        throw new BPELConversionException("There is no mapping defined from the 'assign' activity to a BPMN construct.", "assign");
    }
}
