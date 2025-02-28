extends Control

var volume_slider: Slider

func _ready() -> void:
	# Obtener el slider correctamente
	volume_slider = $VBoxContainer/Volumen

	# Configurar el slider con valores adecuados
	volume_slider.min_value = -30  # Silencio
	volume_slider.max_value = 0    # Volumen normal

	# Obtener el volumen actual desde AudioServer
	var bus_index = AudioServer.get_bus_index("Master")  
	var current_volume = AudioServer.get_bus_volume_db(bus_index)

	# Sincronizar volumen del slider con el bus de audio
	volume_slider.value = current_volume  

	# Conectar la señal del slider
	volume_slider.value_changed.connect(_on_volume_changed)

	# Conectar los botones adicionales
	$VBoxContainer/Controlador.pressed.connect(_on_controls_button_pressed)
	$VBoxContainer/Creditos.pressed.connect(_on_credit_button_pressed)
	$atras.pressed.connect(_on_back_button_pressed)

func _on_volume_changed(value: float) -> void:
	# Aplicar el volumen al bus de audio
	AudioServer.set_bus_volume_db(AudioServer.get_bus_index("Master"), value)

	# Guardar el volumen para futuras escenas
	ProjectSettings.set("audio/volume_master", value)
	ProjectSettings.save()

# Ir a la pantalla de controles
func _on_controls_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)
	get_tree().change_scene_to_file("res://src/escenas/controles.tscn")

# Ir a la pantalla de créditos
func _on_credit_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)
	get_tree().change_scene_to_file("res://src/escenas/creditos.tscn")

# Volver a la escena anterior
func _on_back_button_pressed() -> void:
	var previous_scene_path = get_tree().get_meta("previous_scene", "res://src/escenas/pausa.tscn")
	
	if previous_scene_path == "res://src/escenas/pausa.tscn":
		queue_free()  # Cierra el menú de ajustes
	else:
		get_tree().change_scene_to_file(previous_scene_path)
