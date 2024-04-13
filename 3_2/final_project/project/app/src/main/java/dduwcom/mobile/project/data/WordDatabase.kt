package dduwcom.mobile.project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordDto::class], version=1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao() : WordDao

    companion object {
        @Volatile       // Main memory 에 저장한 값 사용
        private var INSTANCE : WordDatabase? = null

        // INSTANCE 가 null 이 아니면 반환, null 이면 생성하여 반환
        fun getDatabase(context: Context) : WordDatabase {
            return INSTANCE ?: synchronized(this) {     // 단일 스레드만 접근
                val instance = Room.databaseBuilder(context.applicationContext,
                    WordDatabase::class.java, "word_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}