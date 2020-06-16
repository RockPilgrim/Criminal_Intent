package my.rockpilgrim.criminalintent.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_crime_list.*
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.adapters.CrimeAdapter
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.data.CrimeLab
import my.rockpilgrim.criminalintent.databinding.FragmentCrimeListBinding

class CrimeListFragment:Fragment() {

    private val adapter: CrimeAdapter = CrimeAdapter()
    private val TAG = "CrimeListFragment"
    private val SAVED_SUBTITLE_VISIBLE = "subtitle"
    private var subtitleVisibility = false
    private lateinit var data:CrimeLab

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        data = CrimeLab.initCrimeLab(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView()")
        val binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        binding.crimeRecyclerView.adapter = adapter

        if (savedInstanceState != null) {
            subtitleVisibility = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }

        adapter.submitList(data.crimes)
        // TODO: binding size do some think
        binding.size = data.crimes.size
        binding.addCrime.setOnClickListener {
            Log.d(TAG,"Button add new crime")
            addNewCrime()
        }
        return binding.root
    }

    // Setup Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)


        val subtitleItem = menu.findItem(R.id.showSubtitle)

        if (subtitleVisibility) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }
    }

    // Item menu selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.newCrime -> {
                addNewCrime()
                return true
            }
            R.id.showSubtitle ->{
                subtitleVisibility = !subtitleVisibility
                (requireActivity() as AppCompatActivity).invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    private fun addNewCrime() {
        val crime: Crime = Crime("", false)
        data.addCrime(crime)
        startActivity(CrimePagerActivity().newIntent(packageContext = requireContext(), crimeId = crime.mID))
    }

    private fun updateSubtitle() {
        val crimeCount = data.crimes.size
        var subtitle =
            resources.getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount)
//        var subtitle = getString(R.string.subtitle_format, crimeCount)

        if (!subtitleVisibility) {
            subtitle = ""
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = subtitle
    }

    override fun onResume() {
        Log.d(TAG, "onResume()")
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        adapter.submitList(CrimeLab.initCrimeLab(requireContext()).crimes)
        adapter.notifyDataSetChanged()
        updateSubtitle()
        if (CrimeLab.crimes.size > 0) {
            addCrime.visibility = View.GONE
        } else {
            addCrime.visibility = View.VISIBLE
        }
//        adapter.submitList(CrimeLab.crimes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisibility)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy()")
    }

}