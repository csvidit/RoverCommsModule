#!/bin/bash

build() {
    cd MissionControl
    javac *.java
    
    cd ../Rover
    repep9_asm status.pep
    repep9_asm command.pep
    
    cd ..
}

clean() {
    rm -f MissionControl/*.class
    
    rm -f Rover/*.pepo
}

tst() {
    echo ----------
    echo - Status -
    echo ----------
    
    for filename in tests/status/in/*.txt; do
        outfile=$(echo $filename | sed 's#\/in\/#\/out\/#g' )
        # diff -iw # Ignore case and whitespace when diffing
        repep9 Rover/status.pepo < $filename | java MissionControl.StatusMessageDecoder | diff -iw - $outfile
    done
    
    echo -----------
    echo - Command -
    echo -----------
    
    for filename in tests/command/in/*.txt; do
        outfile=$(echo $filename | sed 's#\/in\/#\/out\/#g' )
        # diff -iw # Ignore case and whitespace when diffing
        java MissionControl.CommandMessageEncoder < $filename | repep9 Rover/command.pepo | diff -iw - $outfile
    done
}

# Exit on failures
set -e

if [[ "$1" == "clean" ]]; then
    clean
else
    build
    tst
fi

