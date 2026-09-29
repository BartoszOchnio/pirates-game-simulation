# Pirates Game

Rozwijana aplikacja symulacyjna w języku Java, inspirowana problemem podziału zasobów pomiędzy piratów.

Program umożliwia konfigurowanie populacji łodzi, strategii graczy oraz metryk sprawiedliwości. Wyniki symulacji są prezentowane w formie tabel i wykresów dla pojedynczej łodzi oraz całej populacji.

> **Status projektu:** prototyp w wersji rozwojowej. Podstawowy przebieg symulacji i wizualizacje działają, ale część algorytmów oraz parametrów wymaga dalszej implementacji i testów.


## Obecnie zaimplementowane elementy

- konfiguracja liczby łodzi, rozmiaru planszy, monet i liczby gier;
- tworzenie hierarchii piratów;
- wybór strategii hierarchii, propozycji, modyfikacji podziału i głosowania;
- konfiguracja mutacji, konkurencji i modelu ryzyka;
- obliczanie wybranych metryk sprawiedliwości;
- prezentacja wyników pojedynczej łodzi;
- porównanie wyników całej populacji;
- zapis konfiguracji symulacji.

## Strategie

Zachowanie łodzi jest opisywane przez cztery rodziny strategii:

| Symbol | Znaczenie |
|---|---|
| `H` | Tworzenie i zmiana hierarchii |
| `P` | Generowanie propozycji podziału |
| `PR` | Modyfikowanie propozycji |
| `V` | Podejmowanie decyzji w głosowaniu |

Zestaw aktywnych strategii tworzy opis zachowania łodzi, określany w projekcie jako jej DNA.

## Metryki sprawiedliwości

Aplikacja obsługuje między innymi następujące metryki:

- envy-freeness;
- egalitarian;
- proportionality;
- individual rationality;
- utilitarian.

Użytkownik może wybrać aktywne metryki i przypisać im wagi wykorzystywane przy obliczaniu wyniku łącznego.

## Wizualizacja wyników

Widok pojedynczej łodzi przedstawia:

- rundę zakończenia kolejnych gier;
- zmiany strategii H, P, PR i V;
- wartości metryk sprawiedliwości.


Widok populacji umożliwia porównanie wszystkich łodzi dla wybranej gry oraz prezentuje średnią i najlepszą wartość łącznej oceny.


## Technologie

- Java;
- JavaFX;
- FXML;
- Maven;
- Jackson.

## Główne elementy projektu

```text
src/main/
|-- java/com.example.piratesgames/
|   |-- conf/                    # konfiguracja symulacji
|   |-- core/                    # modele i silnik symulacji
|   |-- strategies/              # strategie H, P, PR i V
|   |-- PiratesGamesController   # główny formularz
|   |-- ResultsController        # wyniki pojedynczej łodzi
|   `-- PopulationController     # widok całej populacji
`-- resources/com.example.piratesgames/
    |-- game_GUI.fxml
    |-- hello-view.fxml
    `-- charts.css
```

## Uruchomienie gotowej wersji w systemie Windows

1. Pobierz archiwum z gotową wersją programu.
2. Rozpakuj wszystkie pliki do jednego katalogu.
3. Zachowaj oryginalną strukturę folderów.
4. Przejdź do katalogu `app/bin`.
5. Uruchom plik `app.bat`.

Nie przenoś samego pliku `app.bat` poza katalog aplikacji. Program korzysta z pozostałych plików pakietu oraz dołączonych danych.

## Uruchomienie z kodu źródłowego

### Wymagania

- środowisko Java zgodne z projektem;
- Maven albo dołączony Maven Wrapper;
- IntelliJ IDEA lub inne środowisko obsługujące projekty Maven;
- wymagane pliki danych i macierzy.

### Instrukcja

1. Sklonuj albo pobierz repozytorium.
2. Otwórz katalog zawierający `pom.xml` w IntelliJ IDEA.
3. Poczekaj na pobranie zależności Maven.
4. Upewnij się, że katalog `DATA` zawiera wymagane pliki.
5. Ustaw właściwy katalog roboczy, jeżeli aplikacja korzysta ze ścieżek względnych.
6. Uruchom główną klasę JavaFX.

> Uruchomienie przez `app.bat` i bezpośrednio z IDE może wykorzystywać różne katalogi robocze. W takiej sytuacji te same ścieżki względne mogą wskazywać inne lokalizacje danych.

## Dokumentacja

Szerszy opis konfiguracji, modelu symulacji, struktury projektu i wizualizacji znajduje się w pliku:


## Planowany rozwój

- dokończenie i walidacja wszystkich wariantów strategii;
- ujednolicenie konfiguracji oraz ścieżek do danych;
- dodanie testów automatycznych;
- eksport wyników symulacji;
- wykonywanie długich obliczeń poza wątkiem interfejsu;
- przygotowanie stabilnej konfiguracji demonstracyjnej.

## Autor

Bartosz Ochnio

Projekt wykonany w ramach zajęć akademickich.
