# Commande_SAV_Application

## Project Description

Commande_SAV_Application is a project developed during my internship as part of my BTS program. The application was designed to simplify the exchange of orders between technicians and the secretary. Prior to this, the company used an Excel file to manage orders, which could not be accessed and edited simultaneously, leading to inefficiencies. Additionally, if the file was unavailable, employees had to physically move to another location to access it.

The goal of this project was to simplify, secure, and improve the order exchange process between the technicians and the secretary. 

From a technical standpoint, the application consists of:
- A desktop application built with **JavaFX**
- A **MySQL** database integrated into the company's server

## Features

- **User Authentication**: 
  - Login system with different profiles (Supervisor, Technician, Secretary)
  - Each profile has different permissions and options

- **Order Management**:
  - A table displaying the list of orders with their statuses and details
  - Different views and functionalities depending on the selected user profile:
    - **Technician**: Can add new orders with detailed information.
    - **Supervisor**: Monitors orders and tracks the time between order reception and dispatch.
    - **Secretary**: Can modify ongoing orders, such as updating delivery notes if an order is canceled.

- **CRUD Operations**:
  - Managing transporters, orders, and clients
  - Users can create, modify, and delete these entities

This project provided me with significant professional experience. It taught me the importance of client communication to deliver value quickly and gather feedback during the application's development.
