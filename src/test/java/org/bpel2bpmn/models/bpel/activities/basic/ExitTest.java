package org.bpel2bpmn.models.bpel.activities.basic;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.TerminateEventDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExitTest {

    private static Exit exit;
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        exit = new Exit();
        builder = BPMNBuilderFactory.create();
    }

    @Test
    public void toBPMN() {
        FlowNode start = builder.createElement(StartEvent.class);
        FlowNode bpmn = exit.toBPMN(builder, start);

        assertEquals(EndEventImpl.class, bpmn.getClass());
        assertEquals(1, bpmn.getChildElementsByType(TerminateEventDefinition.class).size());
    }
}