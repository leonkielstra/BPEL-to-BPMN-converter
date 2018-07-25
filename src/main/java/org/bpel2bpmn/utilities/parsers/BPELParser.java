package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.activities.basic.Empty;
import org.bpel2bpmn.models.bpel.activities.basic.Exit;
import org.bpel2bpmn.models.bpel.activities.structured.Sequence;
import org.bpel2bpmn.models.bpel.generic.PartnerLink;
import org.bpel2bpmn.utilities.parsers.model.ProcessParser;
import org.bpel2bpmn.utilities.parsers.model.activities.ActivityParser;
import org.bpel2bpmn.utilities.parsers.model.activities.basic.WaitParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.IfParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.PickParser;
import org.bpel2bpmn.utilities.parsers.model.generic.DocumentationParser;
import org.bpel2bpmn.utilities.parsers.model.generic.PartnerLinksParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class BPELParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELParser.class);

    public static Process parse(MultipartFile bpelFile, MultipartFile[] wsdlFiles) throws IOException {
        SAXBuilder builder = new SAXBuilder();
        InputStream inputStream;

        // Parse WSDL files
        HashMap<String, Document> wsdlDocuments = new HashMap<>();
        Document wsdlDocument;
        String targetNamespace;
        for (MultipartFile wsdlFile : wsdlFiles) {
            try {
                inputStream = wsdlFile.getInputStream();
                wsdlDocument = builder.build(inputStream);
                targetNamespace = wsdlDocument.getRootElement().getAttributeValue("targetNamespace");
                wsdlDocuments.put(targetNamespace, wsdlDocument);
            } catch (IOException | JDOMException e) {
                LOG.error("Could not parse this WSDL file:");
                LOG.error(e.getMessage());
            }
        }

        // Parse BPEL file
        try {
            inputStream = bpelFile.getInputStream();
            Document bpelXML = builder.build(inputStream);

            Element root = bpelXML.getRootElement();
            Process process = (Process) parseElement(root);
            if (process != null) {
                process.setWsdlDocuments(wsdlDocuments);
            }

            return process;
        } catch (IOException | JDOMException e) {
            LOG.error("Could not parse this BPEL file:");
            LOG.error(e.getMessage());
        }

        throw new IOException("Could not parse the given files.");
    }

    private static BPELObject parseElement(Element element) {
        LOG.debug("Parsing element: " + element.getName());
        BPELObject bpelObject;

        boolean parseChildren = true; // If false, children are parsed within element parser.
        switch (element.getName().toLowerCase()) {
            case "documentation":
                bpelObject = DocumentationParser.parse(element);
                parseChildren = false;
                break;
            case Activity.EMPTY:
                bpelObject = ActivityParser.parse(element, Empty.class);
                break;
            case Activity.EXIT:
                bpelObject = ActivityParser.parse(element, Exit.class);
                break;
            case Activity.IF:
                bpelObject = IfParser.parse(element);
                parseChildren = false;
                break;
            case "partnerlinks":
                PartnerLinksParser.parse(element);
                bpelObject = null;
                parseChildren = false;
                break;
            case Activity.PICK:
                bpelObject = PickParser.parse(element);
                parseChildren = false;
                break;
            case "process":
                bpelObject = ProcessParser.parse(element);
                break;
            case Activity.SEQUENCE:
                bpelObject = ActivityParser.parse(element, Sequence.class);
                break;
            case Activity.WAIT:
                bpelObject = WaitParser.parse(element);
                parseChildren = false;
                break;
            default:
                return null; // TODO: action if not recognized.
        }

        if (parseChildren) { parseChildren(bpelObject, element); }

        return bpelObject;
    }

    public static void parseChildren(BPELObject bpelObject, Element element) {
        for (Object child : element.getChildren()) {
            Element childElement = (Element) child;
            BPELObject parsedChild = parseElement(childElement);
            if (bpelObject != null && parsedChild != null) {
                bpelObject.addChild(parsedChild);
                parsedChild.setParent(bpelObject);
            }
        }
    }
}
