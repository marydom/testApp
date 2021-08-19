# JavaScript frameworks - Spring boot app

## Entity

* There is a unique constraint added for the `name` and `version` columns.
* Bean validation (JSR-303) is used to check entity field values.
* All the entity fields are mandatory.
* Chosen column types:
  * `name` - non-blank String with length up to 30 chars
  * `version` - non-blank String with length up to 30 chars
  * `deprecationDate` - not-null `LocalDate`
  * `hypeLevel` - `int` between `0` and `100`

## Repository

The repository class contains few methods showing how to create queries.
More can be easily added by following simple rules described in the
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation

## Controller

The controller defines REST endpoints. It accepts and produces JSON objects.
Following endpoints are available:

```
/frameworks GET, POST, DELETE
/frameworks/{id} GET, PUT, DELETE
/frameworks/with-name/{name} GET
/frameworks/with-hype/{hypeLevel} GET
/frameworks/actual-by/{date} GET
/frameworks/actual-by/now GET
```

#### Sample usage

```bash
curl http://localhost:8080/frameworks \
  -H "Content-Type: application/json" \
  -d '{"name":"Vue.js","deprecationDate":"2023-12-31","hypeLevel":12,"version":"3.1"}'

curl http://localhost:8080/frameworks

curl http://localhost:8080/frameworks/with-name/Vue.js

curl http://localhost:8080/frameworks/with-hype/99

curl http://localhost:8080/frameworks/actual-by/2021-01-01

curl http://localhost:8080/frameworks/actual-by/now

curl -X DELETE http://localhost:8080/frameworks/2
curl -X DELETE http://localhost:8080/frameworks \
  -H "Content-Type: application/json" \
  -d '{"name":"Vue.js","deprecationDate":"2023-12-31","hypeLevel":12,"version":"3.1"}'
```

## Other thoughts

The current data model only shows the actual "state" of frameworks.
In the real world there would probably be more columns in the entity:
* `releaseDate` of the framework
* `hypeLevelDate` indicating for which date is the given `hypeLevel` valid

## GitHub - CI
The [java-ci.yml](.github/workflows/java-ci.yml) Github workflow builds project
and runs the tests on push and pull requests to the respective branch.
