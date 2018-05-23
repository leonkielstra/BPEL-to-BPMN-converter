package org.bpel2bpmn.utilities.parsers.model;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.RecursiveParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

public class ProcessParser extends RecursiveParser {

    private static Logger LOG = LoggerFactory.getLogger(ProcessParser.class);

    public static Process parse(XMLEventReader eventReader, StartElement startElement) {
        Process process = new Process();

        parseAttributes(startElement, process);

        try {
            while (eventReader.hasNext()) {
                XMLEvent nextEvent = eventReader.nextEvent();

                if (nextEvent.isStartElement()) {
                    BPELObject activity = BPELParser.parseStartElement(eventReader, nextEvent.asStartElement());
                    process.addActivity(activity);
                }

                if (nextEvent.isEndElement()) {
                    return process;
                }
            }
        } catch (XMLStreamException e) {
            LOG.error(e.getMessage());
        }

        return null;
    }
}
