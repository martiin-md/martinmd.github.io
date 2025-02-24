extends Node

@onready var paddle = get_parent().get_node("PadlePlayer")

func _ready():
	# Conexión de señales de los botones
	$"TouchScreenButton".connect("pressed", Callable(self, "_on_left_pressed"))
	$"TouchScreenButton".connect("released", Callable(self, "_on_left_released"))

	$"TouchScreenButton2".connect("pressed", Callable(self, "_on_right_pressed"))
	$"TouchScreenButton2".connect("released", Callable(self, "_on_right_released"))

func _on_left_pressed():
	paddle.set_move_direction(-1)

func _on_left_released():
	paddle.set_move_direction(0)

func _on_right_pressed():
	paddle.set_move_direction(1)

func _on_right_released():
	paddle.set_move_direction(0)
