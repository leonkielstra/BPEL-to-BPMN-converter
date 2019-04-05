package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Error;

public class Throw extends Activity {

    public static final String[] ATTRIBUTES = {
            "faultName",         // mandatory
            "faultVariable",     // optional
    };

    public Throw() {
        super();
        attributes.put("faultName", null);
        attributes.put("faultVariable", null);
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
        BpmnModelInstance modelInstance = builder.getModelInstance();

        Error error = builder.createElement(modelInstance.getDefinitions(), Error.class);
        error.setName(getFaultName());

        FlowNode endEvent = builder.createElement(EndEvent.class);
        ErrorEventDefinition errorEventDef = builder.createElement(endEvent, ErrorEventDefinition.class);
        errorEventDef.setError(error);

        return new MappedPair(endEvent);
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Throw");

        if (attributes.get("faultName") == null) {
            result.invalidate();
            result.addMissingAttribute("faultName");
        }

        return result;
    }

    /*
     * Getters & Setters
     */

    public String getFaultName() {
        return attributes.get("faultName");
    }

    public String getFaultVariable() {
        return attributes.get("faultVariable");
    }
}
