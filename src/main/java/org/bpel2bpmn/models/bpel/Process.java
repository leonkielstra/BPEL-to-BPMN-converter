package org.bpel2bpmn.models.bpel;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;

import java.util.ArrayList;

public class Process extends BPELObject {

    public final String[] ATTRIBUTES = {
            "name",                 // mandatory
            "targetNamespace",      // optional
            "queryLanguage",        // optional
            "expressionLanguage",   // optional
            "suppressJoinFailure",  // optional
            "exitOnStandardFault",  // optional
            "xmlns"                 // mandatory
    };

    private ArrayList<BPELObject> children;

    public Process() {
        this.children = new ArrayList<>();

        /* Set default attributes */
        addAttribute("name", null);
        addAttribute("targetNamespace", null);
        addAttribute("queryLanguage", "urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0");
        addAttribute("expressionLanguage", "urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0");
        addAttribute("suppressJoinFailure", "no");
        addAttribute("exitOnStandardFault", "no");
        addAttribute("xmlns", null);
    }

    @Override
    public void addChild(BPELObject activity) {
        this.children.add(activity);
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Process");
        if (attributes.get("name") == null) {
            result.invalidate();
            result.addMissingAttribute("name");
        }

        if (attributes.get("xmlns") == null) {
            result.invalidate();
            result.addMissingAttribute("xmlns");
        }

        return result;
    }

    public BpmnModelInstance toBPMN() {
        BPMNBuilder builder = new BPMNBuilder();
        Definitions definitions = builder.createDefinitions("bpel2bpmn", attributes.get("targetNamespace"));
        builder.createExecutableProcess(definitions, attributes.get("name"));

        StartEvent start = builder.createElement("process_start", StartEvent.class);

        // Map and connect children.
        FlowNode lastElement = start;
        for (BPELObject child : children) {
            Activity activity = (Activity) child;
            lastElement = activity.toBPMN(builder, start);
        }

        EndEvent end = builder.createElement("process_end", EndEvent.class);
        builder.createSequenceFlow(lastElement, end);

        return builder.getModelInstance();
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getChildren() {
        return children;
    }
}
