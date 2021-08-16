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

## Other thoughts

The current data model only shows the actual "state" of frameworks.
In the real world there would probably be more columns in the entity:
* `releaseDate` of the framework
* `hypeLevelDate` indicating for which date is the given `hypeLevel` valid

## GitHub integration
There is the following GitHub integration added to the test project.

### CI
The [java-ci.yml](.github/workflows/java-ci.yml) builds project and runs the tests on push and pull requests to the respective branch.

### Dependabot
The [dependabot.yml](.github/dependabot.yml) checks daily if there are dependency updates and opens pull requests with new versions.
