extends StaticBody2D

# Señal para notificar la destrucción del bloque
signal brick_destroyed

# Método que destruye el bloque o ladrillo.
func destroy_brick():
	$CollisionShape2D.set_deferred("disabled", true)
	
	var tween: Tween = get_tree().create_tween()
	tween.tween_property(self, "modulate:a", 0.0, 0.5)
	tween.tween_callback(finish_animation)

# Método que se llama cuando la animación de destrucción termina.
func finish_animation():
	emit_signal("brick_destroyed")  # 🔹 Notifica que el bloque fue destruido
	queue_free()
