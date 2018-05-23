package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.models.bpel.BPELObject;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

public abstract class RecursiveParser {

    protected static void parseAttributes(StartElement startElement, BPELObject bpelObject) {
        Iterator<Attribute> attributes = startElement.getAttributes();
        while (attributes.hasNext()) {
            Attribute attribute = attributes.next();
            bpelObject.addAttribute(attribute.getName().getLocalPart(), attribute.getValue());
        }
    }
}
