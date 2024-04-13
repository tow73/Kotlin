package ddwu.com.mobile.fooddbexam_arch

import android.app.Application
import ddwu.com.mobile.fooddbexam_arch.data.FoodDatabase
import ddwu.com.mobile.fooddbexam_arch.data.FoodRepository

class FoodApplication: Application() {
    val foodDatabase by lazy {
        FoodDatabase.getDatabase(this)  //by lazy: 사용이 되는 시점에 초기화(초기화를 늦게 한다/FoodDatabase사용 시점에 초기화)
    }

    val foodRepository by lazy {
        FoodRepository(foodDatabase.foodDao())
    }
}