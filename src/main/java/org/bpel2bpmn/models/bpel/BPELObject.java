package org.bpel2bpmn.models.bpel;

import org.bpel2bpmn.models.bpel.activities.structured.Scope;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class BPELObject {

    private static Logger LOG = LoggerFactory.getLogger(BPELObject.class);

    protected BPELObject parent;
    protected HashMap<String, String> attributes = new HashMap<>();
    private HashMap<String, Document> wsdlDocuments;

    public void addChild(BPELObject child) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This element cannot hold any child elements.");
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
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
    }
}
