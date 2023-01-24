package barrier

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    // Allows waiting for multiple concurrent tasks before proceeding further

//    println("Latch implementation")
//    val latchCharacter = LatchImplementation.getFavoriteCharacter()
//    println(latchCharacter.introduce())


        println("Async implementation")
        println(
                measureTimeMillis {
                        val asyncCharacter = AsyncImplementation.getFavoriteCharacter()
                        println(asyncCharacter.introduce())
                }
        )

}
