# Makefile for WeatherLookup

NAME	= WeatherLookup
ECHO	= @echo
SRC_DIR = src
LIB_DIR = lib
BIN_DIR = bin
JAR_DIR = jar

all: $(NAME)
 
$(NAME): $(BIN_DIR) $(JAR_DIR)
	$(ECHO) "Building $@"
	@javac -cp "./lib/*" src/com/joshwalters/weather/*.java -d bin
	@jar cf jar/WeatherLookup.jar -C bin com
	$(ECHO) "Built $(NAME)"
	
example: $(NAME)
	$(ECHO) "Compiling example/ExampleUsage.java"
	@javac -cp jar/WeatherLookup.jar example/ExampleUsage.java -d bin
	$(ECHO) "Running weather lookup example on Irvine, CA (zip code 92614).\n"
	java -classpath "./bin:./jar/WeatherLookup.jar" ExampleUsage 92614

$(BIN_DIR):
	$(ECHO) "Folder '$(BIN_DIR)' does not exist, creating."
	@mkdir $(BIN_DIR)
	
$(JAR_DIR):
	$(ECHO) "Folder '$(JAR_DIR)' does not exist, creating."
	@mkdir $(JAR_DIR)

clean:
	@rm -rf bin/*
	@rm -rf jar/*
	$(ECHO) "Project has been cleaned"

new:
	make clean
	make
