package com.decagon.android.sq007

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.*
import com.decagon.android.sq007.databinding.FragmentEditContactBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    lateinit var name: String
    private lateinit var phoneNumber: String
    lateinit var contactId: String
    private var binding: FragmentEditContactBinding? = null

    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditContactBinding.bind(view)

        binding!!.etName.text = savedInstanceState?.getString("name") as Editable?
        binding!!.etPhoneNumber.text = savedInstanceState?.getString("phone") as Editable?

        // Listener for ContactDetailsFragment
        setFragmentResultListener("nameKey") { _, bundle ->
            val name = bundle.getString("bundleKey")
            binding!!.etName.setText(name)
        }
        setFragmentResultListener("phoneNumberKey") { _, bundle ->
            val phone = bundle.getString("bundleKey")
            binding!!.etPhoneNumber.setText(phone)
        }
        setFragmentResultListener("idKey") { _, bundle ->
            val id = bundle.getString("bundleKey")
            contactId = id!!
        }

        binding?.saveContactButton?.setOnClickListener {

            name = binding?.etName?.text.toString()
            phoneNumber = binding?.etPhoneNumber?.text.toString()

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("contacts")

            val contactModel = ContactModel(contactId, name, phoneNumber)
            if (contactModel.fullName == "" || contactModel.contactNumber == "") {
                Toast.makeText(context, "An empty field cannot be saved", Toast.LENGTH_SHORT).show()
            } else {
                if (!Utils.phoneNumberValidator(phoneNumber)) {
                    Toast.makeText(context, "Please, enter a valid phone number", Toast.LENGTH_SHORT).show()
                } else {
                    reference.child(contactId).setValue(contactModel)
                    Toast.makeText(context, "Contact saved successfully", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", binding!!.etName.text.toString())
        outState.putString("phone", binding!!.etPhoneNumber.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
