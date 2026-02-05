# REST Assured API Testing Project

Projekt do automatyzacji testów REST API wykorzystujący Java 21, REST Assured, JUnit 5 i Allure.

## 🚀 Technologie

- **Java 21** - język programowania
- **Maven** - zarządzanie zależnościami i budowanie projektu
- **REST Assured** - framework do testowania REST API
- **JUnit 5** - framework testowy
- **AssertJ** - biblioteka do asercji
- **Allure** - raportowanie testów
- **Jackson** - serializacja/deserializacja JSON
- **Lombok** - redukcja boilerplate code
- **JavaFaker** - generowanie danych testowych
- **SLF4J + Logback** - logowanie
- **Owner** - zarządzanie konfiguracją

## 📋 Wymagania

- Java 21 (JDK)
- Maven 3.6+
- Allure CLI (opcjonalnie, do lokalnego przeglądania raportów)

## 🛠️ Instalacja

1. Sklonuj repozytorium:

```bash
git clone https://github.com/AkademiaQA/rest-assured-json-placeholder-demo
cd rest-assured-json-placeholder-demo
```

2. Zainstaluj zależności:

```bash
mvn clean install -DskipTests
```

## ▶️ Uruchamianie testów

### Uruchomienie wszystkich testów:

```bash
mvn clean test
```

### Uruchomienie konkretnego testu:

```bash
mvn test -Dtest=UserTests
```

### Uruchomienie testów z konkretnym tagiem:

```bash
mvn test -Dgroups=users
```

## 📊 Generowanie raportu Allure

### Wygenerowanie raportu:

```bash
mvn allure:report
```

### Otwarcie raportu w przeglądarce:

```bash
mvn allure:serve
```

Raport zostanie automatycznie otwarty w domyślnej przeglądarce.

## 📁 Struktura projektu

```
rest-assured-json-placeholder-demo/
├── src/
│   └── test/
│       ├── java/
│       │   └── pl/akademiaqa/
│       │       ├── base/           # Klasy bazowe dla testów
│       │       ├── config/         # Konfiguracja projektu
│       │       ├── model/          # Modele danych (POJO)
│       │       ├── tests/          # Testy API
│       │       └── utils/          # Narzędzia pomocnicze
│       └── resources/
│           ├── allure.properties   # Konfiguracja Allure
│           ├── config.properties   # Konfiguracja aplikacji
│           └── logback-test.xml    # Konfiguracja logowania
├── target/
│   ├── allure-results/            # Wyniki testów dla Allure
│   └── logs/                      # Logi testów
├── pom.xml                        # Konfiguracja Maven
└── README.md
```

## 🔧 Konfiguracja

### config.properties

Edytuj plik `src/test/resources/config.properties` aby zmienić konfigurację:

```properties
base.uri=https://jsonplaceholder.typicode.com
base.path=/
timeout=10000
environment=test
```

### Nadpisanie konfiguracji przez system properties:

```bash
mvn test -Dbase.uri=https://api.example.com
```

## 📝 Przykładowy test

```java
@Test
@DisplayName("Get user by ID - should return correct user")
@Severity(SeverityLevel.CRITICAL)
void shouldGetUserById() {
    int userId = 1;

    User user = given()
            .pathParam("id", userId)
            .when()
            .get("/users/{id}")
            .then()
            .statusCode(200)
            .extract()
            .as(User.class);

    assertThat(user.getId()).isEqualTo(userId);
    assertThat(user.getName()).isNotEmpty();
}
```

## 🎯 Dobre praktyki

1. **BaseTest** - wszystkie testy dziedziczą po klasie `BaseTest`
2. **Page Object Pattern** - można rozszerzyć o klasy dla endpointów
3. **Test Data** - wykorzystuj `FakerUtils` do generowania danych
4. **Annotations** - używaj adnotacji Allure dla lepszego raportowania
5. **AssertJ** - preferuj AssertJ zamiast standardowych asercji JUnit

## 📚 API testowe

Projekt używa [JSONPlaceholder](https://jsonplaceholder.typicode.com/) jako API testowego.

Dostępne endpointy:

- `/users` - zarządzanie użytkownikami
- `/posts` - zarządzanie postami
- `/comments` - komentarze
- `/albums` - albumy
- `/photos` - zdjęcia
- `/todos` - zadania

## 📄 Licencja

MIT License

## 📄 Author

Created with 🧉 by [@akademiaqa](https://github.com/akademiaqa) x [@bkita](https://github.com/bkita)
