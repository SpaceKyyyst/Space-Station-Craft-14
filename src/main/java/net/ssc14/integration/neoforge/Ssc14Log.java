package net.ssc14.integration.neoforge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ssc14.common.Ssc14Identity;

/** Canonical logging entry point for integration and content adapters. */
public final class Ssc14Log {
    public static final Logger LOGGER = LogManager.getLogger(Ssc14Identity.MOD_ID);

    private Ssc14Log() {
    }
}
