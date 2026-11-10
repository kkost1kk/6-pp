import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class OlympiadAwardApp extends JFrame {
    private Locale currentLocale = new Locale("ru", "RU");
    private ResourceBundle bundle;
    private JLabel congratulatorLabel;
    private JLabel giftLabel;
    private JLabel concertLabel;
    private JLabel clientLabel;
    private JLabel localeLabel;
    private JLabel totalCostLabel;
    private JLabel shortDateLabel;
    private JLabel longDateLabel;
    private JCheckBox deanCheckBox;
    private JCheckBox rectorCheckBox;
    private JCheckBox sponsorCheckBox;
    private JList<String> giftList;
    private DefaultListModel<String> giftListModel;
    private JRadioButton concertYesRadio;
    private JRadioButton concertNoRadio;
    private JRadioButton clientYesRadio;
    private JRadioButton clientNoRadio;
    private JButton saveButton;
    private JComboBox<String> localeComboBox;
    private final Map<String, Double> itemPrices = new HashMap<>();
    private final Map<String, String[]> congratulatorGifts = new HashMap<>();

    public OlympiadAwardApp() {
        initPricesAndGifts();
        initUI();
        updateGiftsList();
        updateTexts();
        calculateTotal();
    }
    private void initPricesAndGifts() {
        itemPrices.put("Диплом (10)", 10.0);
        itemPrices.put("Книга (25)", 25.0);
        itemPrices.put("Кубок (60)", 60.0);
        itemPrices.put("Медаль (30)", 30.0);
        itemPrices.put("Ноутбук (500)", 500.0);
        itemPrices.put("Мерч (40)", 40.0);
        congratulatorGifts.put("dean", new String[]{"Диплом (10)", "Книга (25)"});
        congratulatorGifts.put("rector", new String[]{"Диплом (10)", "Кубок (60)", "Медаль (30)"});
        congratulatorGifts.put("sponsor", new String[]{"Ноутбук (500)", "Мерч (40)"});
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(0, 1));
        JPanel localePanel = new JPanel();
        localeLabel = new JLabel();
        localeComboBox = new JComboBox<>(new String[]{"ru_RU", "en_US", "de_DE"});
        localeComboBox.addActionListener(e -> {
            String selected = (String) localeComboBox.getSelectedItem();
            if (selected != null) {
                String[] parts = selected.split("_");
                currentLocale = new Locale(parts[0], parts[1]);
                updateTexts();
                calculateTotal();
            }
        });
        localePanel.add(localeLabel);
        localePanel.add(localeComboBox);
        add(localePanel);
        JPanel datePanel = new JPanel(new GridLayout(2, 1));
        shortDateLabel = new JLabel();
        longDateLabel = new JLabel();
        datePanel.add(shortDateLabel);
        datePanel.add(longDateLabel);
        add(datePanel);
        JPanel congPanel = new JPanel();
        congratulatorLabel = new JLabel();
        deanCheckBox = new JCheckBox();
        rectorCheckBox = new JCheckBox();
        sponsorCheckBox = new JCheckBox();
        ButtonGroup congGroup = new ButtonGroup();
        congGroup.add(deanCheckBox);
        congGroup.add(rectorCheckBox);
        congGroup.add(sponsorCheckBox);
        deanCheckBox.setSelected(true);
        deanCheckBox.addActionListener(e -> { updateGiftsList(); calculateTotal(); });
        rectorCheckBox.addActionListener(e -> { updateGiftsList(); calculateTotal(); });
        sponsorCheckBox.addActionListener(e -> { updateGiftsList(); calculateTotal(); });
        congPanel.add(congratulatorLabel);
        congPanel.add(deanCheckBox);
        congPanel.add(rectorCheckBox);
        congPanel.add(sponsorCheckBox);
        add(congPanel);
        JPanel giftPanel = new JPanel(new BorderLayout());
        giftLabel = new JLabel();
        giftListModel = new DefaultListModel<>();
        giftList = new JList<>(giftListModel);
        giftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        giftList.addListSelectionListener(e -> calculateTotal());
        giftPanel.add(giftLabel, BorderLayout.NORTH);
        giftPanel.add(new JScrollPane(giftList), BorderLayout.CENTER);
        add(giftPanel);
        JPanel concertPanel = new JPanel();
        concertLabel = new JLabel();
        concertYesRadio = new JRadioButton();
        concertNoRadio = new JRadioButton();
        ButtonGroup concertGroup = new ButtonGroup();
        concertGroup.add(concertYesRadio);
        concertGroup.add(concertNoRadio);
        concertNoRadio.setSelected(true);
        concertYesRadio.addActionListener(e -> calculateTotal());
        concertNoRadio.addActionListener(e -> calculateTotal());
        concertPanel.add(concertLabel);
        concertPanel.add(concertYesRadio);
        concertPanel.add(concertNoRadio);
        add(concertPanel);
        JPanel clientPanel = new JPanel();
        clientLabel = new JLabel();
        clientYesRadio = new JRadioButton();
        clientNoRadio = new JRadioButton();
        ButtonGroup clientGroup = new ButtonGroup();
        clientGroup.add(clientYesRadio);
        clientGroup.add(clientNoRadio);
        clientNoRadio.setSelected(true);
        clientYesRadio.addActionListener(e -> calculateTotal());
        clientNoRadio.addActionListener(e -> calculateTotal());
        clientPanel.add(clientLabel);
        clientPanel.add(clientYesRadio);
        clientPanel.add(clientNoRadio);
        add(clientPanel);
        totalCostLabel = new JLabel();
        add(totalCostLabel);
        saveButton = new JButton();
        saveButton.addActionListener(e -> saveOrderToFile());
        add(saveButton);
    }

    private void updateGiftsList() {
        giftListModel.clear();
        String key = "dean";
        if (rectorCheckBox.isSelected()) {
            key = "rector";
        } else if (sponsorCheckBox.isSelected()) {
            key = "sponsor";
        }
        for (String gift : congratulatorGifts.get(key)) {
            giftListModel.addElement(gift);
        }
    }

    private void updateTexts() {
        bundle = ResourceBundle.getBundle("Messages", currentLocale, OlympiadAwardApp.class.getClassLoader());
        setTitle(bundle.getString("title"));
        localeLabel.setText(bundle.getString("locale"));
        congratulatorLabel.setText(bundle.getString("congratulator"));
        deanCheckBox.setText(bundle.getString("dean"));
        rectorCheckBox.setText(bundle.getString("rector"));
        sponsorCheckBox.setText(bundle.getString("sponsor"));
        giftLabel.setText(bundle.getString("gift"));
        concertLabel.setText(bundle.getString("concert"));
        concertYesRadio.setText(bundle.getString("yes"));
        concertNoRadio.setText(bundle.getString("no"));
        clientLabel.setText(bundle.getString("client"));
        clientYesRadio.setText(bundle.getString("yes"));
        clientNoRadio.setText(bundle.getString("no"));
        saveButton.setText(bundle.getString("save"));

        Date now = new Date();
        DateFormat shortFmt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, currentLocale);
        DateFormat longFmt = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, currentLocale);
        shortDateLabel.setText(shortFmt.format(now));
        longDateLabel.setText(longFmt.format(now));
    }

    private double calculateTotal() {
        double total = 0;
        if (deanCheckBox.isSelected()) total += 50;
        if (rectorCheckBox.isSelected()) total += 100;
        if (sponsorCheckBox.isSelected()) total += 80;
        for (String selectedGift : giftList.getSelectedValuesList()) {
            total += itemPrices.getOrDefault(selectedGift, 0.0);
        }

        if (concertYesRadio.isSelected()) {
            total += 200;
        }
        if (clientYesRadio.isSelected()) {
            total *= 0.9;
        }
        totalCostLabel.setText(bundle.getString("total") + String.format(currentLocale, "%.2f", total));
        return total;
    }

    private void saveOrderToFile() {
        try (FileWriter writer = new FileWriter("order.txt")) {
            writer.write(getTitle() + "\n");
            writer.write(shortDateLabel.getText() + "\n");
            if (deanCheckBox.isSelected()) writer.write(deanCheckBox.getText() + "\n");
            if (rectorCheckBox.isSelected()) writer.write(rectorCheckBox.getText() + "\n");
            if (sponsorCheckBox.isSelected()) writer.write(sponsorCheckBox.getText() + "\n");
            writer.write("Gifts: " + giftList.getSelectedValuesList() + "\n");
            writer.write("Concert: " + (concertYesRadio.isSelected() ? bundle.getString("yes") : bundle.getString("no")) + "\n");
            writer.write("Discount: " + (clientYesRadio.isSelected() ? bundle.getString("yes") : bundle.getString("no")) + "\n");
            writer.write(totalCostLabel.getText() + "\n");
            JOptionPane.showMessageDialog(this, bundle.getString("saved"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("fail"));
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OlympiadAwardApp().setVisible(true));
    }
}
