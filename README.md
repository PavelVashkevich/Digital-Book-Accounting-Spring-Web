## Digital-Book-Accounting-Spring-Web

### Spring full course - project #2

This project is an expansion of project https://github.com/PavelVashkevich/DigitalBookAccounting. JDBC template in previous project was changed with Hibernate and Spring Data JPA. Besides new functionality requirements were accomplished. 

_New required functionality:_

1. Pagination for books. There may be many books and they may not fit on one page in
   browser. To solve this problem, the controller method must be able to
   give out not only all the books at once, but also break the issue into pages.
2. Sorting books by year. The controller method must be able to
   give out books in sorted order.
3. Book search page. By enter initial letters in the field on the page books with these letters in title should be returned.
4. An automatic check that the person has overdue the return
   books. Book is overdue when person hasn't been returning the book for 10 days.   

_Supported HTTP requests and example of their results:_ 

Used technologies:
• Spring MVC
• PostgreSQL
• Hibernate
• Spring Data JPA
• Thymeleaf
• HTML
• Maven

_How to execute_
To execute the application clone the repository and in Intellij Idea/Eclipse configure Tomcat 9.0.X where application will be deployed and run the appication. 