package com.redcarpethomes.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redcarpethomesassistant.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedCarpetChatbotScreen() {
    var messages by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    var inputValue by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        // === Background Logo ===
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon), // Your logo file
            contentDescription = "Red Carpet Homes Logo Background",
            modifier = Modifier
                .fillMaxSize(1f) // Full screen size
                .align(Alignment.Center)
                .alpha(0.3f), // Increased alpha for better visibility
            contentScale = ContentScale.Fit
        )

        // === Foreground Content ===
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // AppBar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.red_carpet_icon),
                    contentDescription = "Red Carpet Homes Logo",
                    modifier = Modifier.size(70.dp) // Increased size for better visibility
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Red Carpet Homes Assistant",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB71C1C)
                    )
                )
            }

            // Chat messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                reverseLayout = false
            ) {
                items(messages) { (message, isUser) ->
                    ChatBubble(message, isUser)
                }
            }

            // Input area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = inputValue,
                    onValueChange = {
                        if (it.text.length <= 1000) inputValue = it
                    },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        val text = inputValue.text.trim()
                        if (text.isNotEmpty()) {
                            messages = messages.takeLast(59) + (text to true) // Limit 60 messages
                            inputValue = TextFieldValue("")
                            coroutineScope.launch {
                                delay(800L)
                                val botResponse = getRealEstateResponse(text)
                                messages = messages.takeLast(59) + (botResponse to false)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C))
                ) {
                    Text("Send", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isUser: Boolean) {
    val bgColor = if (isUser) Color(0xFFB71C1C) else Color(0xFFF1F1F1)
    val textColor = if (isUser) Color.White else Color.Black
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor)
                .padding(12.dp)
                .widthIn(max = 320.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Start)
            )
        }
    }
}

/* --- Smart Real Estate Chatbot Logic --- */
fun getRealEstateResponse(input: String): String {
    val lowercase = input.lowercase()

    return when {
        listOf("hi", "hello", "hey").any { it in lowercase } ->
            "Hello 👋! Welcome to *Red Carpet Homes*.\nWe help you find premium homes and land across Kenya. Are you interested in *buying a home* or *investing in land* or *knowing about Us*?"

        "about" in lowercase || "company" in lowercase ->
            "Red Carpet Homes is a trusted real estate company founded by *Rachael Ndunge*. We specialize in selling premium land and modern apartments across Kenya — offering flexible payment plans, ready title deeds, and mortgage assistance."

        "land" in lowercase || "plot" in lowercase ->
            "🌍 Available land in *Kitengela, Juja Farm, Watamu, Diani, Riruta, Roysambu, Kiambu,* and *Fedha.*\n\n✅ All land comes with *ready title deeds*\n💧 Water & ⚡ Electricity on site\n🚗 All-weather access roads\n🏫 Schools and malls nearby\n\n💰 Prices start from *Ksh 1.25M* per 1/8 acre.\nWould you like to view *full price details* or *book a site visit*?"

        "price" in lowercase || "cost" in lowercase ->
            "💰 *Land*: From *Ksh 1.25M* (1/8 acre)\n🏘 *Apartments*: From *Ksh 7.8M* (1–3 bedroom)\n\nFor full pricing, visit 🌐 www.redcarpethomes.co.ke or chat with us on WhatsApp 👉 +254 706 127 197."

        "apartment" in lowercase || "house" in lowercase || "home" in lowercase ->
            "🏡 Available apartments in *Runda, Lavington, Kilimani, Kileleshwa, Westlands*.\n\nFeatures:\n🛏 Bedrooms (1–3)\n🚿 Bathrooms\n🛋 Living area & common area\n🌇 Balcony with views\n\nNearby:\n🛍 Shopping Centres, 🍴 Restaurants, 🏫 Schools, 🏥 Hospitals, ⛽ Petrol Stations.\n\nWould you like me to *share available units* in a preferred area?"

        "unit" in lowercase || "available" in lowercase ->
            "Currently, we have *modern units in Kilimani, Westlands, and Runda*.\nEach comes with modern finishes and great views.\nFor complete listings, visit 🌐 www.redcarpethomes.co.ke or text us on WhatsApp 👉 +254 706 127 197."

        "visit" in lowercase || "view" in lowercase || "book" in lowercase ->
            "You can easily schedule a viewing 👀.\n📞 WhatsApp us at +254 706 127 197\n📧 Email: rachael@theredcarpethomes.co.ke\n🌐 Visit: www.redcarpethomes.co.ke"

        "contact" in lowercase || "call" in lowercase || "whatsapp" in lowercase || "email" in lowercase ->
            "📞 WhatsApp: +254 706 127 197\n📧 Email: rachael@theredcarpethomes.co.ke\n🌐 Visit: www.redcarpethomes.co.ke"

        "payment" in lowercase || "mortgage" in lowercase ->
            "💳 We offer flexible payment plans and mortgage assistance for both land and apartments. You can begin with a deposit and clear in installments."

        else ->
            "I'm your *Red Carpet Homes Assistant* 😊.\nYou can ask about available *land*, *apartments*, *prices*, or how to *schedule a visit*."
    }
}