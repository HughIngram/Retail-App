package uk.co.hughingram.retailapp.model

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE


/**
 * DTO representing a Product.
 */
@Entity
data class Product(
    @PrimaryKey
    val identifier: Long,
    val name: String,
    val brand: String,
    val originalPrice: Double,
    val currentPrice: Double,
    val currency: String,
    @Embedded val image: ProductImage
)

// TODO embed or link from product
@Entity
data class ProductImage(
    val url: String,
    @PrimaryKey
    val id: Long
)

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Insert(onConflict = REPLACE)
    fun insertMultipleProducts(products: List<Product>)

}

@Database(entities = [Product::class, ProductImage::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
