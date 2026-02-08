plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.aletheia"
version = "0.1.0"
description = "UK Government Contracts Finder API Client Library"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // No external dependencies - using only JDK built-in libraries
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.0")
}

tasks {
    compileJava {
        options.release.set(17)
        options.compilerArgs.addAll(listOf(
            "-Xlint:all",
            "-parameters"
        ))
    }

    test {
        useJUnitPlatform()
    }

    jar {
        archiveBaseName.set("contracts-finder")
        archiveVersion.set(project.version.toString())
        
        manifest {
            attributes(mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Created-By" to "Gradle"
            ))
        }

        exclude("META-INF/maven/**")
        exclude("META-INF/gradle/**")
        exclude("META-INF/*.kotlin_module")
    }

    register<Jar>("minimalJar") {
        dependsOn("jar")
        archiveBaseName.set("contracts-finder-minimal")
        archiveVersion.set(project.version.toString())
        
        from(sourceSets["main"].output)
        
        manifest {
            attributes(mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            ))
        }
        
        exclude("**/*.kotlin_module")
        exclude("META-INF/maven/**")
        exclude("META-INF/gradle/**")
        exclude("META-INF/licenses/**")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/Aletheia/uk-gov-contracts-finder-java")
                
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                
                scm {
                    connection.set("scm:git:https://github.com/Aletheia/uk-gov-contracts-finder-java.git")
                    developerConnection.set("scm:git:ssh://git@github.com:Aletheia/uk-gov-contracts-finder-java.git")
                    url.set("https://github.com/Aletheia/uk-gov-contracts-finder-java")
                }
            }
        }
    }
}
