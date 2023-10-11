plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.dishdiary"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.dishdiary"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Room
    implementation ("androidx.room:room-runtime:2.5.2")
    annotationProcessor ( "androidx.room:room-compiler:2.5.2")

    //Retrofit
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation  ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation  ("com.squareup.retrofit2:converter-gson:2.9.0")

    //GLIDE For Image Processing
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor( "com.github.bumptech.glide:compiler:4.14.2")

    //Lotti animation
    implementation ("com.airbnb.android:lottie:6.1.0")


    //RX java and android
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
// optional - RxJava3 support for Retrofit
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
// optional - RxJava3 support for Room
    implementation ("androidx.room:room-rxjava3:2.5.2")


    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment:2.5.3")
    implementation ("androidx.navigation:navigation-ui:2.5.3")

    //video player for youtube
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")


    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
     // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-firestore")

    //apply shimmer layout
    implementation ("com.facebook.shimmer:shimmer:0.5.0");



}