package barrier

data class FavoriteCharacter(
    private val name: String,
    private val catchphrase: String,
    private val repeats: Int
) {

    fun introduce() = "I am $name, ${catchphrase.repeat(repeats)}"

}
