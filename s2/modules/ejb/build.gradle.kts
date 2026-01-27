plugins {
    id("java-library")
}

dependencies {
    implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    implementation("org.springframework:spring-web:6.1.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}