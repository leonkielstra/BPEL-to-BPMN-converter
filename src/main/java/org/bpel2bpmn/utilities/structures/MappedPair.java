package org.bpel2bpmn.utilities.structures;

import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class MappedPair {

    private FlowNode startNode;
    private FlowNode endNode;

    public MappedPair() {
        this(null, null);
    }

    public MappedPair(FlowNode node) {
        this(node, node);
    }

    public MappedPair(FlowNode startNode, FlowNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public boolean isSingleNode() {
        return startNode.equals(endNode);
    }

    public boolean isEmpty() {
        return (startNode == null && endNode == null);
    }

    /*
     * Getters & Setters
     */

    public FlowNode getStartNode() {
        return startNode;
    }

    public void setStartNode(FlowNode startNode) {
        this.startNode = startNode;
    }

    public FlowNode getEndNode() {
        return endNode;
    }

    public void setEndNode(FlowNode endNode) {
        this.endNode = endNode;
    }
}
