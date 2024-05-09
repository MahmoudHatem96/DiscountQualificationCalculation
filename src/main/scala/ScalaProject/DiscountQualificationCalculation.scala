package ScalaProject

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.io.Source
import org.apache.logging.log4j.{LogManager, Logger}
import java.io.{File, FileOutputStream, PrintWriter}

object DiscountQualificationCalculation extends App{
  // Initialize logger
  val logger: Logger = LogManager.getLogger(getClass.getName)

  // Load all lines from the file
  val lines = Source.fromFile("src/main/scala/Sources/TRX1000.csv").getLines().drop(1).toList

  case class Discount(orderDate: String, productName: String, expiryDate: String, quantity: Int, unitPrice: Double, channel: String, paymentMethod: String)

  // Function to convert lines from csv file to a Discount object
  def toDiscount(line: String): Discount={
    val columns = line.split(",")
    Discount(columns(0), columns(1), columns(2),columns(3).toInt, columns(4).toDouble, columns(5), columns(6))
  }

  // Object to parse string to date
  val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  // Function to take the date only from a timestamp and converting the string to a date format (for orderDate column)
  def getDateOnly(timestamp: String): LocalDate = LocalDate.parse(timestamp.take(10), dateFormatter)

  // Function to convert string to date (for expiryDate column)
  def toDate(stringDate: String): LocalDate = LocalDate.parse(stringDate, dateFormatter)

  // Qualifying function for expiry date < 30 days
  def isExpiryQualified(discount: Discount): Boolean=
    val daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(getDateOnly(discount.orderDate), toDate(discount.expiryDate))
    daysRemaining < 30

  // Calculation of discount percent based on the days between expiry and order date
  def expiryDiscount(discount: Discount): Double=
    val discountPercent = (30 - (java.time.temporal.ChronoUnit.DAYS.between(toDate(discount.expiryDate), getDateOnly(discount.expiryDate))))/100.0
    val orderDiscount = discount.unitPrice * discount.quantity * discountPercent
    orderDiscount

  // Qualifying function for products Wine and Cheese
  def isProductQualified(discount: Discount): Boolean=
    val productType = discount.productName.toLowerCase.startsWith("cheese") || discount.productName.toLowerCase.startsWith("wine")
    productType

  // Calculation of discount based on the product type: Cheese, Wine
  def productDiscount(discount: Discount): Double=
    val orderDiscount = if(discount.productName.toLowerCase.startsWith("cheese")) {
      discount.unitPrice * discount.quantity * 0.1
    }
    else if(discount.productName.toLowerCase.startsWith("wine")) {
      discount.unitPrice * discount.quantity * 0.05
    }
    else 0
    orderDiscount

  // Qualifying function for the date 23 March
  def isDayOfMarchQualified(discount: Discount): Boolean=
    val orderDate = getDateOnly(discount.orderDate)
    val dayOfMarch = LocalDate.parse("2023-03-23", dateFormatter)
    orderDate == dayOfMarch

  // Calculation of discount based on the 23 of March
  def marchDayDiscount(discount: Discount): Double=
    discount.unitPrice * discount.quantity * 0.5

  // Qualifying for the quantity of product
  def isQuantityQualified(discount: Discount): Boolean=
    discount.quantity > 5

  // Calculation of discount based on quantity
  def quantityDiscount(discount: Discount): Double=
    val orderDiscount = if(discount.quantity > 5 && discount.quantity <= 9){
      discount.unitPrice * discount.quantity * 0.05
    }else if(discount.quantity > 9 && discount.quantity <= 14){
      discount.unitPrice * discount.quantity * 0.07
    }else if(discount.quantity > 14){
      discount.unitPrice * discount.quantity * 0.1
    }else 0
    orderDiscount

  ////////////////////////////////////// New Requirements //////////////////////////////////////////////////////////
  // Qualifying function for the app orders
  def isAppQualified(discount: Discount): Boolean=
    discount.channel.toLowerCase.startsWith("app")

  // Calculation of discount based on the channel used in order: App
  def appChannelDiscount(discount: Discount): Double=
    val appDiscount = (discount.quantity / 5.0).ceil * 0.05 * discount.quantity * discount.unitPrice
    appDiscount

  // Qualifying function for the payment method
  def isVisaQualified(discount: Discount): Boolean=
    discount.paymentMethod.toLowerCase.startsWith("visa")

  // Calculation of discount based on the payment method: Visa
  def visaPaymentDiscount(discount: Discount): Double=
    val visaDiscount = discount.unitPrice * discount.quantity * 0.05
    visaDiscount
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // List to contain all qualifying and calculation functions
  val DiscountFunctions: List[(Discount => Boolean, Discount => Double)]=     //list type is two functions which input of both is a Discount object
    List(
      (isExpiryQualified, expiryDiscount),
      (isProductQualified, productDiscount),
      (isDayOfMarchQualified, marchDayDiscount),
      (isQuantityQualified, quantityDiscount),
      (isAppQualified, appChannelDiscount),
      (isVisaQualified, visaPaymentDiscount)
    )

  // Output deserving orders with their average discounts
  val f: File = new File("src/main/scala/Output/Processed_TRX1000.csv")
  val writer = new PrintWriter(new FileOutputStream(f, true))

  // Defining an object to hold the headline
  val headline = "Order Date, Product Name, Expiry Date, Quantity, Unit Price, Channel, Payment Method, Discount, Total After Discount"

  // Output the headline to the file
  writer.println(headline)

  def processDiscount(discount: Discount): String = {
    // Apply discount qualification and calculation logic
    val applicableDiscount = DiscountFunctions.filter { case (qualifyingFunction, _) =>
      qualifyingFunction(discount)
    }.map { case (_, calculationFunction) =>
      calculationFunction(discount)
    }.sorted.reverse.take(2)

    // Calculate average discount, replace NaN with 0.0
    val averageDiscount = if (applicableDiscount.nonEmpty) {
      applicableDiscount.sum / applicableDiscount.length.toDouble
    } else {
      0.0
    }

    // Log warning if discount is null
    if (averageDiscount.isNaN) {
      logger.warn("- Discount resulted in null for order: {}", discount)
    }

    // Calculate total after discount
    val totalAfterDiscount = (discount.unitPrice * discount.quantity) - averageDiscount

    // Format the result string with all necessary output
    s"${getDateOnly(discount.orderDate)}, ${discount.productName}, ${discount.expiryDate}, ${discount.quantity}, ${discount.unitPrice}, ${discount.channel}, ${discount.paymentMethod}, $averageDiscount, $totalAfterDiscount"
  }

  // Writing each line separately
  def writeLine(line: String): Unit = writer.write(line + "\n")

  // Write ti the output
  lines.map(toDiscount).map(processDiscount).foreach(writeLine)
  writer.close()

  // Print to console
  lines.map(toDiscount).map(processDiscount).foreach(println)
}