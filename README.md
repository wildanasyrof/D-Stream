# D-Stream 🎥📱

**D-Stream** is an Android anime streaming app built with Kotlin and MVVM (Model-View-ViewModel) architecture. This app displays a list of anime, anime details, and allows users to stream episodes using ExoPlayer.

## ✨ Features

- 🔍 Display anime list from API
- 📄 Show detailed information and episodes of selected anime
- ❤️ Add anime to favorites list
- ▶️ Play anime episodes with ExoPlayer
- 🔄 Pagination support using Paging 3
- 💾 Local storage using Room database
- ⚙️ Dependency injection using Hilt
- 🌐 API consumption using Retrofit & OkHttp
- 💡 MVVM architecture + Repository Pattern

## 🔗 API

This application is powered by a custom-built backend API developed in Golang.

Check out the backend repository here:

➡️ [Golang-stream (REST API)](https://github.com/wildanasyrof/Golang-stream)

The API provides anime data, detail endpoints, search functionality, and favorites support.

## 🧰 Libraries & Tools

Main libraries and tools used in this project:

### Networking
- Retrofit
- OkHttp

### Local Storage
- Room
- DataStore

### Architecture Components
- ViewModel & LiveData
- Navigation Component

### UI
- Glide
- CircleImageView
- FlexboxLayout
- ExoPlayer

### Dependency Injection
- Hilt

### Others
- Gson
- Paging 3

## 📦 Getting Started

1. Clone this repository:
   ```bash
   git clone https://github.com/wildanasyrof/D-Stream.git
   ```
2. Open the project in Android Studio.
3. Make sure to configure the correct base URL for the API inside your Retrofit setup.
4. Run the app on an emulator or physical device.

## 📸 Screenshots

### 🏠 Home Page
![Home Page](Screenshot/Screenshot_20250415-210036_DS.png)

### 📄 Detail Page
![Detail Page](Screenshot/Screenshot_20250415-210235_DS.png)

### ❤️ Favorite Page
![Favorite Page](Screenshot/Screenshot_20250415-210050_DS.png)

## 🧑‍💻 Contributions

Open for contributions! Feel free to fork this repository and submit a pull request if you’d like to add features or improvements.
