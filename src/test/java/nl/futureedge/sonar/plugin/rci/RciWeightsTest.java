package nl.futureedge.sonar.plugin.rci;

import org.junit.Assert;
import org.junit.Test;

public class RciWeightsTest {

    @Test
    public void test() {
        final RciWeights subject = new RciWeights(20, 12, 6, 2, 1);
        Assert.assertEquals(20, subject.getBlocker());
        Assert.assertEquals(12, subject.getCritical());
        Assert.assertEquals(6, subject.getMajor());
        Assert.assertEquals(2, subject.getMinor());
        Assert.assertEquals(1, subject.getInfo());
    }
}
