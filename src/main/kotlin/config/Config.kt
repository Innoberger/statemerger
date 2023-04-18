package config

import kotlinx.serialization.Serializable

@Serializable
data class Config(val states: List<State>)