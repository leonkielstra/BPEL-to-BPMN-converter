package org.bpel2bpmn.models.bpel.activities.structured;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.basic.Exit;
import org.bpel2bpmn.models.bpel.activities.basic.Wait;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.ExclusiveGatewayImpl;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IfTest {

    private static If ifObject;
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        ifObject = new If();
        builder = BPMNBuilderFactory.create();
    }

    @Test
    public void toBPMN() throws BPELConversionException {
        FlowNode start = builder.createElement(StartEvent.class);

        Branch ifBranch = new Branch();
        ifBranch.addChild(new Exit());

        Branch elseIfBranch = new Branch();
        elseIfBranch.addChild(new Exit());

        Branch elseBranch = new Branch();
        elseBranch.addChild(new Wait());

        ifObject.setIfBranch(ifBranch);
        ifObject.addElseIfBranch(elseIfBranch);
        ifObject.setElseBranch(elseBranch);

        FlowNode result = ifObject.toBPMN(builder, start);

        Object[] children = builder.getExecutableProcess().getFlowElements().toArray();

        FlowNode divergeGateway = (FlowNode) children[1];
        SequenceFlow seq1 = (SequenceFlow) children[2];
        FlowNode joinGateway = (FlowNode) children[3];
        FlowNode end1 = (FlowNode) children[4];
        SequenceFlow seq2 = (SequenceFlow) children[5];
        FlowNode end2 = (FlowNode) children[6];
        SequenceFlow seq3 = (SequenceFlow) children[7];
        FlowNode wait = (FlowNode) children[8];
        SequenceFlow seq4 = (SequenceFlow) children[9];
        SequenceFlow seq5 = (SequenceFlow) children[10];

        assertEquals(1, start.getOutgoing().size());

        assertEquals(ExclusiveGatewayImpl.class, divergeGateway.getClass());
        assertEquals(3, divergeGateway.getOutgoing().size());
        assertEquals(1, divergeGateway.getIncoming().size());

        assertEquals(start.getId() + "-" + divergeGateway.getId(), seq1.getId());

        assertEquals(1, end1.getIncoming().size());
        assertEquals(0, end1.getOutgoing().size());

        assertEquals(divergeGateway.getId() + "-" + end1.getId(), seq2.getId());

        assertEquals(1, end2.getIncoming().size());
        assertEquals(0, end2.getOutgoing().size());

        assertEquals(divergeGateway.getId() + "-" +end2.getId(), seq3.getId());

        assertEquals(1, wait.getIncoming().size());
        assertEquals(1, wait.getOutgoing().size());

        assertEquals(divergeGateway.getId() + "-" +wait.getId(), seq4.getId());
        assertEquals(wait.getId() + "-" +joinGateway.getId(), seq5.getId());
    }
}