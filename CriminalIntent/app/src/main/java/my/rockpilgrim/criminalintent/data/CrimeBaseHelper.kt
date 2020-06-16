package my.rockpilgrim.criminalintent.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import my.rockpilgrim.criminalintent.data.CrimeDbSchema.CrimeTable


class CrimeBaseHelper(
    context: Context
) : SQLiteOpenHelper(context,"crimeBase.db", null, 1) {

    private val TAG = "CrimeBaseHelper"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreate()")
        db?.execSQL(
            "CREATE TABLE " + CrimeTable.NAME + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CrimeTable.Cols.UUID + "," +
                    CrimeTable.Cols.TITLE + "," +
                    CrimeTable.Cols.DATE + "," +
                    CrimeTable.Cols.SOLVED + "," +
                    CrimeTable.Cols.POLICE + "," +
                    CrimeTable.Cols.SUSPECT + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}