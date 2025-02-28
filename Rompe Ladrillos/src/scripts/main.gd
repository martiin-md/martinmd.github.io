extends Node

var total_blocks = 0  # Contador de bloques
var espacio_x = 10  # Espacio entre columnas
var espacio_y = 10  # Espacio entre filas
var settings_scene = preload("res://src/escenas/settings_menu.tscn")
var settings_instance
var pause_scene = preload("res://src/escenas/pausa.tscn")  # Ajusta la ruta si es necesario
var pause_instance


func _ready() -> void:
	$Menu.show()
	$pausa.hide()  
	$reinicio.hide()
	count_blocks()  
	update_lives_display()
	$ajustes.pressed.connect(_on_settings_pressed)
	$reinicio.pressed.connect(_on_restart_pressed)
	$ajustes.pressed.connect(_on_settings_pressed)
	$pausa.pressed.connect(_on_pause_pressed)

func _process(delta: float) -> void:
	$Contador/LabelScore.text = str(Global.score)

	# Detección de tecla ESC y ESCAPE para pausar/reanudar
	if Input.is_action_just_pressed("ui_cancel"):  # "ui_cancel" es ESC para el menu
		if get_tree().paused:
			_on_resume_pressed()  # Reanudar si está en pausa
		else:
			_on_pause_pressed()  # Pausar si está en juego

	if Input.is_action_pressed("ui_accept"):  # ui_accept es espacio para reiniciar
		if Global.lives <= 0 or total_blocks <= 0:  # Si el jugador ha perdido o ha ganado
			restart_game()

func play_game():
	$Menu.hide()
	$pausa.show()
	$reinicio.show()
	$Ball.start()
	$PadlePlayer.position = $Markets/MarkerPaddle.position
	$Ball.position = $Markets/MarkerBall.position
	Global.reset_score()
	count_blocks()
	update_lives_display()

func _on_settings_pressed() -> void:
	if not settings_instance:
		settings_instance = settings_scene.instantiate()
		add_child(settings_instance)
	
	settings_instance.visible = true

func _on_area_down_body_entered(body: Node2D) -> void:
	# Si la bola cae, restamos una vida
	print("La bola cayó, restando una vida...")
	Global.lives -= 1  # Se resta la vida 
	update_lives_display()

	if Global.lives <= 0:
		show_game_over()
	else:
		reset_ball()

func count_blocks():
	total_blocks = $Bricks.get_child_count()
	print("Bloques totales:", total_blocks)

func block_destroyed():
	total_blocks -= 1
	print("Bloques restantes:", total_blocks)

	if total_blocks <= 0:
		show_win_message()

func show_win_message():
	print("¡Has ganado!")
	$WinLabel.visible = true  
	await get_tree().create_timer(2.0).timeout 
	restart_game()

func show_game_over():
	print("¡Game Over!")
	$GameOverLabel.visible = true
	await get_tree().create_timer(2.0).timeout
	restart_game()

func restart_game():
	print("🔄 Reiniciando juego...")

	Global.lives = 3
	Global.reset_score()

	$GameOverLabel.visible = false  
	$WinLabel.visible = false  

	reset_blocks()
	reset_paddle()
	reset_ball()
	update_lives_display()
	$Contador/LabelScore.text = str(Global.score)

	$pausa.hide()
	$reinicio.hide()
	$Menu.show()

func reset_blocks():
	var bloques_container = $Bricks

	# Eliminar bloques anteriores
	for block in bloques_container.get_children():
		block.queue_free()

	# Cargar y crear nuevos bloques
	var brick_scene = load("res://src/escenas/brick.tscn")
	var columnas = 13
	var filas = 4
	var bloque_ancho = 64
	var bloque_alto = 32
	var espacio_x = 20
	var espacio_y = 15
	var margen_x = 190
	var margen_y = 80

	var pantalla_ancho = get_viewport().get_visible_rect().size.x
	var inicio_x = (pantalla_ancho - (columnas * (bloque_ancho + espacio_x))) / 2
	var inicio_y = margen_y

	for i in range(filas):  
		for j in range(columnas):
			var new_brick = brick_scene.instantiate()
			new_brick.position = Vector2(
				inicio_x + j * (bloque_ancho + espacio_x),  
				inicio_y + i * (bloque_alto + espacio_y)
			)
			bloques_container.add_child(new_brick)

			# 🔹 Conectar la señal cuando se destruye un bloque
			new_brick.brick_destroyed.connect(block_destroyed)

	count_blocks()  # Actualiza el número de bloques

func reset_ball():
	print("Reubicando bola...")
	$Ball.position = $Markets/MarkerBall.position
	$Ball.start()

func reset_paddle():
	print("Reubicando la pala...")
	$PadlePlayer.position = $Markets/MarkerPaddle.position
	$PadlePlayer.visible = true

func update_lives_display():
	var vidas_ui = $Vidas/HBoxContainer 
	
	for i in range(3):  # Mostrar 3 vidas
		if i < Global.lives:
			vidas_ui.get_child(i).visible = true
		else:
			vidas_ui.get_child(i).visible = false

func _on_restart_pressed() -> void:
	print("Reiniciando el juego desde el botón de pantalla táctil...")
	restart_game()

func _on_pause_pressed() -> void:
	print("Juego en pausa...")

	if pause_instance == null or not is_instance_valid(pause_instance):
		pause_instance = pause_scene.instantiate()
		add_child(pause_instance)

	pause_instance.visible = true  

	# Ahora podemos pausar el juego
	await get_tree().process_frame  # se espera un frame antes de pausar
	get_tree().paused = true  

func _on_resume_pressed():
	get_tree().paused = false  # Se reanuda el juego
	if pause_instance and is_instance_valid(pause_instance):
		pause_instance.queue_free()  # Después se cierra la escena de pausa
		pause_instance = null  # Y se resetea la referencia
