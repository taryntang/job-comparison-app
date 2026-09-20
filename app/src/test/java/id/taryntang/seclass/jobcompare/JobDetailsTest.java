package id.taryntang.seclass.jobcompare;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


import java.util.concurrent.TimeUnit;

import id.taryntang.seclass.jobcompare.models.JobDetails;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class JobDetailsTest {
    private JobDetails jobDetails;


    @Test
    public void jobDetailsInputIsValid() {
        int uniqueId = 1;
        String title = "Developer";
        String company = "Google";
        String city = "Boston";
        String state = "Massachussets";
        double salary = 200000;
        double bonus = 4000;
        int leaveTime = 20;
        int noOfShares = 200;
        double homeBuyingFund = 500000;
        double wellnessFund = 5000;
        jobDetails = new JobDetails(uniqueId, title, company, city, state, salary, bonus,leaveTime,noOfShares,homeBuyingFund,wellnessFund);
        assertEquals(true, jobDetails.validateFields());
    }

    @Test
    public void jobDetailsInputIsNotValid() {
        int uniqueId = 1;
        String title = "Developer";
        String company = "Google";
        String city = "Boston";
        String state = "";
        double salary = 0;
        double bonus = 4000;
        int leaveTime = 0;
        int noOfShares = 200;
        double homeBuyingFund = 500000;
        double wellnessFund = 5000;
        jobDetails = new JobDetails(uniqueId, title, company, city, state, salary, bonus,leaveTime,noOfShares,homeBuyingFund,wellnessFund);
        assertEquals(false, jobDetails.validateFields());
    }

    @Test
    public void homeBuyingFundIsValid() {
        int uniqueId = 1;
        String title = "Developer";
        String company = "Google";
        String city = "San Diego";
        String state = "California";
        double salary = 200000;
        double bonus = 4000;
        int leaveTime = 30;
        int noOfShares = 200;
        double homeBuyingFund = 2500;
        double wellnessFund = 5000;
        jobDetails = new JobDetails(uniqueId, title, company, city, state, salary, bonus,leaveTime,noOfShares,homeBuyingFund,wellnessFund);
        assertEquals(true, jobDetails.validateHomeBuyingFund());
    }

    @Test
    public void wellnessFundIsValid() {
        int uniqueId = 1;
        String title = "Developer";
        String company = "Google";
        String city = "San Diego";
        String state = "California";
        double salary = 200000;
        double bonus = 4000;
        int leaveTime = 30;
        int noOfShares = 200;
        double homeBuyingFund = 2500;
        double wellnessFund = 4000;
        jobDetails = new JobDetails(uniqueId, title, company, city, state, salary, bonus,leaveTime,noOfShares,homeBuyingFund,wellnessFund);
        assertEquals(true, jobDetails.validateWellnessFund());
    }

}
