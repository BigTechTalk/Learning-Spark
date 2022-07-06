package com.spark.tutorial

import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions.{col,expr}

object selectExample {

  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR);
    val spark = SparkSession.builder().appName("SparkSelectExample").master("local[*]")
      .getOrCreate();

    val df = spark.read.format("csv")
      .option("header", true)
      .load("D:\\SampleData\\IPL\\matches.csv");

    //df.show();

    println("Select function using column name as string")
    df.select("id", "season", "city", "date", "team1", "team2").show(10, false)

    println("Select function using dataframe as object name");
    df.select(df("id"), df("season"), df("season").alias("Year"), 
        df("city"), df("date"), df("team1"), df("team2")).show(10,false)

    println("Select function using col ");
    df.select(col("id"),col("season"),col("season").alias("Year"),col("city"),
        col("date"),col("team1"),col("team2")).show(10,false)
     
    println("Select function using col and dataframe object ");
    df.select(df("id"),col("season"),col("season").alias("Year"),col("city"),
        col("date"),col("team1"),col("team2"),expr("concat(team1,team2) as teams")).show(10,false)    
  }

}