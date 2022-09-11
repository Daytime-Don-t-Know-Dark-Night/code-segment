package boluo.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 * @Author dingc
 * @Date 2022-09-11 12:01
 * @Description
 */
public class GenerateTimePicture {

    // 模板图片路径
    private static String TEMPLATEPAHT = "";

    // 模板印章路径
    private static String TEMPLATEPATHYZ = "";

    static {
        // 初始化健康证图片模板路径
        TEMPLATEPAHT = "C:\\Projects\\home\\parent\\image\\health\\健康证.png";
        // 初始化健康证图片模板印章路径
        TEMPLATEPATHYZ = "C:\\Projects\\home\\parent\\image\\health\\健康证章.png";
    }


    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("age", 25);
        map.put("no", "20170910001");
        map.put("name", "小崔");
        map.put("gender", "男");
        map.put("begin", "2017年9月10日");
        map.put("project", "小食品");
        map.put("idCode", "123456789012345678");
        createImage(map, "C:\\Projects\\home\\parent\\image\\health\\CBC.png");
    }

    public static void createImage(Map<String,Object> map, String path) {
        try{
            // 加载模板图片
            BufferedImage image = ImageIO.read(new File(TEMPLATEPAHT));
            // 得到图片操作对象
            Graphics2D graphics = image.createGraphics();
            //消除文字锯齿
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除图片锯齿
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 设置文字的颜色为黑色
            graphics.setColor(Color.black);
            // 设置文字的字体,大小
            graphics.setFont(new Font("宋体", Font.BOLD, 60));
            // 健康证印章
            File files = new File(TEMPLATEPATHYZ);
            // 年龄
            graphics.drawString(map.get("age") == null ? "" : map.get("age").toString(), 1620, 580);
            // 编号
            String no =  map.get("no") == null ? "" : map.get("no").toString();
            graphics.drawString("编号:"+ no, 135, 1130);
            // 姓名
            graphics.drawString(map.get("name") == null ? "" : map.get("name").toString(), 1170, 400);
            // 性别
            graphics.drawString(map.get("gender") == null ? "" : map.get("gender").toString(), 1170, 580);
            // 有效期开始时间
            graphics.drawString(map.get("begin") == null ? "" : map.get("begin").toString(), 1170, 930);
            // 经营项目
            graphics.drawString(map.get("project") == null ? "" : map.get("project").toString(), 1170, 750);
            // 身份证号
            graphics.drawString(map.get("idCode") == null ? "" : map.get("idCode").toString(), 1170, 1110);
            // 创建头像地址
            String paths = map.get("facePhoto") == null ? "C:\\Projects\\home\\parent\\image\\health\\健康证默认头像.png": map.get("facePhoto").toString();
            File file = new File(paths);
            // 对头像进行裁剪
            Image img = thumbnail(file, 500, 690);
            // 将头像放入模板中
            graphics.drawImage(img, 130, 130, null);
            // 对印章进行裁剪
            Image imgs = thumbnail(files, 895, 639);
            // 将印章放入模板中
            graphics.drawImage(imgs, 440, 440, null);
            // 将健康证图片存储到本地
            createImage(path, image);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void createImage(String fileLocation, BufferedImage image) {
        try {
            String formatName = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName , new File(fileLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image thumbnail(File img, int width, int height) throws IOException {
        BufferedImage BI = ImageIO.read(img);
        Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return image;
    }

}
