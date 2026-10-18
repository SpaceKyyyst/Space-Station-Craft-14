package net.mcreator.ssc;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

/** Runs the isolated domain test source set with an explicit classpath. */
public final class DomainTestLauncher {
    private DomainTestLauncher() {
    }

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage("net.mcreator.ssc"))
                .build();
        var listener = new SummaryGeneratingListener();
        var launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        listener.getSummary().printTo(new java.io.PrintWriter(System.out));
        if (listener.getSummary().getFailures().size() > 0) {
            listener.getSummary().getFailures().forEach(failure -> failure.getException().printStackTrace(System.err));
            System.exit(1);
        }
    }
}
