package models.bpel.activities.structured;

import models.bpel.activities.Activity;

public class Conditional extends Activity {

    // TODO: Represents the if/elseif/else construct, should still be implemented.

    public Conditional(String name) {
        super(name);
    }

    @Override
    public Object toBPMN() {
        return null;
    }
}
