package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import java.util.Arrays;
import java.util.List;


public class Main {
    private static SparkConf conf = new SparkConf()
            .setMaster("local").setAppName("appName");
    private static JavaSparkContext sc = new JavaSparkContext(conf);

    public static void main(String[] args) {
        String inputPath = "/Users/imac-1682/Documents/test.txt";
        String inputPath2 = "/Users/imac-1682/Documents/test2.txt";
        String outputPath = "/Users/imac-1682/Documents/output";

        JavaRDD<String> readFile,readFile2;
        Operation.deleteDir(outputPath);

        readFile = sc.textFile(inputPath);
        readFile2 = sc.textFile(inputPath2);

        readFile2.map(s -> s.split(","))
                .mapToPair(s -> new Tuple2<>(s[0],Operation.stringArrayPlusAll(s,1,s.length)))
                .foreach(s -> System.out.println(s));

        //--------高到低----------//
        JavaPairRDD<String,Integer> sumPairRdd = readFile.flatMap(s ->
                        Arrays.stream(s.split("\\|"))
                                .iterator())
                .filter(s -> (Integer.parseInt(s.split(",")[1])) > 0)
                .mapToPair(s -> new Tuple2<>(s.split(",")[0],Integer.parseInt(s.split(",")[1])))
                .reduceByKey((a,b) ->a+b);

        sumPairRdd.foreach(s -> System.out.println(s));

        JavaPairRDD<String,Integer> highToLowPairRdd = sumPairRdd
                        .mapToPair(tuple2 -> tuple2.swap())
                        .sortByKey(false)
                        .mapToPair(tuple2 -> tuple2.swap());

        highToLowPairRdd.foreach(s -> System.out.println(s));

        //--------平均-----------//
        List<Tuple2<String,Integer>> times = readFile.flatMap(s ->
                        Arrays.stream(s.split("\\|"))
                                .iterator())
                .filter(s -> (Integer.parseInt(s.split(",")[1])) > 0)
                .mapToPair(s -> new Tuple2<>(s.split(",")[0],1))
                .reduceByKey((a,b) -> a+b)
                .collect();

        times.forEach(s ->{

        });
        times.forEach(s -> System.out.println(s));

//        times.map(s -> s.)

//        System.out.println(rddList.toString());

//        Operation.saveListAsTextFile(sc, rddList, outputPath);
        System.out.println("輸出完成");
//        Tuple2<String,String>

    }
}
