# 1. 取得 Git SHA (短碼)
$GIT_SHA = git rev-parse --short HEAD

# 2. 取得 Git Branch 並將 '/' 替換為 '-'
$GIT_BRANCH = (git rev-parse --abbrev-ref HEAD) -replace '/', '-'

# 3. 組合版本號
$VERSION = "${GIT_BRANCH}-${GIT_SHA}"

Write-Host "🚀 Starting build for version: ${VERSION}" -ForegroundColor Cyan

# 4. 執行 Docker Buildx
docker buildx build `
  -t "alexlin7-idv/noticeboard:${VERSION}" `
  -t "alexlin7-idv/noticeboard:latest" `
  --load .

Write-Host "✅ Build completed: alexlin7-idv/noticeboard:${VERSION}" -ForegroundColor Green
