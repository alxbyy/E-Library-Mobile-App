# E-Library Mobile App

E-Library Mobile App is an Android application for personal library management, allowing users to search for and save information about their favorite books.

## Installation

### Step 1: Setting Up Development Environment

1. Move the `elibrary` folder to your local XAMPP/htdocs directory.

### Step 2: Setting Up MySQL Database

1. Open MySQL on your local machine (e.g., using XAMPP).
   
2. Create a new database named `elibrary`.
   ```sql
   CREATE DATABASE elibrary;
   ```
   
3. Create a users table within the elibrary database with the following structure:
    ```sql
    CREATE TABLE users (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL);

### Step 3: Configuring the Application
1. Open the RetrofitClient.java file located in app/src/main/java/com/example/projectkelompok1e_library/.
   
2. Update the BASE_URL value to match your IP address for accessing this application.
   ```java
    public class RetrofitClient {
        private static final String BASE_URL = "http://your_ip_address/elibrary/"; // Replace with your IP address
        // ...
    }
    ```

3. Open the network_security_config.xml file located in app/src/main/res/xml/.

4. Adjust the network security configuration to allow connections to your IP address.
   ```java
   <network-security-config>
      <domain-config cleartextTrafficPermitted="true">
          <domain includeSubdomains="true">your_ip_address</domain> <!-- Replace with your IP address -->
      </domain-config>
   </network-security-config>
  
### Usage
1. Run the application on your Android emulator or device.
2. Log in with your registered account or create a new one.
3. Explore the available book collection and utilize other provided features.

### Contribution
We welcome contributions from developers to enhance this application. To contribute, please fork our GitHub repository and submit a pull request.
