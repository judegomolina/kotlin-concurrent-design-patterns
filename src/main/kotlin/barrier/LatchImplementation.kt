package barrier

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

object LatchImplementation {
    // Not the best solutions. Issues with:
    // - Use of multiple variables and use either nullability or defaults.
    // - Works as long as we use closures


    private var name: String? = null
    private fun getName(latch: CountDownLatch) = GlobalScope.launch {
        delay(Random.nextLong(100))
        println("Got name")
        name = "Pedro Picapiedra"
        latch.countDown()
    }

    private var catchphrase = ""
    private fun getCatchphrase(latch: CountDownLatch) = GlobalScope.launch {
        delay(Random.nextLong(100))
        println("Got catchphrase")
        catchphrase = "Yaba Dabe Doooo "
        latch.countDown()
    }

    private var repeats: Int = 0
    private fun getRepeats(latch: CountDownLatch) = GlobalScope.launch {
        delay(Random.nextLong(100))
        println("Got repeats")
        repeats = 2
        latch.countDown()
    }

    fun getFavoriteCharacter(): FavoriteCharacter {
        val latch = CountDownLatch(3)
        getName(latch)
        getCatchphrase(latch)
        getRepeats(latch)
        latch.await()

        return FavoriteCharacter(
            name = name!!,
            catchphrase = catchphrase,
            repeats = repeats
        )
    }
}
