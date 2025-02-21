extends Node

var score := 0
var lives := 3  # Número de vidas inicial

func increment_score():
	score += 1

func reset_score():
	score = 0

func decrement_life():
	lives -= 1
	if lives <= 0:
		lives = 3  # Reiniciamos vidas para una nueva partida
