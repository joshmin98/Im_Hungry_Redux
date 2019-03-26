# Build Instructions

## If you're coming from a "cold-start" (i.e. you restarted your computer) 

Run the following commands in terminal.  
```
sh ~/startup.sh
```
This command will start Tomcat.  

The next thing you should do is open up Eclipse (or cd into the backend directory via console) and run the following maven (mvn) commands:
```clean verify```
```tomcat7:deploy```

## If you are not coming from a cold start 

Then run the maven commands:
```clean verify```
```tomcat7:redeploy```

Alternatively, you can run the build script in the backend directory. ```sh buildscript.sh```
