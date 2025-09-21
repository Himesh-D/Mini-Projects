@echo off
echo Compiling Java source files...
javac -d bin src/*.java

echo Creating JAR file...
jar cfm BankingSystem.jar manifest.txt -C bin/ .

echo Running the Banking System...
java -jar BankingSystem.jar

pause
