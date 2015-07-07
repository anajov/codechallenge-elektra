#!/bin/bash

gosu postgres postgres --single -jE <<- PSQL
    CREATE DATABASE codechallenge_db OWNER postgres;
PSQL

gosu postgres postgres --single -jE codechallenge_db <<- PSQL
    CREATE TABLE messages ( \
        id serial PRIMARY KEY, \
        content varchar(256) NOT NULL, \
        receivedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW() \
    );
PSQL