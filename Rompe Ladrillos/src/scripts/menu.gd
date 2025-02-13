extends Control

func _ready() -> void:
	$VBoxContainer/StartButton.pressed.connect(_on_start_button_pressed)
	$VBoxContainer/ExitButton.pressed.connect(_on_exit_button_pressed)

func _on_start_button_pressed() -> void:
	get_parent().play_game()  # Llama a `play_game()` en el nodo padre (Main)

func _on_exit_button_pressed() -> void:
	get_tree().quit()  # Cierra el juego
