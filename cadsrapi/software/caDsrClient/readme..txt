Pre-requisits.
1. Install Java 8
2. Install Apache ANT version 1.9.x
3. Configure "ant" command in your environment

To run from command line.
1. Unzip the file caDsrClient.zip
2. Change to directory caDsrClient.
3. Run command:
>ant -f projectBuilder.xml

To run from Eclipse (Eclipse Marc and up configured with Java 8).
1. Create a new Eclipse workspace folder (cadsrworkspace).
2. Unzip caDsrClient.zip in this newly created Eclipse workspace folder (cadsrworkspace/caDsrClient).
3. Open Eclipse in the workspace folder (cadsrworkspace).
4. Go to Eclipse File->Import menu, and import an existed java project "caDsrClient" in your workspace.
5. Check JRE library settings in your project environment (Project->Properties->Java Build Path->Libraries)
7. Run the main class gov.nih.nci.cadsr.client.TestCaDsrApi.
