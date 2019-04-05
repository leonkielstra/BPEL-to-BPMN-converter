package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.models.bpel.activities.structured.OnAlarm;
import org.bpel2bpmn.models.bpel.activities.structured.OnMessage;
import org.bpel2bpmn.models.bpel.activities.structured.Pick;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.basic.WaitParser;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.jdom.Attribute;
import org.jdom.Element;

public class PickParser {

    public static Pick parse(Element element) throws BPELParseException {
        Pick pick = new Pick();
        parseElements(pick, element);

        return pick;
    }

    private static void parseElements(Pick pick, Element element) throws BPELParseException {
        for (Object object : element.getChildren()) {
            Element child = (Element) object;

            switch (child.getName().toLowerCase()) {
                case "onmessage":
                    OnMessage messageEvent = parseOnMessage(child);
                    pick.addMessageEvent(messageEvent);
                    break;
                case "onalarm":
                    OnAlarm alarmEvent = parseOnAlarm(child);
                    pick.addAlarmEvent(alarmEvent);
                    break;
            }
        }
    }

    private static OnMessage parseOnMessage(Element element) throws BPELParseException {
        OnMessage event = new OnMessage();

        // Parse onMessage attributes
        for (String attributeName : OnMessage.ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                event.addAttribute(attributeName, attribute.getValue());
            }
        }

        // Validate onMessage event
        ValidationResult validationResult = event.validate();
        if (!validationResult.isValid()) {
            throw new BPELParseException(validationResult.getMessage());
        }

        BPELParser.parseChildren(event, element);

        return event;
    }

    private static OnAlarm parseOnAlarm(Element element) throws BPELParseException {
        OnAlarm event = new OnAlarm();

        Wait wait = WaitParser.parse(element);
        event.setAttributes(wait.getAttributes());
        event.setTimer(wait.isTimer());
        event.setTimeExpression(wait.getTimeExpression());

        BPELParser.parseChildren(event, element);

        return event;
    }
}
