plugins {
    java
    application
}

group = "org.afrl.rqqd"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf(
                "src/Core",
                "src/Amase",
                "src/SetupTool",
                "src/example"
            ))
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // WorldWind — not reliably available on Maven Central; kept as local file dependency
    implementation(files("lib/worldwind.jar"))

    // Generated LMCP messaging library — built from LmcpGen output
    implementation(files("lib/lmcplib.jar"))

    // FlexDock 1.2.3 — dockable window framework (MIT)
    // Not available on Maven Central in this exact version
    implementation(files("lib/flexdock-1.2.3.jar"))

    // SwingX 1.6.4 — extended Swing components (LGPL 2.1)
    // Exact version not on Maven Central; using vendored JAR for API compatibility
    implementation(files("lib/swingx-all-1.6.4.jar"))

    // GRAL 0.10 — graphing library (LGPL 3.0)
    // Version 0.10 API differs from 0.11 on Maven Central; using vendored JAR
    implementation(files("lib/GRAL/gral-core-0.10.jar"))
}

application {
    mainClass.set("avtas.app.Application")
    applicationDefaultJvmArgs = listOf(
        "-splash:./data/amase_splash.png",
        "-Djava.library.path=./native"
    )
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "avtas.app.Application",
            "Implementation-Title" to "OpenAMASE",
            "Implementation-Version" to project.version
        )
    }
}

distributions {
    main {
        contents {
            from("config") { into("config") }
            from("data") { into("data") }
            from("native") { into("native") }
            from("run") { into("run") }
            from("docs") { into("docs") }
            from("example scenarios") { into("example scenarios") }
        }
    }
}
