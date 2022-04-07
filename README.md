# A Smart Cookie

### Web project that implements the work of the system Periodicals

#### 1 Functional requirements

The following functional requirements have been identified for the program:

- There is a list of publications in the system, which are grouped by topic.
- The reader can subscribe to one or more publications. -For the list of publications it is necessary to realize the
  possibility:
    - sorting publications by title;
    - sorting publications by price;
    - samples of publications on a particular topic;
    - search for a publication by title.
- The reader registers in the system and has a personal account, which displays information about the publications to
  which he is subscribed.
- An unregistered user cannot subscribe.
- The reader has a personal account that he can replenish.
- Funds are withdrawn from the account when subscribing to the publication.
- The system administrator has the rights for:
    - adding, deleting and editing editions;
    - blocking, unlocking the user.

#### 2 Implementation requirements

The following non-functional requirements were identified for the developed program:

1. Based on the essence of the subject area to create classes that correspond to them.
2. Classes and methods should have names that reflect their functionality, and should be spaced apart.
3. The design of the code must comply with the Java Code Convention.
4. Information about the subject area is stored in a relational database (it is recommended to use MySQL or PostgreSQL
   as a database).
5. To access the data, use the JDBC API using a ready-made or self-developed connection pool.
6. The application must support work with the Cyrillic alphabet (be multilingual), including when storing information in
   the database:
   - it must be possible to switch the interface language; 
   - there should be support for input, output and storage of information (in the database), recorded in different languages; 
   - c. choose at least two languages: one based on Cyrillic (Ukrainian), another based on Latin (English).
7. The application architecture must conform to the MVC template.
8. When implementing business logic, it is necessary to use design patterns: Strategy, Factory, Builder, Singleton, Front Controller, Observer, Adapter, etc.
9. Using servlets and JSP, implement the functionality described in the functional requirements.
10. Use Apache Tomcat as a servlet container.
11. Use JSTL library tags and custom tags (minimum: one) on JSP pages custom tag library tag and one tag file tag).
12. Implement protection against re-sending data to the server when refreshing the page
    (implement PRG).
13. Use sessions, filters, listeners when developing.
14. Authentication and authorization, delimitation of access rights of system users to program components must be implemented in the application. Password encryption is encouraged.
15. Introduce an event log into the project using the log4j library.
16. The code should contain comments on the documentation (all top-level classes, non-trivial methods
    and designers).
17. The application should be covered by modular tests (minimum coverage percentage of 40%).
    Writing integration tests is encouraged.
18. Implement the mechanism of pagination of data pages.
19. All input fields must be with data validation.
20. The application must respond correctly to errors and exceptions of various kinds (final user should not see the stack trace on the client side).
21. Independent expansion of the task of functionality is encouraged! (Adding captchas, generating reports in various formats, etc.)
22. The use of HTML, CSS, JS frameworks for the user interface (Bootstrap, Materialize, etc.) is encouraged!

### ER-diagram 
![ER-diagram](../assets/screenshots/A Smart Cookie DB.drawio.png)

### UI

#### 1. Sign In/Up forms:

##### - English
![Sign In](../assets/screenshots/SignIn_eng.png)
![Sign Up](../assets/screenshots/SignUp_eng.png)
##### - Ukrainian
![Sign In](../assets/screenshots/SignIn_uk.png)
![Sign Up](../assets/screenshots/SignUp_uk.png)

#### 2. Guest:

##### - English
![Catalog](../assets/screenshots/CatalogGuest_eng.png)
![Search](../assets/screenshots/Search_eng.png)
##### - Ukrainian
![Catalog](../assets/screenshots/CatalogGuest_uk.png)
![Search](../assets/screenshots/Search_uk.png)

#### 2. Admin:

##### - English
![Users Management](../assets/screenshots/AdminUsers_eng.png)
![Publication Management](../assets/screenshots/AdminPublications_eng.png)
##### - Ukrainian
![Users Management](../assets/screenshots/AdminUsers_uk.png)
![Publication Management](../assets/screenshots/AdminPublications_uk.png)

#### 3. User:

##### - English
![User Account](../assets/screenshots/UserAcc_eng.png)
![Add Funds](../assets/screenshots/AddFunds_eng.png)
##### - Ukrainian
![User Account](../assets/screenshots/UserAcc_uk.png)
![Add Funds](../assets/screenshots/AddFunds_uk.png)