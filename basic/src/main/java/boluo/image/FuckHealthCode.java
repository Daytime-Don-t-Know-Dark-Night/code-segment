package boluo.image;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author dingc
 * @Date 2022-09-11 12:27
 * @Description
 */
public class FuckHealthCode {

    // 模板图片路径
    private static final URI TEMPLATE_PATH;
    private static final String OUTPUT_PATH;

    static {
        // 初始化健康证图片模板路径
        Resource input = new ClassPathResource("image/health/健康码.jpg");
        try {
            TEMPLATE_PATH = input.getURI();
            OUTPUT_PATH = "C:\\Projects\\home\\parent\\basic\\src\\main\\resources\\image\\health\\res.jpg";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("M月dd日"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        map.put("date", date);
        map.put("time", time);
        createImage(map, TEMPLATE_PATH, OUTPUT_PATH);
    }

    public static void createImage(Map<String, Object> map, URI inputURI, String outputURI) {
        try {
            // 加载模板图片
            BufferedImage image = ImageIO.read(new File(inputURI));
            // 得到图片操作对象
            Graphics2D graphics = image.createGraphics();
            //消除文字锯齿
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除图片锯齿
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置文字的颜色为绿色
            graphics.setColor(new Color(0, 205, 102));
            // 设置当前日期的字体, 大小, 位置
            // todo 10月份之后位置前移
            graphics.setFont(new Font("等线", Font.BOLD, 45));
            graphics.drawString(map.get("date").toString(), 140, 175);
            // 设置当前时间的字体, 大小, 位置
            graphics.setFont(new Font("等线", Font.BOLD, 50));
            graphics.drawString(map.get("time").toString(), 125, 235);

            // todo 设置最近核酸检测时间


            // 将健康码图片存储到本地
            saveImage(outputURI, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveImage(String output, BufferedImage image) {
        try {
            ImageIO.write(image, "jpg", new File(output));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
