package org.bpel2bpmn.utilities.parsers.model;

import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessParser {

    private static Logger LOG = LoggerFactory.getLogger(ProcessParser.class);

    public static Process parse(Element element) throws IllegalStateException {
        Process process = new Process();
        parseAttributes(process, element);

        return process;
    }

    private static void parseAttributes(Process process, Element element) throws IllegalStateException {
        for (String attributeName : process.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                process.addAttribute(attributeName, attribute.getValue());
            }
        }

        process.addAttribute("xmlns", element.getNamespace().getURI());

        ValidationResult validationResult = process.validate();
        if (!validationResult.isValid()) {
            throw new IllegalStateException(validationResult.getMessage());
        }
    }
}
