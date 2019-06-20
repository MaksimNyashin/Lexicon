import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ResultsForm extends JPanel {
    private final int WID = 5;
    private SavingList savingList = new SavingList();
    private PlayerTable playerTable = new PlayerTable();
    private PlayerMenu playerMenu = new PlayerMenu();
    private int number = 0;
    private boolean deleting = false;

    ResultsForm() {
        setMinimumSize(new Dimension(Main.getWidth() / 2, Main.getHeight() * 3 / 5));
        setMaximumSize(new Dimension(Main.getWidth() / 2, Main.getHeight() * 3 / 5));
        setPreferredSize(new Dimension(Main.getWidth() / 2, Main.getHeight() * 3 / 5));

        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
        add(playerMenu);
        add(playerTable);
    }

    void addPlayerRF() {
        String newName;
        while (true) {
            newName = JOptionPane.showInputDialog(Main.getBaseForm(), "Введите имя игрока");
            if (newName == null || newName.equals(""))
                return;
            if (newName.length() > 46) {
                JOptionPane.showMessageDialog(Main.getBaseForm(), "Длина имени не должна превышать 46 символов.\n Попробуйте ещё раз");
                continue;
            }
            break;
        }
        playerTable.addPlayer(newName);
        number++;
        Main.getBaseForm().requestFocus();
    }

    void deletePlayerRF() {
        deleting ^= true;
        if (deleting)
            playerMenu.deletePlayer.setBackground(new Color(255, 0, 0));
        else
            playerMenu.deletePlayer.setBackground(new JButton().getBackground());
        Main.getBaseForm().requestFocus();
    }

    void showResultsRF() {
        //System.out.println(savingList);
        JTextArea results = new JTextArea(savingList.toString());
        results.setEditable(false);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        results.setFont(Main.getFont());
        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setPreferredSize(new Dimension(256, 512));
        JOptionPane.showMessageDialog(Main.getBaseForm(), scrollPane);
        //JOptionPane.showMessageDialog(Main.getBaseForm(), savingList.toString());
        Main.getBaseForm().requestFocus();
    }

    void panelClickRFID(int id, boolean isDec) {
        if (playerTable.list.size() <= id)
            return;
        panelClickRF(playerTable.list.get(id), isDec);
    }

    private void panelClickRF(PlayerTable.PlayerPanel panel, boolean isDec) {
        if (deleting)
        {
            playerTable.deletePlayer((int)panel.getClientProperty("id"));
            deleting = false;
        }
        else {
            savingList.plus((int)panel.getClientProperty("id"), isDec);
            if (isDec) {
                panel.intScore--;
                panel.score.setText("" + panel.intScore);
            }
            else {
                panel.intScore++;
                panel.score.setText("" + panel.intScore);
                Main.getBaseForm().changeWord();
            }
        }
        playerMenu.deletePlayer.setBackground(new JButton().getBackground());
        Main.getBaseForm().requestFocus();
    }

    void newGameRF() {
        playerTable.newGame();
    }

    private class PlayerMenu extends JPanel {
        JButton addPlayer, deletePlayer, newGame, showResults;
        PlayerMenu() {
            int w = Main.getWidth() / 2 / WID - 5,  h = Main.getHeight() * 3 / 5 - 5;
            setBorder(BorderFactory.createLineBorder(Color.green));

            Dimension dim = new Dimension(w, h);
            setMinimumSize(dim);
            setMaximumSize(dim);
            setPreferredSize(dim);
            setVisible(true);
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));

            addPlayer = new JButton("Новый ");
            MouseListener mouseListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource().equals(addPlayer)) {
                        addPlayerRF();
                        return;
                    }
                    if (e.getSource().equals(deletePlayer)) {
                        deletePlayerRF();
                        return;
                    }
                    if (e.getSource().equals(showResults)) {
                        showResultsRF();
                        return;
                    }
                    if (e.getSource().equals(newGame)) {
                        newGameRF();
                        return;
                    }
                    System.out.println("" + playerMenu.getX() + " " + playerMenu.getY());
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            };
            addPlayer.addMouseListener(mouseListener);
            jButtonSetSize(addPlayer, w, h / 4 - 1);
            add(addPlayer);

            deletePlayer = new JButton("Удалить");
            deletePlayer.addMouseListener(mouseListener);
            jButtonSetSize(deletePlayer, w, h / 4 - 1);
            add(deletePlayer);

            showResults = new JButton("Результаты");
            showResults.addMouseListener(mouseListener);
            jButtonSetSize(showResults, w, h / 4 - 1);
            add(showResults);

            newGame = new JButton("Новая игра");
            newGame.addMouseListener(mouseListener);
            jButtonSetSize(newGame, w, h / 4 - 1);
            add(newGame);
        }

        private  void jButtonSetSize(JButton jButton, int w, int h) {
            jButton.setFont(Main.getFont());
            Dimension dim = new Dimension(w - 2, h);
            jButton.setMinimumSize(dim);
            jButton.setMaximumSize(dim);
            jButton.setPreferredSize(dim);
            jButton.setVisible(true);
        }
    }

    private class PlayerTable extends JPanel {
        private ArrayList<PlayerPanel> list;

        PlayerTable() {
            list = new ArrayList<>();
            int w = Main.getWidth() * (WID - 1) / 2 / WID - 5,  h = Main.getHeight() * 3 / 5 - 5;
            Dimension dim = new Dimension(w, h);
            setMinimumSize(dim);
            setMaximumSize(dim);
            setPreferredSize(dim);
            setVisible(true);
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setBorder(BorderFactory.createLineBorder(Color.cyan));
            //addPlayer("Ня");
        }

        void addPlayer(String name) {
            PlayerPanel p =new PlayerPanel(name);
            list.add(p);
            savingList.addPlayer(name);
            add(p);
            p.setVisible(true);
            updateScore();
        }

        void deletePlayer(int did) {
            //int nid = players.indexOf(list.get(did).getClientProperty("id"));
            list.get(did).setVisible(false);
            remove(list.get(did));
            list.remove(did);
            for (int i = did; i < list.size(); i++) {
                list.get(i).putClientProperty("id", i);
                list.get(i).setName(list.get(i).getName());
            }
            savingList.deletePlayer(did);
            number--;
            updateScore();
        }

        void newGame() {
            while (number > 0) {
                deleting = true;
                panelClickRFID(0, false);
                //ResultsForm.this.remove(list.get(0));
            }
            savingList.newGame();
            number = 0;
        }

        void updateScore() {
            for (int i = 0; i < number; i++)
                list.get(i).setScore(savingList.getScore(i));
        }

        private class PlayerPanel extends JPanel {
            //int x, y;
            String realName;
            JTextArea name;
            JLabel score;
            int intScore;

            PlayerPanel(String name) {
                putClientProperty("id", number);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(new EmptyBorder(5, 5, 5, 5));
                realName = name;
                this.name = new JTextArea(getName(name));
                this.name.setBackground(Color.getColor("#CCCCCC"));
                this.name.setFont(Main.getFont());
                this.name.setLineWrap(true);
                this.name.setEnabled(false);
                this.name.setDisabledTextColor(Color.black);
                MouseListener mouseListener = new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panelClickRF(PlayerPanel.this, e.isAltDown() | e.isControlDown() | e.isShiftDown());
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                };
                this.name.addMouseListener(mouseListener);
                add(this.name);

                score = new JLabel("0");
                score.setFont(Main.getFont());
                add(score);
                intScore = 0;
                int HEI = 5;
                int w = Main.getWidth() / 2 / WID - 5, h = Main.getHeight() * 3 / 5 / HEI - 5;
                Dimension dim = new Dimension(w, h);
                setAlignmentY(TOP_ALIGNMENT);
                setBorder(BorderFactory.createLineBorder(Color.black));
                setMinimumSize(dim);
                setMaximumSize(dim);
                setPreferredSize(dim);
                setVisible(true);
                addMouseListener(mouseListener);
            }

            String getName(String newName) {
                return String.valueOf((int)getClientProperty("id") + 1) + ") " + newName;
            }

            public String getName() {
                return getName(realName);
            }

            public void setName(String name)
            {
                this.name.setText(name);
                this.score.setText("0");
                this.intScore = 0;
            }

            void setScore(int score) {
                this.intScore = score;
                this.score.setText("" + score);
            }
        }
    }
}
