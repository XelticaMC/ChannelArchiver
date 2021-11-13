plugins {
    kotlin("jvm") version "1.5.0"
    application
}

val main = "com.github.raink1208.channelarchiver.MainKt"

application {
    mainClass.set(main)
}

group = "com.github.raink1208"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(uri("https://m2.dv8tion.net/releases"))
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("net.dv8tion:JDA:4.3.0_277")
    implementation("org.yaml:snakeyaml:1.12")
    implementation("com.google.code.gson:gson:2.8.5")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}



tasks {
    jar {
        archiveFileName.set("ChannelArchiver.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to main)
        }
        from(configurations.runtimeClasspath.get()
            .filter { !it.name.endsWith("pom") }
            .onEach { println("add from dependencies:" + it.name) }
            .map { if (it.isDirectory) it else zipTree(it) }
        )
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add form sources: "+it.name) }
        from(sourcesMain.output)
    }
}