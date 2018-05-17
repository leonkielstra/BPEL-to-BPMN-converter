package models.bpel.activities;

public abstract class Activity {

    /* The standard attributes of an activity */
    private String name;
    private boolean suppressJoinFailure;

    /* The standard elements for an activity */
    // TODO: Implement standard elements.

    public Activity(String name) {
        this(name, false);
    }

    public Activity(String name, boolean suppressJoinFailure) {
        this.name = name;
        this.suppressJoinFailure = suppressJoinFailure;
    }

    public abstract Object toBPMN(); // TODO: replace Object by a class from the bpmn model.

    /*
     * Getters & Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean shouldSuppressJoinFailure() {
        return suppressJoinFailure;
    }

    public void setSuppressJoinFailure(boolean suppressJoinFailure) {
        this.suppressJoinFailure = suppressJoinFailure;
    }
}
