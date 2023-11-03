import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static List<Word> words = new ArrayList<>();
    public static SharedObject sharedObject = new SharedObject();
    public static NeedUp needUp;

    void init() {
        mainInterface();
        loadData();
    }

    void mainInterface() {
        Thread thread = new Thread(() -> {
            sharedObject.waitForSignal();
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    new MainInterface();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        thread.start();
    }

    void loadData() {
        Thread thread = new Thread(() -> {
            DataTool.getData(words);
            File f = new File("D:\\java_project\\LexicalUniverse\\src\\serialized_object\\need_up.ser");
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
                Start.needUp = (NeedUp) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // 加载完数据后发送信号
            sharedObject.sendSignal();
        });
        thread.start(); // 启动线程
    }
}

class Main {
    public static void main(String[] args) {
        Start start = new Start();
        start.init();
    }
}