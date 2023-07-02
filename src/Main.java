import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Main extends JFrame implements ActionListener {
    Font titleFont;
    Font btnFont;

    Color bgColor, fgColor, winColor, panelColor, btnColor;
    boolean isPlayerOne;

    String [] plays;
    JButton [] buttons;

    int player1Score = 0;
    int player2Score = 0;

    JLabel XLabel;
    JLabel OLabel;

    JLabel scoreLabel1;
    JLabel scoreLabel2;

    JLabel player;
    boolean [] turns = {true, false};

    private JButton createButton() {
        JButton btn = new JButton();
        btn.setText("");
        btn.setFont(btnFont);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.addActionListener(this);
        btn.setFocusable(false);

        return btn;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(btnFont);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public Main() {
        Random rd = new Random();

        titleFont = new Font("Consolas", Font.ITALIC, 55);
        btnFont = new Font("Consolas", Font.PLAIN, 30);
        isPlayerOne = turns[Math.round(rd.nextInt(2))];

        plays = new String[9];
        buttons = new JButton[9];

        bgColor = Color.black;
        fgColor = Color.green;
        winColor = Color.gray;
        panelColor = Color.black;
        btnColor = Color.white;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            JButton btn = createButton();
            btn.setActionCommand(String.valueOf(i));
            panel.add(btn);

            buttons[i] = btn;
        }

        JPanel southPanel = new JPanel();
        southPanel.setBackground(panelColor);
        southPanel.setLayout(new GridLayout(2, 1));
        add(southPanel, BorderLayout.SOUTH);

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 2));
        scorePanel.setBackground(panelColor);

        JPanel funcButtons = new JPanel();
        funcButtons.setLayout(new GridLayout(1, 2));

        XLabel = createLabel(acceptName("Player 1"));
        XLabel.setHorizontalAlignment(JLabel.CENTER);
        OLabel = createLabel(acceptName("Player 2"));
        OLabel.setHorizontalAlignment(JLabel.CENTER);

        scoreLabel1 = createLabel(String.valueOf(player1Score));
        scoreLabel2 = createLabel(String.valueOf(player2Score));

        JButton resetBtn = createButton();
        resetBtn.setText("Reset");
        resetBtn.setActionCommand("Reset");
        resetBtn.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        resetBtn.setBackground(btnColor);

        JButton exitBtn = createButton();
        exitBtn.setText("Exit");
        exitBtn.setActionCommand("Exit");
        exitBtn.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        exitBtn.setBackground(btnColor);

        scorePanel.add(XLabel);
        scorePanel.add(scoreLabel1);
        scorePanel.add(OLabel);
        scorePanel.add(scoreLabel2);

        funcButtons.add(resetBtn);
        funcButtons.add(exitBtn);

        southPanel.add(scorePanel);
        southPanel.add(funcButtons);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(panelColor);
        titlePanel.setLayout(new GridLayout(2, 1));
        add(titlePanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Tic Tac Toe");
        titlePanel.add(title);
        title.setFont(titleFont);
        title.setBackground(resetBtn.getBackground());
        title.setForeground(resetBtn.getForeground());
        title.setHorizontalAlignment(JLabel.CENTER);
        player = new JLabel(isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn");
        player.setFont(btnFont);
        player.setHorizontalAlignment(JLabel.CENTER);
        player.setBackground(resetBtn.getBackground());
        player.setForeground(resetBtn.getForeground());
        titlePanel.add(player);

        title.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                title.setForeground(Color.gray);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                title.setForeground(Color.green);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setVisible(true);
        setSize(750, 750);
        getContentPane().setBackground(Color.blue);
        setLocationRelativeTo(null);
    }

    public String acceptName(String text) {
        String res = JOptionPane.showInputDialog(null,
                text + "\nEnter your name").toUpperCase();
        if (text.equalsIgnoreCase("Player 2") &&
                XLabel.getText().equalsIgnoreCase(res)) {
            return acceptName(text);
        }
        return res.isBlank() ?
                acceptName(text) : res;
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (e.getActionCommand().equals("Exit")) {
            int option = JOptionPane.showConfirmDialog(null, "Sure to exit?");
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        else if (e.getActionCommand().equals("Reset")) {
            int result = JOptionPane.showConfirmDialog(null, "Sure to restart?", "Warning Dialog",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == 0) {
                player1Score = 0;
                player2Score = 0;
                reset();

                int option2 = JOptionPane.showConfirmDialog(null, "Do you want to play with the same player?",
                        "Confirmation Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option2 == JOptionPane.NO_OPTION) {
                    player1Score = 0;
                    scoreLabel1.setText(String.valueOf(player1Score));
                    player2Score = 0;
                    scoreLabel2.setText(String.valueOf(player2Score));
                    reset();
                    XLabel.setText(acceptName("Player 1"));
                    OLabel.setText(acceptName("Player 2"));
                    String turn = isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn";
                    player.setText(turn);
                }
            }
        }

        if (btn.getText().equals("")) {
            String played;
            if (isPlayerOne) {
                btn.setForeground(Color.white);
                played = "X";
            } else {
                btn.setForeground(Color.red);
                played = "O";
            }

            int index = Integer.parseInt(e.getActionCommand());
            btn.setText(played);
            plays[index] = played;

            isPlayerOne = !isPlayerOne;
            checkWin(played);

            boolean vacant = false;
            for (String str : plays) {
                if (str == null) {
                    vacant = true;
                }
            }
            if (!vacant) {
                JOptionPane.showMessageDialog(this, "No Winner\nPlay Again");
                reset();
            }
        }
    }

    void checkWin(String text) {
        int [][] winningPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {6, 3, 0},
                {7, 4, 1}, {5, 8, 2}, {0, 4, 8}, {2, 4, 6}
        };

        String turns = isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn";
        player.setText(turns);

        for (int[] arr: winningPositions) {
            int first = arr[0];
            int second = arr[1];
            int third = arr[2];

            if (plays[first] == null || plays[second] == null || plays[third] == null) {
                continue;
            }

            if (plays[first].equals(text) && plays[second].equals(text) && plays[third].equals(text)) {
                buttons[first].setBackground(winColor);
                buttons[second].setBackground(winColor);
                buttons[third].setBackground(winColor);

                if (text.equals("X")) {
                    player1Score++;
                    scoreLabel1.setText(String.valueOf(player1Score));
                    JOptionPane.showMessageDialog(this, XLabel.getText() + " wins");
                } else {
                    player2Score++;
                    scoreLabel2.setText(String.valueOf(player2Score));
                    JOptionPane.showMessageDialog(this, OLabel.getText() + " wins");
                }

                reset();
                break;
            }
        }
    }

    void reset() {
        for (int i = 0; i < buttons.length; i++) {
            plays[i] = null;
            buttons[i].setText("");
            buttons[i].setBackground(bgColor);
        }
    }
}