package scheduler

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    // This pattern is built into the language since both launch() and async()
    // are scheduled through a scheduler under the hood
    // Please note that coroutines will not necessarily be completed in the same thread,
    // since an operation can start in a thread, stop, and be picked up by a different thread later on

    val r1 = GlobalScope.async() {
        for (i in 1..1000) {
            println(Thread.currentThread().name)
            yield()
        }
    }

    runBlocking {
        r1.await()
    }

}
