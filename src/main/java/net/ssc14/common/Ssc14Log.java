package net.ssc14.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Shared logger for canonical runtime services. */
public final class Ssc14Log {
    public static final Logger LOGGER = LogManager.getLogger("net.ssc14");

    private Ssc14Log() { }
}
