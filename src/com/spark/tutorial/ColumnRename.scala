package com.spark.tutorial

import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.SparkSession

object ColumnRename {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().appName("columnRename").master("local[*]")
      .getOrCreate();

    val df = spark.read.format("csv")
      .option("header", true)
      .load(".\\sample_data\\matches.csv")

    df.show(5, false)

    println("Rename column using withColumnRenamed")
    val dfColumnRename1 = df.withColumnRenamed("season", "Year")
      .withColumnRenamed("team1", "teamA")
      .withColumnRenamed("team2", "teamB")

    dfColumnRename1.show(5, false);

    println("Rename column using alias")
    val dfColumnRename2 = df.select(df("id"), df("season").alias("Year"),
      df("team1").alias("teamA"), df("team2").alias("teamB"))
    dfColumnRename2.show(5, false);

    println("Rename column using as")
    val dfColumnRename3 = df.select(df("id"), df("season").as("Year"),
      df("team1").as("teamA"), df("team2").as("teamB"))
    dfColumnRename3.show(5, false);

    println("Rename column using toDF")
    val newColumns = Seq("id", "Year", "city", "date", "teamA", "teamB", "toss_winner", "toss_decision",
      "result", "dl_applied", "winner", "win_by_runs", "win_by_wickets", "player_of_match",
      "venue", "umpire1", "umpire2", "umpire3")

    val dfColumnRename4 = df.toDF(newColumns: _*)
    dfColumnRename4.show(5, false);

  }

}