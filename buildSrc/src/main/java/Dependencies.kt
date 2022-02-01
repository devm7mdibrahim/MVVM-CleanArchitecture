object Dependencies {
    const val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"


    const val coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
    const val coroutine_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
    const val coroutine_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    const val kotlin_coroutines_play_services =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"


    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"

    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_components}"
    const val navigation_runtime = "androidx.navigation:navigation-runtime:${Versions.nav_components}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_components}"
    const val navigation_dynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_components}"


    const val material_dialogs = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
    const val material_dialogs_input =
        "com.afollestad.material-dialogs:input:${Versions.material_dialogs}"
    const val room_runtime = "androidx.room:room-runtime:2.3.0"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val datastore = "androidx.datastore:datastore-core:1.0.0-beta01"
    const val datastore_preference = "androidx.datastore:datastore-preferences:1.0.0-beta01"
    const val play_core = "com.google.android.play:core:${Versions.play_core}"
    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"


    const val firebase_core = "com.google.firebase:firebase-core:${Versions.firebase_core}"
    const val firebase_auth = "com.google.android.gms:play-services-auth:20.0.1"
    const val firebase_analytics =
        "com.google.firebase:firebase-analytics-ktx:${Versions.firebase_analytics}"
    const val firebase_crashlytics =
        "com.google.firebase:firebase-crashlytics:${Versions.firebase_crashlytics}"
    const val firebase_messaging = "com.google.firebase:firebase-messaging:${Versions.firebase_messaging}"
    const val firebase_iid = "com.google.firebase:firebase-iid:21.1.0"


    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:2.2.0-alpha03"
    const val lifecycle_coroutines =
        "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-alpha03"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.6.0"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2_version}"
    const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:1.12.0"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
    const val markdown_processor = "com.yydcdut:markdown-processor:${Versions.markdown_processor}"
    const val logging_interceptor =
        "com.github.ihsanbal:LoggingInterceptor:3.0.0"


    const val rxjava2 = "io.reactivex.rxjava3:rxjava:${Versions.rxjava_version}"
    const val rxjavaAndroid2 = "io.reactivex.rxjava3:rxandroid:${Versions.rxjavaAndroid_version}"
    const val reactivestreams =
        "android.arch.lifecycle:reactivestreams:${Versions.reactivestreams_version}"
    const val rxjava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxjava2adapter_version}"
    const val circleImage = "de.hdodenhof:circleimageview:3.1.0"

    //maps
    const val android_service_map = "com.google.android.gms:play-services-maps:17.0.0"
    const val service_map = "com.google.maps:google-maps-services:0.1.20"
    const val service_location =  "com.google.android.gms:play-services-location:18.0.0"
    const val service_places =  "com.google.android.gms:play-services-places:17.0.0"
    const val google_places =  "com.google.android.libraries.places:places:2.5.0"

    const val gson = "com.google.code.gson:gson:2.8.7"

    const val indicator = "com.tbuonomo:dotsindicator:4.2"
    const val circle_indicator = "me.relex:circleindicator:2.1.6"
}
