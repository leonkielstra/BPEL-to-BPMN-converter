package org.bpel2bpmn.utilities.parsers.model.activities.basic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Reply;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class ReplyParser {

    public static Reply parse(Element element) throws BPELParseException {
        Reply reply = BPELObjectParser.parse(element, Reply.class);
        parseAttributes(reply, element);

        ValidationResult validationResult = reply.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        return reply;
    }

    private static void parseAttributes(Reply reply, Element element) {
        for (String attributeName : Reply.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                reply.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
