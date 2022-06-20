# CallAgent

Steps to run the application

1. Download the application (Command - git clone https://github.com/rajinda1980/CallAgent.git)
2. Set the following configurations in the application.properties file
    a). Configure the data source (h2 is currently configured)
    b). Change the template.src.path (This is where all FTL templates should be stored)
    c). Configure the template file path used to generate call logs (Property - calllog.template.file.path)
    d). Configure the template file path to generate all configurations ( Property - config.template.file.path)
3. Build project using maven command (Command - mvn clean install) Ex : /CallAgent$ mvn clean install
4. Copy the target/callapp-0.0.1-SNAPSHOT.jar file whereever you want the application to run
5. Go to the copy location and un the application (Ex : /CallAgent/target$ java -jar callapp-0.0.1-SNAPSHOT.jar)


To access the database

1. Go to the browser page and type http://localhost:8080/h2
2. Enter the following credentials on the login screen
    a). Driver Class - org.h2.Driver
    b). JDBC URL - jdbc:h2:mem:call_agent
    c). User Name - root
    d). password - root
    

End point details

1. Requirement 01 - To return appropriate telephone number and location
   End point details
       Request type : GET
       URL pattern : localhost:8080/agent/location_and_telephone/{transfer_type}
       Sample URL : localhost:8080/agent/location_and_telephone/lost and stolen
                    localhost:8080/agent/location_and_telephone/fraud
       Output : JSON object

2. Requirement 02 - Include / Exclude location from configuration
   End point details
       Request type : GET
       URL pattern : localhost:8080/agent/update_config/{location_name}
       Sample URL : localhost:8080/agent/update_config/Leeds
       Output : Response message
       
3. Requirement 3 - Output a view of the distribution of calls to date
   End point details
       Request type : GET
       URL pattern : localhost:8080/agent/download/call_details
       Output : PDF document
       
4. Requirement 4 - Output a view of the current live configuration
   End point details
       Request type : GET
       URL pattern : localhost:8080/agent/download/config
       Output : PDF document
