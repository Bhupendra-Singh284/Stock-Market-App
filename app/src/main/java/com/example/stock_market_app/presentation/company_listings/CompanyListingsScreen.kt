package com.example.stock_market_app.presentation.company_listings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = viewModel.state.isRefreshing,
        onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) }
    )
    val state = viewModel.state
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
            .padding(10.dp)
            ) {
        OutlinedTextField(
            value = state.searchQuery, onValueChange = {
                viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it))
            }, modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, color = Color.Black)
            ,
        )
        Box(Modifier.pullRefresh(swipeRefreshState)) {
            LazyColumn(Modifier.fillMaxSize().padding(15.dp)) {
                items(viewModel.state.companies.size) { index ->
                    CompanyItem(

                        viewModel.state.companies[index],
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                //todo later
                            }
                            .padding(6.dp)
                    )
                    if (index < viewModel.state.companies.size) {
                        Divider(Modifier.padding(16.dp))
                    }
                }
            }
            PullRefreshIndicator(modifier = Modifier.align(Alignment.TopCenter), refreshing =  state.isRefreshing, state = swipeRefreshState)
        }

    }
}