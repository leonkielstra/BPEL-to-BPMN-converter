package org.bpel2bpmn.models.bpel;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.models.bpel.activities.structured.Scope;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BPELObject {

    private static Logger LOG = LoggerFactory.getLogger(BPELObject.class);

    protected BPELObject parent;
    protected HashMap<String, String> attributes;
    protected ArrayList<String> sources;
    protected ArrayList<String> targets;

    private HashMap<String, Document> wsdlDocuments;

    public BPELObject() {
        attributes = new HashMap<>();
        sources = new ArrayList<>();
        targets = new ArrayList<>();
    }

    public void addChild(BPELObject child) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(this.getClass().toString() + "; This element cannot hold any child elements.");
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addSource(String source) {
        sources.add(source);
    }

    public void addTarget(String target) {
        targets.add(target);
    }

    /**
     * This method traverses the parents of the given object to find the expression language
     * which is defined in the most enclosing scope.
     * @return expression language
     */
    public String getParentExpressionLanguage() {
        BPELObject current = this;
        while (current.parent != null) {
            current = this.parent;

            if (current instanceof Scope || current instanceof Process) {
                String expLanguage = current.getAttributes().get("expressionLanguage");
                if (expLanguage != null) {
                    return expLanguage;
                }
            }
        }

        return null;
    }

    /**
     * This method maps the BPEL object to a BPMN object.
     * @param from the BPMN element to which the newly mapped element should be connected
     * @return a BPMN element
     */
    public abstract FlowNode toBPMN(BPMNBuilder builder, FlowNode from) throws BPELConversionException;

    /*
     * Getters & Setters
     */

    public BPELObject getParent() {
        return parent;
    }

    public void setParent(BPELObject parent) {
        this.parent = parent;
        this.wsdlDocuments = parent.getWsdlDocuments();
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public Document getWsdlDocument(String namespace) {
        return wsdlDocuments.get(namespace);
    }

    public HashMap<String, Document> getWsdlDocuments() {
        return wsdlDocuments;
    }

    public void setWsdlDocuments(HashMap<String, Document> wsdlDocuments) {
        this.wsdlDocuments = wsdlDocuments;
        if (this.wsdlDocuments == null) this.wsdlDocuments = new HashMap<>();
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
