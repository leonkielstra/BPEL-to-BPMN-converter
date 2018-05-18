package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;

public class Empty extends Activity {

    public Empty(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null; // TODO
    }

}
