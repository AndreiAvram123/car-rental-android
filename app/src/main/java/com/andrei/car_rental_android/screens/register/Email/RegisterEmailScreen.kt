package com.andrei.car_rental_android.screens.register.Email

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andrei.car_rental_android.R
import com.andrei.car_rental_android.composables.TextFieldLabel
import com.andrei.car_rental_android.screens.register.base.ContinueButton
import com.andrei.car_rental_android.screens.register.base.RegisterBackButton
import com.andrei.car_rental_android.screens.register.base.RegisterScreenSurface
import com.andrei.car_rental_android.ui.composables.TextFieldErrorMessage


@Composable
fun RegisterEmailScreen(
    navController: NavController,
    registerEmailNavArgs: RegisterEmailNavHelper.RegisterEmailNavArgs
) {
    val navigator = RegisterEmailNavigatorImpl(
        navController = navController,
        navArgs = registerEmailNavArgs
    )
    RegisterScreenSurface {
        MainContent(navigator = navigator)
    }
}


@Composable
private fun MainContent(
    navigator:RegisterEmailNavigator
){
    val viewModel:RegisterEmailViewModel = hiltViewModel<RegisterEmailViewModelImpl>()
    TopContent {
        navigator.navigateBack()
    }
    CenterContent(viewModel = viewModel)
    BottomContent(
        viewModel = viewModel,
        navigateForward = {
            navigator.navigateToPasswordScreen(viewModel.email.value)
        }
    )
}

@Composable
private fun TopContent(
    navigateBack:()->Unit
){
    Column(modifier = Modifier.fillMaxSize()) {
        RegisterBackButton {
            navigateBack()
        }
        Headings()
    }

}
@Composable
private fun Headings(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.screen_email_provide_email))
    }
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
                viewModel.setEmail(it.trim())
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
                TextFieldLabel(text = stringResource(R.string.screen_email_enter_email_hint))
            }
        )
        EmailValidationError(validationState)
    }

}
@Composable
fun EmailValidationError(
    validationState:State<RegisterEmailViewModel.EmailValidationState>
){
    if(validationState.value is RegisterEmailViewModel.EmailValidationState.EmailValidationError){
        val errorMessage = when(validationState.value){
            is RegisterEmailViewModel.EmailValidationState.EmailValidationError.InvalidFormat -> stringResource(
                R.string.screen_email_invalid_email_format
            )
            is RegisterEmailViewModel.EmailValidationState.EmailValidationError.EmailAlreadyTaken -> stringResource(
                R.string.screen_email_already_taken
            )
            else -> stringResource(R.string.screen_email_unknown_error)
        }
        TextFieldErrorMessage(errorMessage)
    }
}




