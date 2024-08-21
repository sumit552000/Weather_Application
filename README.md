# WeatherApp 🌦️
WeatherApp is an Android application that provides real-time weather information for the user's current location. The app utilizes the OpenWeatherMap API to fetch weather data and displays it with a creative, user-friendly interface. The project is built using the MVVM architecture.

### Features
**Real-time Weather Data:** Displays current temperature, weather conditions, wind speed, humidity, sunrise, and sunset times.<br>
**Location-based Weather:** Automatically fetches weather data based on the user's current location. <br>
**Dynamic Backgrounds:** Changes the background and animation based on the weather condition (e.g., sunny, cloudy, rainy).<br>
**Offline Support:** Ensures that the last seen weather information, including images and icons, is available offline.<br>
**Modern UI:** Uses ConstraintLayout for responsive design and Lottie animations for visual effects.<br>
**MVVM Architecture:** Utilizes the MVVM (Model-View-ViewModel) pattern for better code organization and separation of concerns.<br>
**View Binding:** Simplifies UI interactions by using View Binding instead of traditional findViewById.<br>

**Technologies Used**<br>
**Kotlin:** For app development.<br>
**Retrofit:** For network operations to communicate with the OpenWeatherMap API.<br>
**Kotlin Coroutines:** For asynchronous programming.<br>
**Lottie Animations:** For weather condition animations.<br>
**SharedPreferences:** For caching weather data to provide offline access.<br>
**ViewModel & LiveData:** To manage UI-related data in a lifecycle-conscious way.<br>
**ConstraintLayout:** For building a responsive and adaptive UI.<br>



Clone the project and open it using Android Studio. Then open your MainActivty and paste the apikey = a9d7877ecf28b142eab78b39d9e14c03 in getLocation() function.

# Run the project
Sync the Gradle and run the project. Turn on the internet and location of your testing device. Then Install APK on your emulator or real device. 

# Screenshots
