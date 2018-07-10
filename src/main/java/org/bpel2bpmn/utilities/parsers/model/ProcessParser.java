package org.bpel2bpmn.utilities.parsers.model;

import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.models.bpel.generic.PartnerLink;
import org.bpel2bpmn.utilities.parsers.model.generic.PartnerLinkParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ProcessParser {

    private static Logger LOG = LoggerFactory.getLogger(ProcessParser.class);

    public static Process parse(Element element) throws IllegalStateException {
        Process process = new Process();
        parseAttributes(process, element);
        parsePartnerLinks(process, element);

        return process;
    }

    private static void parseAttributes(Process process, Element element) throws IllegalStateException {
        for (String attributeName : Process.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                process.addAttribute(attributeName, attribute.getValue());
            }
        }

        process.addAttribute("xmlns", element.getNamespaceURI());

        // Parse additional namespaces
        for (Object object : element.getAdditionalNamespaces()) {
            Namespace namespace = (Namespace) object;
            process.addAttribute("xmlns:" + namespace.getPrefix(), namespace.getURI());
        }

        ValidationResult validationResult = process.validate();
        if (!validationResult.isValid()) {
            throw new IllegalStateException(validationResult.getMessage());
        }
    }

    private static void parsePartnerLinks(Process process, Element element)  throws IllegalStateException {
        Element partnerLinksElement = null;
        for (Object child : element.getChildren()) {
            partnerLinksElement = (Element) child;
            if (partnerLinksElement.getName().equals("partnerLinks")) {
                break;
            }
            partnerLinksElement = null;
        }

        if (partnerLinksElement != null) {
            HashMap<String, PartnerLink> partnerLinks = PartnerLinkParser.parse(partnerLinksElement);
            process.setPartnerLinks(partnerLinks);
        }
    }
}
