package dduwcom.mobile.project.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "word_table")
data class WordDto(
    @PrimaryKey (autoGenerate = true)
    val id: Long,
    var word: String,
    var meaning: String,
    var date: String,
    var latitude: Long,
    var longitude: Long,
    var memo: String) : Serializable {
    override fun toString(): String {
        return "${id} (${word} = ${meaning}) : $memo - ${date}"
    }
}
