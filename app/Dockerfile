FROM gradle:jdk21-corretto

WORKDIR /app

COPY . .

RUN gradle installDist

# Открываем порт
EXPOSE 7070

# Используем JSON-формат для CMD
CMD ["./build/install/app/bin/app"]