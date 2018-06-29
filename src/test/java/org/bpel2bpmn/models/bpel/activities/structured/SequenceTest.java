package org.bpel2bpmn.models.bpel.activities.structured;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.models.bpel.activities.basic.Exit;
import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.*;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceTest {

    private static Sequence sequence;
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        sequence = new Sequence();
        builder = BPMNBuilderFactory.create();
    }

    @Test
    public void toBPMN() {
        FlowNode start = builder.createElement(StartEvent.class);

        sequence.addChild(new Wait());
        sequence.addChild(new Exit());
        sequence.toBPMN(builder, start);

        Process process = builder.getExecutableProcess();
        Object[] children = process.getFlowElements().toArray();;

        FlowNode wait = (FlowNode) children[1];
        FlowNode exit = (FlowNode) children[3];
        SequenceFlow seq1 = (SequenceFlow) children[2];
        SequenceFlow seq2 = (SequenceFlow) children[4];

        assertEquals(IntermediateCatchEventImpl.class, wait.getClass());

        assertEquals(SequenceFlowImpl.class, seq1.getClass());
        assertEquals(start.getId() + "-" + wait.getId(), seq1.getId());

        assertEquals(EndEventImpl.class, exit.getClass());

        assertEquals(SequenceFlowImpl.class, seq2.getClass());
        assertEquals(wait.getId() + "-" + exit.getId(), seq2.getId());
    }
}