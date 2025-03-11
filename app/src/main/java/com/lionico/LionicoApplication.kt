// Application class for Hilt dependency injection
package com.lionico

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class required by Hilt.
 */
@HiltAndroidApp
class LionicoApplication : Application()
