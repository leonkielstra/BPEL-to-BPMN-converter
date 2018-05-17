package models.bpel.activities.structured;

import models.bpel.activities.Activity;

public class Pick extends Activity {

    private boolean createInstance;

    // TODO: Implement onAlarm and onMessage

    public Pick(String name, boolean createInstance) {
        super(name);
        this.createInstance = createInstance;
    }

    @Override
    public Object toBPMN() {
        return null;
    }

    public boolean shouldCreateInstance() {
        return createInstance;
    }

    public void setCreateInstance(boolean createInstance) {
        this.createInstance = createInstance;
    }
}
