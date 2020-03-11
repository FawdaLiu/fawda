#!/bin/bash
if [ -f quickstartoffice ]
then 
    sudo chmod a+x quickstartoffice
    cp -p quickstartoffice /usr/bin/
    quickstartoffice start
fi
