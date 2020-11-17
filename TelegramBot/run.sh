#!/bin/bash

# run cityguide telegram bot in the docker container
# Usage: $run.sh <telegram-bot-token>
# Note: logs will be written to the $PWD/logs folder

docker create -e TELEGRAM_TOKEN=$1 --mount "type=bind,src=$(pwd)/logs,dst=/logs" --user "$(id -u):$(id -g)"  --name cityguide-telegram-bot cityguide-telegram-bot && docker start cityguide-telegram-bot

