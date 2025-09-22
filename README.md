# Hotel Booking Service

Бэкенд-составляющая сервиса бронирования отелей с возможностью управлять контентом через административную панель CMS.

## Функциональность

- **Управление отелями**: CRUD операции для отелей с рейтинговой системой
- **Управление комнатами**: CRUD операции для комнат с проверкой доступности
- **Управление пользователями**: Регистрация пользователей с ролями (USER/ADMIN)
- **Система бронирования**: Бронирование комнат с проверкой конфликтов дат
- **Поиск и фильтрация**: Постраничный поиск отелей и комнат с различными критериями
- **Статистика**: Сбор статистики через Kafka и MongoDB с экспортом в CSV
- **Безопасность**: Spring Security с Basic Auth и ролевой моделью

## Технологии

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL** (основная БД)
- **MongoDB** (статистика)
- **Apache Kafka** (события)
- **MapStruct** (маппинг)
- **Docker & Docker Compose**

## Запуск приложения

### Предварительные требования

- Java 17+
- Docker и Docker Compose
- Maven

### 1. Запуск инфраструктуры

```bash
docker-compose up -d postgres mongodb zookeeper kafka
```

### 2. Сборка и запуск приложения

```bash
# Сборка проекта
./mvnw clean package -DskipTests

# Запуск приложения
./mvnw spring-boot:run
```

### 3. Запуск с Docker

```bash
# Сборка и запуск всех сервисов
docker-compose up --build
```

## API Endpoints

### Публичные эндпоинты

- `GET /` - Главная страница
- `POST /api/users/register` - Регистрация пользователя

### Аутентифицированные эндпоинты

#### Отели
- `GET /api/hotels` - Получить список отелей (с пагинацией)
- `GET /api/hotels/{id}` - Получить отель по ID
- `GET /api/hotels/search` - Поиск отелей с фильтрацией
- `PUT /api/hotels/{id}/rating` - Обновить рейтинг отеля

#### Комнаты
- `GET /api/rooms/{id}` - Получить комнату по ID
- `GET /api/rooms/search` - Поиск комнат с фильтрацией
- `GET /api/rooms/available` - Получить доступные комнаты

#### Бронирование
- `POST /api/bookings` - Создать бронирование
- `GET /api/bookings/{id}` - Получить бронирование по ID
- `GET /api/bookings/user/{userId}` - Получить бронирования пользователя

### Административные эндпоинты (требуют роль ADMIN)

#### Отели
- `POST /api/hotels` - Создать отель
- `PUT /api/hotels/{id}` - Обновить отель
- `DELETE /api/hotels/{id}` - Удалить отель

#### Комнаты
- `POST /api/rooms` - Создать комнату
- `PUT /api/rooms/{id}` - Обновить комнату
- `DELETE /api/rooms/{id}` - Удалить комнату

#### Бронирования
- `GET /api/bookings` - Получить все бронирования (с пагинацией)

#### Статистика
- `GET /api/statistics` - Получить все события статистики
- `GET /api/statistics/type/{eventType}` - Получить события по типу
- `GET /api/statistics/export/csv` - Экспорт статистики в CSV

## Аутентификация

Приложение использует HTTP Basic Authentication. Для доступа к защищенным эндпоинтам необходимо передать заголовок:

```
Authorization: Basic <base64(username:password)>
```

### Роли пользователей

- **USER**: Может просматривать отели, комнаты, создавать бронирования
- **ADMIN**: Полный доступ ко всем операциям, включая управление контентом и статистику

## Примеры использования

### Регистрация пользователя

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com",
    "role": "USER"
  }'
```

### Регистрация админа

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password124",
    "email": "test2@example.com",
    "role": "ADMIN"
  }'
```

### Создание отеля (требует роль ADMIN)

```bash
curl -X POST http://localhost:8080/api/hotels \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "name": "Grand Hotel",
    "title": "Luxury Hotel in City Center",
    "city": "Moscow",
    "address": "Red Square 1",
    "distanceFromCenter": 0.5
  }'
```

### Поиск отелей

```bash
curl -X GET "http://localhost:8080/api/hotels/search?city=Moscow&rating=4.0&page=0&size=10" \
  -H "Authorization: Basic dGVzdHVzZXI6cGFzc3dvcmQxMjM="
```

### Создание бронирования

```bash
curl -X POST http://localhost:8080/api/bookings?userId=1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic dGVzdHVzZXI6cGFzc3dvcmQxMjM=" \
  -d '{
    "checkInDate": "2024-02-01",
    "checkOutDate": "2024-02-05",
    "roomId": 1
  }'
```

## Тестирование

```bash
# Запуск всех тестов
./mvnw test

# Запуск тестов с покрытием
./mvnw test jacoco:report
```

## Структура проекта

```
src/
├── main/
│   ├── java/com/hotelbooking/
│   │   ├── controller/          # REST контроллеры
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entity/              # JPA сущности
│   │   ├── exception/           # Обработка ошибок
│   │   ├── mapper/              # MapStruct мапперы
│   │   ├── repository/          # JPA репозитории
│   │   ├── service/             # Бизнес-логика
│   │   ├── security/            # Spring Security
│   │   ├── statistics/          # Слой статистики
│   │   └── config/              # Конфигурация
│   └── resources/
│       └── application.yml      # Конфигурация приложения
└── test/                        # Тесты
```

## Мониторинг

- **Приложение**: http://localhost:8080
- **PostgreSQL**: localhost:5432
- **MongoDB**: localhost:27017
- **Kafka**: localhost:9092

## Лицензия

Этот проект создан в рамках учебного задания.
