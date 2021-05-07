package com.decagon.android.sq007

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagon.android.sq007.databinding.FragmentAddContactBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    // late variable initializations
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var id: String
    private var binding: FragmentAddContactBinding? = null

    // late initialization of Database references to be used later in the code
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddContactBinding.bind(view)

        // To save a new contact to the database
        binding?.saveContactButton?.setOnClickListener {

            // Retrieves the data we've entered into the edit text views and assigns them to variables
            name = binding?.etName?.text.toString()
            phoneNumber = binding?.etPhoneNumber?.text.toString()

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("contacts")

            id = reference.push().key.toString() // Generates a unique random id for each new contact

            // data stored in the variables are passed as parameters to our ContactModel class to instantiate
            // a new contact that will be saved to our database
            val contactModel = ContactModel(id, name, phoneNumber)
            if (contactModel.fullName == "" || contactModel.contactNumber == "") {
                Toast.makeText(context, "An empty field cannot be saved", Toast.LENGTH_SHORT).show()
            } else {
                if (!Utils.phoneNumberValidator(phoneNumber)) { // Validates the phone number
                    Toast.makeText(context, "Please, enter a valid phone number", Toast.LENGTH_SHORT).show()
                } else {
                    reference.child(id).setValue(contactModel)
                    Toast.makeText(context, "Contact saved successfully", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    // Cleans up references to views that have a tendency to cause memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
