plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.devmo.mohelperlib"
    compileSdk = 33
    buildFeatures {
        viewBinding =true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    defaultConfig {
        minSdk = 30

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

    /*********************|retrofit|*********************/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    /*********************|Get Image via url|*********************/
    implementation("com.github.bumptech.glide:glide:4.16.0")

    /*********************|generate excl file|*********************/
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    /*********************|generate pdf file|*********************/
    implementation("com.itextpdf:itextpdf:5.5.13.2")
    implementation("androidx.print:print:1.0.0")
}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.mohammadalmomanii"
            artifactId = "MoHelper"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

