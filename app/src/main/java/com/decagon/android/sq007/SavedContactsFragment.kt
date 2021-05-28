package com.decagon.android.sq007

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.FragmentSavedContactsBinding
import com.google.firebase.database.*
import java.util.ArrayList

class SavedContactsFragment : Fragment(R.layout.fragment_saved_contacts),
    SavedContactsAdapter.OnSavedContactsListener {

    private var binding: FragmentSavedContactsBinding? = null

    var recyclerView: RecyclerView? = null
    lateinit var arrayList: ArrayList<ContactModel>
    lateinit var savedContactsAdapter: SavedContactsAdapter

    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arrayList = arrayListOf()
        savedContactsAdapter = SavedContactsAdapter(arrayList, this@SavedContactsFragment)


        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedContactsBinding.bind(view)

        recyclerView = binding!!.recyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = savedContactsAdapter


        getContactData()

        binding?.addContactButton?.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddContactFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
    }

    // Retrieves contact from database
    private fun getContactData() {
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("contacts")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val contact = userSnapshot.getValue(ContactModel::class.java)
                        arrayList.add(contact!!)
                        savedContactsAdapter.addContacts(arrayList)
                        savedContactsAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // Cleans up references to views that have a tendency to cause memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // overrides method from OnSavedContactsListener interface in SavedContactsAdapter
    override fun onContactClick(position: Int) {
        val name = arrayList[position].fullName
        val phone = arrayList[position].contactNumber
        val id = arrayList[position].id

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ContactDetailsFragment>(R.id.fragment_container_view)
            addToBackStack(null)
        }

        // Set result for ContactDetailsFragment
        setFragmentResult(
            getString(R.string.nameRequestKey),
            bundleOf(getString(R.string.bundleKey) to name)
        )
        setFragmentResult(
            getString(R.string.phoneNumberRequestKey),
            bundleOf(getString(R.string.bundleKey) to phone)
        )
        setFragmentResult(
            getString(R.string.idRequestKey),
            bundleOf(getString(R.string.bundleKey) to id)
        )

        // Set result for savedContactsActivity
        setFragmentResult(
            getString(R.string.contactNameKey),
            bundleOf(getString(R.string.bundleKey) to name)
        )
        setFragmentResult(
            getString(R.string.contactPhoneKey),
            bundleOf(getString(R.string.bundleKey) to phone)
        )
        setFragmentResult(
            getString(R.string.contactIdKey),
            bundleOf(getString(R.string.bundleKey) to id)
        )
    }
}
