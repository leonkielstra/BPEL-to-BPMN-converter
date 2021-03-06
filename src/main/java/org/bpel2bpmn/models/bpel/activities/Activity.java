package org.bpel2bpmn.models.bpel.activities;

import org.bpel2bpmn.models.bpel.BPELObject;

public abstract class Activity extends BPELObject {

    /**
     * Basic activities
     */
    public static final String ASSIGN = "assign";
    public static final String EMPTY = "empty";
    public static final String EXIT = "exit";
    public static final String INVOKE = "invoke";
    public static final String RECEIVE = "receive";
    public static final String REPLY = "reply";
    public static final String RETHROW = "rethrow";
    public static final String THROW = "throw";
    public static final String WAIT = "wait";

    /**
     * Structured activities
     */
    public static final String FLOW = "flow";
    public static final String FOREACH = "forEach";
    public static final String IF = "if";
    public static final String PICK = "pick";
    public static final String REPEATUNTIL = "repeatUntil";
    public static final String SEQUENCE = "sequence";
    public static final String WHILE = "while";

    /**
     * Standard attributes
     */
    public final static String[] STANDARD_ATTRIBUTES = {
            "name",
            "suppressJoinFailure"
    };

    public Activity() {
        addAttribute("suppressJoinFailure", "no");
    }
}
