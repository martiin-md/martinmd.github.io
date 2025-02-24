extends Control

func _ready() -> void:
	$VBoxContainer/reanudar.pressed.connect(_on_resume_pressed)
	$VBoxContainer/ajustes.pressed.connect(_on_settings_pressed)
	$VBoxContainer/salir.pressed.connect(_on_exit_pressed)



func _on_resume_pressed():
	print("Botón de reanudar presionado") 
	get_tree().paused = false
	self.hide()

func _on_exit_pressed():
	print("Botón de salir presionado")  
	get_tree().paused = false
	get_tree().quit()

func _on_settings_pressed():
	print("Botón de ajustes presionado")  
	var settings_scene = preload("res://src/escenas/settings_menu.tscn").instantiate()
	add_child(settings_scene)
	settings_scene.visible = true  
