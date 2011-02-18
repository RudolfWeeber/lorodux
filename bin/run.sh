#!/bin/sh
#
# This file runs the corresponded demo.
DEMO=LoroDux

`dirname $0`/../../../bin/emulator -Xheapsize:128M -Xdescriptor:`dirname $0`/${DEMO}.jad
