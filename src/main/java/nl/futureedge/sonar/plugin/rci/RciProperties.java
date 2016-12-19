package nl.futureedge.sonar.plugin.rci;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.ce.measure.Settings;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

/**
 * Rules Compliance Index (RCI) properties.
 */
public final class RciProperties {

	/**
	 * Categorie.
	 */
	public static final String CATEGORY = "RCI";

	/**
	 * Weights issues.
	 */
	public static final String WEIGTHS_KEY = "sonar.rci.weights";
	/**
	 * Rating.
	 */
	public static final String RATINGS_KEY = "sonar.rci.ratings";

	private RciProperties() {
		// Not instantiable
	}

	/**
	 * Property definitions.
	 *
	 * @return property definitions
	 */
	public static List<PropertyDefinition> definitions() {
		return Arrays.asList(PropertyDefinition.builder(WEIGTHS_KEY).name("Issue weights")
				.description("Relative weiths of issues based on severity (blocker,critical,major,minor,info)")
				.category(CATEGORY).defaultValue("10,5,3,1,0").index(100).onQualifiers(Qualifiers.PROJECT).build(),
				PropertyDefinition.builder(RATINGS_KEY).name("Rating")
						.description(
								"Rating (ranging from A (very good) to E (very bad)). This setting define the values for A through D with E being below the last value.")
						.category(CATEGORY).defaultValue("97,92,85,75").index(200).onQualifiers(Qualifiers.PROJECT)
						.build());
	}

	/**
	 * Get the weights from the configuration.
	 *
	 * @param settings
	 *            settings
	 * @return weights
	 */
	public static RciWeights getWeights(final Settings settings) {
		final String weightsSetting = settings.getString(WEIGTHS_KEY);

		final String[] weights = weightsSetting == null ? new String[] {} : weightsSetting.split(",");

		return new RciWeights(getIntFromArray(weights, 0), getIntFromArray(weights, 1), getIntFromArray(weights, 2),
				getIntFromArray(weights, 3), getIntFromArray(weights, 4));
	}

	/**
	 * Get the ratings from the configuration.
	 *
	 * @param settings
	 *            settings
	 * @return ratings
	 */
	public static RciRating getRating(final Settings settings) {
		final String ratingsSetting = settings.getString(RATINGS_KEY);

		final String[] ratings = ratingsSetting == null ? new String[] {} : ratingsSetting.split(",");

		return new RciRating(getIntFromArray(ratings, 0), getIntFromArray(ratings, 1), getIntFromArray(ratings, 2),
				getIntFromArray(ratings, 3));
	}

	private static int getIntFromArray(final String[] strings, final int index) {
		if (index >= strings.length || "".equals(strings[index])) {
			return 0;
		} else {
			return Integer.parseInt(strings[index]);
		}
	}

}
