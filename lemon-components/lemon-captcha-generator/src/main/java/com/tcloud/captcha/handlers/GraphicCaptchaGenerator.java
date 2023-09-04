package com.tcloud.captcha.handlers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class GraphicCaptchaGenerator {


    private final int width;
    private final int height;
    private final Random random = new Random();
    private final String[] fontStyles = {"Arial", "Verdana", "Times New Roman", "Courier New"};

    public GraphicCaptchaGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BufferedImage generateCaptcha() {
        // 创建一个空白的图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文对象
        Graphics2D g2d = image.createGraphics();

        // 设置背景色为白色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 生成随机干扰线
        drawRandomLines(g2d, 10);

        // 生成随机验证码
        String captchaText = generateRandomCode(5);

        // 设置字体样式和大小
        Font font = new Font(fontStyles[random.nextInt(fontStyles.length)], Font.BOLD, height - 10);
        g2d.setFont(font);

        // 将验证码绘制到图片中心
        int x = (width - g2d.getFontMetrics().stringWidth(captchaText)) / 2;
        int y = height / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(captchaText, x, y);

        // 释放图形上下文资源
        g2d.dispose();

        return image;
    }

    private void drawRandomLines(Graphics2D g2d, int numLines) {
        for (int i = 0; i < numLines; i++) {
            int startX = random.nextInt(width);
            int startY = random.nextInt(height);
            int endX = random.nextInt(width);
            int endY = random.nextInt(height);
            g2d.setColor(getRandomColor());
            g2d.drawLine(startX, startY, endX, endY);
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'A');
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        GraphicCaptchaGenerator captchaGenerator = new GraphicCaptchaGenerator(215, 60);
        BufferedImage captchaImage = captchaGenerator.generateCaptcha();
        // 在这里将验证码图片用于你的需要，比如输出到文件或显示在网页上
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 将BufferedImage对象写入流
            ImageIO.write(captchaImage, "png", baos);

            // 获取字节数组
            byte[] imageBytes = baos.toByteArray();

            // 将字节数组转换为Base64编码的字符串
            String s = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}