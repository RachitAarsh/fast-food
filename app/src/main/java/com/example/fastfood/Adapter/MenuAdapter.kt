package com.example.foodiezz.Adapters

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.fastfood.databinding.MenuItemBinding
import com.example.fastfood.DeatilsActivity
import com.example.fastfood.model.AllMenu


class MenuAdapter(
    private val menuItems: List<AllMenu>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }

            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]
            val intent = Intent(requireContext, DeatilsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemPrice", menuItem.foodPrice)
                putExtra("MenuItemDiscount", menuItem.foodDiscount)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemCategory", menuItem.foodCategory)
            }
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val Uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(Uri).into(menuImage)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}

