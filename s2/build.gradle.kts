plugins {
    id("org.springframework.boot") version "3.2.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    java
}

group = "org.dismefront"
version = "1.0.2"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    
    group = "org.dismefront"
    version = "1.0.2"
    
    repositories {
        mavenCentral()
    }
    
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    
    dependencies {
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    }
    
    tasks.test {
        useJUnitPlatform()
    }
}

project(":web") {
    apply(plugin = "org.springframework.boot")
    dependencies {
        implementation(project(":ejb"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
    }
}

project(":ejb") {
    dependencies {
        implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
        implementation("org.springframework:spring-context:6.1.2")
        implementation("org.springframework:spring-web:6.1.2")
    }
}