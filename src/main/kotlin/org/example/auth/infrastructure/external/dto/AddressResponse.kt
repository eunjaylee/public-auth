package org.example.auth.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)

/**
 * {
"meta": {
"total_count": 4,
"pageable_count": 4,
"is_end": true
},
"documents": [
{
"address_name": "전북 익산시 부송동 100",
"y": "35.97664845766847",
"x": "126.99597295767953",
"address_type": "REGION_ADDR",
"address": {
"address_name": "전북 익산시 부송동 100",
"region_1depth_name": "전북",
"region_2depth_name": "익산시",
"region_3depth_name": "부송동",
"region_3depth_h_name": "삼성동",
"h_code": "4514069000",
"b_code": "4514013400",
"mountain_yn": "N",
"main_address_no": "100",
"sub_address_no": "",
"x": "126.99597295767953",
"y": "35.97664845766847"
},
"road_address": {
"address_name": "전북 익산시 망산길 11-17",
"region_1depth_name": "전북",
"region_2depth_name": "익산시",
"region_3depth_name": "부송동",
"road_name": "망산길",
"underground_yn": "N",
"main_building_no": "11",
"sub_building_no": "17",
"building_name": "",
"zone_no": "54547",
"y": "35.976749396987046",
"x": "126.99599512792346"
}
},
...
]
}
 */

data class AddressResponse(
    var meta: Meta = Meta(),
    var documents: List<Document> = emptyList(),
)

data class Meta(
    var is_end: Boolean = true,
    var pageable_count: Int = 0,
    var total_count: Long = 0,
)

data class Document(
//    var address : Address = Address(),
    var address_name: String = "",
    var address_type: String = "",
    var road_address: String? = null,
    var x: String,
    var y: String,
)
