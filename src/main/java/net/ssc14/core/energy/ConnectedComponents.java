package net.ssc14.core.energy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/** Discovers all reachable components, including nodes missing from the initial seeds. */
public final class ConnectedComponents {
    private ConnectedComponents() {
    }

    public static <T> List<Set<T>> find(Iterable<T> seeds, Predicate<T> available,
            Function<T, ? extends Iterable<T>> neighbors) {
        List<Set<T>> components = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        ArrayDeque<T> queue = new ArrayDeque<>();
        for (T seed : seeds) {
            if (!visited.add(seed) || !available.test(seed)) {
                continue;
            }
            Set<T> component = new LinkedHashSet<>();
            queue.add(seed);
            while (!queue.isEmpty()) {
                T node = queue.removeFirst();
                component.add(node);
                for (T neighbor : neighbors.apply(node)) {
                    if (visited.add(neighbor) && available.test(neighbor)) {
                        queue.addLast(neighbor);
                    }
                }
            }
            components.add(component);
        }
        return components;
    }
}
