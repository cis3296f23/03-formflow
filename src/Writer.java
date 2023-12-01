package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class Writer {
    final String homePath = System.getProperty("user.home");
    final File documentsPath = new File(homePath, "Documents");
    final String subFolder2 = "CompletedPDFs";
    final File saveFolder = new File(documentsPath.getAbsolutePath(), subFolder2);
    public void write(StructuredFile file, List<DisplayedFields> input) throws IOException {
        try {
            PDDocument doc = PDDocument.load(file.file.getAbsoluteFile()); //load the file
            PDPage page = doc.getPage(0); //get the first page
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12); //set the font

            //im not sure that this is the most efficient way to do this
            for (PDFieldWithLocation field : file.fields) {
                for (DisplayedFields displayedFields : input) {
                    if (Objects.equals(field.pdField.getFullyQualifiedName(), displayedFields.actualName)) {
                        float x = field.rectangle.getLowerLeftX() + 10; //uses the PDRectangle to find where to write
                        float y = field.rectangle.getLowerLeftY() - 10;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x, y);
                        contentStream.showText(displayedFields.textFieldReference.getText()); //get the text in this box
                        contentStream.endText();
                    }
                }
            }
            contentStream.close(); //finished writing
            File completedPDF = new File(saveFolder.getAbsolutePath(), file.file.getName()); //get the file path where it will save the file
            System.out.println("PATH TO OG FILE:" + file.file.getAbsolutePath());
            System.out.println("PATH TO NEW FILE:" + completedPDF.getAbsolutePath());
            completedPDF.createNewFile();
            Path path1 = Path.of(file.file.getAbsolutePath());
            Path path2 = Path.of(completedPDF.getAbsolutePath());
            Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);
            doc.save(completedPDF.getAbsolutePath()); //save the file
            System.out.println("saved: " +doc + "TO: " + completedPDF.getAbsolutePath());
            doc.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
