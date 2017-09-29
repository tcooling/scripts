# scripts
A repo with some useful scripts

# Table of Contents
1. [Jenkins](#jenkins)

## jenkins

### [blueGreenDeploy.groovy](./jenkins/blueGreenDeploy.groovy)

[Blue-Green Deployment](https://docs.cloudfoundry.org/devguide/deploy-apps/blue-green.html) is a method of deploying an application with 0 downtime. This variation will also deploy the correct number of instances by checking with CloudFoundry to find the number of instances currently running for that app and redploy the same number.

You must call the deploy method with the following arguments:

```groovy
def deploy(String credentialsId, String organisation, String space, String appName, String pathToArtifact, String pathToManifest, String host, String domain)
```

### [colourText.groovy](./jenkins/colourText.groovy)

You use the colourText methods to wrap a print statement in an ANSI colour to improve readability when looking through the logs of a Jenkins build.

Example Usage:

```groovy
colourText("info","Hello, world!")
```

