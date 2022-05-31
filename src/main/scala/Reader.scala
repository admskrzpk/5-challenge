import org.apache.spark.sql.SparkSession

object Reader {

  def main(args: Array[String]): Unit = {

    //FIXED in order to submit app
    val path = if (args.length > 0) args(0) else "dataset"

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Scala Academy Exam - Bugfix")
      .getOrCreate()

    val ds = spark
      .read.parquet(path)

    ds.createOrReplaceTempView("Persons")

    val result = spark.sql(
      "SELECT position, name, surname, " +
        "SUM(CASE WHEN contact RLIKE '\"(?=[a-zA-Z0-9._%+-]{6,})[a-zA-Z0-9._%+-]+(@|%40)(?:[a-zA-Z0-9.-]{1,63}\\\\.)+[a-zA-Z]{2,}\"' THEN 1 ELSE 0 END) AS email_total " +
        "FROM Persons " +
        "WHERE MOD(id,2)=0 GROUP BY position, name, surname")
      .show()
  }
}