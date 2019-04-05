package org.bpel2bpmn.models.bpel.activities.basic;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.structures.MappedPair;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class EmptyTest {

    private static Empty empty;
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        empty = new Empty();
        builder = BPMNBuilderFactory.create();
    }

    @Test
    public void toBPMN() {
        FlowNode start = builder.createElement(StartEvent.class);
        MappedPair bpmn = empty.toBPMN(builder, start);

        assertTrue(bpmn.isEmpty());
    }

}