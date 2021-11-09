plugins {
    kotlin("jvm") version "1.5.0"
}

group = "com.github.raink1208"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(uri("https://m2.dv8tion.net/releases"))
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("net.dv8tion:JDA:4.3.0_277")
    implementation("org.yaml:snakeyaml:1.12")
}
