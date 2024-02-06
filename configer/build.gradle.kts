plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation("org.pkl-lang:pkl-codegen-kotlin:0.25.1")
    implementation("org.pkl-lang:pkl-config-kotlin:0.25.1")
}

gradlePlugin {
    plugins {
        create("Configer") {
            id = "configer"
            implementationClass = "Configer"
        }
    }
}
