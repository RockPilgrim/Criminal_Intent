package my.rockpilgrim.criminalintent.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import my.rockpilgrim.criminalintent.R
import my.rockpilgrim.criminalintent.databinding.ActivityFragmentBinding

abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityFragmentBinding>(this, getLayoutResId())

        val fm: FragmentManager = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    @LayoutRes
    private fun getLayoutResId(): Int {
        return R.layout.activity_masterdetail
    }

}