package my.rockpilgrim.criminalintent.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.activity_twopane.*
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.data.Crime

class CrimeListActivity : SingleFragmentActivity(),CrimeListFragment.Callbacks, CrimeFragment.CallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun createFragment() =
        CrimeListFragment()

    override fun onPause() {
        super.onPause()
        Log.d("Cra","OnPause")
    }

    override fun onCrimeSelected(crime: Crime) {
        if (findViewById<FrameLayout>(R.id.detailFragmentContainer) == null) {
            val intent: Intent = CrimePagerActivity().newIntent(this, crimeId = crime.mID)
            startActivity(intent)
        } else {
            val newDetail = CrimeFragment.newInstance(crimeId = crime.mID)
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailFragmentContainer, newDetail)
                .commit()
        }
    }

    override fun onCrimeUpdated(crime: Crime) {
        val listFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as CrimeListFragment
        listFragment.updateUI()
    }
}
