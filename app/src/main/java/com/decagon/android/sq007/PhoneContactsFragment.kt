package com.decagon.android.sq007

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.FragmentPhoneContactsBinding
import java.util.ArrayList

class PhoneContactsFragment : Fragment(R.layout.fragment_phone_contacts) {

    private var binding: FragmentPhoneContactsBinding? = null

    var recyclerView: RecyclerView? = null
    var arrayList = ArrayList<ContactModel>()
    var adapter: PhoneContactsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhoneContactsBinding.bind(view)

        recyclerView = binding!!.recyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.setHasFixedSize(true)
        arrayList = arrayListOf()

        checkPermission()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_phone_contacts)
//        recyclerView = findViewById(R.id.recycler_view)
//        checkPermission()
//    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
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
            val cursor = this.requireActivity().contentResolver.query(
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
                    val phoneCursor = this.requireActivity().contentResolver.query(
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
            recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
