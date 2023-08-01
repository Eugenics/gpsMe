buildscript{
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.22")
    }
}
plugins {
<<<<<<< HEAD
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
=======
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
>>>>>>> 009acf6 (Initial commit)
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
}