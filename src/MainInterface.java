import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class MainInterface extends JFrame {
    public JTextField word;
    public JTextField explanation;

    public JTextField quotes;

    public JTextField quotesExplanation;

    public JToggleButton order;
    public JToggleButton silentWritingMode;
    public JToggleButton showWord;

    public JButton playJButton;
    public Word nowWord;
    public int nowIndex = 0;

    MainInterface() {
        this.setTitle("词汇宇宙v1.0");
        this.setSize(600, 800);
        this.setPreferredSize(new Dimension(600, 800));
        this.setLocationRelativeTo(null);
        this.setResizable(false); // 设置为不可改变大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setFocusable(true);

        ImageIcon icon = new ImageIcon("D:\\java_project\\LexicalUniverse\\src\\img\\app1-transformed.png");
        setIconImage(icon.getImage());

        // Load the background image
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("D:\\java_project\\LexicalUniverse\\src\\img\\background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initTextField();
        initShowButton();
        initJToggleButton();
        initPlayJButton();

        initWindowsListener();
        initKeyListener();

        this.setVisible(true);
    }


    private void initWindowsListener() {
        // 添加窗口事件监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 在窗口关闭时执行你想要的操作
                //cleanup(); // 调用你要执行的函数
            }
        });
    }

    private void initKeyListener() {
        //添加键盘监听器
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!order.isSelected()) {
                    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (nowIndex > 0) {
                            nowIndex--;
                            nowWord = Start.words.get(nowIndex);
                            if (!silentWritingMode.isSelected())
                                word.setText(nowWord.data);
                            else
                                word.setText("");
                            explanation.setText(nowWord.explanation);
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (nowIndex < Start.words.size() - 1) {
                            nowIndex++;
                            NeedUp.needUp.add(Start.words.get(nowIndex).uuid);
                            nowWord = Start.words.get(nowIndex);
                            if (!silentWritingMode.isSelected())
                                word.setText(nowWord.data);
                            else
                                word.setText("");
                            explanation.setText(nowWord.explanation);
                        }
                    }
                    if (silentWritingMode.isSelected())
                        word.requestFocus();
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_A
                            || e.getKeyCode() == KeyEvent.VK_LEFT
                            || e.getKeyCode() == KeyEvent.VK_D
                            || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        Random random = new Random();
                        nowIndex = random.nextInt(0, Start.words.size());
                        NeedUp.needUp.add(Start.words.get(nowIndex).uuid);
                        nowWord = Start.words.get(nowIndex);
                        if (!silentWritingMode.isSelected())
                            word.setText(nowWord.data);
                        else
                            word.setText("");
                        explanation.setText(nowWord.explanation);
                        if (silentWritingMode.isSelected())
                            word.requestFocus();
                    }
                }
            }
        });
    }

    private void cleanup() {
        System.out.print("needUp=");
        for (Integer i : NeedUp.needUp) {
            System.out.printf(i + " ");
        }
        System.out.println();
    }

    private void initPlayJButton() {
        playJButton = new JButton();
        ImageIcon icon = new ImageIcon("D:\\java_project\\LexicalUniverse\\src\\img\\play.png");
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        playJButton.setIcon(new ImageIcon(img));
        playJButton.setBounds(510, 270, 40, 40);

        playJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    PlayMP3.play("D:\\java_project\\LexicalUniverse\\src\\word_audio\\" + nowWord.data + ".MP3");
                }
                MainInterface.this.requestFocusInWindow(); // 设置焦点回MainInterface窗口
            }
        });

        // 添加按钮到窗口
        add(playJButton);

        // 添加按钮点击动画效果
        playJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Timer timer = new Timer(10, new ActionListener() {
                        private int currentSize = 40;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int targetSize = 30;
                            if (currentSize > targetSize) {
                                currentSize--;
                                Image img = icon.getImage().getScaledInstance(currentSize, currentSize, Image.SCALE_SMOOTH);
                                playJButton.setIcon(new ImageIcon(img));
                            } else {
                                ((Timer) e.getSource()).stop();
                                currentSize = 40; // 重置大小
                            }
                        }
                    });
                    timer.start();
                }
            }
        });

        this.add(playJButton);
    }

    private void initJToggleButton() {
        order = new JToggleButton("顺序");

        order.setBounds(250, 350, 100, 30);

        order.setFont(new Font("宋体", Font.ITALIC, 15));

        order.addActionListener(e -> {
            if (order.isSelected()) {
                order.setText("乱序");
            } else {
                order.setText("顺序");
            }
            MainInterface.this.requestFocusInWindow(); // 设置焦点回MainInterface窗口
        });

        this.add(order);

        silentWritingMode = new JToggleButton();
        silentWritingMode.setBounds(300, 600, 180, 50);
        silentWritingMode.setText("打开默写模式");
        silentWritingMode.setFont(new Font("宋体", Font.BOLD, 18));
        silentWritingMode.addActionListener(e -> {
            if (silentWritingMode.isSelected()) {
                order.setEnabled(false);
                silentWritingMode.setText("关闭默写模式");
                showWord.setVisible(false);
                playJButton.setVisible(false);
                if (showWord.getText().equals("开启显示单词")) {
                    word.setVisible(true);
                }
                word.setText("");
                word.setFocusable(true);
                word.setEditable(true);
                word.setBorder(new LineBorder(Color.BLACK, 3, true));
                word.requestFocus();
            } else {
                order.setEnabled(true);
                silentWritingMode.setText("打开默写模式");
                showWord.setVisible(true);
                playJButton.setVisible(true);
                if (showWord.getText().equals("开启显示单词")) {
                    word.setVisible(false);
                }
                word.setText(Start.words.get(nowIndex).data);
                word.setFocusable(false);
                word.setEditable(false);
                word.setBorder(null);
                MainInterface.this.requestFocusInWindow(); // 设置焦点回MainInterface窗口
            }
        });

        this.add(silentWritingMode);
    }

    private void initShowButton() {
        showWord = new JToggleButton();
        showWord.setBounds(100, 600, 180, 50);
        showWord.setFont(new Font("宋体", Font.BOLD, 18));
        showWord.setText("关闭显示单词");
        showWord.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    if (word.isVisible()) {
                        word.setVisible(false);
                        showWord.setText("开启显示单词");
                    } else {
                        word.setVisible(true);
                        showWord.setText("关闭显示单词");
                    }
                    MainInterface.this.requestFocusInWindow(); // 设置焦点回MainInterface窗口
                }
            }
        });
        add(showWord);
    }

    private void initTextField() {
        // 创建两个文本框
        word = new JTextField();
        explanation = new JTextField();
        quotesExplanation = new JTextField();
        quotes = new JTextField();

        // 设置文本框的位置和大小
        quotes.setBounds(0, 60, 600, 30);
        quotesExplanation.setBounds(50, 100, 500, 30);
        word.setBounds(100, 240, 400, 100);
        explanation.setBounds(100, 450, 400, 100);
        nowWord = Start.words.get(nowIndex);

        quotes.setOpaque(false);
        quotes.setEditable(false);
        quotes.setFocusable(false);
        quotes.setBorder(null); // 移除边框

        quotesExplanation.setOpaque(false);
        quotesExplanation.setEditable(false);
        quotesExplanation.setFocusable(false);
        quotesExplanation.setBorder(null); // 移除边框

        quotes.setFont(new Font("宋体", Font.ITALIC, 12));
        quotesExplanation.setFont(new Font("宋体", Font.ITALIC, 12));

        quotes.setHorizontalAlignment(SwingConstants.CENTER);
        quotesExplanation.setHorizontalAlignment(SwingConstants.RIGHT);

        FamousQuotes q = null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\java_project\\LexicalUniverse\\src\\serialized_object\\famous_quotes.ser"))) {
            q = (FamousQuotes) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (q != null) {
            Random r = new Random();
            int n = r.nextInt(0, q.data.length);
            quotes.setText(q.data[n].split("=")[0]);
            quotesExplanation.setText("——" + q.data[n].split("=")[1]);
        }

        this.add(quotes);
        this.add(quotesExplanation);

        NeedUp.needUp.add(nowWord.uuid);
        word.setOpaque(false);
        word.setText(nowWord.data);
        word.setEditable(false);
        word.setFocusable(false);
        word.setBorder(null); // 移除边框

        explanation.setOpaque(false);
        explanation.setText(nowWord.explanation);
        explanation.setEditable(false);
        explanation.setFocusable(false);
        explanation.setBorder(null); // 移除边框

        //设置文本框字体大小为20，字体样式为宋体
        word.setFont(new Font("宋体", Font.BOLD, 30));
        explanation.setFont(new Font("宋体", Font.BOLD, 20));
        //设置文本框文字居中
        word.setHorizontalAlignment(SwingConstants.CENTER);
        explanation.setHorizontalAlignment(SwingConstants.CENTER);

        word.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // 检查输入是否是大写或小写字母，空格，'，或者-
                if (!Character.isLetter(c) && c != ' ' && c != '\'' && c != '-') {
                    e.consume(); // 忽略非法输入
                } else {
                    // 如果是合法输入，将其追加到一个临时字符串中
                    String tempString = word.getText() + c;

                    // 检查临时字符串是否与目标字符串相等（不区分大小写）
                    if (tempString.equals(nowWord.data)) {
                        // 如果相等，执行相应的操作
                        PlayMP3.wordTrue();
                        MainInterface.this.requestFocusInWindow();
                    }
                }
            }
        });

        // 将文本框添加到面板中
        add(word);
        add(explanation);
    }
}
