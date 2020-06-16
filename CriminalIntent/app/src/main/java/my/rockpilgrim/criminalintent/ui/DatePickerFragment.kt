package my.rockpilgrim.criminalintent.ui

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.databinding.DialogDateBinding
import java.util.*

class DatePickerFragment() : DialogFragment() {

//    lateinit var date: Date

    private val ARG_DATE = "date"
    val EXTRA_DATE = "my.rockpilgrim.criminalintent.date"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date: Date = requireArguments().getSerializable(ARG_DATE) as Date
        val calendar= Calendar.getInstance().apply {
            time = date
        }
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day:Int =  calendar.get(Calendar.DAY_OF_MONTH)

        val binding = DialogDateBinding.inflate(LayoutInflater.from(context))
        binding.dialogDatePicker.init(year, month, day, null)

        return AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.date_picker_title))
            .setView(binding.root)
            .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                val _year: Int=binding.dialogDatePicker.year
                val _month =binding.dialogDatePicker.month
                val _day:Int =binding.dialogDatePicker.dayOfMonth

                sendResult(Activity.RESULT_OK, GregorianCalendar(_year, _month, _day).time)
            })
            .create()
    }

    fun newInstance(date: Date):DialogFragment {
        val args= Bundle().apply {
            putSerializable(ARG_DATE, date)
        }
        return DatePickerFragment().apply {
            arguments = args
        }
    }

    private fun sendResult(resultCod: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent().putExtra(EXTRA_DATE, date)
        targetFragment!!.onActivityResult(targetRequestCode, resultCod, intent)
    }

}