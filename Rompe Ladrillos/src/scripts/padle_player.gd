extends CharacterBody2D

const SPEED = 600
@onready var initial_position_y = position.y
var move_direction := 0  # Se actualizará desde el controlador virtual

func _ready() -> void:
	# Se busca el ControladorVirtual y registrarse
	var controlador = get_parent().get_node("ControladorVirtual")
	if controlador:
		controlador.paddle = self
		print("Paleta conectada al Controlador Virtual")

func _physics_process(delta: float) -> void:
	position.y = initial_position_y  # Se mantiene la paleta en la misma posición Y

	# Si hay input táctil, se usa, sino, se usa el teclado
	var direction = move_direction if move_direction != 0 else Input.get_axis("ui_left", "ui_right")

	velocity.x = direction * SPEED
	move_and_slide()

# Función para actualizar la dirección desde el controlador
func set_move_direction(dir: int):
	move_direction = dir
