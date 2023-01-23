# Praca_Inznierska
Aplikacja webowa do nauki szybkiego czytania, pisania i zapamiętywania

URUCHOMIENIE APLIKACJI

Baza danych aplikacji

Należy uruchomić XAMPP Control Panel a w nim moduły Apache oraz MySQL.
Przy użyciu narzędzia PHPMyAdmin należy zainicjalizować bazę danych o nazwie "brainutrain".

Uruchomienie serwera aplikacji

Należy uruchomić środowisko IntelliJ IDEA, a w nim folder "backend". 
Następnie należy skonfigurować aplikację:
1. Przyciskiem "+" dodać nową konfigurację
2. Wpisać wybraną nazwę konfiguracji
3. Wybrać SDK Java 17
4. Wybrać główną klasę aplikacji: com.example.brainutrain.BackendApplication
5. Upewnić się, że wybrany jest odpowiedni folder jako "working directory"
6. Wcisnąć przycisk "Apply"
Ostatnim krokiem jest uruchomienie aplikacji za pomocą przycisku "run" (zielona strzałka po prawej stronie).
Serwer aplikacji jest dostępny pod adresem http://localhost:8080.

Uruchomienie części klienckiej aplikacji

Należy uruchomić środowisko Visual Studio Code, a w nim otworzyć folder "frontend".
Następnie uruchomić konsolę w aplikacji VS Code.
Za pomocą polecenia "npm install" należy zainstalować wszystkie potrzebne zależności.
Za pomocą polecenia "ng serve" uruchomić aplikację. 
Aplikacja dostępna jest pod adresem http://localhost:4200.