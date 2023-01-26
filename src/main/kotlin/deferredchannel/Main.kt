package deferredchannel

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.system.measureTimeMillis

//  Here we are sending deferred values over channels

fun main() {
    val elements = 10
    val deferredChannel = Channel<Deferred<Int>>(elements)

    GlobalScope.launch {
        repeat(elements) { i ->
            println("$i sent")
            deferredChannel.send(
                async {
                    delay(if (i == 0) 1000 else 10)
                    i
                }
            )
        }
    }

    runBlocking {
        val time = measureTimeMillis {
            repeat(elements) {
                val result = select {
                    deferredChannel.onReceive {
                        select {
                            it.onAwait { it }
                        }
                    }
                }

                println(result)
            }
        }

        println("Took $time ms")
    }
}