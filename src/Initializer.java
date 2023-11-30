package src;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Initializer {
    final String programName = "FormFlow";
    final String subFolder1 = "BlankPDFs";
    final String subFolder2 = "CompletedPDFs";
    final String subFolder3 = "Data";
    final String fileExtension = ".pdf";

    final String homePath = System.getProperty("user.home");
    final File documentsPath = new File(homePath, "Documents");
    final File mainFolder = new File(documentsPath.getAbsolutePath(), programName);

    Set<String> uniqueFileNames = new HashSet<>();
    List<StructuredFile> StructuredFiles = new ArrayList<>();
    ListView savedListView;

    public void createFolders(){
        if (mainFolder.exists()){ //check if the program has been opened before
            System.out.println("Folder exists, do nothing");
        } else { // First time running, set up environment
            boolean folderCreated = mainFolder.mkdir();
            if(folderCreated){
                System.out.println("Folder Created at: " + mainFolder.getAbsolutePath());
                createSubFolder(mainFolder, subFolder1);
                createSubFolder(mainFolder, subFolder2);
                createSubFolder(mainFolder, subFolder3);
            } else {
                System.out.println("Failed to create subfolder: " + programName);
            }
        }
    }
    public void loadFiles(){
        //get the files that already exist in the folders and add them to the data structure
        File folder = new File(mainFolder, subFolder1);
        if (folder.exists() && folder.isDirectory()){ //make sure you have the right folder
            File[] files = folder.listFiles(); //get all the files in the folder
            if(files != null){ //if it found files
                for (File file : files){ //iterate through the files
                    if(file.isFile() && file.getName().toLowerCase().endsWith(fileExtension)){ // check file validity and extension
                        if (uniqueFileNames.add(file.getName())){ // see if the file name is unique to what is already in the list
                            System.out.println("StructuredFiles: Adding file: " + file.getName());
                            //add the file to the structured file List
                            StructuredFiles.add(new StructuredFile(file.getName(),file,"placeholder"));
                        }
                    }
                }
            }
        }
    }
    public void populateFileNameList(ListView listView){
        listView.getItems().clear(); //clear the list so we don't have to check what's new
        savedListView = listView; //save the list view so that internal methods can reference it
        for(StructuredFile file : StructuredFiles){ //iterate through all the files in structured file list
            // checkbox to select files and make fields appear
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(actionEvent -> handleCheckBox(checkBox, file));
            // wrap the file name if it is too long
            String text = file.fileName;
            if (file.fileName.length() > 29){
                text = (file.fileName.substring(0, 26) + "...");
            }
            //create a label to hold the file name
            Label label = new Label("  " + text); //space for indentation
            label.setTooltip(new javafx.scene.control.Tooltip(file.fileName)); // Show the full name on hover in case it is wrapped

            //trash button to remove files
            Button trash = new Button("✖");
            trash.setStyle("-fx-background-radius: 2em;"); //makes the button round :)
            trash.setOnAction(actionEvent -> removeFile(file));

            //combine the checkbox and its label
            HBox hbox = new HBox(checkBox, label);
            // format the entity so that trash button is on the very right
            BorderPane bp = new BorderPane();
            bp.setLeft(hbox);
            bp.setRight(trash);
            //finally add the new entity to the listview
            listView.getItems().add(bp);
        }
    }
    private void removeFile(StructuredFile file){
        // Add checker to make sure user wants to delete the file (and maybe a "don't ask again" button)
        File folder = new File(mainFolder, subFolder1);
        File fileToDelete = new File(folder, String.valueOf(file.fileName));

        if(fileToDelete.exists()){
            if(fileToDelete.delete()){
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
    public void uploadNewFile(Stage stage){
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
            for (File file : selectedFiles){ //iterate through the files selected
                try{
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
    private void createSubFolder(File parent, String folderName){
        File subFolder = new File(parent, folderName); //where to create folder
        boolean subFolderCreated = subFolder.mkdir();
        if(subFolderCreated){
            System.out.println("Subfolder: " + folderName + " success");
            System.out.println("Folder Created at: " + subFolder.getAbsolutePath());
        } else {
            System.out.println("Failed to create subfolder: " + folderName);
        }
    }
    //handles checkbox on list view eventually
    private void handleCheckBox(CheckBox checkBox, StructuredFile fileName) {
        if (checkBox.isSelected()) {
            System.out.println(fileName + " Selected");
        } else {
            System.out.println(fileName + " Un-Selected");
        }
    }
}
