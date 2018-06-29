package org.bpel2bpmn.models.bpel.activities.basic;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.IntermediateCatchEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.TimerEventDefinitionImpl;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.TimerEventDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaitTest {

    private static FlowNode start;
    private static Wait wait;
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        wait = new Wait();
        builder = BPMNBuilderFactory.create();
        start = builder.createElement(StartEvent.class);
    }

    @Test
    public void toBPMNDuration() {
        String timeExpression = "P5Y2M10D";
        wait.setTimer(true);
        wait.setTimeExpression(timeExpression);

        FlowNode bpmn = wait.toBPMN(builder, start);
        TimerEventDefinitionImpl timer = (TimerEventDefinitionImpl) bpmn.getChildElementsByType(TimerEventDefinition.class).toArray()[0];

        assertEquals(IntermediateCatchEventImpl.class, bpmn.getClass());
        assertEquals(timeExpression, timer.getTimeDuration().getTextContent());
    }

    @Test
    public void toBPMNDate() {
        String timeExpression = "2002-12-24T18:00+01:00";
        wait.setTimer(false);
        wait.setTimeExpression(timeExpression);

        FlowNode bpmn = wait.toBPMN(builder, start);
        TimerEventDefinitionImpl timer = (TimerEventDefinitionImpl) bpmn.getChildElementsByType(TimerEventDefinition.class).toArray()[0];

        assertEquals(IntermediateCatchEventImpl.class, bpmn.getClass());
        assertEquals(timeExpression, timer.getTimeDate().getTextContent());
    }
}