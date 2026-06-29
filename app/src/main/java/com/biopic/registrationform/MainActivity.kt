package com.biopic.registrationform

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biopic.registrationform.ui.theme.RegistrationFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationFormTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        RegistrationForm()
                    }
                }
            }
        }
    }
}

val genderList = listOf("Male", "Female", "Others")

@Composable
fun RegistrationForm(){

    val nameText = remember {
        mutableStateOf("")
    }

    val emailText = remember {
        mutableStateOf("")
    }

    val passwordText = remember {
        mutableStateOf("")
    }

    val showDetails = remember {
        mutableStateOf(false)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    val errorMessage = remember {
        mutableStateOf("")
    }

    val countryList = stringArrayResource(R.array.country)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(55.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = "",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.size(20.dp))

            TextField(
                value = nameText.value,
                onValueChange = {
                    nameText.value = it
                },
                placeholder = {
                    Text(
                        text = "Enter name",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(0.75f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.size(15.dp))

            TextField(
                value = emailText.value,
                onValueChange = {
                    emailText.value = it
                },
                placeholder = {
                    Text(
                        text = "Enter email",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(0.75f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.size(15.dp))

            TextField(
                value = passwordText.value,
                onValueChange = {
                    passwordText.value = it
                },
                placeholder = {
                    Text(
                        text = "Enter password",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth(0.75f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.size(15.dp))

        val selectedGender = gender()
        Spacer(modifier = Modifier.size(10.dp))
        val country = selectCountry(countryList)
        Spacer(modifier = Modifier.size(10.dp))
        val isNotificationOn = receiveNotification()
        val accepted = acceptTC()
        Spacer(modifier = Modifier.size(10.dp))
        SubmitAndResetButton(
            nameText,
            emailText,
            passwordText,
            selectedGender,
            country,
            accepted,
            isNotificationOn,
            showDetails,
            showError,
            errorMessage)
        Spacer(modifier = Modifier.size(10.dp))
        ShowDetails(nameText.value, emailText.value, selectedGender.intValue, country.intValue, countryList, showDetails.value, showError.value, errorMessage.value)
    }
}

@Composable
fun gender() : MutableIntState{
    val genderIndex = remember {
        mutableIntStateOf(-1)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            text = "Gender",
            fontSize = 18.sp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            genderList.forEachIndexed { index, gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            genderIndex.intValue = if(genderIndex.intValue == index) -1 else index
                        }
                ) {
                    RadioButton(
                        selected = genderIndex.intValue == index,
                        onClick = {
                            genderIndex.intValue = if(genderIndex.intValue == index) -1 else index
                        }
                    )

                    Text(
                        text = gender
                    )
                }
            }
        }
    }
    return genderIndex
}

@Composable
fun selectCountry(countryList : Array<String>) : MutableIntState{

    val isExpanded = remember {
        mutableStateOf(false)
    }

    val countryIndex = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box{
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .fillMaxHeight(0.1f)
                    .background(
                        Color(0xFF65FCE7),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        isExpanded.value = true
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = countryList[countryIndex.intValue],
                    color = Color.Black,
                    modifier = Modifier.padding(start = 20.dp)

                )
                Image(
                    painter = painterResource(R.drawable.dropdown_arrow),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }

            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = {
                    isExpanded.value = false
                },
                offset = DpOffset(x = 0.dp, y = 0.dp)
            ) {
                countryList.forEachIndexed { index, country ->
                    DropdownMenuItem(
                        text = {
                            Text(text = country)
                        },
                        onClick = {
                            isExpanded.value = false
                            countryIndex.intValue = index
                        }
                    )
                }
            }
        }
    }
    return countryIndex
}

@Composable
fun receiveNotification() : MutableState<Boolean>{

    val isNotificationOn = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Receive Notifications")
            Switch(
                checked = isNotificationOn.value,
                onCheckedChange = {
                    isNotificationOn.value = it
                }
            )
        }
    }
    return isNotificationOn
}

@Composable
fun acceptTC() : MutableState<Boolean>{

    val isAccepted = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.805f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = isAccepted.value,
                onCheckedChange = {
                    isAccepted.value = it
                }
            )
            Text(
                text = "I agree to terms & conditions"
            )
        }
    }
    return isAccepted
}

@Composable
fun SubmitAndResetButton(nameText : MutableState<String>, emailText : MutableState<String>, passwordText : MutableState<String>, selectedGender : MutableIntState, country : MutableIntState, accepted : MutableState<Boolean>, isNotificationOn : MutableState<Boolean>, showDetails : MutableState<Boolean>, showError : MutableState<Boolean>, errorMessage : MutableState<String>){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    nameText.value = ""
                    emailText.value = ""
                    passwordText.value = ""
                    selectedGender.intValue = -1
                    country.intValue = 0
                    accepted.value = false
                    isNotificationOn.value = false
                    showDetails.value = false
                    showError.value = false
                },
                modifier = Modifier
                    .size(100.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(13.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = "Reset",
                )
            }
            Button(
                onClick = {
                    val nameInvalid = nameText.value.isBlank()
                    val emailInvalid = emailText.value.isBlank() || !isValidEmail(emailText.value)
                    val passwordInvalid = passwordText.value.isBlank() || !isValidPassword(passwordText.value)
                    val genderInvalid = selectedGender.intValue == -1
                    val  countryInvalid = country.intValue == 0
                    val termsInvalid = !accepted.value

                    if(nameInvalid || emailInvalid || passwordInvalid || genderInvalid || countryInvalid || termsInvalid) {
                        showError.value = true
                        showDetails.value = false
                        errorMessage.value = "Please fill " +
                                (if (nameInvalid) "name " else "") +
                                (if (emailInvalid) "valid email " else "")+
                                (if (passwordInvalid) "valid password " else "") +
                                (if (genderInvalid) "gender " else "") +
                                (if (countryInvalid) "country " else "") +
                                (if(termsInvalid) "checkbox for terms & condition " else "")
                    }
                    else {
                        showError.value = false
                        showDetails.value = true
                    }
                },
                modifier = Modifier
                    .size(100.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(13.dp),
                border = BorderStroke(0.5.dp, Color.Black)
            ) {
                Text(
                    text = "Submit",
                )
            }
        }
    }
}

@Composable
fun ShowDetails(nameText : String, emailText : String, selectedGender : Int, country : Int, countryList : Array<String>, showDetails : Boolean, showError : Boolean, errorMessage : String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(showError) {
            Spacer(modifier = Modifier.size(40.dp))
            Text(text = errorMessage, modifier = Modifier.fillMaxWidth(0.75f))
        }
        if(showDetails) {
            Text(text = "Name : $nameText", modifier = Modifier.fillMaxWidth(0.75f))
            Text(text = "Email : $emailText", modifier = Modifier.fillMaxWidth(0.75f))
            Text(text = "Gender : ${genderList[selectedGender]}", modifier = Modifier.fillMaxWidth(0.75f))
            Text(text = "Country : ${countryList[country]}", modifier = Modifier.fillMaxWidth(0.75f))
        }
    }
}

fun isValidEmail(email : String) : Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
fun isValidPassword(password : String) : Boolean{
    return password.length >= 8
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistrationFormTheme {
        RegistrationForm()
    }
}