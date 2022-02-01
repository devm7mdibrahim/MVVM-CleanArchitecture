object AndroidTestDependencies{
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_version}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso_core}"
    const val idling_resource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso_idling_resource}"
    const val test_runner = "androidx.test:runner:${Versions.test_runner}"
    const val test_rules = "androidx.test:rules:${Versions.test_runner}"
    const val text_core_ktx = "androidx.test:core-ktx:${Versions.test_core}"
    const val mockk_android = "io.mockk:mockk-android:${Versions.mockk_version}"
    const val fragment_testing = "androidx.fragment:fragment-testing:${Versions.fragment_version}"
    const val androidx_test_ext = "androidx.test.ext:junit-ktx:${Versions.androidx_test_ext}"
    const val navigation_testing = "androidx.navigation:navigation-testing:${Versions.nav_components}"

    const val instrumentation_runner = "com.codingwithmitch.cleannotes.framework.MockTestRunner"
}