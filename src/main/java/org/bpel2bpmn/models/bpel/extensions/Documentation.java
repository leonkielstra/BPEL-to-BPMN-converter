package org.bpel2bpmn.models.bpel.extensions;

import org.bpel2bpmn.models.bpel.BPELObject;

public class Documentation extends BPELObject {

    private String content;

    public Documentation(String content) {
        this.content = content;
    }

    /*
     * Getters & Setters
     */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
