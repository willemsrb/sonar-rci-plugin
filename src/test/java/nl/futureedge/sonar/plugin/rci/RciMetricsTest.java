package nl.futureedge.sonar.plugin.rci;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.measures.Metric;

public class RciMetricsTest {

    @Test
    public void test() {
        final RciMetrics subject = new RciMetrics();
        final List<Metric> metrics = subject.getMetrics();

        Assert.assertEquals(2, metrics.size());
        // Assert.assertEquals("rules_compliance_index",
        // metrics.iterator().next().getKey());
    }

}
