package com.example.stock_market_app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.stock_market_app.presentation.company_listings.NavGraphs
import com.example.stock_market_app.ui.theme.Stock_Market_AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stock_Market_AppTheme {
                    Surface(Modifier.fillMaxSize(), color = Color.White) {
                        DestinationsNavHost(navGraph = NavGraphs.root)
                    }
                }
            }
        }
}
