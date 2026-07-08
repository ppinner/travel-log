#!/bin/sh
cd "$(dirname "$0")"
export JAVA_HOME="$HOME/.jdks/jdk-21.0.11+10/Contents/Home"
if [ -f .env ]; then
  set -a
  . ./.env
  set +a
fi
exec ./mvnw spring-boot:run
