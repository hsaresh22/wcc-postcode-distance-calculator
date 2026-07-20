# wcc-postcode-distance-calculator

REST-style Spring Boot service that returns the straight-line distance between two UK postcodes.

## Requirements

- Java 17+
- Maven 3.9+ (or IntelliJ with Maven support)

## Build and Run

### IntelliJ

1. Open the project as a Maven project.
2. Run `PostcodeDistanceCalculatorApplication`.

### Command line

```bash
mvn clean spring-boot:run
```

The API starts on `http://localhost:8080`.

## Authentication

Basic Auth is enabled for `/api/**` endpoints.

Default credentials from `src/main/resources/application.properties`:

- Username: `user123`
- Password: `passwd`

Public endpoint:

- `GET /actuator/health`

## Swagger / OpenAPI

After starting the app, open:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## API

### Distance endpoint

`GET /api/v1/distance?from={POSTCODE_1}&to={POSTCODE_2}`

Example:

```bash
curl -u user123:passwd "http://localhost:8080/api/v1/distance?from=SW1A1AA&to=EC1A1BB"
```

Sample success response:

```json
{
	"from": {
		"postcode": "SW1A1AA",
		"latitude": 51.501009,
		"longitude": -0.141588
	},
	"to": {
		"postcode": "EC1A1BB",
		"latitude": 51.5202,
		"longitude": -0.0977
	},
	"distance": 3.8503293368217045,
	"unit": "km"
}
```

Sample invalid postcode response (`400`):

```json
{
	"message": "Invalid postcode: ?"
}
```

Sample unknown postcode response (`404`):

```json
{
	"message": "Postcode not found: ZZ99 1ZZ"
}
```

## Tests

Run tests:

```bash
mvn test
```

Current tests are intentionally lightweight and focus on:

- postcode lookup service behavior
- distance calculation service behavior
- distance controller auth and response contract

## Postcode Data Notes

The file `src/main/resources/postcodes-sample.csv` now contains a real subset extracted from the official downloaded archive `ukpostcodes.zip` (from the task-provided FreemapTools sources).

For a larger dataset, regenerate the CSV from the task-provided sources:

- http://www.freemaptools.com/download-uk-postcode-lat-lng.htm
- http://www.freemaptools.com/download/full-postcodes/postcodes.zip
- http://www.freemaptools.com/download/full-postcodes/fullukpostcodes.zip
