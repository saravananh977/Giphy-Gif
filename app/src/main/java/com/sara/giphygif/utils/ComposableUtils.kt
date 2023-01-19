package com.sara.giphygif.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sara.giphygif.R

@Composable
fun Loader() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = Color.LightGray,
            strokeWidth = 3.dp,
        )
    }

}


@Composable
fun ProgressDialog(text: String, onDismiss: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                strokeWidth = 4.dp, color = MaterialTheme.colors.primary
            )
            Text(
                text = text,
                modifier = Modifier.padding(
                    16.dp,
                    0.dp,
                    16.dp,
                    16.dp
                ),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

    }

}


@Composable
fun ErrorMessageComposable(errorMessage: String) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = errorMessage,
            textAlign = TextAlign.Center
        )
    }
}


//@Preview
//@Composable
//fun previewProgressDialog(){
//    ProgressDialog(stringResource(id = R.string.loading),{})
//}