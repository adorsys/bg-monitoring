#!/bin/bash
set -ev

# if it's a release tag build use 'release' maven profile during install to prevent partial deploy in case of failure
if [[ $TRAVIS_TAG =~ ^v[0-9]+\.[0-9]+\.[0-9]+(-.*)?$ ]]
then
# GPG_SECRET_KEY=$(gpg -a --export-secret-key <keyId> | base64 -w 0)
  echo $GPG_SECRET_KEY | base64 --decode | gpg --import
  mvn install -B -P release -Dgpg.passphrase=$GPG_PASSPHRASE
else
  mvn install -B
  docker build -t $IMAGE:$TAG .
fi
