package top.xym.tetris.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Tetris extends JFrame implements KeyListener {
    // 游戏的行数26，列数12
    private static final int game_x = 26;
    private static final int game_y = 12;
    // 文本域数组 text定义了每个格子是个文本域
    JTextArea[][] text;
    // 二维数组 data定义了每个格子的值 规定1代表有方块，0代表空白区域
    int[][] data;
    // 显示游戏状态的标签
    JLabel label1;
    // 显示游戏分数的标签
    JLabel label;
    // 用于判断游戏是否结束
    boolean isrunning;
    // 用于存储所有的方块的数组
    int[] allRect;
    // 用于存储当前方块的变量
    int rect;
    // 线程的休眠时间
    int time = 1000;
    // 表示方块坐标
    int x,y;
    // 该变量用于计算得分
    int score = 0;
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

    // 初始化游戏的说明面板
    public void initExplainPanel() {
        // 创建游戏的左说明面板
        JPanel explain_left = new JPanel();
        // 创建游戏的右说明面板
        JPanel explain_right = new JPanel();
        // 初始化格式布局
        explain_left.setLayout(new GridLayout(4,1));
        explain_right.setLayout(new GridLayout(2,1));
        // 在左说明面板，添加说明文字
        explain_left.add(new JLabel("按空格键，方块变形"));
        explain_left.add(new JLabel("按左箭头，方块左移"));
        explain_left.add(new JLabel("按右箭头，方块右移"));
        explain_left.add(new JLabel("按下箭头，方块下落"));
        // 设置标签的内容为蓝色字体
        label1.setForeground(Color.BLUE);
        // 把游戏状态标签，游戏分数标签，添加到右说明面板
        explain_right.add(label);
        explain_right.add(label1);
        // 将左说明面板添加到窗口的左侧
        this.add(explain_left, BorderLayout.WEST);
        // 将右说明面板添加到窗口的右侧
        this.add(explain_right, BorderLayout.EAST);
    }

    public Tetris() {
        text = new JTextArea[game_x][game_y];
        data = new int[game_x][game_y];
        // 初始化表示游戏状态的标签
        label1 = new JLabel("游戏状态：正在游戏中！");
        // 初始化表示游戏分数的标签
        label = new JLabel("游戏得分为：0");

        initGamePanel();
        initExplainPanel();
        initWidow();
        // 初始化开始游戏的标识
        isrunning = true;
        // 初始化存放方块的数组
        allRect = new int[] {0x00cc,0x8888,0x000f,0x888f,0xf111,0x111f,0x0eee,0xffff,0x0008,0x0888,0x000e,0x0088,
        0x000c,0x08c8,0x00e4,0x04c4,0x004e,0x08c4,0x006c,0x04c8,0x00c6};
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.game_begin();
    }

    // 开始游戏的方法
    public void game_begin() {
        while (true) { // 游戏失败才结束，所以写个死循环
            // 判断游戏是否结束
            if (!isrunning) {
                break;
            }

            // 进行游戏
            game_run();
        }
        // 在标签位置显示“游戏结束”
        label1.setText("游戏状态：游戏结束！");
    }

    // 随机生成下落方块形状的方法
    public void ranRect() {
        Random random = new Random();
        // 0到21的数字即可
        rect = allRect[random.nextInt(22)];
    }

    // 游戏运行的方法
    public void game_run() {
        ranRect();
        // 方块下落位置
        x = 0;
        y = 5;

        for(int i = 0; i < game_x; i++){
            try {
                Thread.sleep(time);

                // 判断方块是否可以下落
                if (!canFall(x,y)) {
                    // 将data置为1，表示有方块占用
                    changeData(x,y);
                    // 循环遍历4层，看是否有行可以消除
                    for(int j = x; j < x + 4; j++) {
                        //统计有多少列有方块
                        int sum = 0;
                        for(int k = 1; k < (game_y-2); k++) {
                            if (data[j][k] == 1) {
                                sum++;
                            }
                        }

                        // 判断是否有一行可以被消除
                        if (sum == (game_y-2)) {
                            // 消除这一行
                            removeRow(j);
                        }
                    }
                    // 判断游戏是否失败
                    for (int j = 1; j <= (game_y-2); j++) {
                        if(data[3][j] == 1){
                            isrunning = false;
                            break;
                        }
                    }
                    break;
                } else {
                    // 层数+1
                    x++;
                    // 方块下落一行
                    fall(x,y);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 判断方块是否可以继续下落的方法
    public boolean canFall(int m,int n) {
        // 定义一个变量
        int temp = 0x8000;
        // 遍历4 * 4方格
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((temp & rect) != 0) {
                    // 判断该位置的下一行是否有方块
                    if (data[m+1][n] == 1) {
                        return false;
                    }
                }
                n++;
                temp >>= 1;
            }
            m++;
            n = n - 4;
        }
        // 可以下落
        return true;
    }

    // 改变不可下降的方块对应的区域的值的方法
    public void changeData(int m,int n) {
        // 定义一个变量
        int temp = 0x8000;
        // 遍历整个4 * 4的方块
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((temp & rect) != 0) {
                    data[m][n] = 1;
                }
                n++;
                temp >>= 1;
            }
            m++;
            n = n - 4;
        }
    }

    // 移除某一行方块，令以上方块掉落的方法
    public void removeRow(int row) {
        int temp = 100;
        for(int i = row; i > 1; i--) {
            for(int j = 1; j < (game_y-2); j++) {
                // 进行覆盖
                data[i][j] = data[i-1][j];
            }
        }
        // 刷新游戏区域
        reflesh(row);

        // 方块加速
        if (time > temp) {
            time -= temp;
        }

        score += temp;

        // 显示变化后的分数
        label.setText("游戏得分：" + score);
    }

    // 刷新移除某一行后的游戏界面的方法
    public void reflesh(int row) {
        for (int i = row; i >= 1; i--) {
            for (int j = 1; j<= (game_y-2); j++) {
                if (data[j][j] == 1) {
                    text[i][j].setBackground(Color.BLUE);
                }else {
                    text[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    // 方块向下掉落一层的方法
    public void fall(int m, int n) {
        if (m > 0) {
            // 清除上一层方块
            clear(m-1, n);
        }
        // 重新绘制方块
        draw(m, n);
    }

    // 清除方块掉落后，上一层有颜色的地方的方法
    public void clear(int m, int n) {
        // 定义变量
        int temp = 0x8000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((temp & rect) != 0) {
                    text[m][n].setBackground(Color.WHITE);
                }
                n++;
                temp >>= 1;
            }
            m++;
            n = n - 4;
        }
    }

    // 重新绘制掉落后的方块的方法
    public void draw(int m, int n) {
        // 定义变量
        int temp = 0x8000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((temp & rect) != 0) {
                    text[m][n].setBackground(Color.BLUE);
                }
                n++;
                temp >>= 1;
            }
            m++;
            n = n - 4;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 控制方块进行变形 空格键
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            // 判断游戏是否结束
            if (!isrunning) {
                return;
            }
        }

        // 定义变量，存储目前方块的索引
        int old;
        for (old = 0; old < allRect.length; old++) {
            // 判断是否是当前方块
            if (rect == allRect[old]) {
                break;
            }
        }

        // 定义变量，存储变形后方块
        int next;

        // 判断是方块
        if (old == 0 || old == 7 || old == 8 || old == 9) {
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 方块进行左移
        if (e.getKeyCode() == 37) {
            // 判断游戏是否结束
            if (!isrunning) {
                return;
            }

            // 方块是否碰到左墙壁
            if (y <= 1) {
                return;
            }

            // 定义一个变量
            int temp = 0x8000;

            // 双重循环，遍历方块此时的位置
            for (int i = x; i < x + 4; i++) {
                for(int j = y; j < y + 4; j++) {
                    if ((temp & rect) != 0) {
                        if (data[i][j-1] == 1) {
                            return;
                        }
                    }
                    temp >>= 1;
                }
            }

            // 首先清除目前方块
            clear(x, y);

            y--;

            draw(x, y);
        }

        // 方块进行右移
        if (e.getKeyCode() == 39) {
            // 判断游戏是否结束
            if (!isrunning) {
                return;
            }

            // 定义变量
            int temp = 0x8000;
            int m = x;
            int n = y;

            // 存储最右边的坐标值
            int num = 1;

            // 双重循环，遍历方块此时的位置
            for (int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    if ((temp & rect) != 0) {
                        if (n > num) {
                            num = n;
                        }
                    }
                    n++;
                    temp >>= 1;
                }
                m++;
                n = n - 4;
            }

            // 判断方块是否碰到右墙壁
            if (num >= game_y - 2) {
                return;
            }

            // 方块右移途中是否碰到别的方块
            temp = 0x8000;
            for (int i = x; i < x + 4; i++) {
                for(int j = y; j < y + 4; j++) {
                    if ((temp & rect) != 0) {
                        if (data[i][j+1] == 1) {
                            return;
                        }
                    }
                    temp >>= 1;
                }
            }

            // 清除当前方块
            clear(x, y);

            y++;

            draw(x, y);
        }

        // 方块进行下落
        if (e.getKeyCode() == 40) {
            // 判断游戏是否结束
            if (!isrunning) {
                return;
            }

            // 判断方块是否可以下落
            if (!canFall(x, y)) {
                return;
            }

            clear(x, y);

            // 改变方块的坐标
            x++;

            draw(x, y);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
