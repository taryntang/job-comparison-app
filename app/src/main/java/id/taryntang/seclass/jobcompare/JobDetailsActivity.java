package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.taryntang.seclass.jobcompare.adapters.JobDetails_RecyclerviewAdapter;
import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;
import id.taryntang.seclass.jobcompare.models.Job;
import id.taryntang.seclass.jobcompare.models.JobDetails;

public class JobDetailsActivity extends AppCompatActivity {

    ArrayList<JobDetails> jobDetailsList = new ArrayList<>();
    List<Job> jobsList = new ArrayList<>();
    List<JobDetails> jobDetailsFromDBList = new ArrayList<>();
    String[] jobDetails;
    Job currentJob;
    Job currentJobFromDB;
    Job newJobOffer;
    boolean isJobSaved;
    Button saveBtn, cancelBtn;
    TextView enterOfferBtn, returnToMainBtn, compareWithCurrentJobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        jobDetails = getResources().getStringArray(R.array.job_details_fields);

        saveBtn = findViewById(R.id.save_btn);
        cancelBtn = findViewById(R.id.cancelBtn);
        enterOfferBtn = findViewById(R.id.enterOffer_Btn);
        returnToMainBtn = findViewById(R.id.returnToMain_Btn);
        compareWithCurrentJobBtn = findViewById(R.id.compareJobs_Btn);

        Intent intent = getIntent();
        boolean isUserEnteringCurrentJob = intent.getBooleanExtra("isCurrentJob", false);
        enableCompareWithCurrentJobAndEnterNewOfferBtns(isUserEnteringCurrentJob);

        RecyclerView recyclerView = findViewById(R.id.jobDetails_recyclerView);
        JobDetails_RecyclerviewAdapter adapter = new JobDetails_RecyclerviewAdapter(this, jobDetailsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!jobsList.isEmpty()) {
            for (Job job : jobsList) {
                if (job.isCurrentJob() && isUserEnteringCurrentJob) {
                    currentJob = job;
                    for (JobDetails jobDetail : jobDetailsFromDBList) {
                        if (jobDetail.getUniqueId() == job.getUniqueID()) {
                            // populate recyclerview
                            jobDetailsList.add(jobDetail);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<>();
                JobDetails jobDetails;
                try {
                    for (int i = 0; i < JobDetailsActivity.this.jobDetails.length; i++) {
                        View jobDetailRow = recyclerView.getChildAt(i);
                        EditText editText = jobDetailRow.findViewById(R.id.enterTitleTextViewId);
                        String detailVal = editText.getText().toString();
                        list.add(detailVal);
                    }
                    jobDetails = new JobDetails(1,
                            list.get(0),
                            list.get(1),
                            list.get(2),
                            list.get(3),
                            Double.parseDouble(list.get(4)),
                            Double.parseDouble(list.get(5)),
                            Integer.parseInt(list.get(6)),
                            Integer.parseInt(list.get(7)),
                            Double.parseDouble(list.get(8)),
                            Double.parseDouble(list.get(9)));
                } catch (Exception e) {
                    jobDetails = new JobDetails(1,
                            "",
                            "",
                            "",
                            "",
                            0.0,
                            0.0,
                            0,
                            0,
                            0,
                            0);
                }
                boolean fieldsAreValid = jobDetails.validateFields();
                if (fieldsAreValid) {
                    boolean homeFundIsValid = jobDetails.validateHomeBuyingFund();
                    boolean wellnessFundIsValid = jobDetails.validateWellnessFund();
                    if (!homeFundIsValid) {
                        Toast.makeText(JobDetailsActivity.this, "Please enter valid home buying fund less than or equal to 15% of yearly salary", Toast.LENGTH_SHORT).show();
                    } else if (!wellnessFundIsValid) {
                        Toast.makeText(JobDetailsActivity.this, "Please enter valid wellness fund that is $0 to $5000 inclusive", Toast.LENGTH_SHORT).show();
                    } else if (currentJob != null && isUserEnteringCurrentJob){
                        DBSQLiteHelper sqLiteHelper = new DBSQLiteHelper(JobDetailsActivity.this);
                        sqLiteHelper.updateJobDetails(jobDetails);
                    } else {
                        DBSQLiteHelper sqLiteHelper = new DBSQLiteHelper(JobDetailsActivity.this);
                        sqLiteHelper.addJobDetail(jobDetails);
                        boolean isUserEnteringCurrentJob = intent.getBooleanExtra("isCurrentJob", false);
                        Job job = new Job(jobDetails.getUniqueId(), jobDetails, isUserEnteringCurrentJob);
                        isJobSaved = sqLiteHelper.addJob(job);
                        newJobOffer = job;
                        Toast.makeText(JobDetailsActivity.this, "Job saved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobDetailsActivity.this, "Please enter valid field values", Toast.LENGTH_SHORT).show();

                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (enterOfferBtn.isClickable()) {
            enterOfferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobDetailsList = new ArrayList<>();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(JobDetailsActivity.this, "Fields are cleared. Please enter the new offer details", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (compareWithCurrentJobBtn.isClickable()) {
            compareWithCurrentJobBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isJobSaved) {
                        for (JobDetails jobDetail : jobDetailsFromDBList) {
                            if (jobDetail.getUniqueId() == currentJobFromDB.getUniqueID()) {
                                currentJobFromDB.setJobDetails(jobDetail);
                                break;
                            }
                        }
                        Intent intentToCompare = new Intent(JobDetailsActivity.this, JobComparisonActivity.class);
                        intentToCompare.putExtra("job 1", currentJobFromDB);
                        intentToCompare.putExtra("job 2", newJobOffer);
                        startActivity(intentToCompare);
                    } else {
                        Toast.makeText(JobDetailsActivity.this, "Please save the entered job offer before comparing with current job", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        returnToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void enableCompareWithCurrentJobAndEnterNewOfferBtns(boolean userEnteringCurrentJob) {

        DBSQLiteHelper dbHelper = new DBSQLiteHelper(JobDetailsActivity.this);
        jobsList = dbHelper.getJobs();
        jobDetailsFromDBList = dbHelper.getJobDetails();
        if (!userEnteringCurrentJob) {
            enterOfferBtn.setTextColor(Color.parseColor("#1B365D"));
            enterOfferBtn.setClickable(true);
        }
        if (!jobsList.isEmpty()) {
            for (Job job: jobsList) {
                if (job.isCurrentJob() && !userEnteringCurrentJob) {
                    currentJobFromDB = job;
                    compareWithCurrentJobBtn.setTextColor(Color.parseColor("#1B365D"));
                    compareWithCurrentJobBtn.setClickable(true);
                    break;
                }
            }
        }
    }

    private void setUpJobDetailsModels() {
        jobDetails = getResources().getStringArray(R.array.job_details_fields);
    }
}