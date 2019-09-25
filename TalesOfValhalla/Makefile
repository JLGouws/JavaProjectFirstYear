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
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/pregame/*.java

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

compileAI:
	$(JC) $(BIN) -cp bin:$(GRL) src/AI/*.java	

compileGraphics:
	$(JC) $(BIN) -cp $(GRL):bin src/graphics/GraphicsHandler.java src/graphics/pregame/Menu.java src/graphics/game/Game.java

compileAll:#compiles all files
	make compileCard
	make compileAvatars
	make compileMain
	make compileGH
	make compilePregame
	make compileDeck
	make compilePlayer
	make compileGame
	make compileBoard
	make compileGraphics
	make compileAI

generate_docs:
	javadoc -d JavaDocs/graphics/pregame/ -cp src:lib/core.jar graphics.pregame
	javadoc -d JavaDocs/graphics -cp src:lib/core.jar graphics
	javadoc -d JavaDocs/deck -cp src:lib/core.jar deck
	javadoc -d JavaDocs/graphics/game -cp src:lib/core.jar graphics.game
	javadoc -d JavaDocs/player -cp src:lib/core.jar player
	javadoc -d JavaDocs/board -cp src:lib/core.jar board
	javadoc -d JavaDocs/cards -cp src:lib/core.jar cards
	javadoc -d JavaDocs/cards/avatars -cp src:lib/core.jar cards.avatars
	javadoc -d JavaDocs/card -cp src:lib/core.jar card
	javadoc -d JavaDocs/main -cp src:lib/core.jar main