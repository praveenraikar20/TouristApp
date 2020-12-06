package com.example.mytravelapplication

data class ResponseData(
        val results: List<Result>,
        val summary: Summary
)
data class Result(
        val address: Address,
        val boundingBox: BoundingBox,
        val dataSources: DataSources,
        val entityType: String,
        val id: String,
        val position: Position,
        val score: Double,
        val type: String,
        val viewport: Viewport
)
data class Summary(
        val fuzzyLevel: Int,
        val numResults: Int,
        val offset: Int,
        val query: String,
        val queryTime: Int,
        val queryType: String,
        val totalResults: Int
)
data class Address(
        val country: String,
        val countryCode: String,
        val countryCodeISO3: String,
        val countrySecondarySubdivision: String,
        val countrySubdivision: String,
        val freeformAddress: String,
        val municipality: String
)
data class BoundingBox(
        val btmRightPoint: BtmRightPoint,
        val topLeftPoint: TopLeftPoint
)
data class DataSources(
        val geometry: Geometry
)
data class Position(
        val lat: Double,
        val lon: Double
)
data class Viewport(
        val btmRightPoint: BtmRightPointX,
        val topLeftPoint: TopLeftPointX
)
data class BtmRightPoint(
        val lat: Double,
        val lon: Double
)
data class TopLeftPoint(
        val lat: Double,
        val lon: Double
)
data class Geometry(
        val id: String
)
data class BtmRightPointX(
        val lat: Double,
        val lon: Double
)
data class TopLeftPointX(
        val lat: Double,
        val lon: Double
)