package my.rockpilgrim.criminalintent.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import my.rockpilgrim.criminalintent.utils.DataFormation
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:dateFormat")
fun setDate(textView: TextView, date: Date?) {
    if (date != null) {
        textView.text = DataFormation.getDate(date)
    }
}