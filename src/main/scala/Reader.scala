import org.apache.spark.sql.SparkSession

object Reader {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Scala Academy Exam - Bugfix")
      .getOrCreate()

    import spark.implicits._

    val ds = spark
      .read.parquet("dataset")

    ds.createOrReplaceTempView("Persons")

    ds.sqlContext.sql(
      "SELECT position, name, surname, " +
        "SUM(CASE WHEN contact RLIKE '\"(?=[a-zA-Z0-9._%+-]{6,})[a-zA-Z0-9._%+-]+(@|%40)(?:[a-zA-Z0-9.-]{1,63}\\\\.)+[a-zA-Z]{2,}\"' THEN 1 ELSE 0 END) AS email_total " +
        "FROM Persons " +
        "WHERE MOD(id,2)=0 GROUP BY position, name, surname")
      .show()
  }

}
