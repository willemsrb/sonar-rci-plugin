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
import org.sonar.wsclient.services.Model;
import org.sonar.wsclient.services.Query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.MavenBuild;
import com.sonar.orchestrator.locator.FileLocation;

@RunWith(Parameterized.class)
public class PluginIT {

    @Parameters
    public static Collection<Object[]> sonarQubeVersions() {
        return Arrays.asList(new Object[][] { { "5.6" }, { "LTS" }, { "6.0" }, { "6.1" }, { "6.2" }, { "6.3" } });
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

        // GET api/measures/component
        // componentKey
        // metricKeys

        final ComponentMeasureQuery query = new ComponentMeasureQuery(
                "nl.future-edge.sonarqube.plugins:sonar-rci-plugin-it:src/main/java/TestClass.java",
                RciMetrics.RULES_COMPLIANCE_INDEX.getKey());

        final String queryResult = orchestrator.getServer().getWsClient().getConnector().execute(query);
        final JsonObject result = new JsonParser().parse(queryResult).getAsJsonObject();
        Assert.assertNotNull("No result", result);
        final JsonObject component = result.getAsJsonObject("component");
        Assert.assertNotNull("No component", component);
        final JsonArray measures = component.getAsJsonArray("measures");
        Assert.assertNotNull("No measures", measures);
        Assert.assertEquals("Only one measure expected", 1, measures.size());
        final JsonObject measure = measures.get(0).getAsJsonObject();
        Assert.assertNotNull("No measure", measure);
        final Double value = measure.get("value").getAsDouble();
        Assert.assertEquals(55.6d, value, 0.01);
    }

    private final class ComponentMeasureQuery extends Query<Model> {

        public static final String BASE_URL = "/api/measures/component";

        private String componentKey;
        private String[] metricKeys;

        public ComponentMeasureQuery(final String componentKey, final String... metricKeys) {
            this.componentKey = componentKey;
            this.metricKeys = metricKeys;
        }

        @Override
        public Class<Model> getModelClass() {
            return Model.class;
        }

        @Override
        public String getUrl() {
            final StringBuilder url = new StringBuilder(BASE_URL);
            url.append('?');
            appendUrlParameter(url, "componentKey", componentKey);
            appendUrlParameter(url, "metricKeys", metricKeys);
            return url.toString();
        }
    }
}
