package org.bpel2bpmn.models.bpel.generic;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.utilities.validation.ValidationResult;

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

    public String getName() {
        return attributes.get("name");
    }
}
