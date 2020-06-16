package my.rockpilgrim.criminalintent.data

import android.content.Context
import java.util.*

data class Crime(
    var title: String,
    var isSolved: Boolean){

    var mID: UUID = UUID.randomUUID()
        private set
    var date: Date = Date()
    var requiresPolice: Int = NO_POLICE
    var suspect: String? = null
    lateinit var context: Context

    constructor(
        mID: UUID, title: String, date: Date = Date(),
        solved: Boolean
    ) : this(title = title, isSolved = solved) {
        this.mID = mID
        this.date = date
    }

    fun getPhotoFileName() =
        "IMG_${mID.toString()}.jpg"
}

