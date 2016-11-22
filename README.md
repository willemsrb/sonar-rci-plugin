# sonar-rci-plugin [![Build Status](https://travis-ci.org/willemsrb/sonar-rci-plugin.svg?branch=master)](https://travis-ci.org/willemsrb/sonar-rci-plugin) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=nl.future-edge.sonarqube.plugins:sonar-rci-plugin)](https://sonarqube.com/dashboard/index?id=nl.future-edge.sonarqube.plugins%3Asonar-rci-plugin)
Rules Compliance Index (RCI) Plugin for SonarQube

This plugin calculates a metric based on the weigthed value of issues and the number of lines of code (default settings are given in parentheses and can be configured on a global and per project level; other values are taken from the core metrics):

<pre>Issue weigth = blocker violations &ast; weigth (10)
             &plus; critical violations &ast; weigth (5)
             &plus; major violations &ast; weigth (3)
             &plus; minor violations &ast; weigth (1)
             &plus; info violations &ast; weigth (0)

Rules Compliance Index = max(1.0 - (Issue Weigth / Lines of Code) * 100, 0)</pre>

The plugin also adds a (configurable) Rules Compliance Rating metric to visualize the RCI (score an A-rating with a rules compliance index of 97%).