package activeobject

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun activeActor(out: SendChannel<String>) = GlobalScope.actor<Int>() {
    for (i in this) {
        out.send(i.toString().reversed())
    }
    out.close()
}

fun main() {
    // This pattern is already built into the language through the Actor

    val channel = Channel<String>()
    val actor = activeActor(channel)

    val job1 = GlobalScope.launch {
        for (i in 42..53) {
            actor.send(i)
        }
    }

    val job2 = GlobalScope.launch {
        for (i in channel) {
            println(i)
        }
    }

    runBlocking {
        job1.join()
        job2.join()
    }
}
