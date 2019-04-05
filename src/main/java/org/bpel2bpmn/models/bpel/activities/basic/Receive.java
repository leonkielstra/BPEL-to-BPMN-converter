package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;

public class Receive extends Activity {

    public static final String[] ATTRIBUTES = {
            "partnerLink",      // mandatory
            "operation",        // mandatory
            "portType",         // optional
            "variable",         // optional
            "createInstance",   // optional
            "messageExchange"   // optional
    };

    public Receive() {
        super();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        IntermediateCatchEvent event = createMessageEvent(builder);

        return new MappedPair(event);
    }

    protected IntermediateCatchEvent createMessageEvent(BPMNBuilder builder) {
        IntermediateCatchEvent event = builder.createElement(IntermediateCatchEvent.class);
        builder.createElement(event, MessageEventDefinition.class);

        builder.createMessageFlow(event, attributes.get("partnerLink"), true);

        return event;
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Receive");

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
