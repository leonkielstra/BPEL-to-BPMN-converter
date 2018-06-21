package org.bpel2bpmn.models.bpel;

import java.util.HashMap;

public class BPELObject {

    protected BPELObject parent;
    protected HashMap<String, String> attributes = new HashMap<>();

    public void addChild(BPELObject activity) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This element cannot hold any child elements.");
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

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
