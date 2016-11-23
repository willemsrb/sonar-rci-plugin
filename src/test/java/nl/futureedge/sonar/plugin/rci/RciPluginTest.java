package nl.futureedge.sonar.plugin.rci;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarQubeVersion;

public class RciPluginTest {

	@Test
	public void test() {
		final RciPlugin javaPlugin = new RciPlugin();
		// final SonarRuntime runtime =
		// SonarRuntimeImpl.forSonarQube(Version.create(5, 6),
		// SonarQubeSide.SERVER);
		final Plugin.Context context = new Plugin.Context(SonarQubeVersion.V5_6);

		Assert.assertEquals(0, context.getExtensions().size());
		javaPlugin.define(context);
		Assert.assertEquals(4, context.getExtensions().size());
	}

}
