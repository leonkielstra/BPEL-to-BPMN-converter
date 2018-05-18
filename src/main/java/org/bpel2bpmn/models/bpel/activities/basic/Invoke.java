package org.bpel2bpmn.models.bpel.activities.basic;

import org.bpel2bpmn.models.bpel.activities.Activity;

// TODO: Implement; Maybe create a super class for all message events?
public class Invoke extends Activity {

    public Invoke(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null;
    }
}
