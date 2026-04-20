# Script PowerShell para executar DDL no MySQL
# Uso: .\executar-mysql.ps1

# Configurações
$MySQLPath = "C:\xampp\mysql\bin\mysql.exe"  # Ajuste conforme sua instalação
$Database = "care_plus_db"
$User = "root"
$ScriptFile = "DLL-Usuarios-Setup.sql"

# Verificar se o MySQL existe
if (-not (Test-Path $MySQLPath)) {
    Write-Host "MySQL não encontrado em: $MySQLPath" -ForegroundColor Red
    Write-Host "Por favor, ajuste a variável MySQLPath no script" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Caminhos comuns para MySQL:" -ForegroundColor Green
    Write-Host "  - XAMPP: C:\xampp\mysql\bin\mysql.exe" -ForegroundColor Gray
    Write-Host "  - WAMP: C:\wamp64\bin\mysql\mysql[version]\bin\mysql.exe" -ForegroundColor Gray
    Write-Host "  - MySQL Installer: C:\Program Files\MySQL\MySQL Server [version]\bin\mysql.exe" -ForegroundColor Gray
    exit 1
}

# Verificar se o script SQL existe
if (-not (Test-Path $ScriptFile)) {
    Write-Host "Script SQL não encontrado: $ScriptFile" -ForegroundColor Red
    exit 1
}

Write-Host "Executando script MySQL..." -ForegroundColor Green
Write-Host "Banco: $Database" -ForegroundColor Cyan
Write-Host "Script: $ScriptFile" -ForegroundColor Cyan
Write-Host ""

# Executar o script
try {
    $content = Get-Content $ScriptFile -Raw
    $content | & $MySQLPath -u $User -p $Database
    Write-Host ""
    Write-Host "Script executado com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "Erro ao executar script:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Para verificar os usuários inseridos, execute:" -ForegroundColor Yellow
Write-Host "USE $Database;" -ForegroundColor Gray
Write-Host "SELECT id, email, sessao_ativa, ultimo_acesso FROM usuarios ORDER BY id;" -ForegroundColor Gray
