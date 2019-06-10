import com.sun.prism.Graphics;

import java.awt.*;


public class Main {
    private static int width = 1920, height = 1080;
    private static Font font = new Font("Dialog", Font.PLAIN, 20);
    private static BaseForm baseForm;

    public static void main(String[] args) {
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //width = screenSize.width * 39 / 40;
        //height = screenSize.height * 9 / 10;
        Rectangle rec  = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        width = rec.width;
        height = rec.height;
        baseForm = new BaseForm();
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }

    public static Font getFont() {
        return font;
    }

    public static BaseForm getBaseForm() {
        return baseForm;
    }

    public static String getHelloWorld() {
        String helloWorld = "Справка:\n\tN - Новый игрок\n\tD - включение режима удаления игрока\n\tR - результаты всех игр\n" +
                "\t1-9 - соответсвенно игроки с номерами 1-9:\n\t\tесли кнопка удалить - красная, удаляет игрока\n\t\tиначе - добавляет ему очко\n" +
                "\tS - поиск в гугле\n\tпробел - следующий вопрос\n\tf1 - снова посмотреть справку";
        return helloWorld;
    }
}
