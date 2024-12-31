package top.xym.tetris.entity;

import javax.swing.*;

public class Tetris extends JFrame {

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

    public Tetris() {
        initWidow();
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
    }

}
