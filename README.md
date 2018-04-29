== Prerequisites
- Install postgres
- Install Flyway https://flywaydb.org/download/community , extract and add the directory to system PATH variable
- Make sure postgres service is started

== Configure
Set DB config parameters in `conf/application.conf` and `flyway.cfg` files

== Init a database
Create a schema and a table with `flyway -configFiles=flyway.cfg migrate`.
Flyway will execute all new scripts found in `sql/` directory.
There's only one script for now - `sql/V1__init_db.sql`.

== DB migration approach
In contrast to Play Evolutions, DB migration process in this project is independent from the application.
It doesn't require the application to be run in order to do a migration.
This is convenient if multiple instances of the app may be started at the same time in cluster mode.

== Run the app
Run the app with `sbt run`. The app will be available at `http://localhost:9000/`.
The web page uses AJAX calls to REST API to store and retrieve data.
At the moment, the `age` column is ignored by the app, because there's no such column in the `clients` table.

== Migrate the table
Copy `sql2/V2__clients_add_age_column.sql` file to `sql/` directory and run `flyway -configFiles=flyway.cfg migrate`.
This will add the nullable `age` column to the table.
Right away, on the web page, you'll be able to store and retrieve records with a value specified in this column.