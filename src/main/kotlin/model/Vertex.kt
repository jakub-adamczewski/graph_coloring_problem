package model

data class Vertex(val id: Int, val adjacentVertices:  MutableList<Vertex>)