package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.activities.basic.Empty;
import org.bpel2bpmn.models.bpel.activities.basic.Exit;
import org.bpel2bpmn.models.bpel.activities.structured.Sequence;
import org.bpel2bpmn.utilities.parsers.model.ProcessParser;
import org.bpel2bpmn.utilities.parsers.model.activities.ActivityParser;
import org.bpel2bpmn.utilities.parsers.model.activities.basic.WaitParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.IfParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class BPELParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELParser.class);

    public static Process parse(MultipartFile bpelFile) {

        SAXBuilder builder = new SAXBuilder();
        InputStream inputStream = null;
        try {
            inputStream = bpelFile.getInputStream();
            Document bpelXML = builder.build(inputStream);

            Element root = bpelXML.getRootElement();
            return (Process) parseElement(root);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BPELObject parseElement(Element element) {
        LOG.debug("Parsing element: " + element.getName());
        BPELObject bpelObject;

        boolean parseChildren = true; // If false, children are parsed within element parser.
        switch (element.getName().toLowerCase()) {
            case "process":
                bpelObject = ProcessParser.parse(element);
                break;
            case Activity.SEQUENCE:
                bpelObject = ActivityParser.parse(element, Sequence.class);
                break;
            case Activity.EMPTY:
                bpelObject = ActivityParser.parse(element, Empty.class);
                break;
            case Activity.EXIT:
                bpelObject = ActivityParser.parse(element, Exit.class);
                break;
            case Activity.WAIT:
                bpelObject = WaitParser.parse(element);
                parseChildren = false;
                break;
            case Activity.IF:
                bpelObject = IfParser.parse(element);
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
