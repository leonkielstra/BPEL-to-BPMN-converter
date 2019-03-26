package org.bpel2bpmn.utilities.parsers.model.activities.basic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Throw;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class ThrowParser {

    public static Throw parse(Element element) throws BPELParseException {
        Throw throwInstance = BPELObjectParser.parse(element, Throw.class);
        parseAttributes(throwInstance, element);

        ValidationResult validationResult = throwInstance.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        return throwInstance;
    }

    private static void parseAttributes(Throw throwInstance, Element element) {
        for (String attributeName : Throw.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                throwInstance.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
