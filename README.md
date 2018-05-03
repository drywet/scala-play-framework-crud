## Description
A simple CRUD REST API using:
- Play Framework for HTTP API
- doobie for DB access
- Flyway for DB migrations
- WIX Accord for validation
- Pureconfig for configuration
- sbt-play-swagger for swagger spec generation.

## Prerequisites
- Install postgres
- Install Flyway https://flywaydb.org/download/community , extract and add the directory to system PATH variable
- Make sure postgres service is started.

## Configure
Set DB config parameters in `conf/application.conf` and `flyway.cfg` files.

## Init a database
Create a schema and a table with:
```
flyway -configFiles=flyway.cfg migrate
```
Flyway will execute all new scripts found in `sql/` directory.
There's only one script for now - `sql/V1__init_db.sql`.

## DB migration approach
In contrast to Play Evolutions, DB migration process in this project is independent from the application.
It doesn't require the application to be run in order to do a migration.
This is convenient if multiple instances of the app may be started at the same time in cluster mode.

## Run the app
Run the app with `sbt run`. The app will be available at `http://localhost:9000/`.
Check `/v1` API methods using swagger-ui. `/v2` methods won't work correctly until `v2` migration.

## Migrate the table to the version 2
Copy `sql2/V2__clients_add_age_column.sql` file to `sql/` directory and run:
```
flyway -configFiles=flyway.cfg migrate
```
This will add the nullable `age` column to the table.
Right away, you'll be able to store and retrieve records with a value specified in this column using `/v2` methods.
`/v1` methods will still work because `age` column is optional.
Note: use integer value for the `age` field, since sbt-play-swagger plugin processes Short type incorrectly.

## Failed validation response example
Status: `Bad request`<br/>
Payload:
```
{
  "errorId": "k47rm",
  "message": "Validation failed",
  "info": [
    "phone with value \"123\" has size 3, expected 5 or more"
  ]
}
```