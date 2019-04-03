package org.bpel2bpmn.models.bpel.generic;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.bpel2bpmn.utilities.validation.ValidationResult;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

public class Import extends BPELObject {

    public static final String[] ATTRIBUTES = {
            "namespace",        // mandatory
            "location",         // optional
            "importType",       // mandatory
    };

    public Import() {
        super();
        attributes.put("namespace", null);
        attributes.put("location", null);
        attributes.put("importType", null);
    }

    @Override
    public FlowNode toBPMN(BPMNBuilder builder, FlowNode from) {
        Definitions definitions = builder.getModelInstance().getDefinitions();
        org.camunda.bpm.model.bpmn.instance.Import importInstance = builder.createElement(definitions, org.camunda.bpm.model.bpmn.instance.Import.class);

        importInstance.setImportType(getImportType());
        importInstance.setNamespace(getNamespace());
        importInstance.setLocation(getLocation());

        if (getLocation() != null) {
            importInstance.setLocation(getLocation());
        }

        return from;
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("Import");

        if (attributes.get("namespace") == null) {
            result.invalidate();
            result.addMissingAttribute("namespace");
        }

        if (attributes.get("location") == null) {
            result.invalidate();
            result.addMissingAttribute("location");
        }

        if (attributes.get("importType") == null) {
            result.invalidate();
            result.addMissingAttribute("importType");
        }

        return result;
    }

    /*
     * Getters & Setters
     */

    public String getImportType() {
        return attributes.get("importType");
    }

    public String getLocation() {
        return attributes.get("location");
    }

    public String getNamespace() {
        return attributes.get("namespace");
    }
}
