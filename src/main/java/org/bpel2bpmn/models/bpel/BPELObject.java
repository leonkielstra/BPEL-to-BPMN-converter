package org.bpel2bpmn.models.bpel;

import org.bpel2bpmn.models.bpel.extensions.Documentation;

import java.util.HashMap;

public class BPELObject {

    private BPELObject parent;
    private Documentation documentation;
    private HashMap<String, String> attributes = new HashMap<>();

    public void addActivity(BPELObject activity) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This element cannot hold any child elements.");
    }

    public String getAttributeByName(String key) {
        if (attributes.containsKey(key)) {
            return attributes.get(key);
        }

        return null;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    /*
     * Getters & Setters
     */

    public BPELObject getParent() {
        return parent;
    }

    public void setParent(BPELObject parent) {
        this.parent = parent;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
