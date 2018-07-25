package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.models.bpel.activities.structured.OnAlarm;
import org.bpel2bpmn.models.bpel.activities.structured.OnMessage;
import org.bpel2bpmn.models.bpel.activities.structured.Pick;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.basic.WaitParser;
import org.jdom.Element;

public class PickParser {

    public static Pick parse(Element element) {
        Pick pick = new Pick();
        parseElements(pick, element);

        return pick;
    }

    private static void parseElements(Pick pick, Element element) {
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

    private static OnMessage parseOnMessage(Element element) {
        OnMessage event = new OnMessage();

        // TODO: Implement message event (probably Receive).

        BPELParser.parseChildren(event, element);

        return event;
    }

    private static OnAlarm parseOnAlarm(Element element) {
        OnAlarm event = new OnAlarm();

        Wait wait = WaitParser.parse(element);
        event.setAttributes(wait.getAttributes());
        event.setTimer(wait.isTimer());
        event.setTimeExpression(wait.getTimeExpression());

        BPELParser.parseChildren(event, element);

        return event;
    }
}
