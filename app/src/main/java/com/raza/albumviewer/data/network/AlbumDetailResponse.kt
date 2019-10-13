package com.raza.albumviewer.data.network


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raza.albumviewer.data.Image
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.artist.Artist

data class AlbumDetailResponse(
    @SerializedName("album")
    var album: AlbumItem
) {
    @Entity(tableName = "album", indices = [Index(value = ["name","artist"], unique = true)])
//@Entity(tableName = "")
    data class AlbumItem (
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        @SerializedName("artist")
        var artist: String = "", // Cher
        @SerializedName("image")
        var image: List<Image>,
        @SerializedName("listeners")
        var listeners: String, // 394628
        @SerializedName("mbid")
        var mbid: String? = "", // 63b3a8ca-26f2-4e2b-b867-647a6ec2bebd
        @SerializedName("name")
        var name: String = "", // Believe
        @SerializedName("playcount")
        var playcount: String, // 2541230
        @SerializedName("tracks")
        var tracks: Tracks? = null,
        @SerializedName("url")
        var url: String, // https://www.last.fm/music/Cher/Believe
        @SerializedName("wiki")
        var wiki: Wiki? = null
    ) {
        data class Tracks(
            @PrimaryKey(autoGenerate = true)
            var id: Int,
            @SerializedName("track")
            var track: List<Track>
        ) {
            data class Track(
                @PrimaryKey(autoGenerate = true)
                var id: Int,
                @SerializedName("artist")
                var artist: Artist,
                @SerializedName("duration")
                var duration: String, // 310
                @SerializedName("name")
                var name: String, // We All Sleep Alone
                @SerializedName("url")
                var url: String // https://www.last.fm/music/Cher/_/We+All+Sleep+Alone
            )
        }

        data class Wiki(
            @PrimaryKey(autoGenerate = true)
            var id: Int,
            @SerializedName("content")
            var content: String, // Believe is the twenty-third studio album by American  singer-actress Cher, released on November 10, 1998 by Warner Bros. Records. The RIAA certified it Quadruple Platinum on December 23, 1999, recognizing four million shipments in the United States; Worldwide, the album has sold more than 20 million copies, making it the biggest-selling album of her career. In 1999 the album received three Grammy Awards nominations including "Record of the Year", "Best Pop Album" and winning "Best Dance Recording" for the single "Believe".It was released by Warner Bros. Records at the end of 1998. The album was executive produced by Rob Dickens. Upon its debut, critical reception was generally positive. Believe became Cher's most commercially-successful release, reached number one and Top 10 all over the world. In the United States, the album was released on November 10, 1998, and reached number four on the Billboard 200 chart, where it was certified four times platinum.The album featured a change in Cher's music; in addition, Believe presented a vocally stronger Cher and a massive use of vocoder and Auto-Tune. In 1999, the album received 3 Grammy Awards nominations for "Record of the Year", "Best Pop Album" and winning "Best Dance Recording". Throughout 1999 and into 2000 Cher was nominated and winning many awards for the album including a Billboard Music Award for "Female Vocalist of the Year", Lifelong Contribution Awards and a Star on the Walk of Fame shared with former Sonny Bono. The boost in Cher's popularity led to a very successful Do You Believe? Tour.The album was dedicated to Sonny Bono, Cher's former husband who died earlier that year from a skiing accident.Cher also recorded a cover version of "Love Is in the Air" during early sessions for this album. Although never officially released, the song has leaked on file sharing networks.Singles"Believe""Strong Enough""All or Nothing""Dov'è L'Amore" <a href="http://www.last.fm/music/Cher/Believe">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.
            @SerializedName("published")
            var published: String, // 27 Jul 2008, 15:55
            @SerializedName("summary")
            var summary: String // Believe is the twenty-third studio album by American  singer-actress Cher, released on November 10, 1998 by Warner Bros. Records. The RIAA certified it Quadruple Platinum on December 23, 1999, recognizing four million shipments in the United States; Worldwide, the album has sold more than 20 million copies, making it the biggest-selling album of her career. In 1999 the album received three Grammy Awards nominations including "Record of the Year", "Best Pop Album" and winning "Best Dance Recording" for the single "Believe". <a href="http://www.last.fm/music/Cher/Believe">Read more on Last.fm</a>.
        )
    }
}