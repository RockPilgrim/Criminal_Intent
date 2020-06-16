package my.rockpilgrim.criminalintent.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import my.rockpilgrim.criminalintent.data.CrimeDbSchema.CrimeTable
import java.io.File
import java.util.*

object CrimeLab {


//    val crimes: List<Crime> = listOf()
    val crimes: List<Crime>
    get() {
        Log.d(TAG,"getCrimes()")
        val cursor = queryCrimes(null, arrayOf())
        val mCrimes = mutableListOf<Crime>()
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                mCrimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        }finally {
            cursor.close()
        }
        return mCrimes
    }
    private val TAG = "CrimeLab"
    private lateinit var mContext: Context
    private lateinit var dataBase: SQLiteDatabase

    init {
        Log.d(TAG, "init()")
    }

    fun initCrimeLab(context: Context): CrimeLab {
        Log.d(TAG,"initCrimeLab()")
        this.mContext = context.applicationContext
        dataBase = CrimeBaseHelper(mContext).writableDatabase
        return this
    }

    fun addCrime(crime: Crime) {
        dataBase.insert(CrimeTable.NAME, null, getContentValues(crime))
    }

    fun getCrime(id: UUID): Crime? {
        val cursor: CrimeCursorWrapper = queryCrimes(
            CrimeTable.Cols.UUID + " =?",
            arrayOf(id.toString()))

        try {
            if (cursor.count == 0) {
                return null
            }
            cursor.moveToFirst()
            return cursor.getCrime()
        }finally {
            cursor.close()
        }
    }

    fun updateCrime(crime: Crime) {
        dataBase.update(
            CrimeTable.NAME, getContentValues(crime),
            CrimeTable.Cols.UUID + " =?",
            arrayOf(crime.mID.toString()))
    }

    fun findPositionById(crimeId: UUID):Int {
        return crimes.indexOf(crimes.first { crime: Crime -> crime.mID == crimeId })
    }

    fun getPhotoFile(crime: Crime): File {
        val filesDir = mContext.filesDir
        return File(filesDir, crime.getPhotoFileName())
    }

    fun delete(crime: Crime) {
        dataBase.delete(
            CrimeTable.NAME,
            CrimeTable.Cols.UUID + " =?",
            arrayOf(crime.mID.toString()))
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>): CrimeCursorWrapper {
        return CrimeCursorWrapper(dataBase.query(
            CrimeTable.NAME,
            null,// all columns
            whereClause,
            whereArgs,
            null, null, null))
    }

    private fun getContentValues(crime: Crime): ContentValues {
        return ContentValues().apply {
            put(CrimeTable.Cols.UUID, crime.mID.toString())
            put(CrimeTable.Cols.TITLE, crime.title)
            put(CrimeTable.Cols.DATE, crime.date.time)
            if (crime.isSolved) put(CrimeTable.Cols.SOLVED, 1) else {
                put(CrimeTable.Cols.SOLVED, 0)
            }
            put(CrimeTable.Cols.POLICE, crime.requiresPolice)
//            put(CrimeTable.Cols.SUSPECT, crime.suspect)
        }
    }


}

