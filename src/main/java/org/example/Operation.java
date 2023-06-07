package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Operation {

    public static Boolean deleteDir(String dirPath){
        File directory = new File(dirPath);
//        Path path = Paths.get(outPutPath);
        try {
            FileUtils.deleteDirectory(directory);
            return true;
        }catch (IOException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static Boolean deleteFile(String filePath){
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
            return true;
        }catch (IOException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static <E> void saveListAsTextFile(JavaSparkContext sc, List<E> dataList, String outPutPath){
        JavaRDD<E> outPutFile;
        outPutFile = sc.parallelize(dataList);
        outPutFile.saveAsTextFile(outPutPath);
    }

    public static int stringArrayPlusAll(String[] array,int start, int end) {
        int sum = 0;
        for(int i = start; i < end;i++){
            sum = sum + Integer.parseInt(array[i]);
        }
        return sum;
    }

}
