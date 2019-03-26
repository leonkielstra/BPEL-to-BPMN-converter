package org.bpel2bpmn.utilities.parsers.model.activities.basic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Receive;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class ReceiveParser {

    public static Receive parse(Element element) throws BPELParseException {
        Receive receive = BPELObjectParser.parse(element, Receive.class);
        parseAttributes(receive, element);

        ValidationResult validationResult = receive.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        return receive;
    }

    private static void parseAttributes(Receive receive, Element element) {
        for (String attributeName : Receive.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                receive.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
