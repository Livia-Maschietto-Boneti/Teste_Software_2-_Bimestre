@echo off
setlocal
if exist out rmdir /s /q out
mkdir out
javac -encoding UTF-8 -d out src\main\java\pife\*.java src\test\java\pife\*.java
if errorlevel 1 exit /b 1
java -cp out pife.CardTest
if errorlevel 1 exit /b 1
java -cp out pife.PifeGameTest
if errorlevel 1 exit /b 1
