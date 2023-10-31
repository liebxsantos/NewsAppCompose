package com.liebersonsantos.training.newsappcompose.presentation.detail.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.liebersonsantos.training.newsappcompose.R
import com.liebersonsantos.training.newsappcompose.ui.theme.NewsAppComposeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    onBrowser: () -> Unit,
    onSharedClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body)
        ),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onBookmarkClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null
                )
            }
            IconButton(onClick = { onSharedClick() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
            IconButton(onClick = { onBrowser() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailTopBarPreview() {
    NewsAppComposeTheme {
        DetailTopBar(
            onBrowser = { /*TODO*/ },
            onSharedClick = { /*TODO*/ },
            onBookmarkClick = { /*TODO*/ }) {
            
        }
    }
}