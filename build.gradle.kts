plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.9.0"
}

group = "com.DoMigliaccio"
version = "3.1.3"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1")
    /*version.set("2022.1.4")*/
    type.set("IC") // Target IDE Platform
    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        //sinceBuild.set("221")
        sinceBuild.set("211")
        /*untilBuild.set("241.*") - Not specifying until-build means it will include all future builds. This includes future, yet unreleased versions and possibly new IDEs, which might impact compatibility later.*/
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
