package nl.futureedge.sonar.plugin.rci;

import static java.util.Arrays.asList;

import java.util.List;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * Rules Compliance Index (RCI) metrics.
 */
public final class RciMetrics implements Metrics {

	/**
	 * The Rules Compliance Index (RCI) metric.
	 */
	public static final Metric<Double> RULES_COMPLIANCE_INDEX = new Metric.Builder("rules_compliance_index",
			"Rules Compliance Index", Metric.ValueType.PERCENT).setDescription("Rules Compliance Index (RCI)")
					.setDomain("RCI").setDirection(Metric.DIRECTION_BETTER).setQualitative(true).setWorstValue(0.0)
					.setBestValue(100.0).create();

	@Override
	public List<Metric> getMetrics() {
		return asList(RULES_COMPLIANCE_INDEX);
	}
}
