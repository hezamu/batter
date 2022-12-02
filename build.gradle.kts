plugins {
    kotlin("js") version "1.7.21"
}

group = "me.h1122"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(LEGACY) {
        binaries.executable()

        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
    }

    dependencies {
        implementation(kotlin("stdlib-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")

//        testImplementation(kotlin("test-js"))
    }

    tasks.register<Copy>("copyOutputsToRoot") {
        from(layout.buildDirectory.dir("distributions"))
        include("*")
        into(layout.projectDirectory)
    }
}