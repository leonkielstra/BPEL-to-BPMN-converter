package org.bpel2bpmn.models.bpel.activities.structured;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.ComplexGateway;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.util.ArrayList;
import java.util.HashMap;

public class Flow extends Activity {

    private ArrayList<String> links;
    private ArrayList<BPELObject> children;

    public Flow() {
        links = new ArrayList<>();
        children = new ArrayList<>();
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException {
        ComplexGateway afterGateway = builder.createElement(ComplexGateway.class);
        ParallelGateway beforeGateway = builder.createElement(ParallelGateway.class);

        builder.createSequenceFlow(from, beforeGateway);

        // No links
        if (links.size() < 1) {
            for (BPELObject child : children) {
                // TODO: Check for documentation

                FlowNode bpmnOpbject = child.toBPMN(builder, beforeGateway);
                builder.createSequenceFlow(bpmnOpbject, afterGateway);
            }
        } else {
            // Create sequenceflows for all links to be connected to sources and targets later on.
            HashMap<String, SequenceFlow> targets = new HashMap<>();
            SequenceFlow flow;
            for (String link : links) {
                flow = builder.createElement(SequenceFlow.class);
                flow.setName(link);
                targets.put(link, flow);
            }

            for (BPELObject child : children) {
                FlowNode bpmnOpbject = child.toBPMN(builder, beforeGateway);

                for (String source : child.getSources()) {
                    flow = targets.get(source);
                    if (flow != null) flow.setSource(bpmnOpbject);
                }

                for (String target : child.getTargets()) {
                    flow = targets.get(target);
                    if (flow != null) flow.setTarget(bpmnOpbject);
                }
            }
        }


        // TODO: Take into account JoinSupressionFail

        return from;
    }

    public void addChild(BPELObject child) {
        this.children.add(child);
    }

    public void addLink(String name) {
        this.links.add(name);
    }

    /*
     * Getters & Setters
     */

    public ArrayList<BPELObject> getChildren() {
        return children;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
}
