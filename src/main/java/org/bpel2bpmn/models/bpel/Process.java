package org.bpel2bpmn.models.bpel;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.generic.PartnerLink;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Process extends BPELObject {

    private static Logger LOG = LoggerFactory.getLogger(Process.class);

    public static final String[] ATTRIBUTES = {
            "name",                 // mandatory
            "targetNamespace",      // optional
            "queryLanguage",        // optional
            "expressionLanguage",   // optional
            "suppressJoinFailure",  // optional
            "exitOnStandardFault",  // optional
            "xmlns"                 // mandatory
    };

    private ArrayList<BPELObject> children;
    private HashMap<String, PartnerLink> partnerLinks;

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
    public void addChild(BPELObject child) {
        this.children.add(child);
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return from;
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
        Definitions definitions = builder.createDefinitions("bpel2bpmn");
        builder.createExecutableProcess(definitions, attributes.get("name"));

        mapPartnerLinks(builder);

        StartEvent start = builder.createElementWithId("process_start", StartEvent.class);

        // Map and connect children.
        FlowNode lastElement = start;
        for (BPELObject child : children) {
            // TODO: Implement non-activities.
            Activity activity = (Activity) child;
            lastElement = activity.toBPMN(builder, start);
        }

        if (lastElement.getClass() != EndEventImpl.class) {
            EndEvent end = builder.createElementWithId("process_end", EndEvent.class);
            builder.createSequenceFlow(lastElement, end);
        }

        Bpmn.validateModel(builder.getModelInstance());
        return builder.getModelInstance();
    }

    private void mapPartnerLinks(BPMNBuilder builder) {
        for (PartnerLink partnerLink : partnerLinks.values()) {
            partnerLink.toBPMN(builder);
        }
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getChildren() {
        return children;
    }

    public HashMap<String, PartnerLink> getPartnerLinks() {
        return partnerLinks;
    }

    public void setPartnerLinks(HashMap<String, PartnerLink> partnerLinks) {
        this.partnerLinks = partnerLinks;
    }
}
