
package id.taryntang.seclass.jobcompare;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import id.taryntang.seclass.jobcompare.models.Job;
import id.taryntang.seclass.jobcompare.models.JobComparison;
import id.taryntang.seclass.jobcompare.models.JobDetails;
import id.taryntang.seclass.jobcompare.models.JobSettings;

@RunWith(JUnit4.class)
public class JobComparisonTest {

    private JobDetails jobDetails1;
    private JobSettings jobSettings;
    private JobComparison jobComparison = new JobComparison();


    // Get instance of computeJobRank() by putting f(x) into JobComparison.java
    @Test
    public void testJobRankComputation() {

        int salaryWeight = 2;
        int bonusWeight = 2;
        int vacationWeight = 2;
        int sharesWeight = 2;
        int homeFundWeight = 4;
        int wellnessFundWeight = 5;

        jobSettings = new JobSettings(salaryWeight, bonusWeight, vacationWeight, sharesWeight, homeFundWeight, wellnessFundWeight);

        int uniqueId = 10;
        String title = "Developer";
        String company = "Amazon";
        String city = "San Jose";
        String state = "California";
        double salary = 100000;
        double bonus = 3000;
        int leaveTime = 10;
        int noOfShares = 100;
        double homeBuyingFund = 10000;
        double wellnessFund = 10000;

        jobDetails1 = new JobDetails(uniqueId, title, company, city, state, salary, bonus,leaveTime,noOfShares,homeBuyingFund,wellnessFund);

        int jobRank = jobComparison.computeJobRank(jobDetails1, jobSettings);

        assertEquals(17870, jobRank);



    }

}


