package ru.ifmo.enf.tasks.t02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by May on 16.11.2014.
 */
public class Convertervar extends JFrame {
    private Scanner scnr;
    final DefaultListModel<Entity> lis = new DefaultListModel<Entity>();
    final DefaultListModel<Entity> list = new DefaultListModel<Entity>();
    //Список для начальных компонентов сборки
    public JList<Entity> markCBox = new JList<Entity>(lis);
    //Список для выбранных компонентов сборки
    public JList<Entity> listOfChoose = new JList<Entity>(lis);

    /**
     * Главный конструктор класса, сканирует данные, которые лежат в корне проекта
     */
    private Convertervar() {
        super("Выгодная сборка компьютера");
        try {
            scnr = new Scanner(new File("input"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    /**
     * Главный метод, который запускает всевсе
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Convertervar converter = new Convertervar();
                converter.createAndShowGUI();
            }
        });
    }

    /**
     * метод, который создает клиентскую часть, здесь прописываются все видимые элементы и их реакции
     */
    private void createAndShowGUI() {
        //Главная панелька
        JPanel mainPane = new JPanel(new GridBagLayout());
        //Элементы панели задаются в виде таблице
        GridBagConstraints c = new GridBagConstraints();
        //Отступ между элементами
        c.insets = new Insets(8, 8, 8, 8);
        markCBox = new JList<Entity>(list);
        listOfChoose = new JList<Entity>(lis);
        new Reader().execute();
        //Первый столбик
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        final JLabel markLbl = new JLabel("Список компонентов"); //Mark of auto label
        c.gridy = 0;
        mainPane.add(markLbl, c);
        c.gridy = 1;
        markCBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scroll = new JScrollPane(markCBox);
        mainPane.add(scroll, c);
        final JButton recountBtn = new JButton("Итого"); //Recount button
        recountBtn.setEnabled(false);
        c.gridy = 3;
        mainPane.add(recountBtn, c);

//Второй столбик
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        //    c.gridy = 0;
        JButton addButn = new JButton("Добавить>"); //Recount button
        c.gridy = 0;
        mainPane.add(addButn, c);
        JButton deleteButn = new JButton("<Удалить"); //Recount button
        c.gridy = 1;
        mainPane.add(deleteButn, c);
        final JLabel output = new JLabel(); //Result label
        c.gridy = 3;
        mainPane.add(output, c);
        //Третий столбец
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 2;
        JLabel mileageLbl = new JLabel("Мой компьютер"); //Annual mileage label
        c.gridy = 0;
        mainPane.add(mileageLbl, c);
        c.gridy = 1;
        //  listOfChoose.setSize(400,200);
        listOfChoose.setVisible(true);
        listOfChoose.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scrolll = new JScrollPane(listOfChoose);
        mainPane.add(scrolll, c);

        /**
         * В последующих действиях мы добавляем слухачей для кнопок Добавить,
         * Удалить и Итого
         * Также прописывает, когда сделать кнопку Итого активной
         */
        addButn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean hasElem = false;
                boolean isVHere = false;
                for (Entity en : markCBox.getSelectedValuesList()) {
                    for (int i = 0; i < lis.size(); i++) {
                        if (lis.get(i).shortName.equals("V")) {
                            isVHere = true;
                        }
                        if (en.shortName.equals(lis.get(i).shortName)) {
                            hasElem = true;
                            break;
                        }
                    }
                }
                if (!markCBox.isSelectionEmpty() && !hasElem) {
                    List<Entity> listChoose = markCBox.getSelectedValuesList();

                    for (Entity x : listChoose) {
                        lis.addElement(x);
                        list.removeElement(x);
                        if (lis.size() == 5 && !isVHere) {
                            recountBtn.setEnabled(true);
                        } else {
                            if (lis.size() > 5) {
                                recountBtn.setEnabled(true);
                            }
                        }
                    }
                } else {
                    errorFormatMessage();
                }
            }
        });
        deleteButn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!markCBox.isSelectionEmpty()) {
                    List<Entity> listChoose = listOfChoose.getSelectedValuesList();

                    for (Entity x : listChoose) {
                        lis.removeElement(x);
                        list.addElement(x);
                    }
                }
            }
        });

        recountBtn.addActionListener(new ActionListener() { //Recount button listener
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double cost = (double) 200;
                for (int i = 0; i < lis.size(); i++) {
                    cost = cost + lis.get(i).costOfProd;
                }
                if (lis.size()>=5) {
                    output.setText(cost.toString() + " рублей");
                }else {
                    JOptionPane.showMessageDialog(null, "Сборка неверна, добавьте компоненты",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Set frame parameters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPane);
        setResizable(true);
        //Начало в центре странице
        setLocationRelativeTo(null);
        pack();
        //Отобразить
        setVisible(true);
    }

    /**
     * Текст ошибки, показываемой при повторном добавлении элемента
     */
    private void errorFormatMessage() {
        JOptionPane.showMessageDialog(null, "Вы уже добавили данный элемент, пожалуйста, выберите другой.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Парсим входящие данные, в случае неверных данных выводим окно ошибки
     *
     * @param line String line
     * @return Entity object
     */
    private Entity parse(String line) {
        String split[] = line.split("\t");//Split by tabs
        String fullName = split[2];
        Double price = 0.0;
        try {
            price = Double.parseDouble(split[1]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bad input data", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        String shortName = split[0];
        return new Entity(fullName, price, shortName);
    }

    /**
     * Класс данных: короткого имени, стоимости и полного имени элементов
     */
    private class Entity {
        //Just fields and getters
        private String shortName;
        private Double costOfProd;
        private String fullProdName;

        private Entity(String fullProdName, Double costOfProd, String shortName) {
            this.shortName = shortName;
            this.costOfProd = costOfProd;
            this.fullProdName = fullProdName;
        }

        //Use mark name in Combo Box
        @Override
        public String toString() {
            return "(" + shortName + ") " + fullProdName;
        }
    }

    private class Reader extends SwingWorker<Integer, Entity> {
        /**
         * Считывает большой объем данных в фоновом режиме с задержкой
         *
         * @return new data
         * @throws Exception
         */
        @Override
        protected Integer doInBackground() throws Exception {
            while (scnr.hasNext()) {
                publish(parse(scnr.nextLine()));
                Thread.sleep(500);
            }
            return 0;
        }

        /**
         * Добавляем элементы из файла в видимый список
         *
         * @param e List<Entity>
         */
        @Override
        protected void process(final List<Entity> e) {
            for (final Entity en : e) {
                list.addElement(en);
                pack();
            }
        }
    }
}
