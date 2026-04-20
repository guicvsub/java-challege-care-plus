@echo off
REM Script simples para executar inserção de usuários
REM Uso: inserir-usuarios-simples.cmd

echo Inserindo usuarios na tabela usuarios...
echo.

REM Tentar encontrar o MySQL automaticamente
set MYSQL_CMD=

REM Verificar caminhos comuns do MySQL
if exist "C:\xampp\mysql\bin\mysql.exe" (
    set MYSQL_CMD="C:\xampp\mysql\bin\mysql.exe"
) else if exist "C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe" (
    set MYSQL_CMD="C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe"
) else if exist "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" (
    set MYSQL_CMD="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
) else (
    echo MySQL nao encontrado em caminhos padrao.
    echo Por favor, ajuste o script ou use um cliente MySQL manualmente.
    echo.
    echo Execute os comandos SQL abaixo manualmente:
    echo.
    type inserir-usuarios.sql
    pause
    exit /b 1
)

echo Usando MySQL: %MYSQL_CMD%
echo.

REM Executar o script SQL
%MYSQL_CMD% -u root -p care_plus_db < inserir-usuarios.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Usuarios inseridos com sucesso!
    echo.
    echo Para testar, use:
    echo Email: admin@careplus.com
    echo Senha: Admin@123
) else (
    echo.
    echo Erro ao inserir usuarios.
    echo Verifique a senha do MySQL e se o banco care_plus_db existe.
)

echo.
pause
