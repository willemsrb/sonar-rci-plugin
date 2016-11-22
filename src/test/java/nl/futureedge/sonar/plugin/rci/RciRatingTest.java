package nl.futureedge.sonar.plugin.rci;

import org.junit.Assert;
import org.junit.Test;

public class RciRatingTest {

	@Test
	public void test() {
		final RciRating subject = new RciRating(95, 85, 70, 50);

		Assert.assertEquals(1, subject.getRating(96));
		Assert.assertEquals(1, subject.getRating(95));
		Assert.assertEquals(2, subject.getRating(94));

		Assert.assertEquals(2, subject.getRating(86));
		Assert.assertEquals(2, subject.getRating(85));
		Assert.assertEquals(3, subject.getRating(84));

		Assert.assertEquals(3, subject.getRating(71));
		Assert.assertEquals(3, subject.getRating(70));
		Assert.assertEquals(4, subject.getRating(69));

		Assert.assertEquals(4, subject.getRating(51));
		Assert.assertEquals(4, subject.getRating(50));
		Assert.assertEquals(5, subject.getRating(49));

		Assert.assertEquals(5, subject.getRating(0));
	}
}
