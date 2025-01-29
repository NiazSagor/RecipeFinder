## Recipe Community App


📌 *Overview*

The Recipe Community App is a modern Android application built with Jetpack Compose that allows users to explore, share, and bookmark recipes. Users can post their own recipe experiences, interact with community posts, and manage their favorite recipes seamlessly.

🚀 *Features*

📖 Browse a variety of recipes  
✍️ Share your own cooking experiences  
❤️ Like and bookmark favorite recipes  
📸 Upload photos to accompany posts  
🔎 Search and filter recipes

## 🛠️ Technologies Used


📱*Frontend (Android App)*


- ![Kotlin](https://img.shields.io/badge/Kotlin-%230095D5.svg?logo=kotlin&logoColor=white) **Kotlin** - Primary language for Android development  
- ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-%23009B86.svg?logo=jetpackcompose&logoColor=white) **Jetpack Compose** - Modern UI toolkit for building UI declaratively  
- ![StateFlow](https://img.shields.io/badge/StateFlow-%23FF5722.svg?logo=kotlin&logoColor=white) **StateFlow** - Manage UI state reactively  
- ![Coil](https://img.shields.io/badge/Coil-%23FFDD00.svg?logo=coil&logoColor=black) **Coil** - Image loading and caching  
- ![DataStore](https://img.shields.io/badge/DataStore-%2361DAFB.svg?logo=googlecloud&logoColor=white) **DataStore** - Efficient data persistence for storing user preferences and bookmarked recipes  


💻 *Backend & Cloud Services*


- Firebase Firestore - NoSQL database to store user posts and
  interactions
- Firebase Authentication - Secure user authentication
- Firebase Firestore - Store the recipe's likes and comments for each
  of the recipes
- Supabase Storage - Store the recipe images uploaded by the community as the Firebase Storage is now available only in the paid plan.

*📸 Image Upload Flow*

- Users pick an image from the gallery
- The image is uploaded to Supabase
- The download URL is saved in Firestore and displayed in the app



## *📂 Project Structure*



    app/  
    │── ui/             # Jetpack Compose UI components  
    │── data/           # Repositories & Data sources  
    │── model/          # Data models (Recipes, Posts, Users)  
    │── viewmodel/      # State management using ViewModel  
    │── network/        # API & Firebase interactions  


*🛠️ Future Enhancements*  
📝 Commenting system for recipes  
🔔 Push notifications for interactions
🌎 Multi-language support

*🤝 Contributing*

Pull requests are welcome! If you'd like to improve the app, feel free to fork and contribute.

*📜 License*

This project is licensed under the MIT License.

*⚠️ Disclaimer*

Currently, the project does not fully follow the MVVM structure and is in a draft state.

🔥 Happy Cooking & Sharing! 🍽️
