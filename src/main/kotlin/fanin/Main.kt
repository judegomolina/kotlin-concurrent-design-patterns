package fanin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun maleTennisResults(collector: Channel<String>) = GlobalScope.launch {
    repeat(10) {
        delay(Random.nextLong(1000))
        collector.send("Federer wins!")
    }
}

fun femaleTennisResults(collector: Channel<String>) = GlobalScope.launch {
    repeat(10) {
        delay(Random.nextLong(1000))
        collector.send("Sharapova wins!")
    }
}

fun main() {
    val resultsCollector = Channel<String>()

    maleTennisResults(resultsCollector)
    femaleTennisResults(resultsCollector)

    runBlocking {
        resultsCollector.consumeEach {
            println(it)
        }
    }
}