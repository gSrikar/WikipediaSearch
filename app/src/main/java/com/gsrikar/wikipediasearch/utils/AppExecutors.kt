package com.gsrikar.wikipediasearch.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Global executor pools for the whole application.
 */
open class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val sleepIO: Executor,
    private val mainThread: Executor
) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        Executors.newSingleThreadExecutor(),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun sleepThread(): Executor {
        return sleepIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
