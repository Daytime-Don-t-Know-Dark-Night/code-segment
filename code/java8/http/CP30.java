package java8.http;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Streams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chao
 * @datetime 2024-09-30 13:38
 * @description
 */
public class CP30 {

    private static ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) throws InterruptedException {
        while (true) {
            postCP30();
            Thread.sleep(10_000L);
        }
    }

    public static void postCP30() {

        String url = "https://www.allcpp.cn/allcpp/ticket/getTicketTypeList.do?eventMainId=1729";  // 替换为你的接口地址

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");  // 设置Header
            con.setRequestProperty("cookie", "token=\"Wv4EdECLIG5FUiAnsPsMkTeApprA1PjJfRhwdjDpSiUARV5zyliGui0VmweJToueX/TKQDGpN8uxTV1uQrktqDKHl0Pyr4t0lQHB2JUxG/wQPOpbQWNJ57miLL7XeZPKPNecnDLAoB4vS5+ESIySOHv7pKwQp7LvWTDzXSu/ieE=\"; JALKSJFJASKDFJKALSJDFLJSF=2463832160c07e8aff308d4b19b36535507ad4ad02218.109.201.71_1852161875; acw_tc=71d7e81d17276739740447441ed315ec1e687b3d36028b09a2259e9433; cdn_sec_tc=71d7e81d17276739740447441ed315ec1e687b3d36028b09a2259e9433; JSESSIONID=7EB6E278EFCE3B4EA2AF9FBBDF1D1ED3; Hm_lvt_75e110b2a3c6890a57de45bd2882ec7c=1727594717,1727673975; HMACCOUNT=4BBB81596FDBD3AC; Hm_lpvt_75e110b2a3c6890a57de45bd2882ec7c=1727674015");  // 如果需要认证，替换为实际token

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 监听
                ObjectNode objectNode = (ObjectNode) mapper.readTree(response.toString());
                ArrayNode arrayNode = (ArrayNode) objectNode.at("/ticketTypeList");
                List<JsonNode> collect = Streams.stream(arrayNode).filter(i -> {
                    return ((ObjectNode) i).at("/id").asText().equals("3629");
                }).collect(Collectors.toList());

                int remainderNum = ((ObjectNode) collect.get(0)).at("/remainderNum").asInt();
                if (remainderNum > 0) {
                    // 弹窗
                    System.out.println("弹窗");
                    // 在事件调度线程中创建和显示窗口
                    SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame("Hello World"); // 创建窗口标题
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作

                        JLabel label = new JLabel("欢迎使用Java窗口程序!", JLabel.CENTER); // 创建标签
                        frame.getContentPane().add(label); // 将标签添加到窗口

                        frame.setSize(300, 200); // 设置窗口大小
                        frame.setVisible(true); // 显示窗口
                    });

                } else {
                    System.out.println(LocalDateTime.now() + " 10月6号已无余票 ...");
                }

            } else {
                System.out.println("请求失败，状态码：" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
