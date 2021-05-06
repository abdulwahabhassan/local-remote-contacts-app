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

class SavedContactsFragment : Fragment(R.layout.fragment_saved_contacts), SavedContactsAdapter.OnSavedContactsListener {

    private var binding: FragmentSavedContactsBinding? = null

    var recyclerView: RecyclerView? = null
    var arrayList = ArrayList<ContactModel>()

    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedContactsBinding.bind(view)

        recyclerView = binding!!.recyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.setHasFixedSize(true)
        arrayList = arrayListOf()

        getContactData()

        binding?.addContactButton?.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddContactFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
    }

    private fun getContactData() {
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("contacts")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val contact = userSnapshot.getValue(ContactModel::class.java)
                        arrayList.add(contact!!)
                        recyclerView!!.adapter = SavedContactsAdapter(arrayList, this@SavedContactsFragment)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onContactClick(position: Int) {
        val name = arrayList[position].fullName
        val phone = arrayList[position].contactNumber
        val id = arrayList[position].id

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ContactDetailsFragment>(R.id.fragment_container_view)
            addToBackStack(null)
        }

        setFragmentResult("nameRequestKey", bundleOf("bundleKey" to name))
        setFragmentResult("phoneNumberRequestKey", bundleOf("bundleKey" to phone))
        setFragmentResult("idRequestKey", bundleOf("bundleKey" to id))

        setFragmentResult("contactNameKey", bundleOf("bundleKey" to name))
        setFragmentResult("contactPhoneKey", bundleOf("bundleKey" to phone))
    }
}
