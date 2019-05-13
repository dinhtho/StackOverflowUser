package com.example.stackoverflowuser.model

import com.example.stackoverflowuser.constants.Constants
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tho nguyen on 2019-05-11.
 */
class UsersResponse : BaseModel {
    @SerializedName("items")
    var items: MutableList<User>? = null
}

open class User : BaseModel, RealmObject() {
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

    @SerializedName("user_id")
    var userId: String? = null

    var isBookmarked = false
}