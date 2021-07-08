import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private final ArrayList<Integer> arrayList;
    private final String filePath;

    public ReadFile(ArrayList<Integer> arrayList, String filePath){
        this.arrayList = arrayList;
        this.filePath = filePath;
    }

    public void getData(){
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNext()){
                arrayList.add(scanner.nextInt());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
