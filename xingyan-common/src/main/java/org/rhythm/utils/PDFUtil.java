package org.rhythm.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.rhythm.utils.LatexUtil.latexToImage;

//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//import org.apache.pdfbox.pdmodel.font.PDType0Font;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;


public class PDFUtil {
    //文件根目录
    public static String RootPath = "";
    // 设置字体文件路径
//注意：如果没有中文字体，那PDF内容涉及中文的区域都不显示
    public static String fontPath = RootPath + "siyuanheiti.otf";

    // 正则表达式模式
    private static String formulationRegex = "\\$[^$\\n]*\\$";
    private static String boldRegex = "\\*\\*(.*?)\\*\\*";  //*....*
    private static String titleRegex = "(#{1,6})\\s+(.*)";  //# ....
    private static String combinedRegex =
            String.format("%s|%s|%s", formulationRegex, boldRegex, titleRegex);
    // 编译正则表达式
    private static Pattern formulationPattern = Pattern.compile(formulationRegex);
    private static Pattern boldPattern = Pattern.compile(boldRegex);
    private static Pattern titlePattern = Pattern.compile(titleRegex);
    private static Pattern combinedPattern = Pattern.compile(combinedRegex);
    private static float m_fontSize = 12.0f;

    public enum SegmentType{
        bold, title, formulation
    }

    public static class SegmentInfo{
        private final int st, ed, param;
        private final SegmentType type;
        private final String content;

        public SegmentInfo(int st, int ed, SegmentType type, int param, String content) {
            this.st = st;
            this.ed = ed;
            this.type = type;
            this.param = param;
            this.content = content;
        }

        public int getSt() {
            return st;
        }

        public int getEd() {
            return ed;
        }

        public SegmentType getType() {
            return type;
        }

        public int getParam() {
            return param;
        }

        public String getContent(){
            return content;
        }
    }


    public static void createDocx(String text) {
        // 创建一个新的Word文档对象
        XWPFDocument doc = new XWPFDocument();

        // 添加段落
        XWPFParagraph para = doc.createParagraph();
        // 添加文本内容
        para.createRun().setText(text);

        try {
            // 将Word文档保存到指定路径
            FileOutputStream out = new FileOutputStream("output.docx");
            doc.write(out);
            out.close();
            System.out.println("Word文档已创建成功！");
        } catch (Exception e) {
            System.out.println("创建Word文档时出现错误：" + e.getMessage());
        }
    }

    public static void createPDF(String contentText, String filepath) {
        deleteFilesInDirectory(new File("temp")); //删除所有临时文件

        // 创建 PdfWriter 和 PdfDocument
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(filepath);
        } catch (FileNotFoundException e) {
            System.out.println("创建PdfWriter失败。。。。。");
            e.printStackTrace();
            return;
        }

        PdfDocument pdfDocument = new PdfDocument(writer);

        // 创建 Document
        Document document = new Document(pdfDocument);

        // 设置页面的边距
        documentMargins(document);

        // 设置页眉和页脚
        headerAndFooter(pdfDocument);

        //编写PDF主体的文档内容 , 这一块是主要编写位置
        setContent(document, contentText);
        //添加水印
        //addWatermark(pdfDocument);

        // 关闭对象
        document.close();   //document文档要在输出前关闭，不然会提示“java.nio.file.NoSuchFileException: editable.pdf”
        pdfDocument.close();
    }

    /**
     * 获取设置的字体
     *
     * @return PdfFont 字体
     */
    private static PdfFont getFont() {
        // 设置中文字体
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(fontPath);
        } catch (IOException e) {
            System.out.println("字体获取失败。。。。。。。。。。。");
            e.printStackTrace();
            return null;
        }
        return font;
    }

    /**
     * 设置页面的边距
     *
     * @param document 内容文档
     */
    private static void documentMargins(Document document) {
        // 上、右、下、左
        int margins = 80;
        document.setMargins(margins, margins, margins, margins);
    }

    /**
     * 设置页眉页脚
     *
     * @param pdfDocument PDF文档
     */
    private static void headerAndFooter(PdfDocument pdfDocument) {

        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new IEventHandler() {

            @Override
            public void handleEvent(Event event) {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfPage page = docEvent.getPage();
                PdfCanvas canvas = new PdfCanvas(page);

                /*
                // 创建页眉
                Rectangle pageSize = page.getPageSize();

                canvas.beginText()
                        .setFontAndSize(getFont(), 10)
                        .moveText(pageSize.getWidth() / 2, pageSize.getTop() - 20)
                        .showText("这是页眉")
                        .endText();

                // 创建页脚
                canvas.beginText()
                        .setFontAndSize(getFont(), 10)
                        .moveText(pageSize.getWidth() / 2, pageSize.getBottom() + 20)
                        .showText("这是页脚")
                        .endText();
                 */

                canvas.release();
            }
        });
    }

    private static String getImageFilenameByPosition(int st, int ed){
        return String.format("temp/image/%d&%d.png", st, ed);
    }

    public static List<SegmentInfo> getMatcherPosition(String text){
        List<SegmentInfo> ret = new ArrayList<SegmentInfo>();
        // 创建matcher对象
        Matcher combineMatcher = combinedPattern.matcher(text);

        // 寻找所有匹配项及其下标
        while (combineMatcher.find()) {
            // 使用SimpleEntry存储起始和结束下标
            int st = combineMatcher.start(), ed = combineMatcher.end() - 1, param = 0;
            SegmentType type;

            String substr = combineMatcher.group();
            Matcher boldMatcher = boldPattern.matcher(substr);
            Matcher titleMatcher = titlePattern.matcher(substr);
            if (boldMatcher.find()) {
                type = SegmentType.bold;
            }
            else if (titleMatcher.find()) {
                type = SegmentType.title;
                for(int i = 0; i<substr.length();i++){
                    if(substr.charAt(i) != '#')
                        break;
                    param++;
                }
            }
            else{ //formulation
                type = SegmentType.formulation;
                try {
                    latexToImage(substr, getImageFilenameByPosition(st, ed));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            ret.add(new SegmentInfo(st, ed, type ,param, substr));
        }
        return ret;
    }

    public static void deleteFilesInDirectory(File directory) {
        if (directory.isDirectory()) {
            // 获取文件夹内所有文件和文件夹的列表
            File[] items = directory.listFiles();
            if (items != null) {
                for (File item : items) {
                    // 如果是文件，则删除
                    if (!item.isDirectory()) {
                        // 删除文件
                        if (!item.delete()) {
                            System.out.println("Failed to delete file: " + item.getAbsolutePath());
                        }
                    }
                    else {
                        // 如果是文件夹，递归调用此方法
                        deleteFilesInDirectory(item);
                    }
                }
            }
        }
    }

    public static void addSegmentToParagraph(Document document, Paragraph paragraph, SegmentInfo info){
        switch (info.getType()){
            case bold -> {
                Text content = new Text(info.getContent().substring(2, info.getContent().length() - 2))
                        .setBold();
                paragraph.add(content);
            }
            case title -> {
                System.out.println("content = " + info.getContent().substring(info.getParam()));
                document.add(paragraph); //添加已有内容
                Paragraph titleParagraph = new Paragraph()
                        .setFixedLeading(0)
                        //.setPadding(0)
                        //.setMargins(5 + (3 - info.param) * 2, 10, 5 + (3 - info.param), 10)
                        .setFont(getFont());//设置中文字体
                Text content = new Text(info.getContent().substring(info.getParam()))
                        .setFontSize(m_fontSize + 3 * (3 - info.getParam()))
                        .setBold();
                titleParagraph.add(content);
                if(info.param == 1) titleParagraph.setTextAlignment(TextAlignment.CENTER);
                document.add(titleParagraph);
                paragraph = new Paragraph()
                        .setFixedLeading(0)
                        .setFont(getFont());  //使它变为一个空段，重新开始
            }
            case formulation -> {
                addImageToParagraph(paragraph,
                        getImageFilenameByPosition(info.getSt(), info.getEd()), m_fontSize);
            }
        }
    }

    private static void addImageToParagraph(Paragraph paragraph, String imageUrl, float imageHeight){
        try {
            // 使用ImageDataFactory创建ImageData对象
            ImageData imageData = ImageDataFactory.create(new File(imageUrl).toURL());
            // 使用ImageData创建Image对象
            Image image = new Image(imageData);
            if(image == null)
                System.out.println("image is null");

            // 获取图片的原始尺寸
            float originalWidth = image.getImageWidth();
            float originalHeight = image.getImageHeight();

            // 根据行高和图片宽高比，计算图片的新尺寸
            float newHeight = imageHeight;
            float newWidth = (originalWidth / originalHeight) * newHeight;

            // 设置图片的缩放比例
            image.scaleToFit(newWidth, newHeight);


            paragraph.add(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PDF主体内容
     *
     * @param document 文档
     */
    private static void setContent(Document document, String contentText) {

        // 设置中文字体
        PdfFont font = getFont();

        /********************************  段落内容由 Paragraph 区域编写 ******************************************/
        // 创建段落标题
        //Paragraph paragraphTitle = new Paragraph().setFont(font);
        // setBold:设置粗体，setItalic:斜体，setUnderline:下划线
        //paragraphTitle.add("这是一份PDF测试文档").setBold().setFontSize(m_fontSize);
        // 设置段落的对齐方式为居中
        //paragraphTitle.setTextAlignment(TextAlignment.CENTER);
        //document.add(paragraphTitle);

        List<SegmentInfo> segmentInfos = getMatcherPosition(contentText);

        Paragraph paragraph1 = new Paragraph().setFont(font).setFontSize(9);

        Integer p0 = 0, p1;
        for (SegmentInfo info : segmentInfos) {
            int st = info.getSt(), ed = info.getEd();
            p1 = st;
            if(p1 >= p0) //需要添加非match部分的字符串
                paragraph1.add(contentText.substring(p0, p1));
            //String imgUrl = getImageFilenameByPosition(st, ed);
            addSegmentToParagraph(document, paragraph1, info);
            //addImageToParagraph(paragraph1, imgUrl, m_fontSize);
            p0 = ed + 1;
        }
        if(contentText.length() > p0) //需要添加非公式部分的字符串
            paragraph1.add(contentText.substring(p0, contentText.length() - 1));
        document.add(paragraph1);
        /********************************  内容区域编写END ******************************************/
    }

    public static void main(String[] args) {
        String latexText = "# 高考数学模拟卷1\n" +
                "## 二级标题\n" +
                "### 三级标题\n" +
                "**选择题**\n\n"
                + "1. 若$x, y \\in \\mathbb{R}$，则$x^2 + y^2 = 1$的图形是：\n"
                + "   A. 一条直线 B. 一个圆 C. 一个椭圆 D. 一个抛物线\n\n"
                + "2. 已知函数$f(x) = \\frac{1}{x}$，则$f'(x)$等于：\n"
                + "   A. $-\\frac{1}{x^2}$ B. $\\frac{1}{x^2}$ C. $0$ D. $x$\n\n"
                + "3. 若$a, b \\in \\mathbb{R}^+$，则$a^{b/c}$可以表示为：\n"
                + "   A. $(a^b)^c$ B. $(a^c)^b$ C. $a^{(b/c)}$ D. $a^{bc}$\n\n"
                + "4. 若$\\triangle ABC$中，角$A$的对边是$a$，角$B$的对边是$b$，角$C$的对边是$c$，且$\\cos A = \\frac{1}{2}$，则三角形的形状是：\n"
                + "   A. 等腰三角形 B. 直角三角形 C. 等边三角形 D. 钝角三角形\n\n"
                + "**大题**\n\n"
                + "1. 设$f(x) = x^3 - 3x^2 + 2x - 1$，求$f'(x)$，并找出$f(x)$的极值点。\n"
                + "解析：首先求导数$f'(x) = 3x^2 - 6x + 2$，然后令$f'(x) = 0$，解得$x = 1$或$x = \\frac{2}{3}$。通过二阶导数测试或者代入原函数计算可以确定这些点是极大值点还是极小值点。\n\n"
                + "2. 在平面直角坐标系中，给定两点$A(-1, 0)$和$B(1, 0)$，求线段$AB$的垂直平分线的方程。\n"
                + "解析：线段$AB$的中点为$(0, 0)$，垂直平分线的斜率与$AB$的斜率互为负倒数，由于$AB$平行于$x$轴，其斜率为$0$，因此垂直平分线的斜率不存在，即垂直平分线是一条垂直线，且经过中点$(0, 0)$，所以垂直平分线的方程为$x = 0$。";

        createPDF(latexText, "testOut.pdf");

        //createDocx("123123123123123123");

        /*
        createPDF("题目一：直线与圆的位置关系\n" +
                "已知圆的方程为 \n" +
                "\uD835\uDC65\n" +
                "2\n" +
                "+\n" +
                "\uD835\uDC66\n" +
                "2\n" +
                "=\n" +
                "25\n" +
                "x \n" +
                "2\n" +
                " +y \n" +
                "2\n" +
                " =25，直线的方程为 \n" +
                "\uD835\uDC4E\n" +
                "\uD835\uDC65\n" +
                "+\n" +
                "\uD835\uDC4F\n" +
                "\uD835\uDC66\n" +
                "=\n" +
                "10\n" +
                "ax+by=10。求证：无论 \n" +
                "\uD835\uDC4E\n" +
                "a 和 \n" +
                "\uD835\uDC4F\n" +
                "b 的值如何，直线与圆总有两个交点。\n" +
                "\n" +
                "题目二：椭圆的性质\n" +
                "已知椭圆的方程为 \n" +
                "\uD835\uDC65\n" +
                "2\n" +
                "\uD835\uDC4E\n" +
                "2\n" +
                "+\n" +
                "\uD835\uDC66\n" +
                "2\n" +
                "\uD835\uDC4F\n" +
                "2\n" +
                "=\n" +
                "1\n" +
                "a \n" +
                "2\n" +
                " \n" +
                "x \n" +
                "2\n" +
                " \n" +
                "\u200B\n" +
                " + \n" +
                "b \n" +
                "2\n" +
                " \n" +
                "y \n" +
                "2\n" +
                " \n" +
                "\u200B\n" +
                " =1，其中 \n" +
                "\uD835\uDC4E\n" +
                ">\n" +
                "\uD835\uDC4F\n" +
                ">\n" +
                "0\n" +
                "a>b>0。若椭圆上存在一点 \n" +
                "\uD835\uDC43\n" +
                "(\n" +
                "\uD835\uDC65\n" +
                "0\n" +
                ",\n" +
                "\uD835\uDC66\n" +
                "0\n" +
                ")\n" +
                "P(x \n" +
                "0\n" +
                "\u200B\n" +
                " ,y \n" +
                "0\n" +
                "\u200B\n" +
                " )，满足 \n" +
                "\uD835\uDC65\n" +
                "0\n" +
                "2\n" +
                "+\n" +
                "\uD835\uDC66\n" +
                "0\n" +
                "2\n" +
                "=\n" +
                "4\n" +
                "x \n" +
                "0\n" +
                "2\n" +
                "\u200B\n" +
                " +y \n" +
                "0\n" +
                "2\n" +
                "\u200B\n" +
                " =4，求证：椭圆的长轴长度 \n" +
                "2\n" +
                "\uD835\uDC4E\n" +
                "2a 必须大于或等于 \n" +
                "2\n" +
                "2\n" +
                "2 \n" +
                "2\n" +
                "\u200B\n" +
                " 。");

         */
        //createDocx("一、解：设D(x,y)，则$x-y-1=0$，又A(1,0)，B(2,1)，C(3,3)在直线l上，所以有$1-0-1=0$，$2-1-1=0$，$3-3-1=0$。解得$x=3$，$y=2$或$x=0$，$y=-1$。所以点D的坐标为(3,2)或$(0,-1)$。");
    }
}
