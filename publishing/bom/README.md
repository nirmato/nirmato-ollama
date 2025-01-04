# Bill of Materials

This library is composed of multiple modules where each module is published as a separate artifact. We also publish a
single top-level artifact that automatically brings in all the artifacts of this library, so the simplest way to depend
on the entire library is to add a single dependency to the top-level artifact as described in
[dependency details](../../README.md#dependency).

In addition to the above, we also publish a bill of materials, BOM, which references all the artifact versions in that
release. The main use-case for the BOM is if you prefer to pick and choose individual components instead of
depending on the entire library. In that case, you can reference the BOM at a particular version and add dependencies
for the components that you want without specifying their individual versions.

## Gradle

```kotlin
dependencies {
    // import the BOM at a particular version
    implementation(platform("org.nirmato.ollama:nirmato-ollama-bom:[version]"))

    // pick the artifacts that you want but don't specify their versions as that's controlled by the BOM
    implementation("org.nirmato.ollama:nirmato-ollama-client")
}
```

## Maven

```xml

<project>
    <!-- import the BOM at a particular version -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.nirmato.ollama</groupId>
                <artifactId>nirmato-ollama-bom</artifactId>
                <version>[version]</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!-- pick the artifacts that you want but don't specify their versions as that's controlled by the BOM -->
    <dependencies>
        <dependency>
            <groupId>org.nirmato.ollama</groupId>
            <artifactId>nirmato-ollama-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.nirmato.ollama</groupId>
            <artifactId>nirmato-ollama-client</artifactId>
        </dependency>
    </dependencies>
</project>
```

## Artifacts

The `bom` references the following artifacts:

| GroupId                  | ArtifactId                   | Description                |
|:-------------------------|:-----------------------------|:---------------------------|
| `org.nirmato.ollama`     | `nirmato-ollama-annotations` | Ollama nirmato annotations |
| `org.nirmato.ollama`     | `nirmato-ollama-api`         | Ollama client API          |
| `org.nirmato.ollama`     | `nirmato-ollama-client`      | KMP implementation         |
