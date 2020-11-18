#!/bin/bash

# build docker image clean-telegram-bot

gradle clean build
cp ./build/libs/TelegramBot.jar .
docker build -t cityguide-telegram-bot .
rm TelegramBot.jar

