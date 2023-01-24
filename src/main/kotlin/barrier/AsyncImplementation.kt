package barrier

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object AsyncImplementation {
    // Preferred implementation

    private fun getName() = GlobalScope.async {
        val delayTime = Random.nextLong(100)
        delay(delayTime)
        println("Got name with a delay of $delayTime")
        "Pedro Picapiedra"
    }

    private fun getCatchphrase() = GlobalScope.async {
        val delayTime = Random.nextLong(100)
        delay(delayTime)
        println("Got catchphrase with a delay of $delayTime")
        "Yaba Dabe Doooo "
    }

    private fun getRepeats() = GlobalScope.async {
        val delayTime = Random.nextLong(100)
        delay(delayTime)
        println("Got repeats with a delay of $delayTime")
        2
    }

    fun getFavoriteCharacter(): FavoriteCharacter {
        val character: FavoriteCharacter

        runBlocking {
            val name = getName()
            val catchphrase = getCatchphrase()
            val repeats = getRepeats()

            character = FavoriteCharacter(
                name = name.await(),
                catchphrase = catchphrase.await(),
                repeats = repeats.await()
            )
        }

        return character
    }
}
