package com.example.stackoverflowuser.model

import com.example.stackoverflowuser.constants.Constants
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tho nguyen on 2019-05-11.
 */
class ReputationsResponse : BaseModel {
    @SerializedName("items")
    var items: MutableList<Reputation>? = null
}

open class Reputation : BaseModel {
    @SerializedName("creation_date")
    var creationDate: Long? = null
    @SerializedName("reputation_history_type")
    var reputationHistoryType: String? = null
    @SerializedName("reputation_change")
    var reputationChange: Int? = null
    @SerializedName("post_id")
    var postId: Int? = null
}