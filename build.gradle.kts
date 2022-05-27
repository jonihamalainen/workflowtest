plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("io.quarkus")
    id("org.sonarqube") version "3.3"
}

repositories {
    mavenCentral()
    mavenLocal()
}


sonarqube {
    properties {
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.sources", "src/main/")
        property("sonar.tests", "src/test/")
        property("sonar.exclusions", "src/generated/")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/jacoco/test/jacocoTestReport.xml")
        property("sonar.junit.reportsPath","build/test-results/test")
        property("sonar.core.codeCoveragePlugin","jacoco")
        property("sonar.verbose", "true")
        property("sonar.binaries" ,"build/classes/kotlin")
        property("sonar.java.binaries" ,"build/classes/java, build/classes/kotlin")
        property("sonar.dynamicAnalysis", "reuseReports")
    }
}

jacoco {
    toolVersion = "0.7.9"
    reportsDir = file("${project.projectDir}/build/reports")
}

tasks.named("sonarqube") {
    dependsOn(tasks.named("jacocoTestReport"))
}

reports {
    xml.isEnabled = true
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "org.acme"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
