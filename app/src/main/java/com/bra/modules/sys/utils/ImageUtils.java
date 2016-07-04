package com.bra.modules.sys.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DDT on 2016/6/24.
 */
public class ImageUtils {
    private static Image img;
    private static int width;
    private static int height;
    /**
     * 构造函数
     */
    public static void init(File file) throws IOException {
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }
    /**
     * 按照宽度还是高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public static void resizeFix(File file,int w, int h) throws IOException {
        init(file);
        if (width / height > w / h) {
            resizeByWidth(file,w);
        } else {
            resizeByHeight(file,h);
        }
    }
    /**
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */
    public static void resizeByWidth(File file,int w) throws IOException {
        int h = (int) (height * w / width);
        resize(file,w, h);
    }
    /**
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    public static void resizeByHeight(File file,int h) throws IOException {
        int w = (int) (width * h / height);
        resize(file,w, h);
    }
    /**
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */
    public static void resize(File file,int w, int h) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        FileOutputStream out = new FileOutputStream(file); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }
}
