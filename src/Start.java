import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static List<Word> words = new ArrayList<>();
    public static SharedObject sharedObject = new SharedObject();

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
                new MainInterface();
            });
        });
        thread.start();
    }

    void loadData() {
        Thread thread = new Thread(() -> {
            DataTool.getData(words);
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