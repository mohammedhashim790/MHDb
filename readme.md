# MH Database Management System

A lightweight database management system developed as a coursework project for CSCI5408 - Data Management and Analytics (Master's in Applied Computer Science).

## Overview

This project implements a basic database management system with features similar to H2 and MSSQL. It provides a console-based interface for database operations and includes user authentication.

### Key Features

- **Authentication System**: Secure user authentication with MD5 password encryption
- **Database Console**: Interactive command-line interface similar to H2/MSSQL
- **Core Database Operations**:
  - Database creation and management
  - Table creation and modification
  - Index management
  - SQL-like query support

## Getting Started

### Prerequisites

- Java JDK 23 or higher
- Maven for dependency management

### Installation

1.  Build the project using Maven:
```
mvn clean install
``` 

## Usage

### Starting the Database Console

1. Run the main application
2. Log in using your credentials
3. Use SQL-like commands to interact with the database

### Basic Commands
Create a new database
```
CREATE DATABASE database_name;
``` 
Create a new table
```
CREATE TABLE table_name ( column1 datatype, column2 datatype, ... );
```
Exit The console
```
exit;
```




## Architecture

The system is built with a modular architecture that includes:

- Authentication Module
- Query Processing Engine
- Storage Controller
- Console Interface

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/{feature}`)
3. Commit your changes (`git commit -m 'Message'`)
4. Push to the branch (`git push origin feature/{feature}`)
5. Open a Pull Request

## Academic Context

This project was developed as part of the CSCI5408 - Data Management and Analytics course in the Master's in Applied Computer Science program.

## References

[1] Dev.to, "Unboxing a Database-How Databases Work Internally", [Online]. 
Available: https://dev.to/gbengelebs/unboxing-a-database-how-databases-work-internally-155h. 
[Accessed: March 3, 2025].

[2] Stackoverflow, "How do databases physically store data on a filesystem?", [Online]. 
Available: https://stackoverflow.com/questions/12018565/how-do-databases-physically-store-data-on-a-filesystem. 
[Accessed: March 3, 2025].

## License

MIT License

Copyright (c) 2025 CSCI5408 Database Management System Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

