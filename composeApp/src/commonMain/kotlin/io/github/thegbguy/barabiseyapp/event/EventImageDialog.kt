package io.github.thegbguy.barabiseyapp.event

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.thegbguy.barabiseyapp.components.ZoomableAsyncImage

@Composable
fun EventImageDialog(
    image: Any?,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize(Alignment.Center),
            shape = MaterialTheme.shapes.large
        ) {
            ZoomableAsyncImage(
                model = image,
                contentDescription = "Event Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 400.dp)
            )
        }
    }
}