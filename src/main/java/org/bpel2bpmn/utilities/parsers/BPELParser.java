package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.models.bpel.Scope;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.activities.basic.*;
import org.bpel2bpmn.models.bpel.activities.structured.*;
import org.bpel2bpmn.models.bpel.extensions.Documentation;
import org.bpel2bpmn.utilities.parsers.model.AssignParser;
import org.bpel2bpmn.utilities.parsers.model.ProcessParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.StringReader;
import java.text.ParseException;

public class BPELParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELParser.class);

    public static Process parse(String bpelXML) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLEventReader eventReader = factory.createXMLEventReader(new StringReader(bpelXML));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase("process")) {
                    return ProcessParser.parse(eventReader, event.asStartElement());
                }

                LOG.error("No process node found."); // TODO: Throw exception for this.
            }
        } catch (XMLStreamException e) {
            LOG.error(e.getMessage());
        }

        return null;
    }

    public static BPELObject parseStartElement(XMLEventReader eventReader, StartElement startElement) throws IllegalArgumentException {
        String elementType = startElement.getName().getLocalPart();
        BPELObject bpelObject;

        /* Basic activities */
        if (elementType.equalsIgnoreCase(Activity.ASSIGN)) {
            return AssignParser.parse(eventReader, startElement);
        } else if (elementType.equalsIgnoreCase(Activity.EMPTY)) {
            bpelObject = new Empty("name"); // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.EXIT)) {
            bpelObject = new Exit("name");  // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.INVOKE)) {
            bpelObject = new Invoke("name");    // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.RECEIVE)) {
            bpelObject = new Receive("name");   // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.REPLY)) {
            bpelObject = new Reply("name"); // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.RETHROW)) {
            bpelObject = new Rethrow("name");   // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.THROW)) {
            bpelObject = new Throw("name"); // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.WAIT)) {
            bpelObject = new Wait("name", false, "31-12-1994"); // TODO: Create parser
        }

        /* Structured Activities */
        else if (elementType.equalsIgnoreCase(Activity.FLOW)) {
            bpelObject = new Flow("name");  // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.FOREACH)) {
            bpelObject = new ForEach("name");   // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.IF)) {
            bpelObject = new If("name");    // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.PICK)) {
            bpelObject = new Pick("name", false);   // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.REPEATUNTIL)) {
            bpelObject = new RepeatUntil("name");   // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.SEQUENCE)) {
            bpelObject = new Sequence("name");  // TODO: Create parser
        } else if (elementType.equalsIgnoreCase(Activity.WHILE)) {
            bpelObject = new While("name"); // TODO: Create parser
        }

        /* Documentation */
        else if (elementType.equalsIgnoreCase("documentation")) {
            // TODO: Create parser
        }

        /* Scope */
        else if (elementType.equalsIgnoreCase("scope")) {
            bpelObject = new Scope();   // TODO: Create parser
        }

        /* Unrecognized element */
        else {
            throw new IllegalArgumentException("Parse error: Element <" + elementType + "> not recognised.");
        }

        return null;
    }

    private static void parseEndElement(BPELObject current, EndElement endElement) throws IllegalStateException {
        String elementType = endElement.getName().getLocalPart();
        if (!current.getClass().toString().equalsIgnoreCase(elementType)) {
            throw new IllegalStateException("Parse error: This end element does not belong to the element that is currently being processed.");
        }
    }
}
