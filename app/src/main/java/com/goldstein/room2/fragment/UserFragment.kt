package com.goldstein.room2.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.goldstein.room2.BaseFragment
import com.goldstein.room2.R
import com.goldstein.room2.databinding.FragmentUserBinding
import com.goldstein.room2.fragment.adapter.UserAdapter
import com.goldstein.room2.model.User
import com.goldstein.room2.viewmodel.UserEvent
import com.goldstein.room2.viewmodel.UserState
import com.goldstein.room2.viewmodel.UserViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment() {
    private val viewModel: UserViewModel by viewModel()
    private lateinit var adapter: UserAdapter
    private lateinit var binding: FragmentUserBinding

    companion object {
        fun newInstance() = UserFragment()
    }

    override fun getLayout() = R.layout.fragment_user

    override fun setupViews() {
        binding = FragmentUserBinding.bind(rootView)
        setUpObserver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllUsers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDelete.setOnClickListener {
            deleteAllDialog()
        }

        binding.btnAdd.setOnClickListener {
            showDialog()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpObserver() {
        viewModel.screenState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
        ).onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.event.flowWithLifecycle(
            viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
        ).onEach { event -> handleEvent(event) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Error -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }

            is UserEvent.onSuccess -> {
                Toast.makeText(requireContext(), "${event.users}", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }


    private fun handleState(state: UserState) {
        if (state.loading) {
            // Handle loading state
            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
        } else {
            // Handle data load success and populate RecyclerView
            adapter = UserAdapter(
                onClick = { state -> showDialog() },
                onUpdate = { state -> updateUser(state) },
                onDelete = { state -> deleteDialog(state) }
            )
            binding.recyclerView.adapter = adapter
            adapter.submitList(state.users)
        }
    }

    private fun updateUser(user: User) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Enter Details")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_layout, null)
        val etName = dialogLayout.findViewById<EditText>(R.id.et_name)
        val etEmail = dialogLayout.findViewById<EditText>(R.id.et_email)
        val etAge = dialogLayout.findViewById<EditText>(R.id.et_age)

        // Pre-fill fields with user data
        etName.setText(user.name)
        etEmail.setText(user.email)
        etAge.setText(user.age)

        builder.setView(dialogLayout).setCancelable(false)

        builder.setPositiveButton("Submit") { dialogInterface, i ->
            // Handle submit button click
            val updatedUser = User(
                id = user.id,
                name = etName.text.toString(),
                email = etEmail.text.toString(),
                age = etAge.text.toString()
            )
            viewModel.updateUser(updatedUser)
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Enter Details")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_layout, null)
        val etName = dialogLayout.findViewById<EditText>(R.id.et_name)
        val etEmail = dialogLayout.findViewById<EditText>(R.id.et_email)
        val etAge = dialogLayout.findViewById<EditText>(R.id.et_age)


        builder.setView(dialogLayout).setCancelable(false)

        builder.setPositiveButton("Submit") { dialogInterface, i ->
            // Handle submit button click
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val age = etAge.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty()) {
                val newUser = User(
                    name = name,
                    email = email,
                    age = age
                )
                viewModel.insertUser(newUser)

            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun deleteDialog(user: User) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete User")

        builder.setMessage("Are you sure you want to delete this user?").setCancelable(false)
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            viewModel.deleteUser(user)
            Log.d("====", "handleState:${user.id} ___ ${user.age} ")

        }

        builder.setNegativeButton("No", null)
        builder.show()
    }

    private fun deleteAllDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete User")

        builder.setMessage("Are you sure you want to delete all users?").setCancelable(false)
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            viewModel.deleteAll()
        }

        builder.setNegativeButton("No", null)
        builder.show()
    }
}
