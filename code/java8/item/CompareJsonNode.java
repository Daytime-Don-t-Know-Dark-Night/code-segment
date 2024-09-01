package java8.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * @author chao
 * @date 2023/2/14 22:52
 * @desc
 */
public class CompareJsonNode {

    // 前两个测试用例和方法都是ChatGPT生成的

    @Test
    public void test1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = objectMapper.readTree("{\"field1\": 123, \"field2\": [4, 5, 6]}");
        JsonNode node2 = objectMapper.readTree("{\"field1\": 123, \"field2\": [4, 5, 6]}");

        boolean result = isJsonNodesEqual(node1, node2);
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void test2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = objectMapper.readTree("{\"field1\": 123, \"field2\": [4, 5, 6]}");
        JsonNode node2 = objectMapper.readTree("{\"field1\": 123, \"field2\": [4, 5, 7]}");

        boolean result = isJsonNodesEqual(node1, node2);
        System.out.println(result);
        Assert.assertFalse(result);
    }

    public static boolean isJsonNodesEqual(JsonNode node1, JsonNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == node2;
        }

        if (node1.getNodeType() != node2.getNodeType()) {
            return false;
        }

        switch (node1.getNodeType()) {
            case ARRAY:
                if (node1.size() != node2.size()) {
                    return false;
                }
                for (int i = 0; i < node1.size(); i++) {
                    if (!isJsonNodesEqual(node1.get(i), node2.get(i))) {
                        return false;
                    }
                }
                break;
            case OBJECT:
                if (node1.size() != node2.size()) {
                    return false;
                }
                for (Iterator<String> it = node1.fieldNames(); it.hasNext(); ) {
                    String fieldName = it.next();
                    if (!node2.has(fieldName)) {
                        return false;
                    }
                    if (!isJsonNodesEqual(node1.get(fieldName), node2.get(fieldName))) {
                        return false;
                    }
                }
                break;
            case NUMBER:
                if (!node1.numberValue().equals(node2.numberValue())) {
                    return false;
                }
                break;
            case BOOLEAN:
                if (node1.booleanValue() != node2.booleanValue()) {
                    return false;
                }
                break;
            case STRING:
                if (!node1.textValue().equals(node2.textValue())) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

}
