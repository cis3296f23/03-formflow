package src;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PDFieldWithLocation {
    public final PDField pdField;
    public final PDRectangle rectangle;

    public PDFieldWithLocation(PDField pdField, PDRectangle rectangle) {
        this.pdField = pdField;
        this.rectangle = rectangle;
        test();
    }

    public void test(){
        System.out.println("TESTHERE: " + pdField.getPartialName());
    }
}
