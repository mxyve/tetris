package top.xym.tetris.entity;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {
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
    }

    // 初始化游戏界面
    public void initGamePanel() {
        JPanel game_main = new JPanel();
        // 水平间距和竖直间距都设置为1
        game_main.setLayout(new GridLayout(game_x,game_y,1,1));
    }

    public Tetris() {
        text = new JTextArea[game_x][game_y];
        data = new int[game_x][game_y];
        initWidow();
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
    }

}
