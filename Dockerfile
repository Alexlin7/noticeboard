# STAGE: build
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

# 針對 JVM 啟動進行優化，適合在 CI/CD 或資源受限環境
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

WORKDIR /app

# 利用 BuildKit 快取下載依賴
# 下次構建時若 pom.xml 沒變，就不會重新下載所有 jar 包
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:go-offline -B

# 複製原始碼並編譯
COPY src ./src
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn package -Dmaven.test.skip=true

# STAGE: application image
FROM eclipse-temurin:21.0.6_7-jre-noble AS image

# 參數定義
ARG WAIT_FOR_VERSION=v2.2.4
ARG APP_USER=noticeboard

# 建立非 root 使用者，並使用 NCHC 鏡像站加速
RUN useradd --create-home --home-dir /app --shell /bin/bash ${APP_USER} \
    && sed -i "s@http://archive.ubuntu.com@https://free.nchc.org.tw@g" /etc/apt/sources.list.d/ubuntu.sources \
    && apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y --no-install-recommends netcat-openbsd \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 使用 ADD 直接從 Github 下載 wait-for 並賦予執行權限 (774)
ADD --chown=${APP_USER} --chmod=774 https://raw.githubusercontent.com/eficode/wait-for/${WAIT_FOR_VERSION}/wait-for /app/wait-for

# 這裡要注意：因為你的 pom.xml 是 <packaging>war</packaging>
# 所以 target 底下產出的是 .war 檔案
COPY --from=build --chown=${APP_USER} /app/target/noticeboard-*.war ./app.war

USER ${APP_USER}

# 設定時區
ENV TZ=Asia/Taipei

# 注意：這裡將資料庫檢查改為你的 mysql-db:3306
# 並加入 -Djava.security.egd=file:/dev/urandom 解決容器環境亂數生成過慢導致啟動卡住的問題
ENTRYPOINT ["./wait-for", "-t", "300", "mysql-db:3306", "--", "sh", "-c", "java -jar ./app.war"]
