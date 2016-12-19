package nl.futureedge.sonar.plugin.rci;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.ce.measure.test.TestSettings;

public class RciPropertiesTest {

	@Test
	public void testWeigths() {
		final TestSettings settings = new TestSettings();
		settings.setValue(RciProperties.WEIGTHS_KEY, "40,30,20,10,5,3");

		RciWeights weights = RciProperties.getWeights(settings);
		Assert.assertEquals(40, weights.getBlocker());
		Assert.assertEquals(30, weights.getCritical());
		Assert.assertEquals(20, weights.getMajor());
		Assert.assertEquals(10, weights.getMinor());
		Assert.assertEquals(5, weights.getInfo());

		settings.setValue(RciProperties.WEIGTHS_KEY, "40,30");
		weights = RciProperties.getWeights(settings);
		Assert.assertEquals(40, weights.getBlocker());
		Assert.assertEquals(30, weights.getCritical());
		Assert.assertEquals(0, weights.getMajor());
		Assert.assertEquals(0, weights.getMinor());
		Assert.assertEquals(0, weights.getInfo());

		settings.setValue(RciProperties.WEIGTHS_KEY, "");
		weights = RciProperties.getWeights(settings);
		Assert.assertEquals(0, weights.getBlocker());
		Assert.assertEquals(0, weights.getCritical());
		Assert.assertEquals(0, weights.getMajor());
		Assert.assertEquals(0, weights.getMinor());
		Assert.assertEquals(0, weights.getInfo());

		settings.setValue(RciProperties.WEIGTHS_KEY, null);
		weights = RciProperties.getWeights(settings);
		Assert.assertEquals(0, weights.getBlocker());
		Assert.assertEquals(0, weights.getCritical());
		Assert.assertEquals(0, weights.getMajor());
		Assert.assertEquals(0, weights.getMinor());
		Assert.assertEquals(0, weights.getInfo());
	}

	@Test
	public void testRatings() {
		final TestSettings settings = new TestSettings();
		settings.setValue(RciProperties.RATINGS_KEY, "40,30,20,10,5,3");

		RciRating rating = RciProperties.getRating(settings);
		Assert.assertEquals(1, rating.getRating(45));
		Assert.assertEquals(2, rating.getRating(35));
		Assert.assertEquals(3, rating.getRating(25));
		Assert.assertEquals(4, rating.getRating(15));
		Assert.assertEquals(5, rating.getRating(5));

		settings.setValue(RciProperties.RATINGS_KEY, "40,30");
		rating = RciProperties.getRating(settings);
		Assert.assertEquals(1, rating.getRating(45));
		Assert.assertEquals(2, rating.getRating(35));
		Assert.assertEquals(3, rating.getRating(25));
		Assert.assertEquals(3, rating.getRating(15));
		Assert.assertEquals(3, rating.getRating(5));

		settings.setValue(RciProperties.RATINGS_KEY, "");
		rating = RciProperties.getRating(settings);
		Assert.assertEquals(1, rating.getRating(45));
		Assert.assertEquals(1, rating.getRating(35));
		Assert.assertEquals(1, rating.getRating(25));
		Assert.assertEquals(1, rating.getRating(15));
		Assert.assertEquals(1, rating.getRating(5));

		settings.setValue(RciProperties.RATINGS_KEY, null);
		rating = RciProperties.getRating(settings);
		Assert.assertEquals(1, rating.getRating(45));
		Assert.assertEquals(1, rating.getRating(35));
		Assert.assertEquals(1, rating.getRating(25));
		Assert.assertEquals(1, rating.getRating(15));
		Assert.assertEquals(1, rating.getRating(5));
	}
}
