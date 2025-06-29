package com.giraffe.profile.components.mycollections.createCollection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.presentation.R

@Composable
fun CreateCollectionContent(
    modifier: Modifier = Modifier,
    leadingIcon : Int,
    hintText : String,
    value : String,
    onValueChange : (String) -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth()
    ) {

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
        onValueChange = {}
    ) 
}