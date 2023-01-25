package fanout

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// The fan-out design pattern allows us to efficiently
// distribute the work across a number of coroutines, threads, and CPUs.

fun producePages() = GlobalScope.produce {
    for (i in 1..10_000) {
        for (c in 'a'..'z') {
            send(i to "page $c")
        }
    }
}

fun consumePages(channel: ReceiveChannel<Pair<Int, String>>) = GlobalScope.async {
    for (p in channel) {
        println(p)
    }
}


fun main() {
    val producer = producePages()

    val consumers = List(10) {
        consumePages(producer)
    } // We can have N consumers for the same producer

    runBlocking {
        consumers.forEach {
            it.await()
        }
    }

}
