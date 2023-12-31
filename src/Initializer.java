package src;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Initializer class is where the back end of the program is set up.
 * Specifically setting up the folders that FormFlow uses.
 */
public class Initializer {
    /**
     * To name a folder FormFlow
     */
    final String programName = "FormFlow";
    /**
     * To name a folder BlankPDFs
     */
    final String subFolder1 = "BlankPDFs";
    /**
     * To name a folder CompletedPDFs
     */
    final String subFolder2 = "CompletedPDFs";
    /**
     * To name a folder Data
     */
    final String subFolder3 = "Data";
    /**
     * To append the filetype .pdf to a given string filename
     */
    final String fileExtension = ".pdf";

    /**
     * Gets and stores home path of the system
     */
    static final String homePath = System.getProperty("user.home");
    /**
     * Gets and stores the path of the user's Documents folder
     */
    final File documentsPath = new File(getDocumentsFolderPath());
    /**
     * Creates a new folder called FormFlow in the user's Documents folder
     */
    final File mainFolder = new File(documentsPath.getAbsolutePath(), programName);
    /**
     * Gets and stores the completed path to the FormFlow folder
     */
    final String completedPdfsPath = new File(mainFolder, subFolder2).getAbsolutePath();

    /**
     * A Set of Strings called uniqueFileNames
     */
    Set<String> uniqueFileNames = new HashSet<>();
    /**
     * A List of StructuredFiles
     */
    List<StructuredFile> StructuredFiles = new ArrayList<>();
    /**
     * A ListView called savedListView
     */
    ListView savedListView;
    /**
     * A List of StructuredFiles called selectedFiles
     */
    List<StructuredFile> selectedFiles = new ArrayList<>();
    /**
     * A private Controller
     */
    private Controller controller;

    /**
     * Sets the Controller to the given controller
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Creates the folders in the filepath. First checks if they
     * already exist, and if they do not they get set up in the
     * Documents folder in the home path of your system. It creates
     * a folder and 3 sub folders. The main folder is called FormFlow
     * and the three sub folders are called BlankPDFs, CompletedPDFs,
     * and Data.
     */
    public void createFolders() {
        if (mainFolder.exists()) { //check if the program has been opened before
            System.out.println("Folder exists, do nothing");
        } else { // First time running, set up environment
            boolean folderCreated = mainFolder.mkdir();
            if (folderCreated) {
                System.out.println("Folder Created at: " + mainFolder.getAbsolutePath());
                createSubFolder(mainFolder, subFolder1);
                createSubFolder(mainFolder, subFolder2);
                createSubFolder(mainFolder, subFolder3);
            } else {
                System.out.println("Failed to create subfolder: " + programName);
            }
        }
    }

    /**
     * The loadFiles() method gets the files that already exist in
     * the folders and add them to the StructuredFile data structure.
     * It makes
     * @throws IOException
     */
    public void loadFiles() throws IOException {
        //get the files that already exist in the folders and add them to the data structure
        File folder = new File(mainFolder, subFolder1);
        if (folder.exists() && folder.isDirectory()) { //make sure you have the right folder
            File[] files = folder.listFiles(); //get all the files in the folder
            if (files != null) { //if it found files
                for (File file : files) { //iterate through the files
                    if (file.isFile() && file.getName().toLowerCase().endsWith(fileExtension)) { // check file validity and extension
                        if (uniqueFileNames.add(file.getName())) { // see if the file name is unique to what is already in the list
                            System.out.println("StructuredFiles: Adding file: " + file.getName());
                            //get the fields if there are any
                            List<PDFieldWithLocation> fieldsList = new ArrayList<>();
                            try (PDDocument document = PDDocument.load(file)) {
                                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
                                if (acroForm != null) {
                                    for (PDField field : acroForm.getFields()){
                                        List<PDAnnotationWidget> annotations = field.getWidgets();
                                        if (!annotations.isEmpty()) {
                                            PDAnnotationWidget widget = annotations.get(0); // Assuming the first widget represents the field appearance
                                            PDRectangle rect = widget.getRectangle();
                                            fieldsList.add(new PDFieldWithLocation(field, rect));
                                        }
                                    }
                                    System.out.println("FieldsLsit:" + fieldsList);
                                }
                            }
                            //add the file to the structured file List
                            StructuredFiles.add(new StructuredFile(file.getName(), file, fieldsList));
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is an overload of the other populateFileNameList()
     * @param listView
     */
    public void populateFileNameList(ListView listView) {
        populateFileNameList(listView, null);
    }

    /**
     * In our list of files this method allows us to search our
     * StructuredFiles for what is specified in the searchText
     * and it populates the UI with the results
     * @param listView
     * @param searchText
     */
    public void populateFileNameList(ListView listView, String searchText) {
        listView.getItems().clear(); //clear the list o we don't have to check what's new
        savedListView = listView; //save the list view so that internal methods can reference it
        for (StructuredFile file : StructuredFiles) { //iterate through all the files in the structured file list
            // Perform filtering based on search text
            if (searchText == null || file.fileName.toLowerCase().contains(searchText.toLowerCase())) {
                // checkbox to select files and make fields appear
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(actionEvent -> handleCheckBox(checkBox, file));
                // wrap the file name if it is too long
                String text = file.fileName;
                if (file.fileName.length() > 29) {
                    text = (file.fileName.substring(0, 26) + "...");
                }
                // create a label to hold the file name
                Label label = new Label("  " + text); // space for indentation
                label.setTooltip(new Tooltip(file.fileName)); // Show the full name on hover in case it is wrapped

                // trash button to remove files
                Button trash = new Button("✖");
                trash.setStyle("-fx-background-radius: 2em;"); // makes the button round :)
                trash.setOnAction(actionEvent -> {
                    generateFields.updateFields(file, false);
                    controller.updateUIWithFields(generateFields.getUniqueFields());
                    try {
                        removeFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                // combine the checkbox and its label
                HBox hbox = new HBox(checkBox, label);
                // format the entity so that the trash button is on the very right
                BorderPane bp = new BorderPane();
                bp.setLeft(hbox);
                bp.setRight(trash);
                // finally, add the new entity to the ListView
                listView.getItems().add(bp);
            }
        }
    }

    /**
     * This method removes specified StructuredFile file
     * @param file is a StructuredFile
     * @throws IOException
     */
    private void removeFile(StructuredFile file) throws IOException {
        // Add checker to make sure user wants to delete the file (and maybe a "don't ask again" button)
        File folder = new File(mainFolder, subFolder1);
        File fileToDelete = new File(folder, String.valueOf(file.fileName));

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("Removed File: " + fileToDelete);
            } else {
                System.out.println("Failed to Removed File: " + fileToDelete);
            }
        } else {
            System.out.println("File does not exist: " + fileToDelete);
        }

        //remove the file from our data structures
        StructuredFiles.remove(file);
        uniqueFileNames.remove(file.fileName);

        //reload the files and the list view
        loadFiles();
        populateFileNameList(savedListView);
    }

    /**
     * uploadNewFile() allows us to upload a file to FormFlow
     * with a FileChooser. Then the chosen file gets placed in
     * a File called destination. The selected files get added
     * to the stage.
     * @param stage
     */
    public void uploadNewFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose PDFs to upload");

        // Add a PDF filter so that user can only select pdfs
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file dialog
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        // Where to put the newly uploaded files
        File destination = new File(mainFolder, subFolder1);

        // If there are files selected, copy them to the applications folders for processing
        if (selectedFiles != null) {
            System.out.println("Selected files: " + selectedFiles); //TESTING
            for (File file : selectedFiles) { //iterate through the files selected
                try {
                    //Copy each file into a folder that the program can access
                    Path dest = Path.of(destination.getAbsolutePath(), file.getName());
                    Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("User canceled file selection");
        }
    }

    /**
     * Creates subfolder with the name of the subfolder passed to it.
     * It goes to the parent file and makes a new directory in the given
     * File parent.
     * @param parent is a File
     * @param folderName is a String
     */
    private void createSubFolder(File parent, String folderName) {
        File subFolder = new File(parent, folderName); //where to create folder
        boolean subFolderCreated = subFolder.mkdir();
        if (subFolderCreated) {
            System.out.println("Subfolder: " + folderName + " success");
            System.out.println("Folder Created at: " + subFolder.getAbsolutePath());
        } else {
            System.out.println("Failed to create subfolder: " + folderName);
        }
    }


    /**
     * GenerateFields object used to handle checkboxes
     */
    private GenerateFields generateFields = new GenerateFields();

    /**
     * Handles check boxes if they are selected. It calls
     * updateFields and indicates whether to add or remove
     * the files.
     * @param checkBox
     * @param file is a StructuredFile
     */
    private void handleCheckBox(CheckBox checkBox, StructuredFile file) {
        // Update the unique fields based on the selection status of the checkbox
        generateFields.updateFields(file, checkBox.isSelected());

        if (checkBox.isSelected()) {
            selectedFiles.add(file);
            System.out.println(file + " Selected");
        } else {
            selectedFiles.remove(file);
            System.out.println(file + " Un-Selected");
        }
        // Update the UI with the current set of unique fields
        controller.updateUIWithFields(generateFields.getUniqueFields());

    }

    /**
     * The getDocumentsFolderPath() method identifies the Documents folder
     * and returns the path as a String
     * @return the filepath to the Documents folder on the computer
     */
    private static String getDocumentsFolderPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        // check if the OS is Windows and if onedrive is in use
        if (osName.contains("win") && new File(homePath, "OneDrive").exists() && !(new File(homePath, "Documents").exists())) {
            // get the documents folder inside the onedrive folder
            return homePath + File.separator + "OneDrive" + File.separator + "Documents";
        } else {
            //get the documents folder inside the users home
            return homePath + File.separator + "Documents";
        }
    }

}