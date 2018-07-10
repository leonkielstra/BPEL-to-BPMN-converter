package org.bpel2bpmn.utilities.parsers.model.generic;

import org.bpel2bpmn.models.bpel.generic.Documentation;
import org.jdom.Element;

public class DocumentationParser {

    public static Documentation parse(Element element) {
        return new Documentation(element.getText());
    }
}
