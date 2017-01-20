[![Build Status](https://travis-ci.org/vaimr/rt-checks.svg?branch=master)](https://travis-ci.org/vaimr/rt-checks)

# Table of Contents
**[Motivation](#motivation)**  
**[How it works](#how-it-works)**  
**[Usage sample](#usage-sample)**  
**[Next Steps, Versions, Authors, License](#versions)** 

# Motivation
When the project is run in many environments with different configurations always have problems with missed configs, code version, and so on. 

This library (as usual they say, bicycle) allows you to write smaller checks for various subsystems that test some initial conditions or performance.

I can check the maximum allowed number of open files in the Linux (ulimit) or parameters web application connector, or free space in disk, or certain classes classloader. Checks are run and report output in any convenient form: log file, web page, email.
# How it works
You implement various inspections classes with methods. Annotate them, and run through the most convenient runner. Output the results in the required form.

Each RtChecker have level, title and description attributes. Each RtCheck have priority, name and resolveInstruction attributes.
## Usage sample
### Realize checker with one or more checks
```java
@RtChecker(
  level = RtChecker.Level.CORE,
  title = "My super app checks",
  description = "Check DB params and app configuration")
public class AppCheck {

  @RtCheck(
    priority = RtCheck.Priority.LOWEST,
    name = "DB params check",
    resolveInstruction = "Change connect url in myAppConfig.xml")
  RtCheckResult checkDBConnectionParams() {
    //...
    
    return RtCheckResult.ACCEPT;
  }

  @RtCheck(
    name = "Verify App configuration",
    resolveInstruction = "Check load properties section in myAppConfig.xml")
  RtCheckResult verifyConfiguration() {
    //...
    
    return RtCheckResult.FAILED;
  }
}
```

### Run checks in runner
#### Run concrete check
```java
    new RtConcreteCheckRunner(AppCheck.class).run(new RtCheckRunListenerAdapter() {
      /**
       * Main method. Invoke after every check.
       *
       * @param check       Check configuration
       * @param checkResult Check result
       */
      public void after(RtCheck check, RtCheckResult checkResult) {
        // Check output
      }
    });
```
#### Run checks in list
```java
    List<Class<?>> checkers = new ArrayList<>();
    new RtListCheckRunner(checkers).run(new RtCheckRunListenerAdapter() {
      /**
       * Main method. Invoke after every check.
       *
       * @param check       Check configuration
       * @param checkResult Check result
       */
      public void after(RtCheck check, RtCheckResult checkResult) {
        // Check output
      }
    });
```
#### Run all checkers in ClassLoader by base package name
```java
    new RtAllCheckRunner("org.myapp").run(new RtCheckRunListenerAdapter() {
      /**
       * Main method. Invoke after every check.
       *
       * @param check       Check configuration
       * @param checkResult Check result
       */
      public void after(RtCheck check, RtCheckResult checkResult) {
        // Check output
      }
    });
```
#### Run all checkers in ClassLoader by base package name with ordering level and priority checks
```java
    new RtOrderedAllCheckRunner("org.myapp",
        new RtCheckerLevelComparator(), 
        new RtCheckPriorityComparator()).run(new RtCheckRunListenerAdapter() {
      /**
       * Main method. Invoke after every check.
       *
       * @param check       Check configuration
       * @param checkResult Check result
       */
      public void after(RtCheck check, RtCheckResult checkResult) {
        // Check output
      }
    });
```
#### Run localized checks
Specify msgBundle annotation argument - resource bundle name. Write to annotations arguments localized message keys from resource bundles

Checker sample:
```java
    @RtChecker(msgBundle = "MyProject", title = "localizedCheckTitle")
    public class LocalizedCheck {
      @RtCheck(name = "myProjectCheckName", resolveInstruction = "myProjectCheckInstruction")
      RtCheckResult configCheck() {
        // check
        return RtCheckResult.ACCEPT;
      }
    }
```   

Sample checks runner:
```java    
    new RtConcreteCheckRunner(LocalizedCheck.class).run(new RtCheckRunListenerAdapter() {
      private final ResourceBundle bundle = ResourceBundle.getBundle("MyProject");
      @Override
      public void setUp(RtChecker checker) {
        // Output localized Checker title
        System.out.println(bundle.getString(checker.title()));
      }

      /**
       * Main method. Invoke after every check.
       *
       * @param check       Check configuration
       * @param checkResult Check result
       */
      public void after(RtCheck check, RtCheckResult checkResult) {
        // Output localized Check name and resolve description
        System.out.println(bundle.getString(check.name()));
        System.out.println(bundle.getString(check.resolveInstruction()));
      }
    });

```

Sample bundle MyProject_en.properties:
```ini
    localizedCheckTitle=My project configuration check
    myProjectCheckName=Check config param
    myProjectCheckInstruction=Add param "dbUrl" to "app.config"
```

#### Check results with dynamically messages
For return custom message with check result use static methods from RtCheckResult (failure(""), accetp("")...)

Example:
```java
      @RtCheck(name = "myProjectCheckName", resolveInstruction = "myProjectCheckInstruction")
      RtCheckResult configCheck() {
        return RtCheckResult.accept("Custom message");
      }
    }
```

#### Ignore checkers and concrete checks
For ignore checks exist annotation RtCheckIgnore

Sample in checker usage:
```java
    @RtCheckIgnore // This checker with all the checks will be ignored
    @RtChecker(title = "Ignored test")
    public class IgnoreChecker {
    }
}
```   

Sample in checks usage:
```java
    @RtCheckIgnore // This concrete check will be ignored
    @RtCheck(name = "ignoredCheck", resolveInstruction = "")
    public RtCheckResult ignoredCheck() {
      return RtCheckResult.FAILED;
    }
}
```   

# Versions
1.1 - Published in [Central Maven Repository](https://repo1.maven.org/maven2/io/github/vaimr/rt-checks/1.1/)

1.2 - Added msgBundle param to RtChecker annotation for localization checks

1.3 - Custom messages in RtCheckResult and ignorable checkers and checks

# Authors
* **Saponenko Denis** - *Initial work* - [vaimr](https://github.com/vaimr)

See also the list of [contributors](https://github.com/vaimr/rt-checks/contributors) who participated in this project.

# License
```
The MIT License

Copyright (c) 2016 Saponenko Denis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
