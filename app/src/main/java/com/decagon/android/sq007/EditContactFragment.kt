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

    // late variable initializations
    lateinit var name: String
    private lateinit var phoneNumber: String
    lateinit var contactId: String
    private var binding: FragmentEditContactBinding? = null

    // late initialization of Database references to be used later in the code
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var fireBaseDatabaseReference: DatabaseReference
    lateinit var activity: SavedContactsActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditContactBinding.bind(view)
        activity = requireActivity() as SavedContactsActivity

        // Retrieves the saved state of the screen in case of orientation changes
        binding!!.etName.text = savedInstanceState?.getString(getString(R.string.nameKey)) as Editable?
        binding!!.etPhoneNumber.text = savedInstanceState?.getString(getString(R.string.phoneKey)) as Editable?

        // Listener for ContactDetailsFragment
        setFragmentResultListener(getString(R.string.name_key_alt)) { _, bundle ->
            val name = bundle.getString(getString(R.string.bundleKey))
            binding!!.etName.setText(name)
        }
        setFragmentResultListener(getString(R.string.phone_key_alt)) { _, bundle ->
            val phone = bundle.getString(getString(R.string.bundleKey))
            binding!!.etPhoneNumber.setText(phone)
        }
        setFragmentResultListener(getString(R.string.id_key_alt)) { _, bundle ->
            val id = bundle.getString(getString(R.string.bundleKey))
            contactId = id!!
        }

        // Saves the contact we have just edited
        binding?.saveContactButton?.setOnClickListener {

            name = binding?.etName?.text.toString()
            phoneNumber = binding?.etPhoneNumber?.text.toString()

            // Resets the values of the variables in our activity so as to be concurrent with the edited details
            // our our contact
            activity.name = name
            activity.phone = phoneNumber
            activity.id = contactId

            firebaseDatabase = FirebaseDatabase.getInstance()









            fireBaseDatabaseReference = firebaseDatabase.getReference(getString(R.string.fire_base_root_node_reference))

            val contactModel = ContactModel(contactId, name, phoneNumber)
            if (contactModel.fullName == "" || contactModel.contactNumber == "") {
                Toast.makeText(context, R.string.empty_field_warning, Toast.LENGTH_SHORT).show()
            } else {
                if (!Utils.phoneNumberValidator(phoneNumber)) {
                    Toast.makeText(context, R.string.invalid_phone_number_warning, Toast.LENGTH_SHORT).show()
                } else {
                    fireBaseDatabaseReference.child(contactId).setValue(contactModel)
                    Toast.makeText(context, R.string.successful_save_msg, Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    // Saves the state of the screen in case of orientation change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(getString(R.string.nameKey), binding!!.etName.text.toString())
        outState.putString(getString(R.string.phoneKey), binding!!.etPhoneNumber.text.toString())
    }

    // Cleans up references to views that have a tendency to cause memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
