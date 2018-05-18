package org.bpel2bpmn.utilities;

import org.bpel2bpmn.models.bpel.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;

public class BPELParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELParser.class);

    public static Process parse(String bpelXML) {
        LOG.debug("Parsing incoming bpel process...");

        XMLInputFactory factory = XMLInputFactory.newInstance();
        Process process = new Process();

        try {
            XMLEventReader eventReader = factory.createXMLEventReader(new StringReader(bpelXML));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        System.out.print(qName);
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        System.out.print(characters.getData());
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        System.out.print(endElement.getName().getLocalPart());
                        break;
                }
            }

            System.out.println("--------------------------------------------------");

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return process;
    }
}
