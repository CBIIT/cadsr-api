												caDSR API Build Instructions
												----------------------------
												
To build the caDSR API application and generate a EAR that can be deployed on Wildfly, follow the instructions below.

1. Open a commandline window (Terminal on Mac) and Goto cadsrapi/software/SDK. 

2. Execute the below command, after replacing the placeholders with the actual credentials
												
ant -buildfile build.xml build:system -DTOOL.BASE.DIR=/local/content -Dlocal-transform=/local/content/cadsrapi/transform -DCADSR.DS.TNS.PORT=<Enter Port here> -DlogLevel=<Enter log level> -DFREESTYLE.DS.USER=<Enter user name> -DCADSR.DS.USER=<Enter user name> -Dtiername=<Enter tier name> -DCADSR.DS.TNS.SID=<Enter SID> -DTEST=false -DCADSR.DS.TNS.HOST=<Enter DB host name> -DJDEBUG=off -DFREESTYLE.DS.PSWD=<Enter password> -DCADSR.DS.PSWD=<Enter password>

