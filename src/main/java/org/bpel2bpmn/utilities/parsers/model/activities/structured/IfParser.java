package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.models.bpel.activities.structured.Branch;
import org.bpel2bpmn.models.bpel.activities.structured.If;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IfParser {

    private static Logger LOG = LoggerFactory.getLogger(IfParser.class);

    public static If parse(Element element) throws IllegalStateException {
        If bpelIf = BPELObjectParser.parse(element, If.class);
        parseElements(element, bpelIf);

        return bpelIf;
    }

    private static void parseElements(Element element, If bpelIf) throws IllegalStateException {
        Branch branch;

        // Parse if branch
        branch = parseBranch(element, bpelIf, true);
        bpelIf.setIfBranch(branch);

        // Parse elseif branches
        for (Object elseIfObject : element.getChildren("elseif")) {
            Element elseIfElement = (Element) elseIfObject;
            branch = parseBranch(elseIfElement, bpelIf, true);
            bpelIf.addElseIfBranch(branch);
        }

        // Parse else branch
        Element elseElement = element.getChild("else");
        if (elseElement != null) {
            branch = parseBranch(elseElement, bpelIf, false);
            bpelIf.setElseBranch(branch);
        }
    }

    private static Branch parseBranch(Element element, If bpelIf, boolean hasCondition) throws IllegalStateException {
        Branch branch = new Branch();
        Element condition = null;

        for (Object object : element.getChildren()) {
            Element child = (Element) object;
            if (child.getName().equals("condition")) {
                condition = child;
                break;
            }
        }

        if (hasCondition) {
            if (condition == null) {
                throw new IllegalStateException("Expected a condition for this if-elseif-else branch.");
            }

            branch.setCondition(condition.getValue());
            String expLanguage = element.getAttributeValue("expressionLanguage");
            if (expLanguage != null) {
                branch.setExpressionLanguage(expLanguage);
            } else {
                branch.setExpressionLanguage(bpelIf.getParentExpressionLanguage());
            }
        }

        BPELParser.parseChildren(branch, element);

        return branch;
    }
}
