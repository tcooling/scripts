# scripts
A repo with some useful scripts.

# Table of Contents
1. [Jenkins](#jenkins)
2. [Node.js](#nodejs)
3. [Javascript](#javascript)
4. [Python](#python)

## Jenkins

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

## Node.js

### [Winston Logging Script](./node_js/logger.js)

Winston is a logging tool for Node.js, this script adds colour and filenames to your logs.

Usage instructions:

1. Install Winston

```shell
npm install --save winston
```

2. Import `logger.js`

```javascript
const logger = require('./logger')(module);
```

3. Start logging

```javascript
logger.info('Hello, world!')
```

## Javascript

### [Pipe](./javascript/pipe.js)

`pipe.js` allows you to 'pipe' any number of input parameters through any number of functions. It can be useful when you need to pass an input through a number of functions in an immutable way.

Example usage:

```javascript
import { pipe } from './pipe';

const addOne = (x) => x + 1;
const multiplyBy5 = (x) => x * 5;

const result = pipe(addOne, multiplyBy5)(2) // 15
```

### [Industry Code Descriptions](./javascript/siccode.js)

`siccode.js` allows you to get the description that corresponds with a SIC code.

There are backticks in `siccode.js`, so do a simple find + replace if you are unable to use them.

Example usage:

1. Import the variable

```javascript
import industryCodeDescription from './siccode';
```

2. Get the description that corresponds with your SIC code

```javascript
const sic = '01110';
const description = (industryCodeDescription[sic] === undefined)
      ? 'No industry code description found' : industryCodeDescription[sic];
```

## Python

### [Compose](./python/compose.py)

`compose.py` allows you to compose multiple functions together so that the result of each function is passed as an argument to the next.

Example usage:

```python
from compose import compose

add_one = lambda x: x + 1
multiply_by_five = lambda x: x * 5

inc_then_multiply_by_five = compose(add_one, multiply_by_five)

print(inc_then_multiply_by_five(2)) # 15
```