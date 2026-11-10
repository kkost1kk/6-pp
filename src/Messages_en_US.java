import java.util.ListResourceBundle;

class Messages_en_US extends ListResourceBundle {
    public Object[][] getContents() {
        return new Object[][]{
                {"title", "Olympiad Winners Awarding"},
                {"congratulator", "Select congratulator:"},
                {"dean", "Dean (50)"},
                {"rector", "Rector (100)"},
                {"sponsor", "Sponsor (80)"},
                {"gift", "Select gifts:"},
                {"concert", "Need a concert?"},
                {"yes", "Yes"},
                {"no", "No"},
                {"client", "Regular client (10%):"},
                {"locale", "Locale:"},
                {"total", "Total: "},
                {"save", "Save to file"},
                {"saved", "Saved to order.txt"},
                {"fail", "Failed to save"}
        };
    }
}
