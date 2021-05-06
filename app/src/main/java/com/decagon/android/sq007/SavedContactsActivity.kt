package com.decagon.android.sq007

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.decagon.android.sq007.databinding.ActivitySavedContactsBinding

class SavedContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySavedContactsBinding
    lateinit var name: String
    lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SavedContactsFragment>(R.id.fragment_container_view)
            }
        }

        supportFragmentManager
            .setFragmentResultListener("contactNameKey", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                name = bundle.getString("bundleKey").toString()
                // Do something with the result
            }

        supportFragmentManager
            .setFragmentResultListener("contactPhoneKey", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                phone = bundle.getString("bundleKey").toString()
                // Do something with the result
            }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        supportFragmentManager.popBackStack()
//    }
}
