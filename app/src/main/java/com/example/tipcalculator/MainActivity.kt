package com.example.tipcalculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipLayout(modifier = Modifier)
                }
            }
        }
    }
}

private fun TipCalculator(bill : Double, tipPercent : Double, roundUp:Boolean):Double{
    var tip = bill * (tipPercent / 100.0)
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return String.format("%.2f", tip).toDoubleOrNull() ?: 0.0
//    return bill * (tipPercent/100)
}

@Composable
fun TipLayout(modifier: Modifier){
    var billAmount by remember { mutableStateOf("0") }
    var tipPercent by remember { mutableStateOf("15") }
    val amount = billAmount.toDoubleOrNull() ?: 0.0
    val tipP = tipPercent.toDoubleOrNull() ?: 0.0
    var tip by remember{ mutableStateOf(0.0) }
//    val tip = TipCalculator(amount, tipP )

    Column (
        modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Text(text = "Calculate Tip",
            modifier
                .align(Alignment.Start)
                .padding(10.dp))
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = billAmount,
            onValueChange = {billAmount = it},
            label = { Text(text = "Bill Amount") }, modifier = modifier
                .align(Alignment.Start)
                .fillMaxWidth()
                .padding(bottom = 36.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), singleLine = true )

        TextField(value = tipPercent,
            onValueChange = {tipPercent = it},
            label = { Text(text = "Tip Percentage") }, modifier = modifier
                .align(Alignment.Start)
                .fillMaxWidth()
                .padding(bottom = 36.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done), singleLine = true )

//        Text(text = "Bill Amount: $billAmount")

        var roundUp by remember{ mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round Up?")
            SwitchComp(isChecked = roundUp, onRoundUpChanged = {roundUp = it})
        }

        Button(onClick = { tip = TipCalculator(amount, tipP, roundUp ) }) {
            Text(text = "Calculate")
        }

        Text(text = "Tip is $tip", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun SwitchComp(isChecked:Boolean, onRoundUpChanged: (Boolean) -> Unit,){
    androidx.compose.material3.Switch(checked = isChecked, onCheckedChange = onRoundUpChanged)
}


@Preview(showBackground = true)
@Composable
fun TipLayoutPreview() {
    TipCalculatorTheme {
        TipLayout(modifier = Modifier)
    }
}