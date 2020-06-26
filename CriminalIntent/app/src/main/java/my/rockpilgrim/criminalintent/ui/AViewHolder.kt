package my.rockpilgrim.criminalintent.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.rockpilgrim.criminalintent.data.Crime
import java.util.*

abstract class AViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Page 363
    abstract fun bind(crime: Crime)

    protected fun makeToast(message: String) {
        Toast.makeText(itemView.context,message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER_VERTICAL, 0, 150)
            show()
        }
    }

    protected fun startActivity(context: Context, crime: Crime) {
        (context as CrimeListFragment.Callbacks).onCrimeSelected(crime = crime)
    }
}