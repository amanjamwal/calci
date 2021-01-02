# calci

Provides RPN based calculations. Consists of two modules `common` and `console`.

## Common module

Has a set of classes and interfaces to implement basic functionality for RPN calculations

## Console module

Provides a console tool to invoke, do calculations and show output on console.

## Dependencies

Project requires [Java v11 or greater](https://www.oracle.com/java/technologies/javase-downloads.html).

## Build

Use gradle commands to build the project.

On Unix based machines
```
./gradlew clean build
```

On Windows based machines

```
gradlew.bat clean build
```

## Test

Use gradle commands to test the project.

On Unix based machines

```
./gradlew test
```

On Windows based machines

```
gradlew.bat test
```

## Run

Use gradle commands to run the project.

On Unix based machines

```
./gradlew --console=plain  runExecutableJar
```

On Windows based machines

```
gradlew.bat --console=plain  runExecutableJar
```
