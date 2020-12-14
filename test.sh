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
    rm MissionControl/*.class
    
    rm Rover/*.pepo
}

tst() {
    echo ----------
    echo - Status -
    echo ----------
    
    for filename in tests/status/in/*.txt; do
        outfile=$(echo $filename | sed 's#\/in\/#\/out\/#g' )
        # diff -iw # Ignore case and whitespace when diffing
        echo repep9 Rover/status.pepo \< $filename \| java MissionControl/StatusMessageDecoder.class \| diff -iw - $outfile
    done
    
    echo -----------
    echo - Command -
    echo -----------
    
    for filename in tests/command/in/*.txt; do
        outfile=$(echo $filename | sed 's#\/in\/#\/out\/#g' )
        # diff -iw # Ignore case and whitespace when diffing
        echo java MissionControl/CommandMessageEncoder.class \< $filename \| repep9 Rover/command.pepo \| diff -iw - $outfile
    done
}

build
tst

