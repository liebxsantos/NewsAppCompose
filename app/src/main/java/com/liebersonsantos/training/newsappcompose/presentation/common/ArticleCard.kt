package com.liebersonsantos.training.newsappcompose.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.model.Source
import com.liebersonsantos.training.newsappcompose.presentation.Dimens.ArticleCardSize
import com.liebersonsantos.training.newsappcompose.ui.theme.NewsAppComposeTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Row(modifier = modifier.clickable { onClick() }) {
        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context = context).data(article.urlToImage),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewsAppComposeTheme(dynamicColor = false) {
        ArticleCard(
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours",
                source = Source(
                    id = "", name = "BBC"
                ),
                title = "Her train broke down. Her phone died. And then she met her Saver in a",
                url = "",
                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
            ), onClick = {}
        )
    }
}