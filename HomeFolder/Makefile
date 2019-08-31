JC = javac
JVM = java
GRL = lib/core.jar
AVTC = class/Cards/Avatars
AVTS = src/cards/avatars
CDS = src/cards
BIN = -d bin 
ALLLIB = bin:lib/core.jar

compileAvatars: 
	$(JC) $(BIN) -cp bin $(CDS)/Avatar.java $(AVTS)/*.java

run: 
	$(JVM) -cp $(ALLLIB) main.Backbone

compileCard:
	$(JC) $(BIN) src/card/Card.java

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