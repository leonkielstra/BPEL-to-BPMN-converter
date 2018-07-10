package org.bpel2bpmn.models.bpel.generic;

import factories.BPMNBuilderFactory;
import org.bpel2bpmn.utilities.bpmn.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentationTest {

    private static Documentation documentation;
    private static String documentationText = "Some descriptive text here.";
    private static BPMNBuilder builder;

    @Before
    public void setUp() throws Exception {
        documentation = new Documentation(documentationText);
        builder = BPMNBuilderFactory.create();
    }

    @Test
    public void toBPMN() {
        FlowNode start = builder.createElement(StartEvent.class);

        FlowNode last = documentation.toBPMN(builder, start);

        Process process = builder.getExecutableProcess();
        Object[] children = process.getChildElementsByType(org.camunda.bpm.model.bpmn.instance.Documentation.class).toArray();
        org.camunda.bpm.model.bpmn.instance.Documentation bpmnDoc = (org.camunda.bpm.model.bpmn.instance.Documentation) children[0];

        assertEquals(start, last);
        assertEquals(documentationText, bpmnDoc.getTextContent());
    }
}