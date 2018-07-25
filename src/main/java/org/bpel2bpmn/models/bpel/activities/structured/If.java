package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;

public class If extends Activity {

    private Branch ifBranch;
    private ArrayList<Branch> elseIfBranches;
    private Branch elseBranch;

    public If() {
        super();
        elseIfBranches = new ArrayList<>();
        elseBranch = null;
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        // Gateway to diverge incoming branch
        ExclusiveGateway divergeGateway = builder.createElement(ExclusiveGateway.class);
        builder.createSequenceFlow(from, divergeGateway);

        // Gateway to join branches
        ExclusiveGateway joinGateway = builder.createElement(ExclusiveGateway.class);

        // Map branches
        FlowNode node = ifBranch.toBPMN(builder, divergeGateway);
        mapSequenceFlow(builder, node, joinGateway);

        for (Branch elseIfBranch : elseIfBranches) {
            node = elseIfBranch.toBPMN(builder, divergeGateway);
            mapSequenceFlow(builder, node, joinGateway);
        }

        if (elseBranch != null) {
            node = elseBranch.toBPMN(builder, divergeGateway);
            mapSequenceFlow(builder, node, joinGateway);
            // The mapping describes a 'default' sequence flow. This is purely for notation purposes and will be ignored.
        }

        return joinGateway;
    }

    private void mapSequenceFlow(BPMNBuilder builder, FlowNode from, FlowNode to) {
        if (from instanceof EndEventImpl) { return; }
        builder.createSequenceFlow(from, to);
    }

    public void addElseIfBranch(Branch branch) {
        this.elseIfBranches.add(branch);
    }

    /*
     * Getters & Setters
     */

    public Branch getIfBranch() {
        return ifBranch;
    }

    public void setIfBranch(Branch ifBranch) {
        this.ifBranch = ifBranch;
    }

    public ArrayList<Branch> getElseIfBranches() {
        return elseIfBranches;
    }

    public void setElseIfBranches(ArrayList<Branch> elseIfBranches) {
        this.elseIfBranches = elseIfBranches;
    }

    public Branch getElseBranch() {
        return elseBranch;
    }

    public void setElseBranch(Branch elseBranch) {
        this.elseBranch = elseBranch;
    }
}
