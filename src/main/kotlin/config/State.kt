package config

import kotlinx.serialization.Serializable

@Serializable
data class State(val name: String, val cities: List<String>)