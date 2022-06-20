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
