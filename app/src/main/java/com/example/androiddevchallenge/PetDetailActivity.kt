package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import com.example.androiddevchallenge.ui.theme.MyTheme

class PetDetailActivity : AppCompatActivity() {

    private val pets = Pets.pets
    private var petIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petIndex = intent.getIntExtra(EXTRA_PET_INDEX, 0)
        updateContent()
    }

    private fun updateContent() {
        setContent {
            MyTheme {
                PetDetails(
                    pet = pets[petIndex],
                    hasPrevious = hasPreviousPet(),
                    hasNext = hasNextPet(),
                    onPreviousClick = this::showPreviousPet,
                    onNextClick = this::showNextPet
                )
            }
        }
    }

    private fun showPreviousPet() {
        if (petIndex > 0) {
            petIndex -= 1
            updateContent()
        }
    }

    private fun showNextPet() {
        if ((petIndex + 1) < pets.size) {
            petIndex += 1
            updateContent()
        }
    }

    private fun hasPreviousPet(): Boolean = (petIndex > 0)

    private fun hasNextPet(): Boolean = ((petIndex + 1) < pets.size)
}

@Composable
fun PetDetails(
    pet: Pet,
    hasPrevious: Boolean = true,
    hasNext: Boolean = true,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = { Text(text = "About ${pet.name}") },
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                PetImage(imageRes = pet.imageRes, name = pet.name)
                PetText(text = "Age: ${pet.age}")
                PetText(text = "Personality: ${pet.personality}.")
                PetText(text = "Seeking a family: ${pet.requirement}.")
                PetText(text = "Arrived at the shelter on ${pet.arrivedAt}.")
                NavButtons(
                    enablePrevious = hasPrevious,
                    enableNext = hasNext,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick
                )
            }
        }
    }
}

@Composable
fun PetImage(
    @DrawableRes imageRes: Int,
    name: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "photo of $name",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .width(240.dp)
                .height(240.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun PetText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    )
}

@Composable
fun NavButtons(
    enablePrevious: Boolean,
    enableNext: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onPreviousClick() },
            enabled = enablePrevious
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = { onNextClick() },
            enabled = enableNext
        ) {
            Text(text = "Next")
        }
    }
}

@Preview("Light Theme Details", widthDp = 360, heightDp = 640)
@Composable
fun LightPreviewDetails() {
    MyTheme {
        PetDetails(pet = Pets.pets[0])
    }
}

@Preview("Dark Theme Details", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreviewDetails() {
    MyTheme(darkTheme = true) {
        PetDetails(pet = Pets.pets[0])
    }
}
