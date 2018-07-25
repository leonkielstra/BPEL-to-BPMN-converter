package org.bpel2bpmn.utilities.parsers.model.generic;

import org.bpel2bpmn.models.bpel.generic.Import;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class ImportParser {

    public static Import parse(Element element) {
        Import importInstance = BPELObjectParser.parse(element, Import.class);
        parseAttributes(importInstance, element);

        ValidationResult validationResult = importInstance.validate();
        if (!validationResult.isValid()) {
            throw new IllegalStateException(validationResult.getMessage());
        }

        return importInstance;
    }

    private static void parseAttributes(Import importInstance, Element element) {
        for (String attributeName : Import.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                importInstance.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
