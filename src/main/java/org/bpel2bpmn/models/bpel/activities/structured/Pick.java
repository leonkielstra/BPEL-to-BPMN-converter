package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class Pick extends Activity {

    private ArrayList<OnMessage> messageEvents;
    private ArrayList<OnAlarm> alarmEvents;

    public Pick() {
        super();
        messageEvents = new ArrayList<>();
        alarmEvents = new ArrayList<>();
    }

    public void addMessageEvent(OnMessage messageEvent) {
        this.messageEvents.add(messageEvent);
    }

    public void addAlarmEvent(OnAlarm alarmEvent) {
        this.alarmEvents.add(alarmEvent);
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        return null;
    }
}
