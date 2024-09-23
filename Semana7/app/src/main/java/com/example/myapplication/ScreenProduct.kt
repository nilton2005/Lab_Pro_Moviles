package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.myapplication.data.Product
import com.example.myapplication.data.ProductDao
import com.example.myapplication.data.ProductDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun ScreenProductWithScaffold() {
    val context = LocalContext.current
    var db: ProductDatabase
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var provider by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var productList = remember { mutableStateOf<List<Product>>(emptyList()) } // Aquí definimos productList
    var isEditMode by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Long?>(null) }

    db = Room.databaseBuilder(
        context,
        ProductDatabase::class.java,
        "product_db"
    ).build()
    val dao = db.productDao()
    val coroutineScope = rememberCoroutineScope()

    val onEditProduct: (Product) -> Unit = { product ->
        name = product.name
        price = product.price.toString()
        provider = product.provider
        brand = product.brand
        date = product.date
        isEditMode = true
        selectedProduct = product.id.toLong()
    }


    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val product = Product(
                                id = selectedProduct?.toInt() ?: 0,
                                name = name,
                                price = price.toDouble(),
                                provider = provider,
                                brand = brand,
                                date = date
                            )
                            coroutineScope.launch {
                                if(isEditMode){
                                    dao.updateProduct(product)
                                    isEditMode = false
                                    selectedProduct = null
                                }else{
                                    dao.insertProduct(product)
                                }
                                refreshProducts(dao, productList) // Aquí se actualiza productList

                            }
                            clearFields(
                                onNameChange = { name = it },
                                onPriceChange = { price = it },
                                onProviderChange = { provider = it },
                                onBrandChange = { brand = it },
                                onDateChange = { date = it }
                            )
                        }
                    ) {
                        Text(if(isEditMode)"Editar Producto" else "Agregar Producto" , fontSize = 16.sp)
                    }
                }
                ProductList(
                    products = productList.value,
                    dao = dao,
                    coroutineScope,
                    productList,
                    onEditProduct
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProductInputFields(
                    name = name,
                    onNameChange = { name = it },
                    price = price,
                    onPriceChange = { price = it },
                    provider = provider,
                    onProviderChange = { provider = it },
                    brand = brand,
                    onBrandChange = { brand = it },
                    date = date,
                    onDateChange = { date = it }
                )
            }
        }
    )
}

@Composable
fun ProductList(
    products: List<Product>, dao:
    ProductDao,
    coroutineScope: CoroutineScope,
    productList: MutableState<List<Product>>,
    onEditProduct: (Product) -> Unit

) {
    LazyColumn {
        itemsIndexed(products) { index, product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Espacio alrededor de cada tarjeta
                shape = RoundedCornerShape(8.dp) // Bordes redondeados
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espacio interno para que el contenido no toque los bordes de la tarjeta
                ) {
                    Text(
                        text = "${product.name} - ${product.price}",
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        coroutineScope.launch {
                            dao.deleteProductById(product.id.toLong())
                            refreshProducts(dao, productList = productList)
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }


                }

                    IconButton(onClick = {
                        // Acciones para editar
                        onEditProduct(product)
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            }
        }
    }




@Composable
fun ProductInputFields(
    name: String,
    onNameChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    provider: String,
    onProviderChange: (String) -> Unit,
    brand: String,
    onBrandChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit
) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Nombre") },
        singleLine = true
    )
    Spacer(Modifier.height(16.dp))
    TextField(
        value = price,
        onValueChange = onPriceChange,
        label = { Text("Precio") },
        singleLine = true
    )
    Spacer(Modifier.height(16.dp))
    TextField(
        value = provider,
        onValueChange = onProviderChange,
        label = { Text("Proveedor") },
        singleLine = true
    )
    Spacer(Modifier.height(16.dp))
    TextField(
        value = brand,
        onValueChange = onBrandChange,
        label = { Text("Marca") },
        singleLine = true
    )
    Spacer(Modifier.height(16.dp))
    TextField(
        value = date,
        onValueChange = onDateChange,
        label = { Text("Fecha") },
        singleLine = true
    )
}

suspend fun refreshProducts(dao: ProductDao, productList: MutableState<List<Product>>) {
    val products = dao.getAllProducts()
    productList.value = products
}
fun clearFields(
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onProviderChange: (String) -> Unit,
    onBrandChange: (String) -> Unit,
    onDateChange: (String) -> Unit
) {
    // Limpia los campos de entrada
    onNameChange("")
    onPriceChange("")
    onProviderChange("")
    onBrandChange("")
    onDateChange("")
}
