package my.rockpilgrim.criminalintent.utils

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import my.rockpilgrim.criminalintent.data.Crime

class CrimeCallBack:DiffUtil.ItemCallback<Crime>() {

    override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
        return oldItem.mID == newItem.mID
    }

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
        Log.d(
            "CallBack",
            "areContainsTheSame(${oldItem.title == newItem.title /*&& oldItem.isSolved == newItem.isSolved*/})")
        return oldItem.title == newItem.title && oldItem.isSolved == newItem.isSolved
    }
}