[![build status](https://travis-ci.org/andreyzhegalov/city-guide-service.svg?branch=develop)](https://travis-ci.org/andreyzhegalov/city-guide-service)
[![codecov](https://codecov.io/gh/andreyzhegalov/city-guide-service/branch/develop/graph/badge.svg?token=BNFBVZS88V)](https://codecov.io/gh/andreyzhegalov/city-guide-service)

# City Guide Service

## Description

The service provides information about city showplaces. The service is available via telegarm bot @CityGuide2020Bot.

The service includes a number of modules:
1. DataCollector module for collecting information about showplaces.
2. GeoCoder module for binding showplaces to coordinates.
3. DataStorage is the central module of the service. Responsible for storing all information about showplaces.
4. TelegramBot - module for interaction with telegram clients.


At the moment, the service contains information about almost 700 objects of the city of St. Petersburg.
The main source of information is the site https://walkspb.ru/

## Usage

Just send message with your current location via attachment in the Telegram(@CityGuide2020Bot) and service returned show place description.

## License

MIT
