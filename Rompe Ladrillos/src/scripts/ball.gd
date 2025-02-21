extends CharacterBody2D

const SPEED = 500.0
	
func start():
	velocity = Vector2.DOWN * SPEED

func _physics_process(delta: float) -> void:

	var collision = move_and_collide(velocity * delta)
	if collision:

		var ref = collision.get_collider() as StaticBody2D
		if ref and ref.is_in_group("brick_group"):
			ref.destroy_brick()
			Global.increment_score()

		velocity = velocity.bounce(collision.get_normal())
