package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.structured.LoopActivity;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.jdom.Element;

public class LoopParser {

    public static <T extends LoopActivity> T parse(Element element, Class<T> loopClass) throws BPELParseException {
        T loopInstance = BPELObjectParser.parse(element, loopClass);

        Element condition = null;
        boolean hasCondition = false;
        for (Object object : element.getChildren()) {
            Element child = (Element) object;
            if (child.getName().equals("condition")) {
                condition = child;
                hasCondition = true;
                break;
            }
        }

        if (hasCondition) {
            loopInstance.setCondition(condition.getValue());
            String expLanguage = element.getAttributeValue("expressionLanguage");
            if (expLanguage != null) {
                loopInstance.setExpressionLanguage(expLanguage);
            } else {
                loopInstance.setExpressionLanguage(loopInstance.getParentExpressionLanguage());
            }
        } else {
            throw new IllegalStateException("Expected a condition for this while loop.");
        }

        BPELParser.parseChildren(loopInstance, element);

        return loopInstance;
    }
}
