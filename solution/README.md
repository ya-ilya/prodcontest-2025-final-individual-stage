# Project Name

## Описание проекта

Этот проект представляет собой рекламную платформу, которая позволяет рекламодателям управлять своими рекламными кампаниями и получать статистику через веб-интерфейс и Telegram-бота.

## Инструкция по запуску

### Требования

- Docker
- Docker Compose

### Запуск приложения

1. Клонируйте репозиторий:

    ```sh
    git clone https://gitlab.prodcontest.ru/2025-final-projects-back/ya-ilya.git
    cd project
    ```

2. Запустите Docker Compose:

    ```sh
    docker-compose up --build
    ```

   Эта команда запустит три сервиса:
    - `database`: MySQL база данных
    - `backend`: Spring Boot приложение
    - `grafana`: Grafana для визуализации данных

3. Приложение будет доступно по адресу `http://localhost:8080`.

### Структура `docker-compose.yml`

#### Сервисы
```
- database:
    container_name: database
    image: mysql
    environment: Переменные окружения для настройки базы данных MySQL.
        ...
    ports: Проброс порта 3306 на хосте на порт 33061 в контейнере.
    volumes: Использование тома mysql_data для хранения данных базы данных.
    networks: Подключение к сети net.
    healthcheck: Проверка состояния контейнера с интервалом 5 секунд.
    command: Настройка кодировки и коллации для MySQL.

- grafana:
    container_name: grafana
    image: grafana/grafana:latest
    ports: Проброс порта 3000 на хосте на порт 3000 в контейнере.
    environment: Переменные окружения для настройки Grafana.
        ...
    volumes: Использование тома grafana_data для хранения данных Grafana.
    networks: Подключение к сети net.

- backend:
    container_name: backend
    build: Настройки сборки контейнера.
        ...
    ports: Проброс порта 8080 на хосте на порт 8080 в контейнере.
    networks: Подключение к сети net.
    volumes: Использование тома backend_data для хранения изображений.
    depends_on: Зависимость от сервиса database с условием service_healthy.
```
#### Тома

`mysql_data`: Том для хранения данных базы данных MySQL.
`grafana_data`: Том для хранения данных Grafana.
`images`: Том для хранения изображений.

#### Сети

`net`: Общая сеть для взаимодействия сервисов.

### Схема данных СУБД

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="assets/database_dark.svg">
  <img alt="database" src="assets/database_light.svg">
</picture>