package org.bpel2bpmn.utilities.parsers.model.generic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.generic.PartnerLink;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

import java.util.HashMap;

public class PartnerLinksParser {

    public static HashMap<String, PartnerLink> parse(Element element) throws BPELParseException {
        HashMap<String, PartnerLink> partnerLinks = new HashMap<>();

        for (Object child : element.getChildren()) {
            Element childElement = (Element) child;
            if (!childElement.getName().equals("partnerLink")) {
                continue;
            }

            PartnerLink partnerLink = parsePartnerLink((Element) child);
            partnerLinks.put(partnerLink.getName(), partnerLink);
        }

        return partnerLinks;
    }

    private static PartnerLink parsePartnerLink(Element element) throws BPELParseException {
        PartnerLink partnerLink = new PartnerLink();
        parseAttributes(partnerLink, element);

        ValidationResult validationResult = partnerLink.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        return partnerLink;
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
