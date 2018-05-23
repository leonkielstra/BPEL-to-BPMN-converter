package org.bpel2bpmn.utilities.parsers.model;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.basic.Assign;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.RecursiveParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class AssignParser extends RecursiveParser {

    private static Logger LOG = LoggerFactory.getLogger(AssignParser.class);

    public static Assign parse(XMLEventReader eventReader, StartElement startElement) {
        Assign assign = new Assign();

        parseAttributes(startElement, assign);

        try {
            while (eventReader.hasNext()) {
                XMLEvent nextEvent = eventReader.nextEvent();

                if (nextEvent.isStartElement()) {
                    BPELParser.parseStartElement(eventReader, nextEvent.asStartElement()); // This calls parseCopy when needed.
                }

                if (nextEvent.isEndElement()) {
                    return assign;
                }
            }
        } catch (XMLStreamException e) {
            LOG.error(e.getMessage());
        }

        return null;
    }

    public static void parseCopy(XMLEventReader eventReader, StartElement startElement) {
        // TODO: Parse <from> and <to>
    }
}
