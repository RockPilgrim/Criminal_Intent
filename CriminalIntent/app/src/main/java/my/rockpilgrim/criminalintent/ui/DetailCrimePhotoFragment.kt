package my.rockpilgrim.criminalintent.ui

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.databinding.DialogCrimePhotoBinding

class DetailCrimePhotoFragment : DialogFragment() {

    private val ARG_PHOTO_PATH = "photo_path"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding=DialogCrimePhotoBinding.inflate(LayoutInflater.from(context))
        val path = requireArguments().getString(ARG_PHOTO_PATH)

        binding.crimePhoto.setImageBitmap(BitmapFactory.decodeFile(path))

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.detail_photo_title))
            .setView(binding.root)
            .create()
    }


    fun newInstance(path: String): DetailCrimePhotoFragment {
        val  args=Bundle().apply {
            putString(ARG_PHOTO_PATH, path)
        }
        return DetailCrimePhotoFragment().apply {
            arguments = args
        }
    }

}