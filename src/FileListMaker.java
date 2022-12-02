import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileListMaker {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<String> myArrList = new ArrayList<>();
        myArrList.add("Witterstaetter");
        myArrList.add("Smith");
        myArrList.add("Johnson");

        String prompt = "Which operation do you want to display: \n A - Add \n D - Delete \n V - View \n" +
                " O - Open a list file from disk \n S - Save the current list file to disk \n " +
                "C - Clear removes all the elements from the current list \n Q - Quit";

        String userInput = SafeInputs.getRegExString(in, prompt, "[AaDdVvOoSsCcQq]");
        userInput = userInput.toUpperCase();

        switch (userInput){
            case "A":
                add(myArrList);
                break;
            case "D":
                delete(myArrList);
                break;
            case "V":
                view(myArrList);
                break;
            case "O":
                openListDisk(myArrList);
                break;
            case "S":
                saveFileDisk(myArrList);
                break;
            case "C":
                break;
            case "Q":
                System.out.println("____________________");
                break;

        }

    }

    private static void add(ArrayList<String> list){
        Scanner in = new Scanner(System.in);
        String added;

        System.out.println(list);
        System.out.println("Enter Name to add: ");
        added = in.nextLine();
        list.add(added);
        System.out.println(list);

        String prompt = "Would you like to continue?";

    }

    private static void delete(ArrayList<String> list){
        Scanner in = new Scanner(System.in);
        String deleted;
        System.out.println(list);
        System.out.println("Enter name to delete: ");
        deleted = in.nextLine();
        list.remove(deleted);
    }

    private static void view(ArrayList<String> list){
        for(String value: list){
            System.out.printf("%s ", value);
        }
    }

    private static void openListDisk(ArrayList<String> lines){
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String line = null;


        try{

            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);


            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){


                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int lineNumber = 0;
                int characterCount = 0;
                int wordCount = 0;

                while(reader.ready()) {

                    line = reader.readLine();
                    lines.add(line);
                    lineNumber++;
                    System.out.printf("\n %-4d ---> %-60s", lineNumber, line);


                    String[] words = line.split(" ");
                    wordCount = wordCount + words.length;

                    characterCount = characterCount + line.length();

                }

                String name = selectedFile.getName();

                reader.close();
                System.out.println("File read successfully");
                System.out.println("-------------------------------------");
                System.out.println("Name of File: " + name);
                System.out.println("Number of Lines: " + lineNumber);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + characterCount);


            }else{
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again");
                System.exit(0);
            }


        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static void saveFileDisk(ArrayList<String> records){
        records.add("This is a sample data to write into our text file");
        records.add("This data will be added to line 2 of our text file");
        records.add("This data is for the line 3 of the text");
        records.add("Sample data for line 4");
        records.add("Sample data for line 5");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath()+"\\src\\sampledata.txt");

        try{

            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec: records){
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }

            writer.close();
            System.out.println("Data has been successfully written to 'sampledata.txt'");

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static void clearFileList(ArrayList<String> list){
        openListDisk(list);
        list.clear();
    }

}
