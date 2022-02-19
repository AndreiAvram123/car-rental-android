package com.andrei.car_rental_android.screens.register.Email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andrei.car_rental_android.R
import com.andrei.car_rental_android.composables.TextFieldLabel
import com.andrei.car_rental_android.screens.register.base.ContinueButton
import com.andrei.car_rental_android.screens.register.base.RegisterScreenSurface
import com.andrei.car_rental_android.ui.composables.TextFieldErrorMessageWithIcon


@Composable
fun RegisterEmailScreen(navController: NavController) {
    RegisterScreenSurface {
        MainContent(navigateForward = {
            //TODO
            //add navigation to next screen
        })
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MainContent(navigateForward : ()-> Unit = {}){
    val viewModel:RegisterEmailViewModel = hiltViewModel<RegisterEmailViewModelImpl>()
            CenterContent(viewModel = viewModel)
            BottomContent(
                viewModel = viewModel,
                navigateForward = navigateForward
            )
}

@Composable
private fun CenterContent(viewModel: RegisterEmailViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmailTextFieldColumn(
            modifier = Modifier.fillMaxWidth(),
            state = viewModel.email.collectAsState()
            , onValueChanged ={
                viewModel.setEmail(it)
            },
            validationState = viewModel.emailValidationState.collectAsState()
        )
    }
}
@Composable
private fun BottomContent(
    viewModel: RegisterEmailViewModel,
    navigateForward:() -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        ContinueButton(
            enabled = viewModel.nextButtonEnabled.collectAsState(),
            onClick = {
                navigateForward()
            }
        )
    }
}


@Composable
private fun EmailTextFieldColumn(
    modifier: Modifier = Modifier,
    state: State<String>,
    onValueChanged: (newValue:String)-> Unit,
    validationState:State<RegisterEmailViewModel.EmailValidationState>
){
    val invalid = when(validationState.value){
        is RegisterEmailViewModel.EmailValidationState.EmailValidationError.EmailAlreadyTaken -> true
        else -> false
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = modifier,
            value = state.value,
            onValueChange = {
                onValueChanged(it)
            },
            isError = invalid,
            placeholder = {
                TextFieldLabel(text = stringResource(R.string.screen_email_email))
            }
        )
        if(invalid){
            TextFieldErrorMessageWithIcon(
                errorMessage = "Invalid email"
            )
        }
    }

}




