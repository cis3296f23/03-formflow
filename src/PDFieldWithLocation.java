package src;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * PDFieldWithLocation is a class necessitated by our use of
 * the Apache PDFBox library to the ends of finding the locations
 * of form fields on a PDF. It has two constant variables. A
 * PDField named pdField and a PDRectangle called rectangle.
 */
public class PDFieldWithLocation {
    public final PDField pdField;
    public final PDRectangle rectangle;

    /**
     * This constructor accepts a PDField and PDRectangle and assigns them
     * to the corresponding global variables
     * @param pdField
     * @param rectangle
     */
    public PDFieldWithLocation(PDField pdField, PDRectangle rectangle) {
        this.pdField = pdField;
        this.rectangle = rectangle;
        test();
    }

    /**
     * The test() method tests if the getPartialName() method works
     */
    public void test(){
        System.out.println("TESTHERE: " + pdField.getPartialName());
    }
}
