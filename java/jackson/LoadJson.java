package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

/**
 * @Author dingc
 * @Date 2022-09-05 21:01
 * @Description
 */
public class LoadJson {

    private static final ObjectMapper mapper = new ObjectMapper();

    // 使用jackson解析json文件
    public static void main(String[] args) throws IOException {

        String path = "C:\\Projects\\home\\dingx\\doc\\test\\recon.json";
        ObjectNode obj = (ObjectNode) mapper.readTree(new File(path));

        String id = obj.at("/NPC/BS001/ID").asText();
        String name = obj.at("/NPC/BS001/Name").asText();
        String hp = obj.at("/NPC/BS001/HP").asText();

        String res = String.format("id: %s, name: %s, hp: %s", id, name, hp);
        System.out.println(res);

    }

}
