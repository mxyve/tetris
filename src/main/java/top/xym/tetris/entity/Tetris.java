package top.xym.tetris.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris extends JFrame implements KeyListener {
    // 游戏的行数26，列数12
    private static final int game_x = 26;
    private static final int game_y = 12;
    // 文本域数组 text定义了每个格子是个文本域
    JTextArea[][] text;
    // 二维数组 data定义了每个格子的值 规定1代表有方块，0代表空白区域
    int[][] data;

    public void initWidow() {
        // 设置窗口大小
        this.setSize(500, 750);
        // 设置窗口是否可见
        this.setVisible(true);
        // 设置窗口居中
        this.setLocationRelativeTo(null);
        // 设置释放窗体
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小不可变
        this.setResizable(false);
        // 设置标题
        this.setTitle("俄罗斯方块游戏");
        this.addKeyListener(this);
    }

    // 初始化游戏界面
    public void initGamePanel() {
        JPanel game_main = new JPanel();
        // 水平间距和竖直间距都设置为1
        game_main.setLayout(new GridLayout(game_x,game_y,1,1));
        // 初始化面板 双重for循环，遍历整个游戏界面
        for (int i = 0; i < text.length ; i++) {
            for(int j = 0; j < text[i].length; j++) {
                // 设置文本域的行列数
                text[i][j] = new JTextArea(game_x,game_y);
                // 设置文本域的背景颜色
                text[i][j].setBackground(Color.WHITE);
                // 添加键盘监听事件
                text[i][j].addKeyListener(this);
                // 初始化游戏边界
                if(j == 0 || j == text[i].length-1 || i == text.length-1) {
                    text[i][j].setBackground(Color.MAGENTA);
                    data[i][j] = 1;
                }
                // 设置文本区域不可编辑
                text[i][j].setEditable(false);
                // 文本区域添加到主面板上
                game_main.add(text[i][j]);
            }
        }
        // 将主面板添加到窗口中
        this.setLayout(new BorderLayout());
        // 把设置好的游戏区域添加到窗口的中间部位
        this.add(game_main, BorderLayout.CENTER);
    }

    public Tetris() {
        text = new JTextArea[game_x][game_y];
        data = new int[game_x][game_y];
        initGamePanel();
        initWidow();
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
