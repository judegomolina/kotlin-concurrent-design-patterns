package deferredvalue

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
    // Deferred are usually returned from the async() function in Kotlin,
    // therefore, are hardly implemented by hand as in this example

    val deferred = CompletableDeferred<String>()

    GlobalScope.launch {
        delay(100)
        if(Random.nextBoolean()) {
            deferred.complete("OK")
        } else {
            deferred.completeExceptionally(RuntimeException())
        }
    }

    runBlocking {
        println(deferred.await())
    }
}
