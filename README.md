# Islamic Calendar

A modern **Islamic Calendar app** built with **Jetpack Compose**, providing a clean, intuitive interface to view Hijri dates with their corresponding Gregorian dates & holidays.

<img width="100" height="100" alt="logo" src="https://github.com/user-attachments/assets/44ac7797-31f9-4a49-96f9-5bcf98508f36" />

## ✨ Features

✅ Islamic Hijri calendar mapped with Gregorian calendar
✅ View daily Hijri and Gregorian details
✅ Swipe or navigate between months
✅ Highlight today's date
✅ View holidays for the selected date
✅ Modern Material 3 theming with dark/light support
✅ Offline-first: caches months for fast loading

## 📸 Screenshots

| Home Screen | Stock List | Stock Detail |
|-------------|------------|--------------|
|![image](https://github.com/user-attachments/assets/1cc409ea-b6b3-4746-b486-a860da392289) |![image](https://github.com/user-attachments/assets/4e9cc797-85ec-401c-902d-126de8c266aa) | ![image](https://github.com/user-attachments/assets/f67e7c8b-d0dd-4c71-9978-b66f73421890)


## 🛠️ Tech Stack

* **Kotlin + Jetpack Compose**
* **Kotlinx-datetime** for date operations
* **Room** for local caching
* **Retrofit + OkHttp** for API integration
* **Material 3 (M3) theming**
* **Koin** for dependency injection
* Clean architecture with MVVM-Architecture

## 🚀 Getting Started

1️⃣ **Clone the repository:**

```bash
git clone https://github.com/yourusername/islamic-calendar-app.git
cd islamic-calendar-app
```

### 📺 Demo

▶️ [Watch Demo on YouTube](https://youtube.com/shorts/n86KwBR7rgw)

## 🌐 Use of [AlAdhan](https://aladhan.com/islamic-calendar-api) APIs

- This app uses the AlAdhan APIs to:

- Fetch Hijri calendar with Greforian date accurately for any month and year

- Ensure correctness of Islamic dates mapped with the Gregorian calendar globally

- All data fetched is cached locally for offline-first performance while respecting user privacy and minimizing network calls.

## 📂 Project Structure

```
Directory structure:
└── sadiquereyaz-islamic-calendar/
    ├── build.gradle.kts
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── settings.gradle.kts
    ├── app/
    │   ├── build.gradle.kts
    │   ├── proguard-rules.pro
    │   └── src/
    │       ├── androidTest/
    │       │   └── java/
    │       │       └── com/
    │       │           └── reyaz/
    │       │               └── islamiccalendar/
    │       │                   └── ExampleInstrumentedTest.kt
    │       ├── main/
    │       │   ├── AndroidManifest.xml
    │       │   ├── java/
    │       │   │   └── com/
    │       │   │       └── reyaz/
    │       │   │           └── islamiccalendar/
    │       │   │               ├── BaseApplication.kt
    │       │   │               ├── MainActivity.kt
    │       │   │               ├── data/
    │       │   │               │   ├── local/
    │       │   │               │   │   ├── AppLocalDatabase.kt
    │       │   │               │   │   ├── Converters.kt
    │       │   │               │   │   ├── dao/
    │       │   │               │   │   │   └── CalendarDao.kt
    │       │   │               │   │   └── entity/
    │       │   │               │   │       ├── CalDateEntity.kt
    │       │   │               │   │       ├── CalMonthEntity.kt
    │       │   │               │   │       └── MonthWithDates.kt
    │       │   │               │   ├── mapper/
    │       │   │               │   │   ├── DateToDomain.kt
    │       │   │               │   │   └── DateToEntity.kt
    │       │   │               │   ├── remote/
    │       │   │               │   │   ├── api/
    │       │   │               │   │   │   └── AlAdhanApiService.kt
    │       │   │               │   │   └── dto/
    │       │   │               │   │       ├── AlAdhanResponse.kt
    │       │   │               │   │       └── HijriCalendarWithGeorgianDto.kt
    │       │   │               │   └── repository/
    │       │   │               │       └── CalendarRepositoryImpl.kt
    │       │   │               ├── di/
    │       │   │               │   ├── AppModule.kt
    │       │   │               │   ├── LocalModule.kt
    │       │   │               │   └── RemoteModule.kt
    │       │   │               ├── domain/
    │       │   │               │   ├── model/
    │       │   │               │   │   ├── CalDate.kt
    │       │   │               │   │   └── CompleteCalendar.kt
    │       │   │               │   └── repository/
    │       │   │               │       └── CalendarRepository.kt
    │       │   │               ├── ui/
    │       │   │               │   ├── components/
    │       │   │               │   │   └── IslamicCalendarTopAppBar.kt
    │       │   │               │   ├── navigation/
    │       │   │               │   │   ├── IslamicCalendarApp.kt
    │       │   │               │   │   └── Route.kt
    │       │   │               │   ├── screen/
    │       │   │               │   │   └── calendar/
    │       │   │               │   │       ├── CalendarScreen.kt
    │       │   │               │   │       ├── CalendarUiState.kt
    │       │   │               │   │       ├── CalendarViewModel.kt
    │       │   │               │   │       └── components/
    │       │   │               │   │           ├── CalendarContent.kt
    │       │   │               │   │           ├── CustomCardContainer.kt
    │       │   │               │   │           ├── GetPaletteColorByIndex.kt
    │       │   │               │   │           └── HolidayContent.kt
    │       │   │               │   └── theme/
    │       │   │               │       ├── Color.kt
    │       │   │               │       ├── Theme.kt
    │       │   │               │       └── Type.kt
    │       │   │               └── utils/
    │       │   │                   └── NetworkUtils.kt
    │       │   └── res/
    │       │       ├── drawable/
    │       │       │   ├── ic_launcher_background.xml
    │       │       │   └── ic_launcher_foreground.xml
    │       │       ├── mipmap-anydpi-v26/
    │       │       │   ├── ic_launcher.xml
    │       │       │   └── ic_launcher_round.xml
    │       │       ├── mipmap-hdpi/
    │       │       │   ├── ic_launcher.webp
    │       │       │   ├── ic_launcher_foreground.webp
    │       │       │   └── ic_launcher_round.webp
    │       │       ├── mipmap-mdpi/
    │       │       │   ├── ic_launcher.webp
    │       │       │   ├── ic_launcher_foreground.webp
    │       │       │   └── ic_launcher_round.webp
    │       │       ├── mipmap-xhdpi/
    │       │       │   ├── ic_launcher.webp
    │       │       │   ├── ic_launcher_foreground.webp
    │       │       │   └── ic_launcher_round.webp
    │       │       ├── mipmap-xxhdpi/
    │       │       │   ├── ic_launcher.webp
    │       │       │   ├── ic_launcher_foreground.webp
    │       │       │   └── ic_launcher_round.webp
    │       │       ├── mipmap-xxxhdpi/
    │       │       │   ├── ic_launcher.webp
    │       │       │   ├── ic_launcher_foreground.webp
    │       │       │   └── ic_launcher_round.webp
    │       │       ├── values/
    │       │       │   ├── colors.xml
    │       │       │   ├── ic_launcher_background.xml
    │       │       │   ├── strings.xml
    │       │       │   └── themes.xml
    │       │       └── xml/
    │       │           ├── backup_rules.xml
    │       │           └── data_extraction_rules.xml
    │       └── test/
    │           └── java/
    │               └── com/
    │                   └── reyaz/
    │                       └── islamiccalendar/
    │                           └── ExampleUnitTest.kt
    ├── extraFiles/
    │   ├── material-theme (1).zip
    │   └── material-theme (1)/
    │       ├── README.md
    │       ├── res/
    │       │   └── values-v23/
    │       │       └── font_certs.xml
    │       └── ui/
    │           └── theme/
    │               ├── Color.kt
    │               ├── Theme.kt
    │               └── Type.kt
    └── gradle/
        ├── libs.versions.toml
        └── wrapper/
            └── gradle-wrapper.properties

```

