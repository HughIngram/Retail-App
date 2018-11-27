package uk.co.hughingram.retailapp.view

import android.content.Context
import android.graphics.Paint
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
        holder.name.text = product.name
        holder.brand.text = product.brand
        val ctx = holder.currentPrice.context
        holder.currentPrice.text = getPriceString(product.currentPrice, product.currency, ctx)
        // show original price (or not)
        if (product.currentPrice != product.originalPrice) {
            holder.originalPrice.text = getPriceString(product.originalPrice, product.currency, ctx)
            holder.originalPrice.paintFlags = holder.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.originalPrice.visibility = View.VISIBLE
        } else {
            holder.originalPrice.visibility = View.INVISIBLE
        }
    }

    private fun getPriceString(currentPrice: Double, currency: String, context: Context) =
        context.getString(R.string.product_price, currentPrice.formatAsPrice(), currency)

    override fun getItemCount() = products.size
}

private fun Double.formatAsPrice() = "%.2f".format(this)

class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
    val name: TextView = itemView.name
    val currentPrice: TextView = itemView.current_price
    val originalPrice: TextView = itemView.original_price
    val brand: TextView = itemView.brand
}