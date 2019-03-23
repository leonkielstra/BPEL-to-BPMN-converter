package org.bpel2bpmn.exceptions;

public class BPELConversionException extends Exception {

    private String className;

    public BPELConversionException(String message, String className) {
        super(message);
        this.className = className;
    }

    /*
     * Getters & Setters
     */

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
