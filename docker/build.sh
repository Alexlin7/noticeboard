#!/bin/bash

# 1. 取得當前 Git Commit 的前 7 位 Hash (精簡版)
# 如果當前目錄沒有 commit，這行會報錯，建議確保專案已初始化 git
GIT_SHA=$(git rev-parse --short HEAD)

# 2. 取得當前分支名稱 (選用，可用於標籤)
GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD | sed 's/\//-/g')

# 3. 組合版本號：分支-Hash (例如: main-a1b2c3d)
# 這樣在 Docker Hub 看到標籤時，一眼就能知道是哪個分支出的
VERSION="${GIT_BRANCH}-${GIT_SHA}"

echo "🚀 Starting build for version: ${VERSION}"

# 執行建置
# 這裡保留你原本的命名空間 alexlin7-idv/noticeboard
docker buildx build \
  -t alexlin7-idv/noticeboard:"${VERSION}" \
  -t alexlin7-idv/noticeboard:latest \
  --load .

echo "✅ Build completed: alexlin7-idv/noticeboard:${VERSION}"
