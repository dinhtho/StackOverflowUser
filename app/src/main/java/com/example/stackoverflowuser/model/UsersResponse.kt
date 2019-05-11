package com.example.stackoverflowuser.model

import com.google.gson.annotations.SerializedName

/**
 * Created by tho nguyen on 2019-05-11.
 */
class UsersResponse : BaseModel() {
    @SerializedName("items")
    var items: List<User>? = null
}

class User : BaseModel() {
    @SerializedName("display_name")
    var displayName: String? = null
    @SerializedName("profile_image")
    var profileImage: String? = null
    @SerializedName("reputation")
    var reputation: Int? = null
    @SerializedName("location")
    var location: String? = null
    @SerializedName("last_access_date")
    var lastAccessDate: Long? = null
}