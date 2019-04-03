package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

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
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
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
