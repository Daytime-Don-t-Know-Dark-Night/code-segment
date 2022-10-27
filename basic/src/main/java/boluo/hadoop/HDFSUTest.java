package boluo.hadoop;

import com.google.common.base.Preconditions;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

/**
 * @author chao
 * @date 2022/10/27 20:11
 * @desc
 */
public class HDFSUTest {

    public static void main(String[] args) throws IOException {
        SparkSession.builder().master("local[*]").appName("SparkDemo").getOrCreate();
        String batchId = "OH20221027";
        String tmpPath = "file:///C:/Users/chao/IdeaProjects/parent/doc/tmp/";
        String targetPath = "file:///C:/Users/chao/IdeaProjects/parent/doc/target/";
        renameMove(tmpPath, targetPath, batchId);
    }

    // 将tmp路径下的文件转移到target路径, 并且重命名, 删除原文件和原目录
    private static void renameMove(String sourcePath, String targetPath, String batchId) throws IOException {

        Path parentPath = new Path(sourcePath);
        FileSystem fs = FileSystem.get(SparkSession.active().sparkContext().hadoopConfiguration());
        FileStatus[] fsParentFiles = fs.listStatus(parentPath);

        Preconditions.checkArgument(fsParentFiles.length == 1);
        FileStatus file = fsParentFiles[0];
        String currentName = file.getPath().getName();
        if (currentName.startsWith("part")) {
            String suffix = currentName.substring(currentName.indexOf("."));
            String newName = targetPath + batchId + suffix;
            fs.rename(new Path(sourcePath + currentName), new Path(newName));
            fs.delete(new Path(sourcePath), true);
        }

    }

}
