extends Node2D

var max_vidas = 3
var vidas_actuales = 3

func _ready():
	actualizar_vidas()

func perder_vida():
	vidas_actuales = clamp(vidas_actuales - 1, 0, max_vidas)
	actualizar_vidas()
	return vidas_actuales > 0

func actualizar_vidas():
	for i in range(get_child_count()):
		get_child(i).visible = i < vidas_actuales
