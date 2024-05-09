# **Discount Qualification Calculation**

## **Overview**

This project implements a discount qualification and calculation system for orders based on specific qualifications. It checks various criteria for each order and applies corresponding discounts if the criteria are met. The program is designed to handle multiple qualifications for each order and selects the top two discounts if more than one discount is qualified for an order.

## **Features**

- **Input Handling**: Reads order data from a CSV file.
- **Discount Qualification**: Checks various criteria such as expiry date, product type, order date, quantity, channel, and payment method for discount qualification.
- **Discount Calculation**: Calculates discounts based on different criteria.
- **Output Generation**: Generates a processed CSV file with the calculated discounts and total amount after discount for each order.
- **Logging**: Utilizes Log4j for logging warning messages if any discount results in null.

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
