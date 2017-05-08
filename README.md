# Rules Compliance Index (RCI) Plugin for SonarQube [![Build Status](https://travis-ci.org/willemsrb/sonar-rci-plugin.svg?branch=master)](https://travis-ci.org/willemsrb/sonar-rci-plugin) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=nl.future-edge.sonarqube.plugins:sonar-rci-plugin)](https://sonarqube.com/dashboard/index?id=nl.future-edge.sonarqube.plugins%3Asonar-rci-plugin)
*Requires SonarQube 5.6+ (tested against 5.6, 5.6.3 (LTS), 6.0, 6.1, 6.2, 6.3)*

This plugin calculates a metric based on the weighted value of issues and the number of lines of code (default settings are given in parentheses and can be configured on a global and per project level; other values are taken from the core metrics):

<pre>Issue weight = blocker violations &ast; weight (10)
             &plus; critical violations &ast; weight (5)
             &plus; major violations &ast; weight (3)
             &plus; minor violations &ast; weight (1)
             &plus; info violations &ast; weight (0)

Rules Compliance Index = max(1.0 - (Issue Weigth / Lines of Code) * 100, 0)</pre>

The plugin also adds a (configurable) Rules Compliance Rating metric to visualize the RCI (score an A-rating with a rules compliance index of 97%).


#### Installation

Install the plugin via the Update Center in the SonarQube administration pages. Or to install the plugin manually; copy the .jar file from the release to the `extensions/plugins` directory of your SonarQube installation.