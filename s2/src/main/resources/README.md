# Navigator Service

## API Endpoints

### Find Routes Between Locations
```
GET /navigator/routes/{id-from}/{id-to}/{order-by}
```
Parameters:
- `id-from`: Source location ID
- `id-to`: Destination location ID
- `order-by`: Field to sort by (id, name, creationDate, distance, etc.)

Query Parameters:
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)

### Add New Route
```
POST /navigator/route/add/{id-from}/{id-to}/{distance}
```
Parameters:
- `id-from`: Source location ID
- `id-to`: Destination location ID
- `distance`: Route distance

## Configuration

The service can be configured using environment variables:

- `ROUTE_SERVICE_URL`: URL of the Route Service API (default: http://localhost:8080/api/v1)
- `SERVER_PORT`: Port to run the Navigator service on (default: 8081)

### Create ssl key command
```shell
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 \
        -keystore src/main/resources/keystore.p12 \
        -validity 3650 \
        -storepass changeit \
        -keypass changeit \
        -dname "CN=localhost, OU=Example, O=Example, L=Moscow, ST=Moscow, C=RU"
```
