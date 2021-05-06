package com.decagon.android.sq007

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class PhoneContactsActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var arrayList = ArrayList<ContactModel>()
    var adapter: PhoneContactsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_contacts)
        recyclerView = findViewById(R.id.recycler_view)
        checkPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.local_contacts_menu) {
            val intent = Intent(this, SavedContactsActivity::class.java)
            startActivity(intent)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this@PhoneContactsActivity, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@PhoneContactsActivity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                100
            )
        } else {
            contactList
        }
    }

    private val contactList: Unit
        get() {
            val uri = ContactsContract.Contacts.CONTENT_URI
            val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            val cursor = contentResolver.query(
                uri, null, null, null, sort
            )
            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.Contacts._ID
                        )
                    )
                    val name = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    val uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val selection = (
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
                            " =?"
                        )
                    val phoneCursor = contentResolver.query(
                        uriPhone, null, selection, arrayOf(id), null
                    )
                    if (phoneCursor!!.moveToNext()) {
                        val number = phoneCursor.getString(
                            phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        val model = ContactModel(id, name, number)

                        arrayList.add(model)
                        phoneCursor.close()
                    }
                }
                cursor.close()
            }
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            adapter = PhoneContactsAdapter(arrayList)
            recyclerView!!.adapter = adapter
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && (
            grantResults[0]
                == PackageManager.PERMISSION_GRANTED
            )
        ) {
            contactList
        } else {
//            Toast.makeText(this@PhoneContactsActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }
}
