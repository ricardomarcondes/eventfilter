# Data sorting and filtering

Read the 3 input files reports.json, reports.csv, reports.xml and output a combined CSV file with the following characteristics:

- The same column order and formatting as reports.csv
- All report records with packets-serviced equal to zero should be excluded
- records should be sorted by request-time in ascending order

Additionally, the application should print a summary showing the number of records in the output file associated with each service-guid.

Please provide source, documentation on how to run the program and an explanation on why you chose the tools/libraries used.

## Submission

You may fork this repo, commit your work and let us know of your project's location, or you may email us your project files in a zip file.

##########################################################################

Instructions:
1. Download and unzip the file
2. Open terminal and change to dir where pom.xml is located
3. Run the command: mvn clean install
4. Run the command: mvn exec:java -Dexec.mainClass="com.event.manager.EventFilterManager"
5. Verify the generate file report_final.csv

Library/Tools used:
1. IntelliJ -> In my opinion the best IDE for Java
2. Maven	-> To easily manage the project structure and dependencies
3. Jackson  -> To easily read/write JSON, XML and CSV files
 
