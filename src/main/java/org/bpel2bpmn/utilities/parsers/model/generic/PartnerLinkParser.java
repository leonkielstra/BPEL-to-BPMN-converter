package org.bpel2bpmn.utilities.parsers.model.generic;

import org.bpel2bpmn.models.bpel.generic.PartnerLink;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

import java.util.HashMap;

public class PartnerLinkParser {

    public static HashMap<String, PartnerLink> parse(Element element) throws IllegalStateException {
        HashMap<String, PartnerLink> partnerLinks = new HashMap<>();

        for (Object child : element.getChildren()) {
            Element partnerLinkElement = (Element) child;
            if (!partnerLinkElement.getName().equals("partnerLink")) {
                continue;
            }

            PartnerLink partnerLink = new PartnerLink();
            parseAttributes(partnerLink, partnerLinkElement);

            ValidationResult validationResult = partnerLink.validate();
            if (!validationResult.isValid()) {
                throw new IllegalStateException(validationResult.getMessage());
            }

            partnerLinks.put(partnerLink.getName(), partnerLink);
        }

        return partnerLinks;
    }

    private static void parseAttributes(PartnerLink partnerLink, Element element) {
        for (String attributeName : PartnerLink.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                partnerLink.addAttribute(attributeName, attribute.getValue());
            }
        }
    }

}
