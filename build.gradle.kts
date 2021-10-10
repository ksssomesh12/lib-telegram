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
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
            vendor.set(JvmVendorSpec.ORACLE)
        }
        withSourcesJar()
    }
    sourceSets {
        val kotlinMain = "src/main/kotlin"
        val kotlinTest = "src/test/kotlin"
    }
    tasks {
        wrapper {
            gradleVersion = "7.2"
            distributionType = Wrapper.DistributionType.ALL
        }
    }
}
