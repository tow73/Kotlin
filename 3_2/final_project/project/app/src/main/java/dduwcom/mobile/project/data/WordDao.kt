package dduwcom.mobile.project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table")
    fun getAllWords() : Flow<List<WordDto>>

    @Query("SELECT * FROM word_table WHERE word = :word")
    suspend fun getMeaningByWord(word: String) : List<WordDto>

    @Insert
    suspend fun insertWord(memo: WordDto)

    @Update
    suspend fun updateWord(memo: WordDto)

    @Delete
    suspend fun deleteWord(memo: WordDto)
}