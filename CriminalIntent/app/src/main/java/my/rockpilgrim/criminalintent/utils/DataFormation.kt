package my.rockpilgrim.criminalintent.utils

import java.text.SimpleDateFormat
import java.util.*

object DataFormation {

    fun getDate(date: Date) =
        SimpleDateFormat("E dd MMM y G", Locale.getDefault()).format(date)

}