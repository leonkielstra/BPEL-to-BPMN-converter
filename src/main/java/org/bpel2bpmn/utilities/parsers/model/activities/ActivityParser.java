package org.bpel2bpmn.utilities.parsers.model.activities;

import org.bpel2bpmn.models.bpel.activities.Activity;
import org.jdom.Attribute;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * This is a generic parser that can parse any activity that only has the standard attributes.
 */
public class ActivityParser {

    private static Logger LOG = LoggerFactory.getLogger(ActivityParser.class);

    /**
     * This method parses the activity with its standard attributes.
     *
     * @param element XML element to be parsed
     * @param activityClass The class to which it should be parsed
     * @param <T>
     * @return parsed activity
     */
    public static <T extends Activity> T parse(Element element, Class<T> activityClass) {
        try {
            T activity = activityClass.getDeclaredConstructor().newInstance();
            parseAttributes(activity, element);

            return activity;
        } catch (InstantiationException e) {
            LOG.error("Could not instantiate " + activityClass.toString());
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method parses the standard attributes of an activity, iff they are defined.
     *
     * @param activity to add the attributes to
     * @param element to extract the attributes from
     * @param <T>
     */
    private static <T extends Activity> void parseAttributes(T activity, Element element) {
        for (String attributeName : Activity.STANDARD_ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                activity.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
