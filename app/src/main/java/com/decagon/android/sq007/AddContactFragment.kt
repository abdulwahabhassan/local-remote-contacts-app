package com.decagon.android.sq007

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagon.android.sq007.databinding.FragmentAddContactBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var id: String
    private var binding: FragmentAddContactBinding? = null

    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddContactBinding.bind(view)

        binding?.saveContactButton?.setOnClickListener {

            name = binding?.etName?.text.toString()
            phoneNumber = binding?.etPhoneNumber?.text.toString()

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("contacts")

            id = reference.push().key.toString()

            val contactModel = ContactModel(id, name, phoneNumber)
            if (contactModel.fullName == "" || contactModel.contactNumber == "") {
                Toast.makeText(context, "An empty field cannot be saved", Toast.LENGTH_SHORT).show()
            } else {
                if (!Utils.phoneNumberValidator(phoneNumber)) {
                    Toast.makeText(context, "Please, enter a valid phone number", Toast.LENGTH_SHORT).show()
                } else {
                    reference.child(id).setValue(contactModel)
                    Toast.makeText(context, "Contact saved successfully", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
