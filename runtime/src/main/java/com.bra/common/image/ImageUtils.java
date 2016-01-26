package com.bra.common.image;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;

/**
 * 图片辅助类
 *
 * @author xiaobin
 */
public abstract class ImageUtils {
    /**
     * 图片的后缀
     */
    public static final String[] IMAGE_EXT = new String[]{"jpg", "jpeg",
            "gif", "png", "bmp"};

    /**
     * 是否是图片
     *
     * @param ext
     * @return "jpg", "jpeg", "gif", "png", "bmp" 为文件后缀名者为图片
     */
    public static boolean isValidImageExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : IMAGE_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the underlying input stream contains an image.
     *
     * @param in input stream of an image
     * @return <code>true</code> if the underlying input stream contains an
     * image, else <code>false</code>
     */
    public static boolean isImage(final InputStream in) {
        ImageInfo ii = new ImageInfo();
        ii.setInput(in);
        return ii.check();
    }

    /**
     * 获得水印位置
     *
     * @param width   原图宽度
     * @param height  原图高度
     * @param p       水印位置 1-5，其他值为随机。1：左上；2：右上；3：左下；4：右下；5：中央。
     * @param offsetx 水平偏移。
     * @param offsety 垂直偏移。
     * @return 水印位置
     */
    public static Position markPosition(int width, int height, int p,
                                        int offsetx, int offsety) {
        if (p < 1 || p > 5) {
            p = (int) (Math.random() * 5) + 1;
        }
        int x, y;
        switch (p) {
            // 左上
            case 1:
                x = offsetx;
                y = offsety;
                break;
            // 右上
            case 2:
                x = width + offsetx;
                y = offsety;
                break;
            // 左下
            case 3:
                x = offsetx;
                y = height + offsety;
                break;
            // 右下
            case 4:
                x = width + offsetx;
                y = height + offsety;
                break;
            // 中央
            case 5:
                x = (width / 2) + offsetx;
                y = (height / 2) + offsety;
                break;
            default:
                throw new RuntimeException("never reach ...");
        }
        return new Position(x, y);
    }

    /**
     * 水印位置
     * <p>
     * 包含左边偏移量，右边偏移量。
     *
     * @author liufang
     */
    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }


    //FileImageInputStream
    public static BufferedImage readImage(File image) throws IOException {
        ImageInputStream stream = new FileImageInputStream(image);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(stream);
        } catch (Exception e){
            bufferedImage = null;
        }finally {
            IOUtils.closeQuietly(stream);
        }
        if (bufferedImage != null) {
			return bufferedImage;
        }
        stream = new FileImageInputStream(image);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
        IOException lastException = null;
        while (iter.hasNext()) {
            ImageReader reader = null;
            try {
                reader = iter.next();
                ImageReadParam param = reader.getDefaultReadParam();
                reader.setInput(stream, true, true);
                Iterator<ImageTypeSpecifier> imageTypes = reader.getImageTypes(0);
                while (imageTypes.hasNext()) {
                    ImageTypeSpecifier imageTypeSpecifier = imageTypes.next();
                    int bufferedImageType = imageTypeSpecifier.getBufferedImageType();
                    if (bufferedImageType == BufferedImage.TYPE_BYTE_GRAY) {
                        param.setDestinationType(imageTypeSpecifier);
                        break;
                    }
                }
                bufferedImage = reader.read(0, param);
                if (null != bufferedImage) break;
            } catch (IOException e) {
                lastException = e;
            } finally {
                if (null != reader) reader.dispose();
            }
        }
        IOUtils.closeQuietly(stream);
        // If you don't have an image at the end of all readers
        if (null == bufferedImage) {
            if (null != lastException) {
                throw lastException;
            }
        }
        return bufferedImage;
    }

}
