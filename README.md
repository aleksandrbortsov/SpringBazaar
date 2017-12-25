# SpringBazaar

Приложение Spring Bazaar демонстрирует работу стека Vaadin -> Spring -> Hibernate (JPA) -> PosgreSQL (& MySQL). 

База данных мигрируется с помощью инструмента Flyway, поэтому перед первым запуском приложения следует создать базу данных spring_bazaar и public схему для нее. В настройках Data Source для Postgres (файл application-postgres.properties) используется порт 5432 и юзер/пароль: postgres/postgres@123. Так как приложение может работать с двумя СУБД (PosgreSQL и MySQL), в application.properties нужно задействовать профиль для соответствующей СУБД:

#spring.profiles.active=mysql

spring.profiles.active=postgres

Если все эти настройки сделаны, таблицы проинициализируются автоматически. Тестовые данные генерируются в компоненте ApplicationCommandLineRunner также при первом запуске, после следует отключить запуск в командной строке (закомментировать @Component).
