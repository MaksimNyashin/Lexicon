import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ShowWordForm extends JPanel{
    private static JTextArea word;
    private static WordList wordList;
    private static final int usedSize = 300;
    private ArrayList<String> usedWords = new ArrayList<>();

    ShowWordForm () {
        int dx = Main.getWidth() / 100;
        setBorder(BorderFactory.createLineBorder(Color.orange));
        wordList = new WordList();
        Dimension dim = new Dimension(Main.getWidth() / 2 - 2, Main.getHeight() * 2 / 5 - 2);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        Dimension dim2 = new Dimension(Main.getWidth() / 2 - dx, Main.getHeight() * 2 / 5 - dx);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        word = new JTextArea();
        word.setBackground(Color.getColor("#CCCCCC"));
        word.setMinimumSize(dim2);
        word.setMaximumSize(dim2);
        word.setPreferredSize(dim2);
        word.setEnabled(false);
        word.setDisabledTextColor(Color.black);
        word.setFont(new Font("Dialog", Font.PLAIN, 45));
        word.setText("Па́бло Дие́го Хосе́ Франси́ско де Па́ула Хуа́н Непомусе́но Мари́я де лос Реме́диос Сиприа́но де ла Санти́сима Тринида́д Ма́ртир Патри́сио Руи́с и Пика́ссо");
        word.setLineWrap(true);
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeWordSWF();
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
        word.addMouseListener(mouseListener);
        getUsedWords();
        //word.setWrapStyleWord(true);
        add(word);
    }

    void changeWordSWF() {
        wordList.next();
        Main.getBaseForm().requestFocus();
    }


    private void changeWord(String s) {
        word.setText(s);
    }

    public class WordList extends ArrayList<Word> {
        int id;
        private double PWMin = (double)1/4;
        private double PWMax = (double)1/13;

        WordList() {
            id = -1;
            getList();
            Random rnd = ThreadLocalRandom.current();
            Collections.shuffle(this, rnd);
            sort(0, size());
        }

        int cmp(Word w1, Word w2) {
            double PW = Math.random() * (PWMax - PWMin) + PWMin;
            double q = Math.random();
            double con = Math.pow(w1.getRate(), PW) / (Math.pow(w1.getRate(), PW) + Math.pow(w2.getRate(), PW));
            if (q < con)
                return -1;
            return 1;
        }

        private void getList() {
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream("rez.txt"), "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                String s;
                while ((s = reader.readLine()) != null) {
                    /*while (s.charAt(s.length() - 1) == ';' || (s.charAt(s.length() - 1) >= '0' && s.charAt(s.length() - 1) <= '9'))
                        s = s.substring(0, s.length() - 1);*/
                    add(new Word(s));
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void next() {
            //changeWord(word.getText().substring(0, max(0, word.getText().length() - 1)));
            if (id >= 0)
                updateUsedWords(get(id).getWord());
            while (id == -1 || checkIfUsed(get(id).getWord())) {
                id++;
                changeWord(get(id).getWord());
            }
        }

        void sort(int l, int r) {
            if (l + 1 == r)
                return;
            int m = (l + r) / 2;
            sort(l, m);
            sort(m, r);
            ArrayList <Word> ar = new ArrayList<>();
            int l1 = l, l2 = m;
            for (int i = 0; i < r - l; i++)
            {
                if (l2 == r || (l1 < m && cmp(get(l1), get(l2)) == -1))
                {
                    ar.add(get(l1));
                    l1++;
                }
                else {
                    ar.add(get(l2));
                    l2++;
                }
            }
            for (int i = 0; i < r - l; i++)
                set(i + l, ar.get(i));
        }
    }

    class Word{
        private String word;
        private int rate;
        Word(String s)
        {
            String t;
            if (s.charAt(s.length() - 1) == ';')
                t = s.substring(0, s.length() - 1);
            else
                t = s;
            int id = t.lastIndexOf(' ');
            word = t.substring(0, id);
            rate = Integer.parseInt(t.substring(id + 1));
        }

        public String getWord() {
            return word;
        }

        public int getRate() {
            return rate;
        }
    }

    public WordList getWordList() {
        return wordList;
    }

    String getText() {
        return word.getText();
    }

    private void getUsedWords() {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("used.txt"), "UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            String s;
            while ((s = reader.readLine()) != null)
                usedWords.add(s);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateUsedWords(String s) {
        usedWords.add(s);
        writeUsedWords();
    }
    private void writeUsedWords() {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("used.txt"), "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw);
            int l = 0, s = usedWords.size();
            if (s > usedSize)
                l = s - usedSize;
            for (int i = l; i < s; i++)
                writer.write(usedWords.get(i) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean checkIfUsed(String s) {
        int pos = usedWords.indexOf(s);
        return pos != -1 && pos + usedSize > usedWords.size();
    }
}
