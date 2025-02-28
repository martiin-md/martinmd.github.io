extends Control

func _ready() -> void:
	$VBoxContainer/reanudar.pressed.connect(_on_resume_pressed)
	$VBoxContainer/salir.pressed.connect(_on_exit_pressed)

func _on_resume_pressed():
	get_tree().paused = false
	self.hide()  # Oculta el menú de pausa

func _on_exit_pressed():
	get_tree().paused = false
	get_tree().quit()

func _on_settings_pressed():
	get_tree().paused = true  # Mantener el juego pausado

	# Verificar si la escena de ajustes ya existe antes de añadirla
	if not get_node_or_null("SettingsMenu"):
		var settings_scene = preload("res://src/escenas/settings_menu.tscn").instantiate()
		settings_scene.name = "SettingsMenu"  # Asegura que la referencia se mantenga
		add_child(settings_scene)
		settings_scene.visible = true
