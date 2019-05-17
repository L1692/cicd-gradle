#!/bin/bash

/usr/local/bin/kubectl delete services cicdapp
/usr/local/bin/kubectl delete deployment cicdapp
sudo docker rmi $(sudo docker images -f dangling=true -q)