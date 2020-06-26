package my.rockpilgrim.criminalintent.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.rockpilgrim.criminalintent.AdapterDismissElement
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.data.NO_POLICE
import my.rockpilgrim.criminalintent.databinding.ListItemCrimeBinding
import my.rockpilgrim.criminalintent.databinding.ListItemCrimePoliseBinding
import my.rockpilgrim.criminalintent.ui.AViewHolder
import my.rockpilgrim.criminalintent.ui.CrimeHolder
import my.rockpilgrim.criminalintent.ui.CrimeHolderPolice
import my.rockpilgrim.criminalintent.utils.CrimeCallBack


class CrimeAdapter: ListAdapter<Crime,AViewHolder>(CrimeCallBack()) {

    private val TAG = "ListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AViewHolder {
        if (viewType == NO_POLICE) {
            return CrimeHolder(
                ListItemCrimeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            return CrimeHolderPolice(
                ListItemCrimePoliseBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: AViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).requiresPolice
    }
}