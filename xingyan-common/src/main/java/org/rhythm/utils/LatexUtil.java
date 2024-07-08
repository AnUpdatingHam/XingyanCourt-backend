package org.rhythm.utils;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LatexUtil {
    /**
     * latex公式转图片
     * */
    public static String latexToImage(String formulaStr,String path) throws IOException {
        TeXFormula tf = new TeXFormula(formulaStr);
        TeXIcon ti = tf.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
        BufferedImage bimg = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bimg.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,ti.getIconWidth(),ti.getIconHeight());
        JLabel jl = new JLabel();
        jl.setForeground(new Color(0, 0, 0));
        ti.paintIcon(jl, g2d, 0, 0);
        File out = new File(path);
        ImageIO.write(bimg, "png", out);
        return path;
    }

    public static void main(String[] args) {
        try {
            // 定义LaTeX公式字符串
            //String formulaStr = "\\int_{0}^\\pi \\sin^2 x \\,dx";
            String formulaStr = "$A=\\{x|x^2-3x+2<0\\}$，$B=\\{x|x^2-x-6>0\\}$";
            // 定义图片保存路径
            String imagePath = "image.png";
            // 调用方法生成图片
            String resultPath = latexToImage(formulaStr, imagePath);
            System.out.println("Image saved to: " + resultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
