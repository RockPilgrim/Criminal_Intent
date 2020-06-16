package my.rockpilgrim.criminalintent.ui

import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.data.CrimeDbSchema
import my.rockpilgrim.criminalintent.databinding.ListItemCrimeBinding

class CrimeHolder(private val binding: ListItemCrimeBinding) :
    AViewHolder(binding.root) {

    override fun bind(crime: Crime) {
//        Log.d("CrimeHolder", "bind(${adapterPosition})")
        binding.crime = crime

        binding.root.setOnClickListener {
            makeToast(crime.title)
            startActivity(context = binding.root.context, crimeId = crime.mID)
        }
    }
}
