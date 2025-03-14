package com.boluo.hadoop;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

/**
 * @author chao
 * @date 2023/1/19 16:52
 * @desc
 */
public class HdfsBatchVideo {

    // 使用HDFS批量重命名视频文件, 1,2,3,4...

    public static void main(String[] args) throws IOException {

        String sourcePath = "file:///C:/Users/chao/Videos/Study-111/";
        String targetPath = "file:///C:/Users/chao/Videos/study/";

        // 读取所有视频
        SparkSession.builder().master("local[*]").appName("SparkDemo").getOrCreate();
        FileSystem fs = FileSystem.get(SparkSession.active().sparkContext().hadoopConfiguration());
        FileStatus[] fsParentFiles = fs.listStatus(new Path(sourcePath));

        // 统计数量, 重命名至新文件夹
        for (int i = 1; i <= fsParentFiles.length; i++) {
            String currentName = fsParentFiles[i - 1].getPath().getName();
            String suffix = currentName.substring(currentName.indexOf("."));
            String newName = targetPath + i + suffix;
            fs.rename(new Path(sourcePath + currentName), new Path(newName));
        }
        fs.delete(new Path(sourcePath), true);
        System.out.println("finished !!");
    }

}
