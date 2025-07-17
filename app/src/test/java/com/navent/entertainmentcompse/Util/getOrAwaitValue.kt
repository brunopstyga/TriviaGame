package com.navent.entertainmentcompse.Util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Extension function for LiveData used in unit testing.
 *
 * This function observes the LiveData until a value is emitted or a timeout occurs,
 * allowing tests to wait for LiveData values in a synchronous manner.
 *
 * This is particularly useful for writing unit tests where you need to assert the value of LiveData
 * after a coroutine or asynchronous operation has updated it.
 *
 * @param time The maximum time to wait for LiveData to emit a value (default is 2 seconds).
 * @param timeUnit The unit of time to use for the timeout (default is TimeUnit.SECONDS).
 *
 * @throws TimeoutException if the LiveData value is not set within the timeout period.
 *
 * @return The value emitted by the LiveData.
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null

    // Used to block the test thread until the value is set
    val latch = CountDownLatch(1)

    // Create an observer that captures the data and removes itself once value is received
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    // Observe the LiveData forever (only for test purposes, removed manually)
    this.observeForever(observer)

    // Wait for the value to be set or timeout
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

