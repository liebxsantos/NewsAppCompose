package com.liebersonsantos.training.newsappcompose.presentation.detail

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.liebersonsantos.training.newsappcompose.R
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.model.Source
import com.liebersonsantos.training.newsappcompose.presentation.Dimens.ArticleImageHeight
import com.liebersonsantos.training.newsappcompose.presentation.Dimens.MediumPadding1
import com.liebersonsantos.training.newsappcompose.presentation.detail.components.DetailTopBar
import com.liebersonsantos.training.newsappcompose.ui.theme.NewsAppComposeTheme

@Composable
fun DetailScreen(
    article: Article,
    event: (DetailEvent) -> Unit,
    navigationUP: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailTopBar(article, context, event, navigationUP)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1,
            )
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(MediumPadding1))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }

    }
}

@Composable
private fun DetailTopBar(
    article: Article,
    context: Context,
    event: (DetailEvent) -> Unit,
    navigationUP: () -> Unit
) {
    DetailTopBar(
        onBrowser = {
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(article.url)
                if (it.resolveActivity(context.packageManager) != null) {
                    context.startActivity(it)
                }
            }
        },
        onSharedClick = {
            Intent(Intent.ACTION_SEND).also {
                it.putExtra(Intent.EXTRA_TEXT, article.url)
                it.type = "text/plan"
                if (it.resolveActivity(context.packageManager) != null) {
                    context.startActivity(it)
                }
            }
        },
        onBookmarkClick = { event(DetailEvent.UpsertDeleteArticle(article)) },
        onBackClick = {
            navigationUP()
        })
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailScreenPreview() {
    NewsAppComposeTheme {
        DetailScreen(
            article = Article(
                author = "",
                title = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                description = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                content = stringResource(id = R.string.content),
                publishedAt = "2023-06-16T22:24:33Z",
                source = Source(
                    id = "", name = "bbc"
                ),
                url = stringResource(id = R.string.url),
                urlToImage = stringResource(id = R.string.image_2)
            ),
            event = {},
            navigationUP = {}
        )
    }
}
