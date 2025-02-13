extends Node

func _process(delta: float) -> void:
	$Contador/LabelScore.text = str(Global.score)

func _ready() -> void:
	$Menu.show()  # Asegura que el menú se muestra al iniciar el juego.

func play_game():
	$Menu.hide()  # Oculta el menú cuando comienza el juego.
	$Ball.start()
	$PadlePlayer.position = $Markets/MarkerPaddle.position
	$Ball.position = $Markets/MarkerBall.position

func _on_area_down_body_entered(body: Node2D) -> void:
	play_game()
	Global.reset_score()
	$Menu.show()  # Muestra el menú si el juego termina.
