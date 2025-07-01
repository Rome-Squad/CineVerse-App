package com.giraffe.profile.mycollection.createcollection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.Theme

@Composable
fun CreateCollectionContent(
    modifier: Modifier = Modifier,
    leadingIcon : Int,
    hintText : String,
    value : String,
    title : String,
    onValueChange : (String) -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = title,
            style = Theme.textStyle.body.md.regular,
            color = Theme.color.shade.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CollectionTextField(
            leadingIcon = leadingIcon,
            hintText = hintText,
            value = value,
            onValueChange = onValueChange
        )


            //  CollectionsButtons()

    }

}
@Preview(showBackground = true)
@Composable
fun CreateCollectionContentPreview(){
    CreateCollectionContent(
        leadingIcon = R.drawable.outline_folder,
        hintText = "e.g. My Watchlist",
        value = "",
        title = "Collection Name",
        onValueChange = {}
    ) 
}