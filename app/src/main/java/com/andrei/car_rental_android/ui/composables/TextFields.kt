package com.andrei.car_rental_android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.andrei.car_rental_android.ui.Dimens
import com.andrei.car_rental_android.ui.theme.LightRed


@Composable
fun TextFieldErrorMessage(errorMessage:String){
    Text(
        text = errorMessage,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = Dimens.small.dp)
    )
}
@Composable
fun TextFieldErrorMessageWithIcon(
    errorMessage: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightRed)
            .padding(Dimens.small.dp)
    ){
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Filled.Error,
            tint = MaterialTheme.colors.error,
            contentDescription = null
        )
        Text(text = errorMessage)
    }
}