# Book Rental API

This is a simple Book Rental API built using `Spring Boot` `3.4.3` and `Java` `21`.

## Database

### Exporting a PostgreSQL Database

    pg_dump -U <username> -W -d <database_name> -F c -f <output_file>

### Importing a PostgreSQL Database

    pg_restore -U <username> -W -d <database_name> <backup_file>

## Dependencies Used

* `Lombok`: For reducing boilerplate code (e.g., getters, setters, constructors).
* `Spring Data JPA`: For database interactions and ORM.
* `PostgreSQL Driver`: For connecting to the PostgreSQL database.
* `Spring Web`: For building RESTful web services.
* `Validation`: For validating request data.

## API Details (Endpoints)

### Base URL

`http://localhost:8080`

### Books

1. Get All Books

    * `Endpoint:` `GET` `/books`
    
    * `Description:` Retrieve a paginated list of all books.
    
    * `Query Parameters:`
    
        - `offset (optional, default: 0)` - The starting point of the list.
    
        - `limit (optional, default: 10)` - The maximum number of books to return.
    
    * `Response Codes:` `200` OK: Returns an array of books.

2. Get a Book by ID

    * `Endpoint:` `GET` `/books/{id}`
    
    * `Description:` Retrieve details of a specific book using its ID. 

    * `Path Parameters:` `id` `(required)` - The UUID of the book.
    
    * `Response Codes:`  
        - `200` OK: Returns the book details.
        - `400` Bad Request: The provided ID is not a valid UUID.
        - `404` Not Found: The book does not exist.

3. Borrow a Book

    * `Endpoint:` `PUT` `/books/borrow`
    
    * `Description:` Borrow a book by providing the user ID and inventory ID.
    
    * `Request Body:` Schema: BorrowRequest
 
    * `Response Codes:`  
        - `202` Accepted: The book was successfully borrowed.
        - `400` Bad Request: Invalid request format or missing fields.
        - `404` Not Found: The book or inventory does not exist.
        - `409` Conflict: The book is already borrowed.

4. Return a Book 

    * `Endpoint:` `PUT` `/books/return`
    
    * `Description:` Return a borrowed book by providing the user ID and inventory ID.
    
    * `Request Body:` Schema: BorrowRequest
 
    * `Response Codes:`  
        - `202` Accepted: The book was successfully returned.
        - `400` Bad Request: Invalid request format or missing fields.
        - `404` Not Found: The book does not exist.
        - `409` Conflict: The book cannot be returned by another user.

### Users


1. User Login

    * `Endpoint:` `POST` `/user/login`
    
    * `Description:` Authenticate a user using their username and password.
    
    * `Request Body:` Schema: LoginRequest
 
    * `Response Codes:`  

        - `200` OK: Successfully authenticated the user.
        -  `404` Not Found: The user does not exist.

2. Get User by ID

    * `Endpoint:` `GET` `/user/{id}`
    
    * `Description:` Retrieve details of a specific user by their ID.
    
    * `Path Parameters:` `id` `(required)` - The UUID of the user.
 
    * `Response Codes:`  

        - `200` OK: Returns the user details.
        - `404` Not Found: The user does not exist.


### Schemas

1. BorrowRequest

    Represents the request body for borrowing or returning a book.

    ```json
    {
        "userId": "string (UUID)",
        "inventoryId": "string (UUID)"
    }
    ```

2. ErrorResponse

    Represents the error response structure.

    ```json
    {
        "errors": [
            "string"
        ]
    }
    ```

    Example

    ```json
    {
        "errors": ["Not found"]
    }

    ```

3. LoginRequest

    Represents the request body for user login.

    ```json
    {
        "username": "string",
        "password": "string"
    }

    ```

4. UserResponse

    Represents the user details in the response.

    ```json
    {
        "id": "string (UUID)",
        "username": "string",
        "role": "string",
        "inventories": [
            {
                "id": "string (UUID)",
                "borrowDate": "string (date-time)",
                "bookId": "string (UUID)",
                "title": "string",
                "author": "string",
                "image": "string"
            }
        ]
    }
    ```

    Example:

    ```json
    {
        "id": "2e1273f4-f574-4807-929e-d7c002643981",
        "username": "user",
        "role": "USER",
        "inventories": [
            {
                "id": "dd95d1e8-7cd1-4a2d-93af-81882f4aca52",
                "borrowDate": "2025-02-21T14:21:20.926843",
                "bookId": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
                "title": "Data Science on AWS",
                "author": "Fregly, Chris",
                "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439"
            },
            {
                "id": "4f8759da-6b36-4aea-865f-06d8cde006c6",
                "borrowDate": "2025-02-21T13:50:25.531284",
                "bookId": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
                "title": "Data Science on AWS",
                "author": "Fregly, Chris",
                "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439"
            },
            {
                "id": "7b12b47f-d7b7-48f1-b845-f5da5be9e813",
                "borrowDate": "2025-02-21T13:49:28.893058",
                "bookId": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
                "title": "Data Science on AWS",
                "author": "Fregly, Chris",
                "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439"
            },
            {
                "id": "f1eb31b6-545b-480e-8bb6-6dba079b7140",
                "borrowDate": "2025-02-21T13:47:39.446071",
                "bookId": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
                "title": "Data Science on AWS",
                "author": "Fregly, Chris",
                "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439"
            } 
        ]
    }
    ```

5. BookResponse

    Represents the book details in the response.

    ```json
    {
        "id": "string (UUID)",
        "title": "string",
        "author": "string",
        "image": "string",
        "inventories": [
            {
                "id": "string (UUID)",
                "borrowDate": "string (date-time)",
                "userId": "string (UUID)",
                "role": "string",
                "username": "string"
            }
        ]
    }
    ```

    Example:

    ```json
    {
        "id": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
        "title": "Data Science on AWS",
        "author": "Fregly, Chris",
        "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439",
        "inventories": [
                {
                    "id": "dd95d1e8-7cd1-4a2d-93af-81882f4aca52",
                    "borrowDate": "2025-02-21T14:21:20.926843",
                    "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                    "username": "user",
                    "role": "USER"
                },
                {
                    "id": "4f8759da-6b36-4aea-865f-06d8cde006c6",
                    "borrowDate": "2025-02-21T13:50:25.531284",
                    "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                    "username": "user",
                    "role": "USER"
                },
                {
                    "id": "7b12b47f-d7b7-48f1-b845-f5da5be9e813",
                    "borrowDate": "2025-02-21T13:49:28.893058",
                    "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                    "username": "user",
                    "role": "USER"
                },
                {
                    "id": "f1eb31b6-545b-480e-8bb6-6dba079b7140",
                    "borrowDate": "2025-02-21T13:47:39.446071",
                    "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                    "username": "user",
                    "role": "USER"
                },
                {
                    "id": "2a3ff7ed-9b8d-430e-b783-6ff4cf5380bb",
                    "borrowDate": null,
                    "userId": null,
                    "username": null,
                    "role": null
                }
            ]
        }
    ```

6. PaginatedBookResponse

    Represent a paginated list of books (bookResponseList) along with paging information (paging).

    ```json
    {
        "books": [
            {
                "id": "string (UUID)",
                "title": "string",
                "author": "string",
                "image": "string",
                "inventories": [
                    {
                        "id": "string",
                        "borrowDate": "string (date-time)",
                        "userId": "string (UUID)",
                        "role": "string",
                        "username": "string"
                    } 
                ]
            }, 
        ],
        "paging": {
                "offset": "integer",
                "limit": "integer",
                "pageNumber": "integer",
                "totalPages": "integer"
            }
        }
    ```

    Example:

    ```json
    {
        "books": [
            {
                "id": "625e265c-30fb-4dc8-98a6-c5e1734a4e08",
                "title": "Terraform: Up and Running",
                "author": "Yevgeniy Brikman",
                "image": "https://cf-assets2.tenlong.com.tw/ig/025/803/605/9781098116743.jpg?1708998744",
                "inventories": [
                    {
                        "id": "777ca193-511e-4025-85e4-7e702df566a2",
                        "borrowDate": null,
                        "userId": null,
                        "username": null,
                        "role": null
                    },
                    {
                    "id": "554fe4ad-e3cb-4eb5-8012-b695c51dedee",
                        "borrowDate": null,
                        "userId": null,
                        "username": null,
                        "role": null
                    },
                    {
                        "id": "42d26686-8264-4097-81c9-cbcdd25c3474",
                        "borrowDate": null,
                        "userId": null,
                        "username": null,
                        "role": null
                    }
                ]
            },
            {
                "id": "703bcc77-2b38-422e-90e6-fd60e7ea8743",
                "title": "Data Science on AWS",
                "author": "Fregly, Chris",
                "image": "https://cf-assets2.tenlong.com.tw/ig/023/084/297/9781492079392.jpg?1709006439",
                "inventories": [
                    {
                        "id": "dd95d1e8-7cd1-4a2d-93af-81882f4aca52",
                        "borrowDate": "2025-02-21T14:21:20.926843",
                        "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                        "username": "user",
                        "role": "USER"
                    },
                    {
                        "id": "4f8759da-6b36-4aea-865f-06d8cde006c6",
                        "borrowDate": "2025-02-21T13:50:25.531284",
                        "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                        "username": "user",
                        "role": "USER"
                    },
                    {
                        "id": "7b12b47f-d7b7-48f1-b845-f5da5be9e813",
                        "borrowDate": "2025-02-21T13:49:28.893058",
                        "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                        "username": "user",
                        "role": "USER"
                    },
                    {
                        "id": "f1eb31b6-545b-480e-8bb6-6dba079b7140",
                        "borrowDate": "2025-02-21T13:47:39.446071",
                        "userId": "2e1273f4-f574-4807-929e-d7c002643981",
                        "username": "user",
                        "role": "USER"
                    },
                    {
                        "id": "2a3ff7ed-9b8d-430e-b783-6ff4cf5380bb",
                        "borrowDate": null,
                        "userId": null,
                        "username": null,
                        "role": null
                    }
                ]
            }
        ],
        "paging": {
            "offset": 4,
            "limit": 2,
            "pageNumber": 2,
            "totalPages": 4
        }
    }
    ```
