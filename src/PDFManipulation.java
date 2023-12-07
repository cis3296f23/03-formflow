package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;

import javax.print.attribute.standard.PrinterName;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;

public class PDFManipulation {

    public PDFManipulation(){

    }

    private void generateHTMLFromPDF(String filename){

        PDDocument pdf = PDDocument.load(new File(filename));
        Writer output = new PrintWriter("src/output/pdf.html", "utf-8");
        new PDFDomTree().writeText(pdf,output);

        output.close();

    }

}
