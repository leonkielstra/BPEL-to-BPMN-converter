package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

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
        // TODO: Find most enclosed data object for input or create new one in builder

        // TODO: Create send task -- Connect to process with partnerLink
        // TODO: Connect send task to input data object

        // Map for synchronous invoke
        if (isSynchronous()) {
            // TODO: Create receive task -- Connect to process with partnerLink
            // TODO: Find most enclosed data object for output or create new one in builder
            // TODO: Connect receive task to output data object
        }

        // TODO: Implement fault handlers

        return from; // TODO: return actual node
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
