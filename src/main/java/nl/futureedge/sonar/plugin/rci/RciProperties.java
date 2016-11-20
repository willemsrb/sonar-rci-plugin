package nl.futureedge.sonar.plugin.rci;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.config.PropertyDefinition;

/**
 * Rules Compliance Index (RCI) properties.
 */
public class RciProperties {

	/**
	 * Categorie.
	 */
	public static final String CATEGORY = "Rules Compliance Index (RCI)";

	/**
	 * Weight of blocker issues.
	 */
	public static final String BLOCKER_KEY = "sonar.rci.weigth.blocker";
	/**
	 * Weight of critical issues.
	 */
	public static final String CRITICAL_KEY = "sonar.rci.weigth.critical";
	/**
	 * Weight of major issues.
	 */
	public static final String MAJOR_KEY = "sonar.rci.weigth.major";
	/**
	 * Weight of minor issues.
	 */
	public static final String MINOR_KEY = "sonar.rci.weight.minor";
	/**
	 * Weight of info issues.
	 */
	public static final String INFO_KEY = "sonar.rci.weigth.info";

	private RciProperties() {
		// only statics
	}

	/**
	 * Property definitions.
	 *
	 * @return property definitions
	 */
	public static List<PropertyDefinition> definitions() {
		return Arrays.asList(
				PropertyDefinition.builder(BLOCKER_KEY).name("Weight of blocker issues").defaultValue("10")
						.category(CATEGORY).build(),
				PropertyDefinition.builder(CRITICAL_KEY).name("Weight of critical issues").defaultValue("5")
						.category(CATEGORY).build(),
				PropertyDefinition.builder(MAJOR_KEY).name("Weight of major issues").defaultValue("3")
						.category(CATEGORY).build(),
				PropertyDefinition.builder(MINOR_KEY).name("Weight of minor issues").defaultValue("1")
						.category(CATEGORY).build(),
				PropertyDefinition.builder(INFO_KEY).name("Weight of info issues").defaultValue("0").category(CATEGORY)
						.build());
	}
}
