package my.rockpilgrim.criminalintent.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.ui.CrimeFragment
import java.util.*

class ViewPagerFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val crimes: List<Crime>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int =
        crimes.size

    override fun createFragment(position: Int): Fragment {
        val crime:Crime = crimes[position]
        return CrimeFragment.newInstance(crimeId = crime.mID)
    }
}