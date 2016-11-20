package nl.futureedge.sonar.plugin.rci;

import org.sonar.api.Plugin;

/**
 * Rules Compliance Index (RCI) plugin.
 */
public final class RciPlugin implements Plugin {

	@Override
	public void define(final Context context) {
		context.addExtensions(RciProperties.definitions());
		context.addExtensions(RciMetrics.class, RciComputer.class);
	}
}
