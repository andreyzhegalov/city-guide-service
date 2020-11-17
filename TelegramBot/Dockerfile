FROM bellsoft/liberica-openjdk-alpine-musl:13

ENV HOME /home/cityguide-telegrambot

WORKDIR $HOME

COPY TelegramBot.jar ./TelegramBot.jar
CMD java -XX:+UseContainerSupport -Xmx10g -Xms10g -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1026 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar TelegramBot.jar --cityguide.telegram.token=$TELEGRAM_TOKEN --logging.file.name=/logs/cityguide-telegram.log
