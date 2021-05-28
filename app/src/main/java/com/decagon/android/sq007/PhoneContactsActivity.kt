package com.decagon.android.sq007

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.decagon.android.sq007.databinding.ActivityPhoneContactsBinding
import java.util.*

class PhoneContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhoneContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<PhoneContactsFragment>(R.id.fragment_container_view)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Launches savedContactsActivity when menu item is selected
        if (item.itemId == R.id.saved_contacts_menu) {
            val intent = Intent(this, SavedContactsActivity::class.java)
            startActivity(intent)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }
}
