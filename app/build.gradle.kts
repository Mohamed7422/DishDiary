plugins {
    id("com.android.application")
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

    //RX Java
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")

    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment:2.5.3")
    implementation ("androidx.navigation:navigation-ui:2.5.3")

    //video player for youtube
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")


}