package my.rockpilgrim.criminalintent.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.fragment_crime.view.*
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.data.Crime
import my.rockpilgrim.criminalintent.data.CrimeLab
import my.rockpilgrim.criminalintent.data.NO_POLICE
import my.rockpilgrim.criminalintent.data.POLICE
import my.rockpilgrim.criminalintent.databinding.FragmentCrimeBinding
import my.rockpilgrim.criminalintent.utils.DataFormation
import my.rockpilgrim.criminalintent.utils.PictureUtils
import java.io.File
import java.util.*

class CrimeFragment private constructor() : Fragment() {

    private lateinit var crime: Crime
    private lateinit var photoFile: File
    private lateinit var root: View
    private var callbacks: CallBacks? = null

    companion object {
        private const val TAG = "CrimeFragment"
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const val DIALOG_PHOTO = "DialogPhoto"
        private const val REQUEST_DATE = 0
        private const val REQUEST_CONTACT = 1
        private const val REQUEST_PHOTO = 2

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args:Bundle=Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
    public interface CallBacks{
        fun onCrimeUpdated(crime: Crime)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as CallBacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        val crimeId = requireArguments().getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeLab.initCrimeLab(requireContext()).getCrime(crimeId)!!
        photoFile = CrimeLab.initCrimeLab(requireContext()).getPhotoFile(crime)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCrimeBinding.inflate(inflater, container, false)
        root = binding.root
        binding.crime = crime
        initUI(binding)

        return root
    }

    // Init User Interface components
    private fun initUI(binding: FragmentCrimeBinding) {
        // EditText
        binding.crimeTitle.addTextChangedListener(onTextChanged = {
            text, start, count, after ->
            crime.title = text.toString()
            updateCrime()
        })


        // Button Date
        binding.crimeDateButton.setOnClickListener {
            val dialog = DatePickerFragment().newInstance(crime.date)
            dialog.setTargetFragment(this, REQUEST_DATE)
            // Don't work with .apply
            dialog.show(parentFragmentManager, DIALOG_DATE)
        }

        // CheckBox solved
        binding.crimeSolved.setOnCheckedChangeListener { buttonView, isChecked ->
            crime.isSolved = isChecked
            updateCrime()
        }
        binding.crimePolice.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                crime.requiresPolice = POLICE
            } else {
                crime.requiresPolice = NO_POLICE
            }
            updateCrime()
        }

        // Delete
        binding.deleteButton.setOnClickListener {
            CrimeLab.initCrimeLab(requireContext()).delete(crime)
            // ToDO change this!!! some how
            parentFragmentManager.popBackStack()
//            requireActivity().finish()
        }

        // Send report
        binding.crimeReport.setOnClickListener {
            val intent =ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setText(getCrimeReport())
                .setSubject(getString(R.string.crime_report_subject))
                .createChooserIntent()
            startActivity(intent)
        }

        // Get contact
        val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        val packageManager = requireActivity().packageManager
        // Check is phone has contact app
        if (packageManager.resolveActivity(
                pickContact,
                PackageManager.MATCH_DEFAULT_ONLY) == null) {
            binding.crimeSuspect.isEnabled = false
        }
        binding.crimeSuspect.setOnClickListener {
            startActivityForResult(pickContact, REQUEST_CONTACT)
        }
        if (crime.suspect != null) {
            binding.crimeSuspect.text = crime.suspect
        }

        // Get Photo
        val captureImage: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val canTakePhoto: Boolean =
            photoFile != null && captureImage.resolveActivity(packageManager) != null
        binding.crimeCameraButton.isEnabled = canTakePhoto
        binding.crimeCameraButton.setOnClickListener {

            val uri: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "my.rockpilgrim.criminalintent.fileprovider",
                photoFile)
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            val cameraActivities = requireActivity().packageManager.queryIntentActivities(
                captureImage,
                PackageManager.MATCH_DEFAULT_ONLY)
            cameraActivities.forEach { activity: ResolveInfo ->
                requireActivity().grantUriPermission(
                    activity.activityInfo.packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            startActivityForResult(captureImage, REQUEST_PHOTO)
        }
        updatePhotoView()

        // Show picture
        binding.crimePhoto.setOnClickListener {
            val dialog = DetailCrimePhotoFragment().newInstance(photoFile.path)
            // Don't work with .apply
            dialog.show(parentFragmentManager, DIALOG_PHOTO)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_DATE) {
            Log.d(TAG,"onActivityResult(OK)")
            val date = data!!.getSerializableExtra(DatePickerFragment().EXTRA_DATE) as Date
            crime.date = date
            updateDate()
            updateCrime()
        }else if (requestCode == REQUEST_CONTACT && data != null) {
            val contactURI: Uri = data.data!!

            val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

            val c: Cursor =
                requireActivity().contentResolver.query(contactURI, queryFields, null, null)!!

            c.use {
                if (it.count == 0) {
                    return
                }
                it.moveToFirst()
                val suspect = it.getString(0)
                crime.suspect = suspect
                crimeSuspect.text = suspect
                updateCrime()
            }
        }else if (requestCode == REQUEST_PHOTO) {
            val uri: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "my.rockpilgrim.criminalintent.fileprovider",
                photoFile)

            requireActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            updatePhotoView()
            updateCrime()
        }
    }

    private fun updateDate() {
        crimeDateButton.text = DataFormation.getDate(crime.date)
    }

    private fun updateCrime() {
        CrimeLab.initCrimeLab(requireActivity()).updateCrime(crime = crime)
        callbacks?.onCrimeUpdated(crime)
    }

    private fun getCrimeReport(): String {
        var solvedString: String? = null
        if (crime.isSolved) {
            solvedString = getString(R.string.crime_report_solved)
        } else {
            solvedString = getString(R.string.crime_report_unsolved)
        }
        val dateString = DataFormation.getDate(crime.date)

        var suspect = crime.suspect
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect)
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect)
        }
        return getString(R.string.crime_report,
            crime.title, dateString, solvedString, suspect)
    }

    private fun updatePhotoView() {
        if (photoFile == null || !photoFile.exists()) {
            root.crimePhoto.setImageDrawable(null)
        } else {
            val bitmap: Bitmap = PictureUtils.getScaledBitmap(photoFile.path, requireActivity())
            root.crimePhoto.setImageBitmap(bitmap)
        }
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.initCrimeLab(requireContext()).updateCrime(crime)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}