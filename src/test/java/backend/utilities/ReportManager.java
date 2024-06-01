package backend.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportManager {
    private static ExtentReports extent;
    private static final String REPORT_PATH = "reports/";
    private static final String NAME_REPORT = "ExtentReport";

    /**
     * Method for initializing the reporter
     */
    public static void initializeReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_PATH + NAME_REPORT + dateFormat() + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    /**
     * Method for assigning a name to the test case in the report
     * @param testName Test case name
     * @return The name of the test case in the report
     */
    public static ExtentTest createTest(String testName) {
        return extent.createTest(testName);
    }

    /**
     * Method that completes report generation
     */
    public static void flushReport() {
        extent.flush();
    }

    /**
     * Method for capturing the current system date and time
     * @return System date and time in String format
     */
    private static String dateFormat(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return localDateTime.format(formatter);
    }
}
