package my.rockpilgrim.criminalintent.ui

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_fragment.*

class CrimeListActivity : SingleFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun createFragment() =
        CrimeListFragment()

    override fun onPause() {
        super.onPause()
        Log.d("Cra","OnPause")
    }
}
