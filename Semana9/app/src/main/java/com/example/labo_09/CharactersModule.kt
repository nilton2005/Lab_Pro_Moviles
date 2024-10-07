package com.example.labo_09

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.labo_09.data.CharacterApiService
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.labo_09.data.Character
import com.example.labo_09.data.CharacterResponse

@Composable
fun CharactersModule(characterApiService: CharacterApiService) {
    val characters = remember { mutableStateOf<List<Character>?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = characterApiService.getAllCharacters()
                characters.value =   response.results.take(10)
            } catch (e: Exception) {
                errorMessage.value = "Error al cargar los personajes ${e.message}" // Manejo de errores
            }
        }
    }
    if(errorMessage.value != null){
       Text(text = errorMessage.value?: "error desconocido ", color = MaterialTheme.colorScheme.error)
    }else if(characters.value != null){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(characters.value!!) { character ->
                    CharacterCard(character = character)
                }
            }
    }
     else {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun CharacterCard(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Character Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = character.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${character.status}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Species: ${character.species}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Origin: ${character.gender}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
