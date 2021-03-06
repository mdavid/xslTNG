package org.docbook.extensions.xslt;

import net.sf.saxon.lib.Logger;
import net.sf.saxon.lib.StandardLogger;

import java.util.HashSet;

public class DebuggingLogger extends StandardLogger {
    public static final String REGISTRATION = "registration";
    public static final String IMAGE_PROPERTIES = "image-properties";
    public static final String IMAGE_ERRORS = "image-errors";
    public static final String PYGMENTIZE_SHOW_COMMAND = "pygmentize-show-command";
    public static final String PYGMENTIZE_SHOW_RESULTS = "pygmentize-show-results";
    public static final String PYGMENTIZE_ERRORS = "pygmentize-errors";

    private static final String propertyName = "org.docbook.extensions.xslt.verbose";
    private Logger logger = null;
    private boolean noisy = false;
    private HashSet<String> flags = new HashSet<> ();

    public DebuggingLogger(Logger logger) {
        this.logger = logger;
        if (System.getProperty(propertyName) != null) {
            String val = System.getProperty(propertyName);
            if ("1".equals(val) || "yes".equals(val) || "true".equals(val)) {
                noisy = true;
            } else if (!("0".equals(val) || "no".equals(val) || "false".equals(val))) {
                noisy = false;
            } else {
                String[] tokens = System.getProperty(propertyName).split("[,\\s]+");
                for (String token : tokens) {
                    String flag = token.trim().toLowerCase();
                    if (!"".equals(flag)) {
                        flags.add(flag);
                    }
                }
            }
        }
    }

    public boolean enabled(String flag) {
        return flags.contains(flag);
    }

    public void debug(String flag, String message) {
        if ((flags.contains(flag) || noisy) && logger != null) {
            logger.info(message);
        }
    }
}
