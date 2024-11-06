# Lancer le jeu avec la commande "make play"
play :
	javac src/*/*.java
	java src/main/Main

# Nettoyer les fichiers .class avec la commande "make clean"
clean :
	rm src/*/*.class; 