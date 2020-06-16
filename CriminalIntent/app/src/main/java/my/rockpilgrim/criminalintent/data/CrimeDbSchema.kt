package my.rockpilgrim.criminalintent.data

import java.util.*

class CrimeDbSchema {
    object CrimeTable {
        const val NAME: String = "crimes"

        object Cols {
            const val UUID: String = "uuid"
            const val TITLE: String = "title"
            const val DATE: String = "date"
            const val SOLVED: String = "solved"
            const val POLICE: String = "policeRequest"
            const val SUSPECT: String = "suspect"


        }

    }
}