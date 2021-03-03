/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.Pets
import com.example.androiddevchallenge.ui.Grid
import com.example.androiddevchallenge.ui.theme.MyTheme

const val EXTRA_PET_INDEX = "com.example.androiddevchallenge.EXTRA_PET_INDEX"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                PetGrid(pets = Pets.pets, onPetClick = this::onPetClick)
            }
        }
    }

    private fun onPetClick(index: Int) {
        startActivity(
            Intent(this, PetDetailActivity::class.java).apply {
                putExtra(EXTRA_PET_INDEX, index)
            }
        )
    }
}

@Composable
fun PetGrid(
    pets: List<Pet>,
    onPetClick: (Int) -> Unit = {},
    numColumns: Int = 2
) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = { Text(text = "Lend a Paw") }
            )
            Grid(
                numColumns = numColumns,
                modifier = Modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                pets.forEachIndexed { index, pet ->
                    PetCard(
                        pet = pet,
                        onClick = { onPetClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun PetCard(pet: Pet, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = pet.imageRes),
            contentDescription = "photo of $pet.name",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Text(text = pet.name)
    }
}

@Preview("Light Theme Grid", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        PetGrid(pets = Pets.pets)
    }
}

@Preview("Dark Theme Grid", widthDp = 360, heightDp = 640)
@Composable
fun Dark3ColPreview() {
    MyTheme(darkTheme = true) {
        PetGrid(pets = Pets.pets)
    }
}
