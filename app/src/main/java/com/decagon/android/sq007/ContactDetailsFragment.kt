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
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentContactDetailsBinding.bind(view)

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

        binding!!.ivCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(binding!!.tvContactPhoneNumber.text as String?)))
            startActivity(intent)
        }

        binding!!.ivEdit.setOnClickListener {

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<EditContactFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }


            // SetResult for EditContactFragment
            setFragmentResult("nameKey", bundleOf("bundleKey" to binding!!.tvContactName.text))
            setFragmentResult("phoneNumberKey", bundleOf("bundleKey" to binding!!.tvContactPhoneNumber.text))
            setFragmentResult("idKey", bundleOf("bundleKey" to binding!!.tvId.text))
        }

        binding!!.ivShare.setOnClickListener {

            val name = binding!!.tvContactName.text.toString()
            val phone = binding!!.tvContactPhoneNumber.text.toString()
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$name\n$phone" )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        binding!!.ivDelete.setOnClickListener {

            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("contacts")

            reference.child(binding!!.tvId.text.toString()).removeValue()

            Toast.makeText(context, "Contact deleted successfully", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            parentFragmentManager.popBackStack()
        }
    }

//    private fun shareContact() {
//        val name = binding!!.tvContactName.text
//        val phoneNumber = binding!!.tvContactName.text
//
//        val shareIntent = Intent()
//        shareIntent.action = Intent.ACTION_SEND
//        shareIntent.type = "text/plain"
//        shareIntent.putExtra(Intent.EXTRA_TEXT, "$name $phoneNumber")
//        startActivity(Intent.createChooser(shareIntent, "share data"))
//
//    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setFragmentResultListener("nameRequestKey") { _, bundle ->
            binding!!.tvContactName.text = bundle.getString("bundleKey").toString()
        }
        setFragmentResultListener("phoneNumberRequestKey") { _, bundle ->
            binding!!.tvContactPhoneNumber.text = bundle.getString("bundleKey").toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", binding!!.tvContactName.text.toString())
        outState.putString("phone", binding!!.tvContactPhoneNumber.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
