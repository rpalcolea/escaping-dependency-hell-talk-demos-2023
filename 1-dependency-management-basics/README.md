# Declaring dependencies

## View and Debug dependencies

This gradle project shows how to declare dependencies and you can get insights via

* Listing all dependencies 

```
./gradlew dependencies
```

* Listing all dependencies for a given configuration/classpath

```
./gradlew dependencies --configuration compileClasspath
```

* Get insight for a given dependency in a configuration/classpath

```
./gradlew dependencyInsight --dependency com.google.guava:guava --configuration runtimeClasspath
```

But you can also get Build Scans!

```
./gradlew dependencies --scan
```

## Conflicts will happen!

In order to show this behavior you can replace the content of `build.gradle` with the one in `build.gradle.bak`

Then run


```
./gradlew dependencies --scan
```

and navigate to the dependencies tab in the build scan to learn more about the resolution