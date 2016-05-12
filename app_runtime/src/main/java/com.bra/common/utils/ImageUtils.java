package com.bra.common.utils;



import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by xiaobin on 15/4/27.
 */
public class ImageUtils {


    // JGP格式
    public static final String JPG = "jpg";


    public static final String JPEG = "jpeg";


    // GIF格式
    public static final String GIF = "gif";
    // PNG格式
    public static final String PNG = "png";
    // BMP格式
    public static final String BMP = "bmp";


    public static boolean hasImgExt(String ext) {
        if (JPG.equals(ext) || GIF.equals(ext) || PNG.equals(ext) || BMP.equals(ext) || JPEG.equals(ext)) {
            return true;
        }
        return false;
    }


    public static void converter(File imgFile, String format, File formatFile)
            throws IOException {
        BufferedImage bIMG = ImageIO.read(imgFile);
        ImageIO.write(bIMG, format, formatFile);
    }


    /**
     * 获取文件类型
     *
     * @param file
     * @return jpg、png、gif
     */
    public static boolean getFormatName(File file, String ext) {
        try {
            if ("jpg".equals(ext)) {
                ext = "jpeg";
            }
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                return false;
            }
            ImageReader reader = iter.next();
            iis.close();
            //System.out.println(reader.getFormatName().toLowerCase());
            return ext.equals(reader.getFormatName().toLowerCase());
        } catch (IOException e) {
        }
        return false;
    }


    public static boolean getFormatName(InputStream stream, String ext) {
        try {
            if ("jpg".equals(ext)) {
                ext = "jpeg";
            }
            ImageInputStream iis = ImageIO.createImageInputStream(stream);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                return false;
            }

            ImageReader reader = iter.next();
            iis.close();
            return ext.equals(reader.getFormatName().toLowerCase());
        } catch (IOException e) {
        }
        return false;
    }
    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

    /**
     * 程序入口：用于测试
     *
     * @param args
     */
    public static void main(String[] args) {
		/*
		 * // 1-缩放图像： // 方法一：按比例缩放 ImageUtils.scale("e:/abc.jpg",
		 * "e:/abc_scale.jpg", 2, true);//测试OK // 方法二：按高度和宽度缩放
		 * ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300,
		 * true);//测试OK
		 *
		 * // 2-切割图像： // 方法一：按指定起点坐标和宽高切割 ImageUtils.cut("e:/abc.jpg",
		 * "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK // 方法二：指定切片的行数和列数
		 * ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK // 方法三：指定切片的宽度和高度
		 * ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK
		 *
		 * // 3-图像类型转换： ImageUtils.convert("e:/abc.jpg", "GIF",
		 * "e:/abc_convert.gif");//测试OK
		 */
		/*
		 * String src =
		 * "E:/work/source/yunos.taobao.com/server/single_color_e6de09d8c5c95fe355e5a7348a4a36b6.jpg"
		 * ; String dest =
		 * "E:/work/source/yunos.taobao.com/server/single_cut_color_e6de09d8c5c95fe355e5a7348a4a36b6.jpg"
		 * ; ImageUtils.cut(src, dest, 267, 68, 500, 640 );
		 */
        // 4-彩色转黑白：
//		String[] picList = new String[3];
//		picList[0] = "D:/yunos/Spring-AOP-Advice-Examples/test1";
//		picList[1] = "D:/yunos/Spring-AOP-Advice-Examples/test2";
//		picList[2] = "D:/yunos/Spring-AOP-Advice-Examples/test3";
//
//		for(int i=0; i<picList.length; i++){
//			String src=picList[i]+".jpg";
//			String gray=src+"_gray.jpg";
//			String black = src+"_black.jpg";
//			String white = src+"_white.jpg";
//			convert(src, gray, black, white);
//		}

        // 5-给图片添加文字水印：
        // 方法一：

        // Color color = new Color(0xeb, 0xe0, 0xd0);

        // 方法二：
        // ImageUtils.pressText2("我也是水印文字",
        // "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0,
        // 0, 0.5f);//测试OK

        // 6-给图片添加图片水印：
        ImageUtils.pressImage("D:/yunos/Spring-AOP-Advice-Examples/test2.jpg",
                "D:/yunos/Spring-AOP-Advice-Examples/test1.jpg","D:/yunos/Spring-AOP-Advice-Examples/test_desc.jpg", 0, 0, 0.5f);//测试OK
    }

    public static void convert(String src, String gray, String black, String white){
        ImageUtils.gray(src, gray);// 测试OK
        try {
            BufferedImage grayImage = ImageIO.read(new File(gray));
            RenderedImage blackImage = ImageUtils
                    .applyBinarize(grayImage, 0.33);
            ImageIO.write(blackImage, "jpg", new File(black));

            BufferedImage blackBufferedImage = ImageIO.read(new File(black));
            // 转为白底
            ImageUtils.turnColor(blackBufferedImage);
            ImageIO.write(blackBufferedImage, "jpg", new File(white));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static BufferedImage convertRenderedImage(RenderedImage img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm
                .createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable properties = new Hashtable();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                properties.put(keys[i], img.getProperty(keys[i]));
            }
        }
        BufferedImage result = new BufferedImage(cm, raster,
                isAlphaPremultiplied, properties);
        img.copyData(raster);
        return result;
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcImageFile
     *            源图像文件地址
     * @param result
     *            缩放后的图像地址
     * @param scale
     *            缩放比例
     * @param flag
     *            缩放选择:true 放大; false 缩小;
     */
    public final static void scale(String srcImageFile, String result,
                                   int scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile
     *            源图像文件地址
     * @param result
     *            缩放后的图像地址
     * @param height
     *            缩放后的高度
     * @param width
     *            缩放后的宽度
     * @param bb
     *            比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale2(String srcImageFile, String result,
                                    int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {// 补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param srcImageFile
     *            源图像地址
     * @param formatName
     *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile
     *            目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName,
                                     String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImageFile
     *            源图像地址
     * @param destImageFile
     *            目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 先转成8bit的
    public final static RenderedImage convertTo8BitGray(RenderedImage colorImage) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(colorImage);
        ColorModel cm = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_GRAY), new int[] { 8 },
                false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        pb.add(cm);
        RenderedImage grayImage = JAI.create("ColorConvert", pb);
        return grayImage;
    }

    // 一级1位
    public static RenderedImage applyDithering(RenderedImage grayImage,
                                               boolean isErrorDiffusion) {
        // Load the ParameterBlock for the dithering operation
        // and set the operation name.
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(grayImage);
        String opName = null;
        if (isErrorDiffusion) {
            opName = "errordiffusion";
            LookupTableJAI lut = new LookupTableJAI(new byte[] { (byte) 0x00,
                    (byte) 0xff });
            pb.add(lut);
            pb.add(KernelJAI.ERROR_FILTER_FLOYD_STEINBERG);
        } else {
            opName = "ordereddither";
            ColorCube cube = ColorCube.createColorCube(DataBuffer.TYPE_BYTE, 0,
                    new int[] { 2 }); // 尝试改变2为其它值，可以得到不同效果
            pb.add(cube);
            pb.add(KernelJAI.DITHER_MASK_441);
        }
        // Create a layout containing an IndexColorModel which maps
        // zero to zero and unity to 255.
        ImageLayout layout = new ImageLayout();
        byte[] map = new byte[] { (byte) 0x00, (byte) 0xff };
        ColorModel cm = new IndexColorModel(1, 2, map, map, map);
        layout.setColorModel(cm);
        // Create a hint containing the layout.
        RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);
        // Dither the image.
        RenderedOp bwImage = JAI.create(opName, pb, hints);
        return bwImage;
    }

    // 然后转成1位的
    /***
     * Binarize image (convert image to 1 bit black and white)
     * 输入图片必须为灰度图片，否则会出错。
     */
    public static RenderedImage applyBinarize(RenderedImage grayImage,
                                              double thresholdValue) {
        // Generate a histogram.
        Histogram histogram = (Histogram) JAI.create("histogram", grayImage)
                .getProperty("histogram");
        // Get a threshold equal to the median.


        double[] threshold = histogram.getPTileThreshold(thresholdValue); // 改变域值可以得到不同效果
        // Binarize the image.
        RenderedImage bwImage = JAI.create("binarize", grayImage, new Double(
                threshold[0]));
        return bwImage;
    }// function applyBinarize

    public static void turnColor(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int _rgb1 = srcImage.getRGB(i, j);
                int _rgb2 = ~_rgb1;
                srcImage.setRGB(i, j, _rgb2);
            }

        }
    }

    /**
     * 给图片添加文字水印
     *
     *            水印文字
     * @param srcImageFile
     *            源图像地址
     * @param destImageFile
     *            目标图像地址
     * @param font
     *            水印的字体名称
     * @param color
     *            水印的字体颜色
     * @param x
     *            修正值
     * @param y
     *            修正值
     * @param alpha
     *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return
     */
    public final static BufferedImage pressText(String text,
                                                String srcImageFile, String destImageFile, Font font, Color color,
                                                int x, int y, float alpha) {
        try {

            URL templetImage = Thread.currentThread().getContextClassLoader()
                    .getResource(srcImageFile);
            Image src = ImageIO.read(templetImage);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(text, x, y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "png", new File(destImageFile));// 输出到文件流
            return ImageIO.read(new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg
     *            水印图片
     * @param srcImageFile
     *            源图像地址
     * @param destImageFile
     *            目标图像地址
     * @param x
     *            修正值。 默认在中间
     * @param y
     *            修正值。 默认在中间
     * @param alpha
     *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,
                                        String destImageFile, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG",
                    new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}
