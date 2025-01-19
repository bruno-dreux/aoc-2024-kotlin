plugins {
    kotlin("jvm") version "2.0.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}

kotlin {
    jvmToolchain(21) // Ensure this matches the Java version
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Replace with your version
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.github.shiguruikai:combinatoricskt:1.6.0")
}
