package my.rockpilgrim.criminalintent.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.adapters.ViewPagerFragmentAdapter
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.data.CrimeLab
import my.rockpilgrim.criminalintent.databinding.ActivityCrimePagerBinding
import java.util.*

class CrimePagerActivity:AppCompatActivity() {

    private val TAG = "CrimePagerActivity"
    private val EXTRA_CRIME_ID = "my.rockpilgrim.crimiinalintent.crime_id"

    private lateinit var crimes: List<Crime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate()")
        val binding = DataBindingUtil.setContentView<ActivityCrimePagerBinding>(
            this,
            R.layout.activity_crime_pager)

        crimes = CrimeLab.initCrimeLab(this).crimes
        initPager(binding)
    }

    private fun initPager(binding: ActivityCrimePagerBinding) {
        binding.crimeViewPager.apply {
            adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle,crimes)
            currentItem = CrimeLab.initCrimeLab(context).findPositionById(crimeId = getExtraId())
        }
    }

    private fun getExtraId() =
        intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID


    fun newIntent(packageContext: Context, crimeId: UUID): Intent {
        intent = Intent(packageContext, CrimePagerActivity::class.java)
        intent.putExtra(EXTRA_CRIME_ID, crimeId)
        return intent
    }



}