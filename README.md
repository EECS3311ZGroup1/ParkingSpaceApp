# ParkingSpaceBookingApp

This is a Maven-based Java application for managing parking space bookings at York University.

## Project Structure

- src/main/java/ – Source code
- src/main/resources/ – CSV data files
- pom.xml – Maven configuration

## How to Run

### Option 1: Run from Eclipse

1. Open the project in Eclipse.
2. Go to src/main/java/yorku/eecs3311/Main.java
3. Right-click Main.java → Run As → Java Application

*Make sure it can access the CSV files in src/main/resources.*

### Option 2: Run from Terminal

1. Open a terminal in the project folder.
2. Build the project: mvn clean package
3. Run the app: java -jar target/ParkingSpaceApp-0.0.1-SNAPSHOT.jar
