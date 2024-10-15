package eu.tricht.gamesense.model

data class SongInformation(
    val artist: String,
    val title: String,
    val imageSrc: String,
    val isPaused: Boolean,
    val songDuration: Int,
    val elapsedSeconds: Int,
    val url: String
) {
    private val artistText = ScrollingText(artist)
    private val songText = ScrollingText(title)
    
    fun artist() = artistText.text
    fun song() = songText.text
}
