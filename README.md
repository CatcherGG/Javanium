# Javanium

Javanium is a simple blockchain java framework. It's meant to be a simple implementation for blockchain and other related technologies and to be used for testing / studying the technology with Java language.

## Blockchain

Javanium blockchain is pretty similiar to Bitcoin logic. Except that I've been using SHA512 as the main hashing algorithem, while Bitcoin uses SHA256. Other than that it should have similar logic.

In order to make it persistent the blockchain itself is stored in MapDB (Embedded noSQL DB)

## P2P Server

Javanium is using a P2P network that uses a server to track the current active users in the system.
The server is based on Jetty, Jersey and SQLite. (To hold the subscribers).

The server is cleaning the database periodically of old Subscribers it didn't hear from for 30 minutes.

#### Server configurations

The server is running on port 8443, Build using maven:

`mvn clean install`

#### RestAPI Details:

`localhost:8443/subscribe`

GET - Returns a list of IPs registered to the server.

POST - Will subscribe the sender to the service.


## Current status
Working on P2P Client. (Basic wallet example)

