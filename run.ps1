<#
.SYNOPSIS
這個腳本會先建置 Spring Boot 應用程式，然後建置 Docker 映像檔，最後使用 Docker Compose 啟動服務。
#>

# 如果任何指令失敗則立即停止
$ErrorActionPreference = "Stop"

Write-Host "Step 1: Building the Spring Boot application (JAR)..."
.\build.ps1

Write-Host "Step 2: Building the Docker image..."
docker build -t idv.alexlin7.tw/noticeboard .

Write-Host "Step 3: Starting services with Docker Compose..."
docker-compose -f docker/docker-compose.yml up -d

Write-Host "Done! The application should be available at http://localhost:8080"
