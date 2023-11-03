import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class FamousQuotes implements Serializable {
    String[] data;

    FamousQuotes() {
        data = new String[]{"Journey of a thousand miles begins with single step=千里之行，始于足下。",
                "If you are doing your best,you will not have to worry about failure=如果你竭尽全力，你就不用担心失败。",
                "There is no royal road to learning=求知无坦途。",
                "Great works are performed not by strength , but by perseverance.=完成伟大的事业不在于体力，而在于坚韧不拔的毅力。"
        };
    }
}

public class Ser {
    public static void main(String[] args) {
        FamousQuotes quotes = new FamousQuotes();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("D:\\java_project\\LexicalUniverse\\src\\serialized_object\\famous_quotes.ser"))) {
            outputStream.writeObject(quotes);
            System.out.println("Object serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
