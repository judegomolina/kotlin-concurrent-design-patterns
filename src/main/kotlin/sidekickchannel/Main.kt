package sidekickchannel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() {
    val superman = GlobalScope.actor<String> {
        for (enemy in this) {
            println("Superman is beating $enemy")
            delay(100)
        }
    }

    val supergirl = GlobalScope.actor<String> {
        //  This is the sidekick
        for (enemy in this) {
            println("supergirl is beating $enemy")
            delay(250)
        }
    }

    val job = GlobalScope.launch {
        for (enemy in listOf("Lex Luthor", "a giant meteor", "Atomic Skull", "Bizarro")) {
            val result = select {
                superman.onSend(enemy) {
                    Pair("Superman", enemy)
                }

                supergirl.onSend(enemy) {
                    Pair("Supergirl", enemy)
                }
            }

            println(result)
        }
    }

    runBlocking {
        job.join()
    }
}
