package nl.futureedge.sonar.plugin.rci;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.ce.measure.Component.Type;
import org.sonar.api.ce.measure.MeasureComputer.MeasureComputerDefinition;
import org.sonar.api.ce.measure.test.TestComponent;
import org.sonar.api.ce.measure.test.TestMeasureComputerContext;
import org.sonar.api.ce.measure.test.TestMeasureComputerDefinitionContext;
import org.sonar.api.ce.measure.test.TestSettings;

public class RciComputerTest {

    private final RciComputer subject = new RciComputer();

    private TestMeasureComputerDefinitionContext definitionContext;
    private MeasureComputerDefinition definition;

    private TestSettings settings;
    private TestComponent component;

    private TestMeasureComputerContext context;

    @Before
    public void setup() {
        definitionContext = new TestMeasureComputerDefinitionContext();
        definition = subject.define(definitionContext);

        settings = new TestSettings();
        component = new TestComponent("test", Type.PROJECT, null);

        context = new TestMeasureComputerContext(component, settings, definition);
    }

    private void setSettings(final int blockerWeight, final int criticalWeight, final int majorWeight,
            final int minorWeight, final int infoWeight) {
        settings.setValue(RciProperties.RATINGS_KEY, "95,85,65,30");
        settings.setValue(RciProperties.WEIGTHS_KEY,
                Integer.toString(blockerWeight) + "," + Integer.toString(criticalWeight) + ","
                        + Integer.toString(majorWeight) + "," + Integer.toString(minorWeight) + ","
                        + Integer.toString(infoWeight));
    }

    private void setMeasures(final int linesOfCode, final int blockers, final int criticals, final int majors,
            final int minors, final int infos) {
        context.addInputMeasure("ncloc", linesOfCode);
        context.addInputMeasure("blocker_violations", blockers);
        context.addInputMeasure("critical_violations", criticals);
        context.addInputMeasure("major_violations", majors);
        context.addInputMeasure("minor_violations", minors);
        context.addInputMeasure("info_violations", infos);
    }

    @Test
    public void testDefinition() {
        final TestMeasureComputerDefinitionContext definitionContext = new TestMeasureComputerDefinitionContext();
        final MeasureComputerDefinition definition = subject.define(definitionContext);

        Assert.assertNotNull(definition);
        Assert.assertEquals(2, definition.getOutputMetrics().size());
        Assert.assertTrue(definition.getOutputMetrics().contains("rules_compliance_index"));
        Assert.assertTrue(definition.getOutputMetrics().contains("rules_compliance_rating"));

        Assert.assertEquals(6, definition.getInputMetrics().size());
        Assert.assertTrue(definition.getInputMetrics().contains("ncloc"));
        Assert.assertTrue(definition.getInputMetrics().contains("blocker_violations"));
        Assert.assertTrue(definition.getInputMetrics().contains("critical_violations"));
        Assert.assertTrue(definition.getInputMetrics().contains("major_violations"));
        Assert.assertTrue(definition.getInputMetrics().contains("minor_violations"));
        Assert.assertTrue(definition.getInputMetrics().contains("info_violations"));
    }

    @Test
    public void test() {
        setSettings(10, 5, 3, 1, 0);
        setMeasures(61, 1, 3, 0, 1, 6);

        subject.compute(context);

        Assert.assertEquals(57.37, context.getMeasure("rules_compliance_index").getDoubleValue(), 0.01);
        Assert.assertEquals(4, context.getMeasure("rules_compliance_rating").getIntValue());
    }

    @Test
    public void testMoreIssuesThanLines() {
        setSettings(10, 5, 3, 1, 0);
        setMeasures(61, 123, 233, 2650, 1, 6);

        subject.compute(context);

        Assert.assertEquals(0.0, context.getMeasure("rules_compliance_index").getDoubleValue(), 0.01);
        Assert.assertEquals(5, context.getMeasure("rules_compliance_rating").getIntValue());
    }

    @Test
    public void testNoLines() {
        setSettings(10, 5, 3, 1, 0);
        setMeasures(0, 1, 3, 0, 1, 6);

        subject.compute(context);

        Assert.assertEquals(null, context.getMeasure("rules_compliance_index"));
        Assert.assertEquals(null, context.getMeasure("rules_compliance_rating"));
    }

    @Test
    public void testNoMeasures() {
        setSettings(10, 5, 3, 1, 0);

        subject.compute(context);

        Assert.assertEquals(null, context.getMeasure("rules_compliance_index"));
        Assert.assertEquals(null, context.getMeasure("rules_compliance_rating"));
    }

    @Test
    public void testNoSettings() {
        setMeasures(61, 1, 3, 0, 1, 6);

        subject.compute(context);

        Assert.assertEquals(100.0, context.getMeasure("rules_compliance_index").getDoubleValue(), 0.01);
        Assert.assertEquals(1, context.getMeasure("rules_compliance_rating").getIntValue());
    }

    @Test
    public void testNoIssues() {
        setSettings(10, 5, 3, 1, 0);
        setMeasures(61, 0, 0, 0, 0, 0);

        subject.compute(context);

        Assert.assertEquals(100.0, context.getMeasure("rules_compliance_index").getDoubleValue(), 0.01);
        Assert.assertEquals(1, context.getMeasure("rules_compliance_rating").getIntValue());
    }

}
