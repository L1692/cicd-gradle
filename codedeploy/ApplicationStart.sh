#!/bin/bash

sudo $(aws ecr get-login --region eu-west-1 --no-include-email)
/usr/local/bin/kubectl run cicdapp --image=195360077735.dkr.ecr.eu-west-1.amazonaws.com/cicdapp:latest --port=80
/usr/local/bin/kubectl expose deployment cicdapp --type='LoadBalancer'