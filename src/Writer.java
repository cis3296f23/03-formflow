package src;

import java.io.File;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Writer {

    public void generatePDF(Map<String, String> formData, String filePath) {
        try (PDDocument document = new PDDocument()) {
            // Create a new PDF page and add it to the document
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream to write content in the PDF page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set the font and font size for the text to be written
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Starting Y position for writing, offset from the top of the page
                float yStart = page.getMediaBox().getHeight() - 50;

                // Loop through each entry in the form data
                for (Map.Entry<String, String> entry : formData.entrySet()) {
                    // Begin text writing in content stream
                    contentStream.beginText();
                    // Move to the specified offset position
                    contentStream.newLineAtOffset(50, yStart);
                    // Prepare the text to write (Key: Value)
                    String text = entry.getKey() + ": " + entry.getValue();
                    // Write the text to the content stream
                    contentStream.showText(text);
                    // End text writing
                    contentStream.endText();
                    // Move yStart up to the next line
                    yStart -= 20;
                }
            }

            // Save the document to the specified file path
            document.save(filePath);
        } catch (Exception e) {
            // Print stack trace if there is any exception
            e.printStackTrace();
        }
    }
}
