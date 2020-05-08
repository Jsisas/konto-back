# KONTO
It's an application that will help you manage your expenses by taking your accounts, balances and transactions with your permissions from your bank automatically. MVP is focused on Estonian banks via OpenBanking API.


## Build
1. `./gradlew build`

## Run
1. app `./gradlew bootrun`  
1. database `docker-compuse up`

## Clean Database
`docker container stop konto-back_database_1`
`docker container stop docker container rm konto-back_database_1`
