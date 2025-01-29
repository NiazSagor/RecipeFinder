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


- Kotlin - Primary language for Android development
- Jetpack Compose - Modern UI toolkit for building UI declaratively
- StateFlow - Manage UI state reactively
- Coil - Image loading and caching
- DataStore - Efficient data persistence for storing user
  preferences and bookmarked recipes


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
