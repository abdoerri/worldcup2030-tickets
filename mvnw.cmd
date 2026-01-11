@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup script for Windows
@REM ----------------------------------------------------------------------------

@echo off
setlocal

set MAVEN_VERSION=3.9.6
set MAVEN_DOWNLOAD_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%
set MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd

if exist "%MAVEN_CMD%" goto runMaven

echo Maven not found. Downloading Maven %MAVEN_VERSION%...
echo This will only happen once.

if not exist "%USERPROFILE%\.m2\wrapper\dists" mkdir "%USERPROFILE%\.m2\wrapper\dists"

set TEMP_ZIP=%TEMP%\maven-%MAVEN_VERSION%.zip
powershell -Command "Invoke-WebRequest -Uri '%MAVEN_DOWNLOAD_URL%' -OutFile '%TEMP_ZIP%'"

echo Extracting Maven...
powershell -Command "Expand-Archive -Path '%TEMP_ZIP%' -DestinationPath '%USERPROFILE%\.m2\wrapper\dists' -Force"

del "%TEMP_ZIP%"

:runMaven
"%MAVEN_CMD%" %*
