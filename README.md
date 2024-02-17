> **GETTING STARTED:** You must start from some combination of the CSV Sprint code that you and your partner ended up with. Please move your code directly into this repository so that the `pom.xml`, `/src` folder, etc, are all at this base directory.

> **IMPORTANT NOTE**: In order to run the server, run `mvn package` in your terminal then `./run` (using Git Bash for Windows users). This will be the same as the first Sprint. Take notice when transferring this run sprint to your Sprint 2 implementation that the path of your Server class matches the path specified in the run script. Currently, it is set to execute Server at `edu/brown/cs/student/main/server/Server`. Running through terminal will save a lot of computer resources (IntelliJ is pretty intensive!) in future sprints.

# Project Details
Project name:
        CSV
    Team members and contributions:
        amercede - Alexandra Mercedes-Santos
        czhan157 - Caroline Zhang
    Total estimated time it took to complete project:
        26 hours
    A link to your repo:
        https://github.com/cs0320-s24/server-amercede-czhan157
# Design Choices
    Explain the relationships between classes/interfaces:
        The end user can use LoadCSV, SearchCSV, and ViewCSV to obtain information relevant to the CSVs they are interested in. The CSVs must be loaded through LoadCSV prior to any API call to SearchCSV or ViewCSV. These endpoints use the CSVParser from the last sprint.

        The CSV-specific classes also throw various error messages informing the user if something goes awry. We also created corresponding ENUMs to make these error messages more concise. 
        We decided to make out main running folder in our server folder titled Server. This would call the
        handlers of each function of the server and set up the server to run. In this Folder is the broadbandhandler,
        which is called by the Server to handle the broadband side of things.
        In the common folder we handle minor technical things like exceptions, records and objects to help other files run.
        In the csv folder we have our csv handlers: loadcsv, searchcsv, and viewcsv, which handle the functionality and
        response to queries to those endpoints.
        The datasource folder contains all the classes that are needed to get the broadband percent including some helper
        classes like StateCodes, CountyCodes and BroadbandPercent to help with the broadband functionality.
        The parser class contains the functionality needed for the csv handlers. Including parsing and searching.

    Discuss any specific data structures you used, why you created it, and other high level explanations.
    We used List of List of Strings many times instead of a hashmap, because we thought it would be good to
        help us more easily work with json and in the event of caching help us save some memory, instead of working
        with a list of objects.

# Errors/Bugs
    We don't quite have the broadband percent know how to handle instances where the state code or county code
    is not found. We also have limits in our program due to some counties not being counted, due to their small
    population

# Tests
Explain the testing suites that you implemented for your program and how each test ensures that a part of the program works.
    In TestCountyCodes, TestStateCodes and TestBroadbandPercent we have the testing suite run some cases where
    the input is null or the query theoretically could be null. We also compare some expected lists of sample
    inputs and run them with the expected outputs

# How to
Run the tests you wrote/were provided
        In TestCountyCodes and TestBroadbandPercent you would need to put a valid statecode and countycode and
        know what the expected output would be.
    Build and run your program
        Call the server class in the the server folder. Use queries like this one with the proper endpoints:
        http://localhost:3232/census?statename=Alabama&countyname=Morgan%20County
        Acceptable endpoints are: census, loadcsv, searchcsv, viewcsv
        You would then put the proper queries like statename or filepath
