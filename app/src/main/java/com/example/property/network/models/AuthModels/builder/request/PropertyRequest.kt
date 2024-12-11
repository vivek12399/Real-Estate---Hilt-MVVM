package com.example.property.network.models.AuthModels.builder.request

import android.graphics.Bitmap

data class PropertyRequest (
    var token:String,
    var propertyName:String,
    var propertyType:String,
    var price:String,
    var buildYear:String,
    var propertyStatus:String,
    var description:String,
    var facalities:String,
    var image1:Bitmap?,
    var image2: Bitmap?,
    var image3:Bitmap?,
    var image4:Bitmap?,
    var image5:Bitmap?
)