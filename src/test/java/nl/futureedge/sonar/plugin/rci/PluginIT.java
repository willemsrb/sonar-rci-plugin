package nl.futureedge.sonar.plugin.rci;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.MavenBuild;
import com.sonar.orchestrator.locator.FileLocation;

@RunWith(Parameterized.class)
public class PluginIT {

	@Parameters
	public static Collection<Object[]> sonarQubeVersions() {
		return Arrays.asList(new Object[][] { { "5.6" }, { "LTS" }, { "6.0" }, { "6.1" }, { "6.2" } });
	}

	private final String sonarQubeVersion;
	private Orchestrator orchestrator;

	public PluginIT(final String sonarQubeVersion) {
		this.sonarQubeVersion = sonarQubeVersion;
	}

	@Before
	public void setupSonarQube() {
		System.getProperties().setProperty("sonar.runtimeVersion", sonarQubeVersion);

		orchestrator = Orchestrator.builderEnv()
				.addPlugin(FileLocation.byWildcardMavenFilename(new File("target"), "sonar-rci-plugin-*.jar"))
				.setOrchestratorProperty("javaVersion", "4.2.1").addPlugin("java").build();

		orchestrator.start();
	}

	@After
	public void teardownSonarQube() {
		if (orchestrator != null) {
			orchestrator.stop();
		}
	}

	public void runSonar() {
		final File pom = new File(new File(".", "target/it"), "pom.xml");

		final MavenBuild install = MavenBuild.create(pom).setGoals("clean verify");
		Assert.assertTrue("'clean verify' failed", orchestrator.executeBuild(install).isSuccess());

		final HashMap<String, String> sonarProperties = new HashMap<>();
		sonarProperties.put("sonar.login", "");
		sonarProperties.put("sonar.password", "");
		sonarProperties.put("sonar.skip", "false");
		sonarProperties.put("sonar.scanner.skip", "false");

		final MavenBuild sonar = MavenBuild.create(pom).setGoals("sonar:sonar").setProperties(sonarProperties);
		Assert.assertTrue("'sonar:sonar' failed", orchestrator.executeBuild(sonar).isSuccess());
	}

	@Test
	public void test() {
		runSonar();

		final ResourceQuery query = ResourceQuery.createForMetrics(
				"nl.future-edge.sonarqube.plugins:sonar-rci-plugin-it:src/main/java/TestClass.java",
				RciMetrics.RULES_COMPLIANCE_INDEX.getKey());

		final Resource resource = orchestrator.getServer().getWsClient().find(query);
		Assert.assertNotNull("Metric not found", resource);
		final Measure measure = resource.getMeasure(RciMetrics.RULES_COMPLIANCE_INDEX.getKey());
		final Double value = measure.getValue();

		Assert.assertEquals(55.6d, value, 0.01);
	}

}
