package com.decagon.android.sq007

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.decagon.android.sq007.databinding.FragmentContactDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {

    private var binding: FragmentContactDetailsBinding? = null

    // late initialization of Database references to be used later in the code
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    // This will be used later to access variables in the host activity
//    private lateinit var activity: SavedContactsActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding =
            FragmentContactDetailsBinding.bind(view) // To be used for finding views in our layout
//        activity = requireActivity() as SavedContactsActivity // gets the host activity

        // Retrieves the saved state of the screen in case of orientation change
        binding?.tvContactName?.text = savedInstanceState?.getString(getString(R.string.nameKey))
        binding?.tvContactPhoneNumber?.text =
            savedInstanceState?.getString(getString(R.string.phoneKey))

        // Listener for SavedContactsFragment on click of item in recycler list
        setFragmentResultListener(getString(R.string.nameRequestKey)) { _, bundle ->
            binding!!.tvContactName.text =
                bundle.getString(getString(R.string.bundleKey)).toString()
        }
        setFragmentResultListener(getString(R.string.phoneNumberRequestKey)) { _, bundle ->
            binding!!.tvContactPhoneNumber.text =
                bundle.getString(getString(R.string.bundleKey)).toString()
        }
        setFragmentResultListener(getString(R.string.idRequestKey)) { _, bundle ->
            binding!!.tvId.text = bundle.getString(getString(R.string.bundleKey)).toString()
        }

        // Intent to start call
        binding!!.ivCall.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse(getString(R.string.tel) + Uri.encode(binding!!.tvContactPhoneNumber.text as String?))
            )
            startActivity(intent)
        }

        // To edit contact details
        binding!!.ivEdit.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<EditContactFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }

            // SetResult for EditContactFragment //The details of our contact will be carried over
            // to the fragment where we will edit the details
            setFragmentResult(
                getString(R.string.name_key_alt),
                bundleOf(getString(R.string.bundleKey) to binding!!.tvContactName.text)
            )
            setFragmentResult(
                getString(R.string.phone_key_alt),
                bundleOf(getString(R.string.bundleKey) to binding!!.tvContactPhoneNumber.text)
            )
            setFragmentResult(
                getString(R.string.id_key_alt),
                bundleOf(getString(R.string.bundleKey) to binding!!.tvId.text)
            )
        }

        // Intent to start share
        binding!!.ivShare.setOnClickListener {

            val name = binding!!.tvContactName.text.toString()
            val phone = binding!!.tvContactPhoneNumber.text.toString()
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$name\n$phone")
                this.type = getString(R.string.text_type)
            }
            startActivity(shareIntent)
        }

        // To delete a contact
        binding!!.ivDelete.setOnClickListener {

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference(getString(R.string.fire_base_root_node_reference))

            reference.child(binding!!.tvId.text.toString()).removeValue()

            Toast.makeText(context, getString(R.string.deleted_msg), Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    // Retrieves stored variables in main activity, when a user clicks on edit button as goes back
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
//        binding!!.tvContactName.text = activity.name
//        binding!!.tvContactPhoneNumber.text = activity.phone
//        binding!!.tvId.text = activity.id
    }

    // Saves the state of the screen in case of orientation change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

//        outState.putString(getString(R.string.nameKey), binding!!.tvContactName.text.toString())
//        outState.putString(
//            getString(R.string.phoneKey),
//            binding!!.tvContactPhoneNumber.text.toString()
//        )
    }

    // Cleans up references to views that have a tendency to cause memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
