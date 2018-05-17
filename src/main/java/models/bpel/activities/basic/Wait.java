package models.bpel.activities.basic;

import models.bpel.activities.Activity;

public class Wait extends Activity {

    private boolean isTimer; /* true in case of a <for> construct, false in case of a <until> construct. */
    private String timeExpression; /* Represents either the duration or deadline expression */

    public Wait(String name, boolean isTimer, String timeExpression) {
        super(name);

        this.isTimer = isTimer;
        this.timeExpression = timeExpression;
    }

    /*
     * Getters & Setters
     */

    public boolean isTimer() {
        return isTimer;
    }

    public String getTimeExpression() {
        return timeExpression;
    }

    @Override
    public Object toBPMN() {
        return null; // TODO
    }
}
