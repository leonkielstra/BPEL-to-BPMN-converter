package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.bpel2bpmn.models.bpel.activities.basic.Assign;
import org.bpel2bpmn.models.bpel.activities.basic.Empty;
import org.bpel2bpmn.models.bpel.activities.basic.Exit;
import org.bpel2bpmn.models.bpel.activities.basic.Rethrow;
import org.bpel2bpmn.models.bpel.activities.structured.Scope;
import org.bpel2bpmn.models.bpel.activities.structured.Sequence;
import org.bpel2bpmn.models.bpel.activities.structured.While;
import org.bpel2bpmn.utilities.parsers.model.ProcessParser;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.bpel2bpmn.utilities.parsers.model.activities.basic.*;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.FlowParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.IfParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.LoopParser;
import org.bpel2bpmn.utilities.parsers.model.activities.structured.PickParser;
import org.bpel2bpmn.utilities.parsers.model.generic.DocumentationParser;
import org.bpel2bpmn.utilities.parsers.model.generic.ImportParser;
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

    public static Process parse(MultipartFile bpelFile, HashMap<String, Document> wsdlList) throws BPELParseException {
        SAXBuilder builder = new SAXBuilder();
        InputStream inputStream;

        try {
            inputStream = bpelFile.getInputStream();
            Document bpelXML = builder.build(inputStream);

            Element root = bpelXML.getRootElement();
            Process process = (Process) parseElement(root);
            if (process != null) {
                process.setWsdlDocuments(wsdlList);
            }

            return process;
        } catch (IOException | JDOMException e) {
            throw new BPELParseException(e.getMessage());
        }
    }

    public BPELParser() {
    }

    private static org.bpel2bpmn.models.bpel.BPELObject parseElement(Element element) throws BPELParseException {
        LOG.debug("Parsing element: " + element.getName());
        org.bpel2bpmn.models.bpel.BPELObject bpelObject;

        boolean parseChildren = true; // If false, children are parsed within element parser.
        switch (element.getName().toLowerCase()) {
            case "documentation":
                bpelObject = DocumentationParser.parse(element);
                parseChildren = false;
                break;
            case Activity.ASSIGN:
                bpelObject = BPELObjectParser.parse(element, Assign.class);
                break;
            case Activity.EMPTY:
                bpelObject = BPELObjectParser.parse(element, Empty.class);
                break;
            case Activity.EXIT:
                bpelObject = BPELObjectParser.parse(element, Exit.class);
                break;
            case Activity.FLOW:
                bpelObject = FlowParser.parse(element);
                parseChildren = false;
                break;
            case Activity.IF:
                bpelObject = IfParser.parse(element);
                parseChildren = false;
                break;
            case Activity.INVOKE:
                bpelObject = InvokeParser.parse(element);
                parseChildren = false;
                break;
            case "import":
                bpelObject = ImportParser.parse(element);
                parseChildren = false;
                break;
            case Activity.PICK:
                bpelObject = PickParser.parse(element);
                parseChildren = false;
                break;
            case "process":
                bpelObject = ProcessParser.parse(element);
                break;
            case Activity.RECEIVE:
                bpelObject = ReceiveParser.parse(element);
                break;
            case Activity.REPLY:
                bpelObject = ReplyParser.parse(element);
                parseChildren = false;
                break;
            case Activity.RETHROW:
                bpelObject = BPELObjectParser.parse(element, Rethrow.class);
                break;
            case "scope":
                bpelObject = BPELObjectParser.parse(element, Scope.class);
                break;
            case Activity.SEQUENCE:
                bpelObject = BPELObjectParser.parse(element, Sequence.class);
                break;
            case Activity.THROW:
                bpelObject = ThrowParser.parse(element);
                parseChildren = false;
                break;
            case Activity.WAIT:
                bpelObject = WaitParser.parse(element);
                parseChildren = false;
                break;
            case Activity.WHILE:
                bpelObject = LoopParser.parse(element, While.class);
                parseChildren = false;
                break;
            default:
                return null; // TODO: action if not recognized.
        }

        if (parseChildren) { parseChildren(bpelObject, element); }

        return bpelObject;
    }

    public static void parseChildren(org.bpel2bpmn.models.bpel.BPELObject bpelObject, Element element) throws BPELParseException {
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
