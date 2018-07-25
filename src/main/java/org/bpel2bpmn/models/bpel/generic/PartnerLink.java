package org.bpel2bpmn.models.bpel.generic;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Participant;

public class PartnerLink extends BPELObject {

    public static final String[] ATTRIBUTES = {
            "name",             // mandatory
            "partnerLinkType",  // mandatory
            "myRole",           // optional
            "partnerRole"       // optional
    };

    public PartnerLink() {
        super();

        addAttribute("name", null);
        addAttribute("partnerLinkType", null);
        addAttribute("myRole", null);
        addAttribute("partnerRole", null);
    }

    public void toBPMN(BPMNBuilder builder) {
        if (attributes.get("partnerRole") == null) {
            return;
        }

        Participant participant = builder.createParticipant();
        participant.setName(getPartnerRole());
        participant.setAttributeValue("processRef", getPartnerRole() + "Process");
        builder.addParticipant(participant);
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("PartnerLink");

        if (attributes.get("name") == null) {
            result.invalidate();
            result.addMissingAttribute("name");
        }

        if (attributes.get("partnerLinkType") == null) {
            result.invalidate();
            result.addMissingAttribute("partnerLinkType");
        }

        return result;
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return from;
    }

    /*
     * Getters & Setters
     */

    public String getName() {
        return attributes.get("name");
    }

    public String getMyRole() {
        return attributes.get("myRole");
    }

    public String getPartnerRole() {
        return attributes.get("partnerRole");
    }
}
