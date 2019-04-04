package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;

public class Reply extends Activity {

    public static final String[] ATTRIBUTES = {
            "partnerLink",      // mandatory
            "operation",        // mandatory
            "portType",         // optional
            "variable",         // optional
            "faultName",        // optional
            "messageExchange"   // optional
    };

    public Reply() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) {
        IntermediateThrowEvent messageEvent = builder.createElement(IntermediateThrowEvent.class);
        builder.createElement(messageEvent, MessageEventDefinition.class);
        builder.createMessageFlow(messageEvent, attributes.get("partnerLink"), false);
            // Note: operation should be added to the message event, but this will invalidate the BPMN model

        return new MappedPair(messageEvent);
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Reply");

        if (attributes.get("partnerLink") == null) {
            result.invalidate();
            result.addMissingAttribute("partnerLink");
        }

        if (attributes.get("operation") == null) {
            result.invalidate();
            result.addMissingAttribute("operation");
        }

        return result;
    }
}
