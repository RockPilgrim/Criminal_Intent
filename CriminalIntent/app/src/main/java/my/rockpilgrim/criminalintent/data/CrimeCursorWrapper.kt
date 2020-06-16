package my.rockpilgrim.criminalintent.data

import android.database.Cursor
import android.database.CursorWrapper
import my.rockpilgrim.criminalintent.data.CrimeDbSchema.CrimeTable.Cols
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime(): Crime {
        val uuidString = getString(getColumnIndex(Cols.UUID))
        val title=getString(getColumnIndex(Cols.TITLE))
        val date=getLong(getColumnIndex(Cols.DATE))
        val solved=getInt(getColumnIndex(Cols.SOLVED))
        val police=getInt(getColumnIndex(Cols.POLICE))
        val suspect=getString(getColumnIndex(Cols.SUSPECT))
        return Crime(UUID.fromString(uuidString),title,Date(date),solved!=0).apply {
            requiresPolice = police
            this.suspect=suspect
        }
    }

}