package pipelines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

fun producePages() = GlobalScope.produce {
    fun getPages() = listOf("<html><body><H1>Cool stuff</H1></body></html>",
        "<html><body><H1>Event more stuff</H1></body></html>").shuffled()

    while (this.isActive) { // checks if the coroutine is running and hasn't been canceled
        val pages = getPages()
        for (page in pages) {
            send(page)
        }
        delay(5000)
    }
}

fun produceDom(pages: ReceiveChannel<String>) = GlobalScope.produce {
    fun parseDom(page: String): String = page.replace(
        regex = "<[^>]*>".toRegex(),
        replacement = ""
    )

    for (page in pages) {
        send(parseDom(page))
    }
}

fun main() {
    //  The entire pipeline could be stopped by calling cancel() on the first coroutine in line.

    val pagesProducer = producePages()
    val domProducer = produceDom(pagesProducer)

    runBlocking {
        domProducer.consumeEach {
            println(it)
        }
    }
}
