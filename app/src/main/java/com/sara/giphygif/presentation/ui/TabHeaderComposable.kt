package com.sara.giphygif.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHeaderComposable(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    tabItems: List<String>
) {

    TabRow(

        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.padding(vertical = 18.dp),
        backgroundColor = MaterialTheme.colors.background,

        contentColor = MaterialTheme.colors.primary,

        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 3.dp,
                color = Color.DarkGray
            )
        }
    ) {

        tabItems.forEachIndexed { index, tabName ->

            Tab(selected = pagerState.currentPage == index, onClick = {

                coroutineScope.launch {

                    pagerState.animateScrollToPage(index)
                }

            }, modifier = Modifier.padding(8.dp)) {

                if (pagerState.currentPage == index) {

                    Text(
                        text = tabName,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        text = tabName,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.primary
                    )

                }


            }
        }

    }

}



