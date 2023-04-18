package config

import kotlinx.serialization.Serializable

@Serializable
data class State(var name: String, val cities: List<String>)