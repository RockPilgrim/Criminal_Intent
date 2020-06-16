package my.rockpilgrim.criminalintent.ui

import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.databinding.ListItemCrimePoliseBinding

class CrimeHolderPolice(private val binding: ListItemCrimePoliseBinding) :
    AViewHolder(binding.root) {

    override fun bind(crime: Crime) {
        binding.crime = crime

        // Call Police
        binding.policeButton.setOnClickListener {
            makeToast(binding.root.context.getString(R.string.police))
        }

        // Show Detail View
        binding.root.setOnClickListener {
            makeToast(crime.title)
            startActivity(context = binding.root.context, crimeId = crime.mID)
        }
    }
}