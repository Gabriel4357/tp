# User Guide for HomeBoss

This comprehensive user guide is your key to a seamless start with our cutting-edge software designed specifically for
home-based businesses. Our solution is tailored to enhance the efficiency of managing delivery addresses, simplifying
your operations. Within these pages, you'll find detailed coverage of the following essential topics:

- Getting Started
- Features
    - User
        - Secure account with a password
        - Login
        - Logout
        - Register
        - Recover password
    - Customer
        - Add customer
        - List customers
        - Update customer details
        - Delete customer
    - Delivery
        - Create delivery
        - View all deliveries
        - Sorted deliveries
        - View details of a delivery
        - Update details of a delivery
        - Delete delivery
- Troubleshooting / FAQ

#

# Getting Started

To get started, you will need to create an account and download the software. Once you have done that, you can follow
the instructions in the Getting Started Guide to start using the software.

#

# Features

\*[ ] specifies optional fields

## <span style="text-decoration:underline;">User</span>

### Register

**Format: `register --user USERNAME --password PASSWORD --confirmPass CONFIRM_PASSWORD`**

**Example: `register --user gabriel --password gabrielIsGreat --confirmPass gabrielIsGreat`**

**Accepted Values:**

_USERNAME:_ String

_PASSWORD:_ String

_CONFIRM PASSWORD:_ String that is the same as PASSWORD.

**Command succeeds: _Register successful._**

**Command fails (missing fields): _Please fill up all the required fields._**

**Command fails (password does not match): _Passwords do not match. Try again._**

### Login

**Format: `login --user USERNAME --password PASSWORD`**

**Example: `login --user gabriel --password gabrielIsGreat`**

**Accepted Values:**

_USERNAME:_ String

_PASSWORD:_ String

**Command succeeds: _Log in successful._**

**Command fails (missing fields): _Please fill up all the required fields._**

**Command fails (wrong login credentials): _Wrong username and/or password. Try again._**

### Forget Password [coming soon]

### Logout

**Format: `logout`**

**Example: `logout`**

**Command succeeds: _Logout successful._**

### Change Password [coming soon]

### Delete Account [coming soon]

## <span style="text-decoration:underline;">Customer</span>

### Add a customer

Adds a customer to the address book.

**Format: `customer add --name NAME --phone PHONE_NUMBER --email EMAIL --address ADDRESS`**

\*\*Example: <code>customer add --name Gabriel --phone 8765 4321
--email [gabrielrocks@gmail.com](mailto:gabrielrocks@gmail.com) --address RVRC Block B</code></strong>

**Accepted Values:**

    _NAME_: String


    _PHONE_NUMBER: _8 digit Integer


    _EMAIL_: String with @ and . in valid email format


    _ADDRESS: _String

**Command succeeds: _Customer 1, Gabriel added._**

**Command fails (missing field): _Please fill up all the required fields (--name NAME --phone PHONE_NUMBER --email EMAIL
--address ADDRESS)._**

### List customers

Lists all the customers added in the address book.

**Format**: `customer list`

**Example**: `customer list`

**Accepted Values:**

_NIL_

**Command succeeds (>0 customers):** _Here is the list of customers:_

1. _Benjamin, Phone: 9898 2323, Email: benjaminCSGod@gmail.com, Address: Carnegie Mellon University, South Block._
2. _Gambe, Phone: 9797 1313, Email: gambeRizzLord@gmail.com, Address: Kent Ridge Hall_
3. _Gabriel, Phone: 9090 9241, Email: gabrielSoCool@gmail.com, Address: RVRC Tower Block_

**Command succeeds (0 customer):** _There are no customers added yet!_

### Update customer details

Updates the personal details of an existing customer in the address book.

**Format**: `customer edit CUSTOMER_ID [--name NAME] [--phone PHONE_NUMBER] [--email EMAIL] [--address ADDRESS]`

**
Example**: `customer edit 1001 --name Gabriel -–phone 1234 5678 --email gabrielSoCool@gmail.com --address RVRC Block B Ben's Room`

**Accepted Values:**

_NAME_: String

_PHONE_NUMBER_: 8 digit Integer

_EMAIL_: String with @ and . in valid email format

_ADDRESS_: String

_CUSTOMER_ID_: Integer

\*At least one of the optional fields must be provided.

**Command succeeds:** _Customer 1, Gabriel has been updated!_

**Command fails (missing_index):** _Please specify the customer to update._

**Command fails (invalid_index):** _The customer does not exist!_

**Command fails (missing_fields):** _Please provide at least one field to update!_

### Delete customer

Deletes the specified customer from the address book.

**Format:** `customer delete CUSTOMER_ID`

**Example:** `customer delete 1001`

**Accepted Values:**

_CUSTOMER_ID:_ Integer

**Command succeeds:** _Customer 1, Gabriel has been deleted!_

**Command fails (missing_index):** _Please specify the customer to delete._

**Command fails (invalid_index):** _The customer does not exist!_

**Command fails (pending_delivery_customer_index):** _Delivery for Customer 1, Gabriel is not completed yet!_

## <span style="text-decoration:underline;">Delivery</span>

### Create delivery

Creates a delivery.

**Format:** `delivery create DELIVERY_NAME --customer CUSTOMER_ID --date DATE`

**Example:** `delivery create furniture --customer 5 --date 2023-12-03`

**Accepted Values:**

_DELIVERY_NAME:_ String of 50 characters

_CUSTOMER_ID:_ Integer

_DATE:_ YYYY-MM-DD format

**Command succeeds:** _Delivery [1001] furniture created successfully for Customer 1, Gabriel!_

**Command fails (missing_fields):** _Please fill up all the required fields (--name NAME --id CUSTOMER_ID --date DATE)!_

**Command fails (invalid_date):** _Invalid date provided!_

**Command fails (invalid_date_format):** _Please provide the date in the format: YYYY-MM-DD._

### View all deliveries

Shows a list of all deliveries.

**Format:** `delivery list STATUS [--sort SORT]`

**Example:** `delivery list all --sort desc`

**Accepted Values:**

_ STATUS_: all or pending or complete

    _SORT: _asc for ascending or desc for descending. If unspecified, default to sort by delivery date.

**Command succeeds (>0 deliveries):**

_Here are all the deliveries:_

1. [1001] Gabriel’s Milk - Completed - Ordered 20th Sept 2023 : Delivered on 30th Sept 2023
2. [1002] Gambe’s Meat - Completed - Ordered 22th Sept 2023 : Delivered on 29th Sept 2023
3. [1003] Ben’s Coffee - Pending - Ordered 25th Sept 2023 : Delivery on 1st October 2023

**Command failed (0 deliveries): **

_There are currently no deliveries!_

### View details of deliveries

Shows the details of the specified delivery.

**Format:** `delivery view DELIVERY_ID `

**Example:** `delivery view 1001`

**Accepted Values:**

_DELIVERY_ID_: Integer

**Command succeeds:**

    _[1001] Gabriel’s Milk_


    _Customer ID: 1_


    _Customer: Gabriel_


    _Ordered on : 23rd September 2023_


    _Delivery Status: Pending_


    _Delivery on : 1st October 2023_

**Command failed (0 deliveries): _There are currently no deliveries. _**

### Update delivery status and date

- Mark delivery as complete
- Mark delivery as pending
- Change date of delivery

#### Mark delivery as complete

#### **Format**: `delivery complete DELIVERY_ID`

**Example**: `delivery complete 1001`

**Accepted Values:**

    _DELIVERY_ID_: Integer

**Command succeeds:** _Delivery [1001] Gabriel’s Milk marked as pending! _

**Command failed (delivery*name missing): \_Please specify a delivery name to delete!***

**Command failed (delivery_name not in database):** _This delivery does not seem to exist!_

**Command failed (delivery already complete): _This delivery is already marked as complete._**

####

#### Mark delivery as pending

**Format:** `delivery pending DELIVERY_ID`

**Example: `delivery pending 1001`**

**Accepted Values:**

_DELIVERY_ID_: Integer

**Command succeeds:** _Delivery [1001] Gabriel’s Milk marked as pending! _

**Command failed (delivery_name missing):** _Please specify a delivery name to delete!_

**Command failed (delivery_name not in database):** _This delivery does not seem to exist! _

**Command failed (delivery already pending): _This delivery is already marked as pending._**

#### Change date of delivery

**Format**: `delivery edit date DELIVERY_ID --date DATE`

**Example**: `delivery edit date 1001 --date 2023-12-12`

**Accepted Values:**

    _DELIVERY_ID_: Integer


    _DATE: _YYYY-MM-DD

**Command succeeds:** _Delivery [1001] Gabriel’s Milk changed delivery date to 1st Oct 2023! _

**Command failed (one field missing): _Please specify a delivery name and date!_**

**Command failed (invalid date format): _Please format date as YYYY-MM-DD. _**

**Command failed (delivery*name not in database): \_This delivery does not seem to exist! ***

### Delete delivery

Deletes the specified delivery.

**Format**: `delivery delete DELIVERY_ID`

**Example**: `delivery delete 1001`

**Accepted Values:**

_DELIVERY_ID_: Integer

**Command succeeds:** _Delivery [1001] Gabriel’s Milk deleted! _

**Command failed (delivery_name missing):** _Please specify a delivery name to delete!_

**Command failed (delivery*name not in database): \_This delivery does not seem to exist***

### Create a note for a delivery `[coming soon in v1.3]`

_Details coming soon..._

### View deliveries for the day `[coming soon in v1.3]`

_Details coming soon..._

### Add customer data to delivery `[coming soon in v1.3]`

_Details coming soon..._

### Remove customer from delivery `[coming soon in v1.3]`

_Details coming soon..._

### Look up delivery details `[coming soon in v1.3]`

_Details coming soon..._
