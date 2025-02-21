extends Node

var total_blocks = 0  # Contador de bloques
var espacio_x = 10  # Espacio entre columnas
var espacio_y = 10  # Espacio entre filas
var settings_scene = preload("res://src/escenas/settings_menu.tscn")
var settings_instance

func _ready() -> void:
	$Menu.show()
	count_blocks()  # Contamos los bloques al iniciar
	update_lives_display()  # Actualiza el contador de vidas en pantalla
	$Menu/ajustes.pressed.connect(_on_settings_pressed)
	$reinicio.pressed.connect(_on_restart_pressed)  # Aquí es donde debe conectarse
	$ajustes.pressed.connect(_on_settings_pressed)


func _process(delta: float) -> void:
	$Contador/LabelScore.text = str(Global.score)
	if Input.is_action_pressed("ui_accept"):  # ui_accept es Space por defecto
		if Global.lives <= 0 or total_blocks <= 0:  # Si el jugador ha perdido o ha ganado
			restart_game()

func play_game():
	$Menu.hide()
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
	Global.lives -= 1  # 🔹 Restar vida correctamente
	update_lives_display()

	if Global.lives <= 0:
		show_game_over()
	else:
		reset_ball()

func count_blocks():
	total_blocks = get_tree().get_nodes_in_group("blocks").size()

func block_destroyed():
	total_blocks -= 1
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

	# 🔄 Asegurar que los bloques están bien alineados ANTES de resetear paddle y bola
	reset_blocks()

	reset_paddle()
	reset_ball()

	update_lives_display()
	$Contador/LabelScore.text = str(Global.score)

	play_game()

func reset_blocks():
	var bloques_container = $Bricks  # Asegúrate de que esta sea la ruta correcta

	# 🚨 Eliminar todos los bloques antes de crear nuevos
	print("Eliminando bloques...")
	for block in bloques_container.get_children():
		block.queue_free()
	
	# Esperar un poco antes de crear los nuevos bloques

	# 🔹 Cargar la escena de los bloques
	var brick_scene = load("res://src/escenas/brick.tscn")  # Ajusta si la ruta es diferente

	# 🔹 Variables para organizar los bloques correctamente
	var columnas = 13  # Ajusta según el diseño de la imagen
	var filas = 4
	var bloque_ancho = 64  # Ajusta según el tamaño de tus bloques
	var bloque_alto = 32
	var espacio_x = 20  # MÁS espacio entre bloques horizontal
	var espacio_y = 15  # MÁS espacio entre bloques vertical
	var margen_x = 190  # Ajuste para centrar los bloques
	var margen_y = 80  # Ajuste para que no estén pegados arriba

	# 🔹 Calcular la posición inicial para centrar los bloques
	var pantalla_ancho = get_viewport().get_visible_rect().size.x
	var inicio_x = (pantalla_ancho - (columnas * (bloque_ancho + espacio_x))) / 2
	var inicio_y = margen_y

	print("Tamaño pantalla:", pantalla_ancho)
	print("Espacio entre bloques X:", espacio_x, " Y:", espacio_y)

	# 🔹 Crear bloques alineados con espacio entre ellos
	for i in range(filas):  
		for j in range(columnas):
			var new_brick = brick_scene.instantiate()
			new_brick.position = Vector2(
				inicio_x + j * (bloque_ancho + espacio_x),  
				inicio_y + i * (bloque_alto + espacio_y)
			)
			bloques_container.add_child(new_brick)
			print("Bloque en:", new_brick.position)  # Ver posición exacta en consola

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

# Función que se llama cuando se presiona el TouchScreenButton
func _on_restart_pressed() -> void:
	print("Reiniciando el juego desde el botón de pantalla táctil...")
	restart_game()
