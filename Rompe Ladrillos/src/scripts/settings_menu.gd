extends Control

var volume_slider: Slider

func _ready() -> void:
	# Se obtiene el slider correctamente
	volume_slider = $VBoxContainer/Volumen 

	# Configuración del slider con valores correctos
	volume_slider.min_value = -30  # Silencio
	volume_slider.max_value = 0    # Volumen normal

	# Recupera el volumen desde AudioServer (no desde AudioStreamPlayer)
	var bus_index = AudioServer.get_bus_index("Master")  # Se cambia "Master" por el bus correcto
	var current_volume = AudioServer.get_bus_volume_db(bus_index)

	# Sincronizar volumen del slider con el bus de audio
	volume_slider.value = current_volume  

	# Conexión de la señal del slider
	volume_slider.value_changed.connect(_on_volume_changed)

	# Conexión botones adicionales
	$VBoxContainer/Controlador.pressed.connect(_on_controls_button_pressed)
	$VBoxContainer/Creditos.pressed.connect(_on_credit_button_pressed)
	$atras.pressed.connect(_on_back_button_pressed)

func _on_volume_changed(value: float) -> void:
	# Se aplicar volumen al bus de audio (no al AudioStreamPlayer individual)
	AudioServer.set_bus_volume_db(AudioServer.get_bus_index("Master"), value)

	# Se guarda el volumen para mantenerlo en futuras escenas
	ProjectSettings.set("audio/volume_master", value)
	ProjectSettings.save()

# Función para cambiar a la escena de controles
func _on_controls_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)
	get_tree().change_scene_to_file("res://src/escenas/controles.tscn")

# Función para cambiar a la escena de créditos
func _on_credit_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)
	get_tree().change_scene_to_file("res://src/escenas/creditos.tscn")

# Función para volver atrás
func _on_back_button_pressed() -> void:
	get_tree().change_scene_to_file("res://src/escenas/main.tscn")
