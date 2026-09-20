package id.taryntang.seclass.jobcompare;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.concurrent.TimeUnit;
import id.taryntang.seclass.jobcompare.models.JobDetails;
import id.taryntang.seclass.jobcompare.models.JobSettings;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class JobSettingsTest {
    private JobDetails jobDetails1;
    private JobDetails jobDetails2;
    private JobSettings jobSettings;

    @Test
    //Description: validate the job comparison setting weights input validation
    public void testJobSettingInputIsValid(){
        int salaryWeight = 2;
        int bonusWeight = 2;
        int vacationWeight = 2;
        int sharesWeight = 1;
        int homeFundWeight = 1;
        int wellnessFundWeight = 1;

        jobSettings = new JobSettings(salaryWeight, bonusWeight, vacationWeight, sharesWeight, homeFundWeight, wellnessFundWeight);
        assertEquals(true, jobSettings.validateFields());
    }

    @Test
    //Description: validate the job comparison setting weights input validation
    public void testJobSettingInputIsNotValid(){
        int salaryWeight = 0;
        int bonusWeight = 2;
        int vacationWeight = 2;
        int sharesWeight = 1;
        int homeFundWeight = 2;
        int wellnessFundWeight = 1;

        jobSettings = new JobSettings(salaryWeight, bonusWeight, vacationWeight, sharesWeight, homeFundWeight, wellnessFundWeight);
        assertEquals(false, jobSettings.validateFields());
    }

    @Test
    //Description: validate the job comparison setting weights input validation with string
    public void testJobSettingInputIsNumeric(){
        int salaryWeight = 1;
        int bonusWeight = 'j';
        int vacationWeight = 2;
        int sharesWeight = 1;
        int homeFundWeight = 2;
        int wellnessFundWeight = 1;

        jobSettings = new JobSettings(salaryWeight, bonusWeight, vacationWeight, sharesWeight, homeFundWeight, wellnessFundWeight);
        assertEquals(false, jobSettings.validateInteger());
    }

    @Test
    //Description: validate the job comparison setting weights input validation with string
    public void testJobSettingInputIsNumeric2(){
        int salaryWeight = 'c';
        int bonusWeight = 5;
        int vacationWeight = 2;
        int sharesWeight = 1;
        int homeFundWeight = 2;
        int wellnessFundWeight = 1;

        jobSettings = new JobSettings(salaryWeight, bonusWeight, vacationWeight, sharesWeight, homeFundWeight, wellnessFundWeight);
        assertEquals(false, jobSettings.validateInteger());
    }
    
}
