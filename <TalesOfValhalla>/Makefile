JC = javac
JVM = java
GRL = lib/core.jar
AVTC = class/Cards/Avatars
AVTS = src/cards/avatars
CDS = src/cards
BIN = -d bin 
ALLLIB = bin:lib/core.jar

run: 
	$(JVM) -cp $(ALLLIB) main.Backbone

runDeck: 
	$(JVM) -cp bin deck.TestDeck

compileAvatars: 
	$(JC) $(BIN) -cp bin $(CDS)/Avatar.java $(AVTS)/*.java

compileCard:
	$(JC) $(BIN) -cp bin src/card/Card.java

compileMain:
	$(JC) $(BIN) -cp bin:$(GRL) src/main/Backbone.java

compilePregame:
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/pregame/Menu.java

compileGame:
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/game/Game.java

compileGH:
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/GraphicsHandler.java

compileDeck:
	$(JC) $(BIN) -cp bin src/deck/*.java

compilePlayer:
	$(JC) $(BIN) -cp bin src/player/*.java
	
compileBoard:
	$(JC) $(BIN) -cp bin src/board/Board.java

compileGraphics:
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/GraphicsHandler.java src/graphics/pregame/Menu.java src/graphics/game/Game.java

compileAll:#compiles all files
	make compileCard
	make compileAvatars
	make compileMain
	make compilePregame
	make compileGame
	make compileGH
	make compileDeck
	make compilePlayer
	make compileBoard
	make compileGraphics