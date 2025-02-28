extends Control

func _ready() -> void:
	# Verificar que los botones existen antes de conectar
	if $"VBoxContainer/StartButton" and $"VBoxContainer/ExitButton":
		$VBoxContainer/StartButton.pressed.connect(_on_start_button_pressed)
		$VBoxContainer/ExitButton.pressed.connect(_on_exit_button_pressed)
	else:
		print("Error: Botones no encontrados en el menú.")

func _on_start_button_pressed() -> void:
	# Verificar que el nodo padre tiene la función `play_game`
	if get_parent().has_method("play_game"):
		get_parent().play_game()
	else:
		print("Error: No se encontró el método 'play_game' en el nodo padre.")

func _on_exit_button_pressed() -> void:
	get_tree().quit()  # Cierra el juego
# En el menú principal
func _on_settings_button_pressed() -> void:
	# Cargar la escena de ajustes
	var settings_scene = preload("res://src/escenas/settings_menu.tscn").instantiate()
	get_tree().current_scene.add_child(settings_scene)
	settings_scene.position = Vector2(100, 100)
