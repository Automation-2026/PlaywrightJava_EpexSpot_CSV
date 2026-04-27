package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterPage {

    Page page;
    private Locator table_rows;
    private Locator table_header;

    public FilterPage(Page page) {
        this.page = page;
        this.table_rows = page.locator(".js-table-values table tbody tr");
        this.table_header = page.locator(".js-table-values table thead th");
    }

    public boolean readCSV(){

        List<List<String>> tableData = new ArrayList<>();
        List<String> colNames = new ArrayList<>();

        /* Reading the 4 column headers from the table */
        for(int i=1;i<5;i++){
            String colText = table_header.nth(i).textContent().replace(" ","").replace("\n","");
            colNames.add(colText);
        }
        tableData.add(colNames);

        /* Reading the rows from the table */

        for(int i=0;i<table_rows.count();i++){
            List<String> rowData = new ArrayList<>();
            Locator cells = table_rows.nth(i).locator("td");

            /* Reading and saving the cell contents onr by one from the columns */
            for (int j = 0; j < 4; j++) {
                String cellText = cells.nth(j).innerText();
                rowData.add(cellText);
            }
            tableData.add(rowData);
        }

        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd MMM. yyyy", Locale.ENGLISH);
        String date = yesterday.format(formatter);
        String fileName = "tableData_"+date+".csv";

        /* Writing the 2 dim List to the file */

        try {
                FileWriter writer = new FileWriter(fileName);
                for (List<String> row : tableData) {
                    writer.append(String.join(",", row));
                    writer.append("\n");
                }
                writer.flush();
                writer.close();
                return true;
        }
        catch(IOException e){
            System.out.println("Error in reading/writing file!!");
            return false;
        }
    }
}
