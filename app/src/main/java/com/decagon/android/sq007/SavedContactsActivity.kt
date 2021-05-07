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
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Loads SavedContactsFragment on launch of activity
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SavedContactsFragment>(R.id.fragment_container_view)
            }
        }
        // Listens for fragment result in SavedContactsFragment
        supportFragmentManager
            .setFragmentResultListener("contactNameKey", this) { requestKey, bundle ->
                name = bundle.getString("bundleKey").toString()
            }
        supportFragmentManager
            .setFragmentResultListener("contactPhoneKey", this) { requestKey, bundle ->
                phone = bundle.getString("bundleKey").toString()
            }
        supportFragmentManager
            .setFragmentResultListener("contactIdKey", this) { requestKey, bundle ->
                id = bundle.getString("bundleKey").toString()
            }
    }
}
