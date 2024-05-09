# **Discount Qualification Calculation**

## **Overview**

This project implements a discount qualification and calculation system for orders based on specific qualifications. It checks various criteria for each order and applies corresponding discounts if the criteria are met. The program is designed to handle multiple qualifications for each order and selects the top two discounts if more than one discount is qualified for an order.

## **Features**

- **Input Handling**: Reads order data from a CSV file.
- **Discount Qualification**: Checks various criteria such as expiry date, product type, order date, quantity, channel, and payment method for discount qualification.
- **Discount Calculation**: Calculates discounts based on different criteria.
- **Output Generation**: Generates a processed CSV file with the calculated discounts and total amount after discount for each order.
- **Logging**: Utilizes Log4j for logging warning messages if any discount results in null.

## **Business Requirements**

### **Problem Statement**

A huge retail store wants a rule engine that qualifies orders’ transactions to discounts based on a set of qualifying rules. And automatically calculates the proper discount based on some calculation rules as follows:

| Qualifying Rules | Calculation Rule |
| --- | --- |
| Less than 30 days remaining for the product to expire (from the day of transaction, i.e. timestamp) | If 29 days remaining -> 1% discount, if 28 days remaining -> 2% discount, if 27 days remaining -> 3% discount, etc. |
| Cheese and wine products are on sale | Cheese -> 10% discount, wine -> 5% discount |
| Products that are sold on 23rd of March have a special discount! | 50% discount |
| Bought more than 5 of the same product | 6 – 9 units -> 5% discount, 10-14 units -> 7% discount, More than 15 -> 10% discount |
| Transactions that didn’t qualify to any discount  | 0% discount |
| Transactions that qualified to more than one discount | Get the top 2 and get their average |

After reading the raw data and calculating the discount, please also calculate the final price and load the result into a table.

### **Additional Requirements**

| Requirement | Description |
| --- | --- |
| Encourage App Usage Discount Rule | - Sales made through the App will have a special discount. <br> - Quantity rounded up to the nearest multiple of 5. <br>     - Ex: If quantity: 1, 2, 3, 4, 5 -> discount 5% <br>     - If quantity: 6, 7, 8, 9, 10 -> discount 10% <br>     - If quantity: 11, 12, 13, 14, 15 -> discount 15% <br>     - etc. |
| Promote Visa Card Usage Discount Rule | - Sales made using Visa cards qualify for a minor discount of 5%. |

## **Project Structure**

The project consists of the following components:

- **Main Scala File**: **`DiscountQualificationCalculation.scala`** contains the main logic for reading, processing, and generating output for orders.
- **Input Data**: The input data is stored in a CSV file located at **`src/main/scala/Sources/TRX1000.csv`**.
- **Output Data**: The processed output is saved in a CSV file located at **`src/main/scala/Output/Processed_TRX1000.csv`**.
- **Logger Configuration**: Log4j configuration is included in the project to handle logging.

## **Dependencies**

The project depends on the following libraries:

- **Log4j**: Version 2.14.1 is used for logging functionalities.

## **Usage**

To run the project, follow these steps:

1. Ensure you have Scala and SBT installed on your system.
2. Clone the repository to your local machine.
3. Navigate to the project directory.
4. Update the input CSV file if needed (**`src/main/scala/Sources/TRX1000.csv`**).
5. Run the project using SBT:
    
    ```bash
    sbt run
    
    ```
    
6. Once the execution is completed, check the processed output in the **`src/main/scala/Output/Processed_TRX1000.csv`** file.

## **Contributing**

Contributions are welcome! Feel free to submit issues or pull requests for any improvements or additional features.
