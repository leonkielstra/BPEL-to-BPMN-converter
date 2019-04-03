package org.bpel2bpmn.utilities.parsers.model.activities.basic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Invoke;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class InvokeParser {

    public static Invoke parse(Element element) throws BPELParseException {
        Invoke invoke = BPELObjectParser.parse(element, Invoke.class);
        parseAttributes(invoke, element);
        parseHandlers(invoke, element);

        ValidationResult validationResult = invoke.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        return invoke;
    }

    private static void parseAttributes(Invoke invoke, Element element) {
        for (String attributeName : Invoke.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                invoke.addAttribute(attributeName, attribute.getValue());
            }
        }
    }

    private static void parseHandlers(Invoke invoke, Element element) {
        // Note: Handlers are not implemented in Invoke yet.
    }
}
