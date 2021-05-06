package com.decagon.android.sq007

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.decagon.android.sq007.databinding.ActivitySavedContactsBinding

class SavedContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySavedContactsBinding

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
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        supportFragmentManager.popBackStack()
//    }
}
