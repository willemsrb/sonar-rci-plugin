package nl.futureedge.sonar.plugin.rci;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.measures.CoreMetrics;

/**
 * Rules Compliance Index (RCI) computer.
 */
public final class RciComputer implements MeasureComputer {

	private static final String METRIC_ISSUES_BLOCKER = CoreMetrics.BLOCKER_VIOLATIONS_KEY;
	private static final String METRIC_ISSUES_CRITICAL = CoreMetrics.CRITICAL_VIOLATIONS_KEY;
	private static final String METRIC_ISSUES_MAJOR = CoreMetrics.MAJOR_VIOLATIONS_KEY;
	private static final String METRIC_ISSUES_MINOR = CoreMetrics.MINOR_VIOLATIONS_KEY;
	private static final String METRIC_ISSUES_INFO = CoreMetrics.INFO_VIOLATIONS_KEY;

	private static final String METRIC_LINES_OF_CODE = CoreMetrics.NCLOC_KEY;

	@Override
	public MeasureComputerDefinition define(final MeasureComputerDefinitionContext context) {
		return context.newDefinitionBuilder()
				.setInputMetrics(METRIC_ISSUES_BLOCKER, METRIC_ISSUES_CRITICAL, METRIC_ISSUES_MAJOR,
						METRIC_ISSUES_MINOR, METRIC_ISSUES_INFO, METRIC_LINES_OF_CODE)
				.setOutputMetrics(RciMetrics.RULES_COMPLIANCE_INDEX.key(), RciMetrics.RULES_COMPLIANCE_RATING.key())
				.build();
	}

	@Override
	public void compute(final MeasureComputerContext context) {
		final RciWeights weights = RciProperties.getWeights(context.getSettings());

		final int blockerIssues = getMeasureValue(context, METRIC_ISSUES_BLOCKER);
		final int criticalIssues = getMeasureValue(context, METRIC_ISSUES_CRITICAL);
		final int majorIssues = getMeasureValue(context, METRIC_ISSUES_MAJOR);
		final int minorIssues = getMeasureValue(context, METRIC_ISSUES_MINOR);
		final int infoIssues = getMeasureValue(context, METRIC_ISSUES_INFO);

		final int issuesWeight = weights.getBlocker() * blockerIssues + weights.getCritical() * criticalIssues
				+ weights.getMajor() * majorIssues + weights.getMinor() * minorIssues + weights.getInfo() * infoIssues;

		final int linesOfCode = getMeasureValue(context, METRIC_LINES_OF_CODE);

		final double rulesComplianceIndex;
		if (linesOfCode != 0) {
			rulesComplianceIndex = (1.0 - (double) issuesWeight / (double) linesOfCode) * 100;
			context.addMeasure(RciMetrics.RULES_COMPLIANCE_INDEX.key(), Math.max(rulesComplianceIndex, 0.0));

			final RciRating rating = RciProperties.getRating(context.getSettings());
			context.addMeasure(RciMetrics.RULES_COMPLIANCE_RATING.key(), rating.getRating(rulesComplianceIndex));
		}
	}

	private int getMeasureValue(final MeasureComputerContext context, final String key) {
		final Measure measure = context.getMeasure(key);
		if (measure == null) {
			return 0;
		} else {
			return measure.getIntValue();
		}
	}

}
