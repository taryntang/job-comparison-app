package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;
import id.taryntang.seclass.jobcompare.models.Job;
import id.taryntang.seclass.jobcompare.models.JobComparison;
import id.taryntang.seclass.jobcompare.models.JobSettings;

public class JobComparisonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_comparison);

        Intent i = getIntent();
        Job job1 = i.getParcelableExtra("job 1");
        Job job2 = i.getParcelableExtra("job 2");

        TextView jobTitle = findViewById(R.id.jobTitle1);
        TextView companyTitle = findViewById(R.id.companyTitle1);
        TextView locationTitle = findViewById(R.id.locationTitle1);
        TextView yearlySalary = findViewById(R.id.yearlyTitle1);
        TextView yearlyBonus = findViewById(R.id.bonusTitle1);
        TextView leaveTime = findViewById(R.id.leaveTimeTitle1);
        TextView numberOfShares = findViewById(R.id.sharesTitle1);
        TextView homeBuyingFund = findViewById(R.id.buyingFund1);
        TextView wellnessFund = findViewById(R.id.wellnessTitle1);

        TextView jobTitle2 = findViewById(R.id.jobTitle2);
        TextView companyTitle2 = findViewById(R.id.companyTitle2);
        TextView locationTitle2 = findViewById(R.id.locationTitle2);
        TextView yearlySalary2 = findViewById(R.id.yearlyTitle2);
        TextView yearlyBonus2 = findViewById(R.id.bonusTitle2);
        TextView leaveTime2 = findViewById(R.id.leaveTimeTitle2);
        TextView numberOfShares2 = findViewById(R.id.sharesTitle2);
        TextView homeBuyingFund2 = findViewById(R.id.buyingFund2);
        TextView wellnessFund2 = findViewById(R.id.wellnessTitle2);

        jobTitle.setText(job1.getJobDetails().getTitle());
        companyTitle.setText(job1.getJobDetails().getCompany());
        locationTitle.setText(job1.getJobDetails().getCity() + ", " + job1.getJobDetails().getState());
        yearlySalary.setText(String.valueOf(job1.getJobDetails().getSalary()));
        yearlyBonus.setText(String.valueOf(job1.getJobDetails().getBonus()));
        leaveTime.setText(String.valueOf(job1.getJobDetails().getLeaveTime()));
        numberOfShares.setText(String.valueOf(job1.getJobDetails().getNoOfShares()));
        homeBuyingFund.setText(String.valueOf(job1.getJobDetails().getHomeBuyingFund()));
        wellnessFund.setText(String.valueOf(job1.getJobDetails().getWellnessFund()));

        jobTitle2.setText(job2.getJobDetails().getTitle());
        companyTitle2.setText(job2.getJobDetails().getCompany());
        locationTitle2.setText(job2.getJobDetails().getCity() + ", " + job2.getJobDetails().getState());
        yearlySalary2.setText(String.valueOf(job2.getJobDetails().getSalary()));
        yearlyBonus2.setText(String.valueOf(job2.getJobDetails().getBonus()));
        leaveTime2.setText(String.valueOf(job2.getJobDetails().getLeaveTime()));
        numberOfShares2.setText(String.valueOf(job2.getJobDetails().getNoOfShares()));
        homeBuyingFund2.setText(String.valueOf(job2.getJobDetails().getHomeBuyingFund()));
        wellnessFund2.setText(String.valueOf(job2.getJobDetails().getWellnessFund()));

        // rank score, computed from the current comparison settings
        List<JobSettings> settings = new DBSQLiteHelper(this).getComparisonSettings();
        TextView rank1 = findViewById(R.id.rankTitle1);
        TextView rank2 = findViewById(R.id.rankTitle2);
        if (settings.isEmpty()) {
            rank1.setText("-");
            rank2.setText("-");
        } else {
            JobSettings setting = settings.get(settings.size() - 1);
            JobComparison comparison = new JobComparison();
            rank1.setText(String.valueOf(comparison.computeJobRank(job1.getJobDetails(), setting)));
            rank2.setText(String.valueOf(comparison.computeJobRank(job2.getJobDetails(), setting)));
        }


    }


    public void goToMenu (View view){
        Intent intentMenu = new Intent (this, MainActivity.class);
        startActivity(intentMenu);
    }

    public void goToCompareJobsSelection (View view){
        Intent intentCompareJobs = new Intent (this, JobComparisonSelectionActivity.class);
        startActivity(intentCompareJobs);
    }


}