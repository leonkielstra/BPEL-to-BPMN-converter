package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.impl.instance.OperationImpl;
import org.camunda.bpm.model.bpmn.impl.instance.SourceRef;
import org.camunda.bpm.model.bpmn.impl.instance.Target;
import org.camunda.bpm.model.bpmn.impl.instance.TargetRef;
import org.camunda.bpm.model.bpmn.instance.*;

public class Invoke extends Activity {

    public static final String[] ATTRIBUTES = {
            "partnerLink",      // mandatory
            "operation",        // mandatory
            "portType",         // optional
            "inputVariable",    // optional
            "outputVariable",   // optional
    };

    public Invoke() {
        super();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        FlowNode lastNode = createSendTask(builder);

        // Map for synchronous invoke
        if (isSynchronous()) {
            lastNode = createReceiveTask(builder, (SendTask) lastNode);
        }

        // Note: Fault handlers are not implemented. Implementation should be placed around here.

        return lastNode;
    }

    private SendTask createSendTask(BPMNBuilder builder) {
        // Create send task
        SendTask sendTask = builder.createElement(SendTask.class);
            // Note: operation should be added to the send task, but this would invalidate the BPMN model

        Property property = builder.createElement(sendTask, Property.class);

        // Connect send task to input data object
        SourceRef sourceRef;
        TargetRef targetRef;
        if (getInputVariable() != null) {
            DataObjectReference inputReference = builder.findOrCreateDataObject(getInputVariable());
            DataInputAssociation inputAssociation = builder.createElement(sendTask, DataInputAssociation.class);

            sourceRef = builder.createElement(inputAssociation, SourceRef.class);
            sourceRef.setTextContent(inputReference.getId());
            targetRef = builder.createElement(inputAssociation, TargetRef.class);
            targetRef.setTextContent(property.getId());
        }

        // Connect send task to process with partnerLink
        builder.createMessageFlow(sendTask, attributes.get("partnerLink"), false);

        return sendTask;
    }

    private ReceiveTask createReceiveTask(BPMNBuilder builder, SendTask sendTask) {
        ReceiveTask receiveTask = builder.createElement(ReceiveTask.class);
            // Note: operation should be added to the receive task, but this would invalidate the BPMN model

        Property property = builder.createElement(receiveTask, Property.class);

        DataObjectReference outputReference = builder.findOrCreateDataObject(getOutputVariable());
        DataOutputAssociation outputAssociation = builder.createElement(receiveTask, DataOutputAssociation.class);

        SourceRef sourceRef = builder.createElement(outputAssociation, SourceRef.class);
        sourceRef.setTextContent(property.getId());
        TargetRef targetRef = builder.createElement(outputAssociation, TargetRef.class);
        targetRef.setTextContent(outputReference.getId());

        // Connect receive task to process with partnerLink
        builder.createMessageFlow(receiveTask, attributes.get("partnerLink"), true);

        // Connect both message tasks
        builder.createSequenceFlow(sendTask, receiveTask);

        return receiveTask;
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Invoke");

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

    private boolean isSynchronous() {
        return (getOutputVariable() != null);
    }

    /*
     * Getters & Setters
     */
    public String getInputVariable() {
        return attributes.get("inputVariable");
    }

    public String getOutputVariable() {
        return attributes.get("outputVariable");
    }
}
