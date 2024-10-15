package eu.tricht.gamesense.events

import eu.tricht.gamesense.model.SongInformation
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson

// Interface definition
interface DataFetcher {
    fun getVolume(): Int
    fun getCurrentSong(): SongInformation?
}

// Data class to hold API response data
data class SongInfoResponse(
    val title: String,
    val artist: String,
    val imageSrc: String,
    val isPaused: Boolean,
    val songDuration: Int,
    val elapsedSeconds: Int,
    val url: String
)

// Function to fetch song info from the API
fun fetchSongInfo(): SongInfoResponse? {
    try {
        val url = URL("http://127.0.0.1:26538/api/v1/song-info")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        
        if (connection.responseCode == 200) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            return Gson().fromJson(response, SongInfoResponse::class.java)
        }
    } catch (e: Exception) {
        e.printStackTrace() // Handle any errors
    }
    return null
}

// Implementation of DataFetcher that fetches song data from the API
class ApiDataFetcher : DataFetcher {

    override fun getCurrentSong(): SongInformation? {
        val songInfoResponse = fetchSongInfo()
        return if (songInfoResponse != null) {
            SongInformation(
                artist = songInfoResponse.artist,
                title = songInfoResponse.title,
                imageSrc = songInfoResponse.imageSrc,
                isPaused = songInfoResponse.isPaused,
                songDuration = songInfoResponse.songDuration,
                elapsedSeconds = songInfoResponse.elapsedSeconds,
                url = songInfoResponse.url
            )
        } else {
            null
        }
    }

    override fun getVolume(): Int {
        // Placeholder logic for getting volume
        return 50  // Example value
    }
}
