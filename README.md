![image](https://github.com/OsipovKonstantin/java-shareit/assets/98541812/0974a421-f97e-45a6-b2d7-ca35f5b85e69)
# share it - приложение шеринга вещей
[![Java](https://img.shields.io/badge/-Java-F29111?style=for-the-badge&logo=java&logoColor=e38873)](https://www.oracle.com/java/)
[![Spring](https://img.shields.io/badge/-Spring-6AAD3D?style=for-the-badge&logo=spring&logoColor=90fd87)](https://spring.io/projects/spring-framework) 
[![Postgresql](https://img.shields.io/badge/-postgresql-31648C?style=for-the-badge&logo=postgresql&logoColor=FFFFFF)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/-Hibernate-B6A975?style=for-the-badge&logo=hibernate&logoColor=717c88)](https://hibernate.org/)
[![Maven](https://img.shields.io/badge/-Maven-7D2675?style=for-the-badge&logo=apache&logoColor=e38873)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![JUnit](https://img.shields.io/badge/JUnit%205-6CA315?style=for-the-badge&logo=JUnit&logoColor=white)](https://junit.org/junit5/docs/current/user-guide/)
[![Mockito](https://img.shields.io/badge/-mockito-6CA315?style=for-the-badge&logo=mockito&logoColor=90fd87)](https://site.mockito.org/)
[![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)](https://www.postman.com/)
[![RestAPI](https://img.shields.io/badge/-rest%20api-007EC0?style=for-the-badge&logo=restapi&logoColor=275ecf)](https://restfulapi.net/)

## Описание
Приложение объединяет людей. Одним из них необходима вещь (дрель, гитара, пылесос и так далее), а вторые готовы ей поделиться
## Архитектура
Приложение разделено на 2 микросервиса
- server - содержит бизнес-логику
- gateway - сервис по валидации данных перед тем, как они попадут на микросервис server

Для микросервиса server имеется своя БД Postgres. Микросервисы и БД запускаются в docker-контейнерах

## Функциональность
## Диаграмма базы данных
![схема БД Postgres](share-it_schema_DB.PNG)

## Тестирование
Реализованы тесты разных слоёв приложения:
- сервис-слой. 4 сервиса - 67 юнит-тестов с использованием Mockito и Junit, для инициализации моков использовалась аннотация @ExtendWith; 24 интеграционных теста, автоматическая настройка контекста Spring Boot - аннотация @SpringBootTest
- web-слой. 4 контроллера - 46 тестов с применением фреймворка MockMvc и аннотаций @WebMvcTest и @MockBean
- слой JPA-репозиториев. 2 репозитория - 4 теста, применяя аннотацию @DataJpaTest
- слой работы с Json. Для 9 классов DTO проверялась сериализация и десериализация Json, применяя аннотацию @JsonTest
## Как запустить и использовать
Для запуска необходимо, чтобы на ПК была установлена и открыта программа [Docker Desktop](https://www.docker.com/products/docker-desktop/). После установки откройте командную строку cmd и выполните следующие команды

   ```
git clone https://github.com/OsipovKonstantin/java-shareit.git
   ```
В командной строке перейдите в корень проекта. Затем:
   ```
mvn clean package
   ```
   ```
docker-compose up
   ```
Приложение готово к использованию! Сервис доступен по андресу [http://localhost:8080](http://localhost:8080)

Со сценариями работы приложения можно ознакомиться, посмотрев и запустив [коллекцию Postman-тестов](postman/sprint.json)
