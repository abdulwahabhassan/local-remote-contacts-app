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
    private lateinit var activity: SavedContactsActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentContactDetailsBinding.bind(view) // To be used for finding views in our layout
        activity = requireActivity() as SavedContactsActivity // gets the host activity

        // Retrieves the saved state of the screen in case of orientation change
        binding!!.tvContactName.text = savedInstanceState?.getString("name")
        binding!!.tvContactPhoneNumber.text = savedInstanceState?.getString("phone")

        // Listener for SavedContactsFragment on click of item in recycler list
        setFragmentResultListener("nameRequestKey") { _, bundle ->
            binding!!.tvContactName.text = bundle.getString("bundleKey").toString()
        }
        setFragmentResultListener("phoneNumberRequestKey") { _, bundle ->
            binding!!.tvContactPhoneNumber.text = bundle.getString("bundleKey").toString()
        }
        setFragmentResultListener("idRequestKey") { _, bundle ->
            binding!!.tvId.text = bundle.getString("bundleKey").toString()
        }

        // Intent to start call
        binding!!.ivCall.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:" + Uri.encode(binding!!.tvContactPhoneNumber.text as String?))
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
            setFragmentResult("nameKey", bundleOf("bundleKey" to binding!!.tvContactName.text))
            setFragmentResult("phoneNumberKey", bundleOf("bundleKey" to binding!!.tvContactPhoneNumber.text))
            setFragmentResult("idKey", bundleOf("bundleKey" to binding!!.tvId.text))
        }

        // Intent to start share
        binding!!.ivShare.setOnClickListener {

            val name = binding!!.tvContactName.text.toString()
            val phone = binding!!.tvContactPhoneNumber.text.toString()
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$name\n$phone")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        // To delete a contact
        binding!!.ivDelete.setOnClickListener {

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("contacts")

            reference.child(binding!!.tvId.text.toString()).removeValue()

            Toast.makeText(context, "Contact deleted successfully", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    // Retrieves stored variables in main activity, when a user clicks on edit button as goes back
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding!!.tvContactName.text = activity.name
        binding!!.tvContactPhoneNumber.text = activity.phone
        binding!!.tvId.text = activity.id
    }

    // Saves the state of the screen in case of orientation change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", binding!!.tvContactName.text.toString())
        outState.putString("phone", binding!!.tvContactPhoneNumber.text.toString())
    }

    // Cleans up references to views that have a tendency to cause memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
