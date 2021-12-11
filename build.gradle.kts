group = "io.ktln.lib"
version = "0.0.1-CANARY"

plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    dependencies {
        implementation(kotlin("stdlib"))

        val ktorVersion = "1.6.7"
        implementation("io.ktor:ktor-client-cio:${ktorVersion}")
        implementation("io.ktor:ktor-client-core:${ktorVersion}")
        implementation("io.ktor:ktor-client-gson:${ktorVersion}")
        implementation("io.ktor:ktor-client-logging:${ktorVersion}")

        implementation("ch.qos.logback:logback-classic:1.2.3")
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
        withJavadocJar()
        withSourcesJar()
    }
    sourceSets {
        val kotlinMain = "src/main/kotlin"
        val kotlinTest = "src/test/kotlin"
    }
    publishing {
        publications {
            create<MavenPublication>("mavenKotlin") {
                from(components["kotlin"])
            }
        }
    }
    tasks {
        wrapper {
            gradleVersion = "7.2"
            distributionType = Wrapper.DistributionType.ALL
        }
    }
}
