@echo off
REM Script Batch para executar DDL no MySQL
REM Uso: executar-mysql-bat.cmd

REM Configurações - ajuste conforme sua instalação
set MYSQL_PATH=C:\xampp\mysql\bin\mysql.exe
set DATABASE=care_plus_db
set USER=root
set SCRIPT=DLL-Usuarios-Setup.sql

echo Executando script MySQL...
echo Banco: %DATABASE%
echo Script: %SCRIPT%
echo.

REM Verificar se o MySQL existe
if not exist "%MYSQL_PATH%" (
    echo MySQL nao encontrado em: %MYSQL_PATH%
    echo Por favor, ajuste a variavel MYSQL_PATH no script
    echo.
    echo Caminhos comuns para MySQL:
    echo   - XAMPP: C:\xampp\mysql\bin\mysql.exe
    echo   - WAMP: C:\wamp64\bin\mysql\mysql[versao]\bin\mysql.exe
    echo   - MySQL Installer: C:\Program Files\MySQL\MySQL Server [versao]\bin\mysql.exe
    pause
    exit /b 1
)

REM Verificar se o script SQL existe
if not exist "%SCRIPT%" (
    echo Script SQL nao encontrado: %SCRIPT%
    pause
    exit /b 1
)

REM Executar o script
"%MYSQL_PATH%" -u %USER% -p %DATABASE% < %SCRIPT%

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Script executado com sucesso!
) else (
    echo.
    echo Erro ao executar script. Verifique a senha do MySQL.
)

echo.
echo Para verificar os usuarios inseridos, execute:
echo USE %DATABASE%;
echo SELECT id, email, sessao_ativa, ultimo_acesso FROM usuarios ORDER BY id;

pause
