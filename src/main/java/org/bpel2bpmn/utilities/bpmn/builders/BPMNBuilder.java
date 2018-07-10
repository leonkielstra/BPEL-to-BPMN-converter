package org.bpel2bpmn.utilities.bpmn.builders;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

public class BPMNBuilder {

    private static final String BPMN_NAMESPACE = "http://bpmn.io/schema/bpmn";

    private BpmnModelInstance modelInstance;
    private Process executableProcess;
    private BpmnModelElementInstance currentScope;
    private Collaboration collaboration = null;

    private boolean conditionPending;
    private String condition;

    public BPMNBuilder() {
        this.modelInstance = Bpmn.createEmptyModel();
    }

    public Definitions createDefinitions(String exporter) {
        Definitions definitions = modelInstance.newInstance(Definitions.class);
        definitions.setExporter(exporter);
        definitions.setTargetNamespace(BPMN_NAMESPACE);
        modelInstance.setDefinitions(definitions);

        return definitions;
    }

    public Process createExecutableProcess(Definitions definitions, String name) {
        executableProcess = modelInstance.newInstance(Process.class);
        executableProcess.setId(name);
        executableProcess.setName(name);
        executableProcess.setExecutable(true);
        definitions.addChildElement(executableProcess);
        currentScope = executableProcess;

        return executableProcess;
    }

    public <T extends BpmnModelElementInstance> T createElement(Class<T> elementClass) {
        return createElement(currentScope, elementClass);
    }

    public <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, Class<T> elementClass) {
        T element = modelInstance.newInstance(elementClass);
        parentElement.addChildElement(element);

        return element;
    }

    public <T extends BpmnModelElementInstance> T createElementWithId(String id, Class<T> elementClass) {
        return createElementWithId(currentScope, id, elementClass);
    }

    public <T extends BpmnModelElementInstance> T createElementWithId(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
        T element = modelInstance.newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);

        return element;
    }

    public SequenceFlow createSequenceFlow(FlowNode from, FlowNode to) {
        return createSequenceFlow(currentScope, from, to);
    }

    public SequenceFlow createSequenceFlow(BpmnModelElementInstance parentElement, FlowNode from, FlowNode to) {
        SequenceFlow sequenceFlow = createElementWithId(parentElement, from.getId() + "-" + to.getId(), SequenceFlow.class);
        parentElement.addChildElement(sequenceFlow);
        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);
        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);

        if (conditionPending) {
            ConditionExpression expression = modelInstance.newInstance(ConditionExpression.class);
            expression.setTextContent(condition);
            sequenceFlow.addChildElement(expression);
            conditionPending = false;
            condition = null;
        }

        return sequenceFlow;
    }

    public void prepareConditionalSequenceFlow(String condition) {
        conditionPending = true;
        this.condition = condition;
    }

    public Participant createParticipant() {
        return modelInstance.newInstance(Participant.class);
    }

    public void addParticipant(Participant participant) {
        Definitions definitions = modelInstance.getDefinitions();

        if (collaboration == null) {
            collaboration = modelInstance.newInstance(Collaboration.class);

            Participant mainParticipant = modelInstance.newInstance(Participant.class);
            mainParticipant.setAttributeValue("name", executableProcess.getName());
            mainParticipant.setAttributeValue("processRef", executableProcess.getId());
            collaboration.addChildElement(mainParticipant);
            definitions.addChildElement(collaboration);
        }

        Process externalProcess = modelInstance.newInstance(Process.class);
        externalProcess.setId(participant.getAttributeValue("processRef"));
        externalProcess.setName(participant.getName());
        externalProcess.setExecutable(false);

        definitions.addChildElement(externalProcess);
        collaboration.addChildElement(participant);
    }

    /*
     * Getters & Setters
     */

    public BpmnModelInstance getModelInstance() {
        return this.modelInstance;
    }

    public Process getExecutableProcess() {
        return this.executableProcess;
    }

    public BpmnModelElementInstance getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(BpmnModelElementInstance currentScope) {
        this.currentScope = currentScope;
    }
}
