import javax.swing.*;

class BrowserForm extends SimpleSwingBrowser {

    BrowserForm () {
        super();
        /*Dimension dimension = new Dimension(Main.getWidth() / 2, Main.getHeight() - 10);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);*/
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            loadURL("https://google.com");
        });
    }


    void search(String s) {
        //String newAddress = "https://www.google.ru/search?q=" + s.replace(" ", "+");
        String newAddress = "https://www.google.com/search?q=words&oq=words&aqs=chrome..69i57j0l5.1266j1j7&sourceid=chrome&ie=UTF-8".replace("words", s.replace(' ', '+'));
        loadURL(newAddress);
    }
}
