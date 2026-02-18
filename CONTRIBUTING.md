# Contributing to Sheetz Examples

Thank you for your interest in contributing! Please see the [main Sheetz contribution guidelines](https://github.com/chitralabs/sheetz/blob/main/CONTRIBUTING.md) for the full guide.

## Quick Start

```bash
git clone https://github.com/chitralabs/sheetz-examples.git
cd sheetz-examples
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E01_BasicReadWrite"
```

## Adding a New Example

1. Create a new class following the naming convention: `E09_YourExample.java`
2. Include a `main()` method that runs standalone
3. Add clear comments explaining what the example demonstrates
4. Update the examples table in `README.md`

## License

By contributing, you agree that your contributions will be licensed under the Apache License 2.0.
