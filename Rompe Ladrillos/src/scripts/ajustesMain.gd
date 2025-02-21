extends TouchScreenButton

var music_player : AudioStreamPlayer
var volume_slider : Slider

func _ready() -> void:
	# Asegurar que el AudioStreamPlayer existe
	music_player = $VBoxContainer/Volumen/AudioStreamPlayer
	volume_slider = $VBoxContainer/Volumen  # Esto es un Slider, así que debe estar bien

	# Configurar el slider


	# Conectar los eventos de las señales
	volume_slider.value_changed.connect(_on_volume_changed)
	$VBoxContainer/Controlador.connect("pressed", _on_controls_button_pressed)
	$VBoxContainer/Creditos.connect("pressed", _on_credit_button_pressed)

	# Conectar un botón para volver atrás (si lo tienes en la escena)
	$atras.connect("pressed", _on_back_button_pressed)

func _on_volume_changed(value : float) -> void:
	music_player.volume_db = linear_to_db(value)

# Función para cambiar a la escena de controles
func _on_controls_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)  # Guardar escena actual
	var controls_scene = load("res://src/escenas/controles.tscn")
	if controls_scene is PackedScene:
		get_tree().change_scene_to_packed(controls_scene)
	else:
		print("Error: No se pudo cargar la escena de controles")

# Función para cambiar a la escena de créditos
func _on_credit_button_pressed() -> void:
	get_tree().set_meta("previous_scene", get_tree().current_scene.scene_file_path)  # Guardar escena actual
	var credit_scene = load("res://src/escenas/creditos.tscn")
	if credit_scene is PackedScene:
		get_tree().change_scene_to_packed(credit_scene)
	else:
		print("Error: No se pudo cargar la escena de créditos")

# Función para volver atrás
func _on_back_button_pressed() -> void:
	get_tree().set_meta("previos_scena",get_tree().current_scene.scene_file_path)
	var credit_scene = load("res://src/escenas/main.tscn")
	if get_tree().has_meta("previous_scene"):
		if credit_scene is PackedScene:
			get_tree().change_scene_to_packed(credit_scene)
		else:
			print("Error: No se pudo cargar la escena de créditos")
