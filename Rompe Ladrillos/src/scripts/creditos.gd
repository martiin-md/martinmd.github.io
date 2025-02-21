extends Control

func _ready() -> void:
	# Botón para regresar a la escena anterior
	$atras.connect("pressed", _on_back_button_pressed)

func _on_back_button_pressed() -> void:
	if get_tree().has_meta("previous_scene"):
		var previous_scene_path = get_tree().get_meta("previous_scene")
		var previous_scene = load("res://src/escenas/settings_menu.tscn")
		if previous_scene is PackedScene:
			get_tree().change_scene_to_packed(previous_scene)
		else:
			print("Error: No se pudo cargar la escena anterior")
	else:
		print("No hay una escena anterior guardada, volviendo al menú principal")
		var menu_scene = load("res://src/escenas/menu.tscn")
		if menu_scene is PackedScene:
			get_tree().change_scene_to_packed(menu_scene)
		else:
			print("Error: No se pudo cargar la escena del menú")
