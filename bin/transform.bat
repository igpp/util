:: Batch file that allows easy execution of a tool
:: without the need to set the CLASSAPTH, placing JAR in extension directory
:: or having to type in that long java command (java spase.tool...)
::
:: Version: $Id$

@echo off

set THIS_DIR=%~dp0

:: Executes tool
:: The special variable '%*' allows the arguments
:: to be passed into the executable.

echo %THIS_DIR%
java -Djava.ext.dirs=%THIS_DIR%jar igpp.util.Transform %*
:END
