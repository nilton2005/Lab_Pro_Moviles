package com.example.myapplication.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT MAX(id) FROM Product")
    suspend fun getMaxProductId(): Long

    @Insert
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM Product WHERE id = :productId")
    suspend fun deleteProductById(productId: Long)

    @Update
    suspend fun updateProduct(product: Product)
}
