#About
This application will decompose standard user-input cron command into something more human-friendly. It supports listed comma-separated values, value ranges and interval notation. 
To satisfy your curiosity about exact capabilities there are some tests available under `src/test/java`. 

#Pre-requisite
Java 11 needs to be present on the environment. For running precompiled binaries JRE will be sufficient, but for building you will need JDK. <br>
If you don't know how to get it for your machine it's best if you ask Google. Or a senior dev. Junior might do as well.

#Build
In order to build application from sources use:<br>
`~$ ./mvnw install`<br>
It will run provided Maven wrapper.

#Running
Application can be run from UNIX command line with: <br>
`~$ run.sh ＂*/15 0 1,15 * 1-5 /usr/bin/find＂`<br>
Quotes are important ;) 

Should you be brave enough to use Windows there is a budget script for it as well: <br>
`~$ run.bat ＂*/15 0 1,15 * 1-5 /usr/bin/find＂`<br>
Mind the quotes.