package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;

// TODO: implement
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
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        BpmnModelInstance modelInstance = builder.getModelInstance();

        IntermediateCatchEvent event = builder.createElement(IntermediateCatchEvent.class);
        MessageEventDefinition message = modelInstance.newInstance(MessageEventDefinition.class);
        event.addChildElement(message);

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
