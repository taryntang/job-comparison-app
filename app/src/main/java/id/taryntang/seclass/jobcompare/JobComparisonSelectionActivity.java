package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import id.taryntang.seclass.jobcompare.adapters.CompareJobs_RecyclerViewAdapter;
import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;
import id.taryntang.seclass.jobcompare.models.Job;
import id.taryntang.seclass.jobcompare.models.JobDetails;
import id.taryntang.seclass.jobcompare.models.JobSettings;
import id.taryntang.seclass.jobcompare.models.JobToCompare;


public class JobComparisonSelectionActivity extends AppCompatActivity {

    ArrayList<JobToCompare> jobsToCompare = new ArrayList<>();
    List<JobDetails> jobDetails;
    List<JobSettings> jobSettings;
    RecyclerView recyclerView;
    CompareJobs_RecyclerViewAdapter adapter;
    Button compareJobsBtn;

    List<Job> jobsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* BEGIN CODE FROM (https://www.youtube.com/watch?v=Mc0XT58A1Z4&t=608s&ab_channel=PracticalCoding) */
        super.onCreate(savedInstanceState);
        setContentView((R.layout.job_comparison_selection));

        recyclerView = findViewById(R.id.mRecyclerView);

        setUpJobsToCompareModels();

        adapter = new CompareJobs_RecyclerViewAdapter(this, jobsToCompare);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* END CODE FROM (https://www.youtube.com/watch?v=Mc0XT58A1Z4&t=608s&ab_channel=PracticalCoding) */

        compareJobsBtn = findViewById(R.id.compareBtn);

    }


    public void goToMenu (View view){
        Intent intentMenu = new Intent (this, MainActivity.class);
        startActivity(intentMenu);
    }

    public void goToCompareJobs (View view){
        Intent intentCompareJobs = new Intent (this, JobComparisonActivity.class);

        Intent goBackToSelectionJobs = new Intent(this, JobComparisonSelectionActivity.class);

        // get data from selectAndCompareJobs
        List<Job> newJobsList = selectAndCompareJobs();

        if (newJobsList.size() != 2) {

            Toast.makeText(JobComparisonSelectionActivity.this, "Please select only 2 jobs to compare",
                    Toast.LENGTH_SHORT).show();

            startActivity(goBackToSelectionJobs);

        } else {

            Job job1 = newJobsList.get(0);
            Job job2 = newJobsList.get(1);

            intentCompareJobs.putExtra("job 1", job1);
            intentCompareJobs.putExtra("job 2", job2);

            startActivity(intentCompareJobs);
        }
    }

    private List<Job> selectAndCompareJobs() {
        // Obtain whichever checkbox is marked as selected

        List<Job> newJobsList = new ArrayList<Job>();

        for (int i=0; i<jobsToCompare.size(); i++) {
            // Capture user checking check box
            if (jobsToCompare.get(i).getChecked() == true) {
                // if checked, get the job
                JobToCompare jobToCompare = jobsToCompare.get(i);
                Job job = jobToCompare.getJob();
                newJobsList.add(job);

            }
        }

        return newJobsList;

    }

    private void getJobToCompare() {

        for (int i=0; i<jobDetails.size() ;i++) {

            JobDetails jobDetail = jobDetails.get(i);

            int jobRank = computeJobRank(jobDetail);

            DBSQLiteHelper dbHelper = new DBSQLiteHelper(JobComparisonSelectionActivity.this);
            jobsList = dbHelper.getJobs();

            Job jobToBeAdded = new Job(jobDetail.getUniqueId(), jobDetail, false);

            for (Job job : jobsList) {
                if (job.getUniqueID() == jobDetail.getUniqueId()) {
                    jobToBeAdded = new Job(jobDetail.getUniqueId(), jobDetail, job.isCurrentJob());
                    break;
                }

            }

            JobToCompare jobToCompare = new JobToCompare(
                    jobDetail.getTitle(),
                    jobDetail.getCompany(),
                    jobDetail.getUniqueId(),
                    jobRank,
                    jobToBeAdded

            );

            jobsToCompare.add(jobToCompare);

        }

    }

    private int computeJobRank(JobDetails jobDetail) {

        // num/den * AYS + num/den * AYB + num/den * (LT * AYS / 260) + num/den * (CSO/2) + num/den * HBP + num/den * WF
        JobSettings jobSetting = jobSettings.get(jobSettings.size()-1);
        int den = (int) (jobSetting.getSalaryWeight() + jobSetting.getBonusWeight() +
                        jobSetting.getVacationWeight() + jobSetting.getSharesWeight() +
                        jobSetting.getHomeFundWeight() +jobSetting.getWellnessFundWeight());

        double yearlySalaryWeight = (jobSetting.getSalaryWeight() / den);
        double yearlyBonusWeight = (jobSetting.getBonusWeight() / den);
        double leaveTimeWeight = (jobSetting.getVacationWeight() / den);
        double companySharesWeight = (jobSetting.getSharesWeight() / den);
        double homeWeight =  (jobSetting.getHomeFundWeight() / den);
        double wellnessWeight = (jobSetting.getWellnessFundWeight() / den);


        double jobRank = (yearlySalaryWeight * jobDetail.getSalary()) + (yearlyBonusWeight * jobDetail.getBonus())
                + leaveTimeWeight * (jobDetail.getLeaveTime() * (jobDetail.getSalary() / 260)) +
                companySharesWeight * (jobDetail.getNoOfShares() / 2) + homeWeight * jobDetail.getHomeBuyingFund() +
                wellnessWeight * jobDetail.getWellnessFund();

        return (int) Math.round(jobRank);
    }

    private void setUpJobsToCompareModels() {
        // Get data from database?
        DBSQLiteHelper dbHelper = new DBSQLiteHelper(JobComparisonSelectionActivity.this);
        jobDetails = dbHelper.getJobDetails();


        DBSQLiteHelper settingsDbHelper = new DBSQLiteHelper(JobComparisonSelectionActivity.this);
        jobSettings = settingsDbHelper.getComparisonSettings();

        // populate JobsToCompare arrayList
        getJobToCompare();

    }

}

