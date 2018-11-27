package uk.co.hughingram.retailapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_product.view.*
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.model.Product

class ProductRecycler(var products: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name
    }

    override fun getItemCount() = products.size
}

class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
    val productName: TextView = itemView.product_name
}