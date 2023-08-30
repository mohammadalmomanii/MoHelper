plugins {
    id("com.android.library")
}

android {
    namespace = "com.devmo.mohelperlib"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //implementation(project(":MoHelperLib"))
    implementation("com.squareup.retrofit2:retrofit:2.9.0")                      //retrofit implementation
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")            //retrofit converter implementation
    implementation("com.github.bumptech.glide:glide:4.13.0")              //get image from database && add gif image
//    implementation 'com.github.zerobranch:SwipeLayout:1.3.1'                    //swipe in recyclerView


    implementation("org.apache.poi:poi:5.1.0")                        //excel generator
    implementation("org.apache.poi:poi-ooxml:5.1.0")                        //excel generator


    implementation("com.itextpdf:itextpdf:5.5.13.2")                         //PDF
    implementation("androidx.print:print:1.0.0")                           //PDF

}