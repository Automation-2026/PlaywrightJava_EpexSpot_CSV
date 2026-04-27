package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FilterPage;


public class ReadCSVTest extends BaseTest{

    FilterPage filterPage;

    /**
     * Test case to verify the first 4 rows are read
     * And successfully saved in csv file.
     * **/

    @Test
    public void verifyData(){
        filterPage = new FilterPage(page);
        Assert.assertTrue(filterPage.readCSV());
    }
}
