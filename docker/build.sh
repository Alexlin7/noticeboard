#!/bin/bash

GIT_SHA=$(git rev-parse --short HEAD)

GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD | sed 's/\//-/g')

VERSION="${GIT_BRANCH}-${GIT_SHA}"

echo "🚀 Starting build for version: ${VERSION}"

docker buildx build \
  -t alexlin7-idv/noticeboard:"${VERSION}" \
  -t alexlin7-idv/noticeboard:latest \
  --load .

echo "✅ Build completed: alexlin7-idv/noticeboard:${VERSION}"
