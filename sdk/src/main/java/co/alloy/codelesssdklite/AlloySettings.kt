package co.alloy.codelesssdklite

import com.google.gson.annotations.SerializedName

data class AlloySettings(
    @SerializedName("key") val apiKey: String,
    @SerializedName("journeyToken") val journeyToken: String,
    @SerializedName("journeyApplicationToken") val journeyApplicationToken: String? = null,
    @SerializedName("isSingleEntity") val isSingleEntity: Boolean = false,
    @SerializedName("entityToken") val entityToken: String? = null,
    @SerializedName("externalEntityId") val externalEntityId: String? = null,
    @SerializedName("journeyData") val journeyData: JourneyData? = null,
    @SerializedName("production") val production: Boolean = false,
    @SerializedName("color") val color: Any? = null,
    @SerializedName("customStyle") val customStyle: Any? = null,
    @SerializedName("isNext") val isNext: Boolean,
) {
    data class Entity(
        @SerializedName("entity_type") val entityType: String,
        @SerializedName("branch_name") val branchName: String?,
        @SerializedName("data") val entityData: EntityData,
    ) {
        data class EntityData(
            @SerializedName("name_first") val firstName: String,
            @SerializedName("name_last") val lastName: String,
        )
    }

    data class JourneyData(
        @SerializedName("entities") val entities: List<Entity>,
        @SerializedName("external_group_id") val externalGroupId: String?,
        @SerializedName("external_product_id") val externalProductId: String?,
        @SerializedName("do_await_additional_entities") val doAwaitAdditionalEntities: Boolean?,
    )
}
