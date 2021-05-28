package com.decagon.android.sq007

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.decagon.android.sq007.databinding.ActivitySavedContactsBinding

class  SavedContactsActivity : AppCompatActivity() {

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
            .setFragmentResultListener(getString(R.string.contactNameKey), this) { _, bundle ->
                name = bundle.getString(getString(R.string.bundleKey)).toString()
            }
        supportFragmentManager
            .setFragmentResultListener(getString(R.string.contactPhoneKey), this) { _, bundle ->
                phone = bundle.getString(getString(R.string.bundleKey)).toString()
            }
        supportFragmentManager
            .setFragmentResultListener(getString(R.string.contactIdKey), this) { _, bundle ->
                id = bundle.getString(getString(R.string.bundleKey)).toString()
            }
    }
}
