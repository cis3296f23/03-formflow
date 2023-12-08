package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.TextPosition;
import org.fit.pdfdom.BoxStyle;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
//import org.apache.pdfbox.tools.imageio.ImageIOUtil;
//import org.apache.pdfbox.util.PDFDomTree;
import javax.print.attribute.standard.PrinterName;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.Writer;
import java.net.PortUnreachableException;

public class PDFManipulation {
    public PDFManipulation(){}
    public void generateHTMLFromPDF(String filename) {
        try {
            Initializer initializer = new Initializer();
            PDDocument pdf = PDDocument.load(new File(filename));
            String[] justNameSplit = filename.split("\\.")[0].split("\\/");
            String justName = justNameSplit[justNameSplit.length - 1];
            String outputFIlePath = initializer.outputHTMLPath + "/" + justName + ".html";

            System.out.println(outputFIlePath);
            Writer output = null;
//            new PDFDomTree().writeText(pdf, output);
            PDFDomTree parser = new PDFDomTree(PDFDomTreeConfig.createDefaultConfig()) {
                @Override
                protected void processTextPosition(TextPosition text) {
                    if (text.getUnicode().trim().isEmpty()) {
                        //finish current box (if any)
                        if (lastText != null)
                        {
                            finishBox();
                        }
                        //start a new box
                        curstyle = new BoxStyle(style);
                        lastText = null;
                    } else {
                        super.processTextPosition(text);
                    }
                }
            };
            output = new PrintWriter(outputFIlePath, "utf-8");
            parser.writeText(pdf, output);
            output.close();
            pdf.close();

        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
            System.out.println("file not found");
        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
            System.out.println("unsupported encoding");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("file exists already");
        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
            System.out.println("could not parse file");
        }
    }

//    private void generateImageFromPDF(String filename, String extension){
//        PDDocument document = PDDocument.load(new File(filename));
//        PDFRenderer pdfRenderer = new PDFRenderer(document);
//        for(int page = 0; page < document.getNumberOfPages(); ++page){
//            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
//            ImageIOUtil.writeImage(bim, String.format("src/assets", page + 1, extension), 300);
//        }
//        document.close();
//    }

}