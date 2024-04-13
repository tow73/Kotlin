package ddwu.com.mobile.naverretrofittest.data

import com.google.gson.annotations.SerializedName

data class BookRoot (val items: List<Item>)

data class Item (
    var title: String,
    val link: String,
    val image: String,
    val author: String,
    val publisher: String,
)