package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.EventBasedGateway;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;

import java.util.ArrayList;

public class Pick extends Activity {

    private ArrayList<OnMessage> messageEvents;
    private ArrayList<OnAlarm> alarmEvents;

    public Pick() {
        super();
        messageEvents = new ArrayList<>();
        alarmEvents = new ArrayList<>();
    }

    @Override
    public MappedPair toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        EventBasedGateway startGateway = builder.createElement(EventBasedGateway.class);
        IntermediateCatchEvent catchEvent = builder.createElement(IntermediateCatchEvent.class);

        MappedPair mapping;
        for (OnMessage messageEvent : messageEvents) {
            mapping = messageEvent.toBPMN(builder, startGateway);
            builder.createSequenceFlow(startGateway, mapping.getStartNode());
            builder.createSequenceFlow(mapping.getEndNode(), catchEvent);
        }

        for (OnAlarm alarmEvent : alarmEvents) {
            mapping = alarmEvent.toBPMN(builder, startGateway);
            builder.createSequenceFlow(startGateway, mapping.getStartNode());
            builder.createSequenceFlow(mapping.getEndNode(), catchEvent);
        }

        return new MappedPair(startGateway, catchEvent);
    }

    public void addMessageEvent(OnMessage messageEvent) {
        this.messageEvents.add(messageEvent);
    }

    public void addAlarmEvent(OnAlarm alarmEvent) {
        this.alarmEvents.add(alarmEvent);
    }
}
