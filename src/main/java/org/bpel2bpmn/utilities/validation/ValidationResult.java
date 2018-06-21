package org.bpel2bpmn.utilities.validation;

import java.util.ArrayList;

public class ValidationResult {
    private String subject;
    private boolean valid;
    private ArrayList<String> missingAttributes;

    public ValidationResult(String subject) {
        this.subject = subject;
        this.valid = true;
        this.missingAttributes = new ArrayList<>();
    }

    public boolean isValid() {
        return this.valid;
    }

    public void invalidate() {
        this.valid = false;
    }

    public void addMissingAttribute(String attribute) {
        this.missingAttributes.add(attribute);
    }

    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(subject);
        builder.append(" is invalid; the following attributes are missing: ");
        for (String attribute : missingAttributes) {
            builder.append(attribute);
            builder.append(", ");
        }

        return builder.toString();
    }
}
