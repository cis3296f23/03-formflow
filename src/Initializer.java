package src;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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

    final String programFilesPath = System.getenv("ProgramFiles");
    final File mainFolder = new File(programFilesPath, programName);

    Set<String> uniqueFileNames = new HashSet<>();
    List<StructuredFile> StructuredFiles = new ArrayList<>();
    public void createFolders(){
        if (mainFolder.exists()){
            System.out.println("Folder exists, do nothing");
        } else {
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
        if (folder.exists() && folder.isDirectory()){
            File[] files = folder.listFiles();
            if(files != null){
                for (File file : files){
                    if(file.isFile() && file.getName().toLowerCase().endsWith(fileExtension)){
                        if (uniqueFileNames.add(file.getName())){
                            System.out.println("StructuredFiles: Adding file: " + file.getName());
                            StructuredFiles.add(new StructuredFile(file.getName(),file,"placeholder"));
                        }
                    }
                }
            }
        }
    }

    public void populateFileNameList(ListView listView){
        listView.getItems().clear();
        for(StructuredFile file : StructuredFiles){
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(actionEvent -> handleCheckBox(checkBox, file));
            Label label = new Label(file.fileName);

            HBox hbox = new HBox(checkBox, label);
            ListItem newListItem = new ListItem(checkBox,label);
            listView.getItems().add(hbox);
        }
    }

    public void uploadNewFile(Stage stage){
        //code for uploading new file goes here
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose PDFs to upload");

        // Add a PDF filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file dialog
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        // Where to put the newly uploaded files
        File destination = new File(mainFolder, subFolder1);

        // If there are files selected, copy them to the applications folders for processing
        if (selectedFiles != null) {
            System.out.println("Selected files: " + selectedFiles);
            for (File file : selectedFiles){
                try{
                    Path dest = Path.of(destination.getAbsolutePath(), file.getName());
                    Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("User cancelled file selection");
        }

    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    private void createSubFolder(File parent, String folderName){
        File subFolder = new File(parent, folderName);
        boolean subFolderCreated = subFolder.mkdir();
        if(subFolderCreated){
            System.out.println("Subfolder: " + folderName + " success");
            System.out.println("Folder Created at: " + subFolder.getAbsolutePath());
        } else {
            System.out.println("Failed to create subfolder: " + folderName);
        }
    }

    private void getFileNames(){

    }

    private void handleCheckBox(CheckBox checkBox, StructuredFile fileName) {
        if (checkBox.isSelected()) {
            System.out.println(fileName + " Selected");
        } else {
            System.out.println(fileName + " Un-Selected");
        }
    }


}
