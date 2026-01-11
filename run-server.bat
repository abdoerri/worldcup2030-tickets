@echo off
echo Starting World Cup 2030 Web Server...
echo.
call mvnw.cmd compile exec:java -Dexec.mainClass=com.worldcup2030.WebServer
pause
