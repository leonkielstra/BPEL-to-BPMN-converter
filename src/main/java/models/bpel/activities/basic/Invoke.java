package models.bpel.activities.basic;

import models.bpel.activities.Activity;

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
