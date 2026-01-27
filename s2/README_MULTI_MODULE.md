## Building the Application

To build both modules:

```bash
./gradlew clean build
```

## Deploying the Application

### Primary Instance
Run the application on port 23169 (default):

```bash
./gradlew :web:bootRun
```

### Secondary Instance
Run the application on port 23170:

```bash
./gradlew :web:bootRun --args='--spring.profiles.active=second'
```

Or set the profile as an environment variable:

```bash
SPRING_PROFILES_ACTIVE=second ./gradlew :web:bootRun
```