package com.wldnasyrf.ds.data.local.datastore.model

import com.google.gson.annotations.SerializedName

data class UserPreferencesModel (

    @field:SerializedName("token")
    val token: String,
)