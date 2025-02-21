@tool
extends Node2D

var circle_position = Vector2.ZERO
var circle_radius = 8
var circle_color := Color("#fff")

func _draw() -> void:

	draw_circle(circle_position, circle_radius, circle_color)
