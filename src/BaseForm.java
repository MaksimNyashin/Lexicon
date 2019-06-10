import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class BaseForm extends JFrame{
    private JPanel contents;
    private ShowWordForm showWordForm;
    private ResultsForm resultsForm;
    private BrowserForm browserForm;

    BaseForm() {
        super ("Словарный запас");
        setResizable(false);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {}
            @Override
            public void componentMoved(ComponentEvent e) {
                setLocation(0, 0);
            }
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contents = new JPanel(new GridBagLayout());
        contents.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(5, 5, 5, 5);

        showWordForm = new ShowWordForm();
        c.gridy = 0;
        c.gridheight = 2;
        contents.add(showWordForm, c);

        resultsForm = new ResultsForm();
        c.gridy = 2;
        c.gridheight = 3;
        contents.add(resultsForm, c);

        browserForm = new BrowserForm();

        setFocusable(true);
        requestFocus();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.toString());
                int q = -1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_N:
                        resultsForm.addPlayerRF();
                        break;
                    case KeyEvent.VK_D:
                        resultsForm.deletePlayerRF();
                        break;
                    case KeyEvent.VK_R:
                        resultsForm.showResultsRF();
                        break;
                    case KeyEvent.VK_G:
                        resultsForm.newGameRF();
                        break;
                    case KeyEvent.VK_1:
                        q = 0;
                        break;
                    case KeyEvent.VK_2:
                        q = 1;
                        break;
                    case KeyEvent.VK_3:
                        q = 2;
                        break;
                    case KeyEvent.VK_4:
                        q = 3;
                        break;
                    case KeyEvent.VK_5:
                        q = 4;
                        break;
                    case KeyEvent.VK_6:
                        q = 5;
                        break;
                    case KeyEvent.VK_7:
                        q = 6;
                        break;
                    case KeyEvent.VK_8:
                        q = 7;
                        break;
                    case KeyEvent.VK_9:
                        q = 8;
                        break;
                    case KeyEvent.VK_0:
                        q = 9;
                        break;
                    case KeyEvent.VK_SPACE:
                        showWordForm.changeWordSWF();
                        break;
                    case KeyEvent.VK_S:
                        browserForm.search(showWordForm.getText());
                        break;
                    case KeyEvent.VK_F1:
                        JTextArea results = new JTextArea(Main.getHelloWorld());
                        results.setEditable(false);
                        results.setLineWrap(true);
                        results.setWrapStyleWord(true);
                        results.setFont(Main.getFont());
                        JScrollPane scrollPane = new JScrollPane(results);
                        scrollPane.setPreferredSize(new Dimension(1024, 512));
                        JOptionPane.showMessageDialog(Main.getBaseForm(), scrollPane);
                        Main.getBaseForm().requestFocus();
                        break;
                }
                if (q != -1)
                    resultsForm.panelClickRFID(q, e.isControlDown() || e.isAltDown() || e.isShiftDown());
            }
        });
        start();
    }

    private void start()
    {
        //int height = Main.getHeight() * 10 / 9, width = Main.getWidth() * 40 / 39;
        int height = Main.getHeight(), width = Main.getWidth();
        setContentPane(contents);
        setBounds(0, 0, width / 2, height);
        setVisible(true);
    }

    void changeWord()
    {
        showWordForm.getWordList().next();
    }

}
