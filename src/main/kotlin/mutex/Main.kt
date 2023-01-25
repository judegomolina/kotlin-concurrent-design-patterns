package mutex

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.yield

// Also known as mutual exclusions, mutexes provide a means to protect a shared state

fun main() {
    var counter = 0
    val mutex = Mutex()  //  Built into the language

    val jobs = List(10) {
        GlobalScope.launch {
            repeat(1000) {
                mutex.withLock {
                    counter++
                }
                yield()
            }
        }
    }

    runBlocking {
        jobs.forEach {
            it.join()
        }
        println(counter)
    }
}