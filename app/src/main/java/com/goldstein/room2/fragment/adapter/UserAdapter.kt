package com.goldstein.room2.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goldstein.room2.databinding.ItemUserBinding
import com.goldstein.room2.model.User

class UserAdapter(
    val onClick: (User) -> Unit, val onUpdate: (User) -> Unit, val onDelete: (User) -> Unit,
) : ListAdapter<User, UserAdapter.VH>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User,
        ): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email
            binding.userAge.text = user.age.toString()
            binding.userId.text = user.id.toString()

            binding.root.setOnClickListener {
                onClick(user)
            }

            binding.imgEdit.setOnClickListener {
                onUpdate(user)
            }
            binding.imgDelete.setOnClickListener {
                onDelete(user)
            }
        }
    }
}