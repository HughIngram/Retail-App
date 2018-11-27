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
        val ctx = holder.currentPrice.context
        val currentPriceText =
            ctx.getString(R.string.product_price, product.currentPrice.formatAsPrice(), product.currency)
        holder.currentPrice.text = currentPriceText
        val originalPriceText =
            ctx.getString(R.string.product_price, product.originalPrice.formatAsPrice(), product.currency)
        // TODO strikethrough
        holder.originalPrice.text = originalPriceText
        holder.name.text = product.name
        holder.brand.text = product.brand
    }

    override fun getItemCount() = products.size
}

private fun Double.formatAsPrice() = "%.2f".format(this)

class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
    val name: TextView = itemView.name
    val currentPrice: TextView = itemView.current_price
    val originalPrice: TextView = itemView.original_price
    val brand: TextView = itemView.brand
}