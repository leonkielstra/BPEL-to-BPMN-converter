package org.bpel2bpmn.utilities.parsers.model.activities.basic;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WaitParser {

    private static Logger LOG = LoggerFactory.getLogger(WaitParser.class);

    private final static String EXP_LANGUAGE = "expressionLanguage";

    public static Wait parse(Element element) throws BPELParseException {
        Wait wait = BPELObjectParser.parse(element, Wait.class);
        parseElements(element, wait);

        return wait;
    }

    private static void parseElements(Element element, Wait wait) throws BPELParseException {
        List children = element.getChildren();
        for (Object child : children) {
            Element childElement = (Element) child;

            String elementName = childElement.getName().toLowerCase();
            if (elementName.equals("for") || elementName.equals("until")) {
                String expLanguage = childElement.getAttributeValue(EXP_LANGUAGE);
                if (expLanguage != null) {
                    wait.addAttribute(EXP_LANGUAGE, expLanguage);
                } else {
                    wait.addAttribute(EXP_LANGUAGE, wait.getParentExpressionLanguage());
                }

                wait.setTimeExpression(childElement.getValue());

                if (elementName.equals("for")) {
                    wait.setTimer(true);
                }

                return;
            }
        }

        throw new BPELParseException("The Wait activity should have an 'until' or a 'for' element.");
    }
}
