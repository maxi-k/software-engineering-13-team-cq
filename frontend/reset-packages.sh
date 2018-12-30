#!/bin/sh

rm yarn.lock 
rm -rf node_modules
rm -rf fleetdata-cns/node_modules 
rm -rf fleetdata-components/node_modules

yarn install
