package id.taryntang.seclass.jobcompare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;
import id.taryntang.seclass.jobcompare.models.JobDetails;
import id.taryntang.seclass.jobcompare.models.JobSettings;


public class AdjustComparisonActivity extends AppCompatActivity {
    private EditText salaryFactorInput;
    private EditText bonusFactorInput;
    private EditText vacationFactorInput;
    private EditText sharesFactorInput;
    private EditText homefundFactorInput;
    private EditText wellnessFactorInput;
    private Button saveAdjustFactorButton;
    private DBSQLiteHelper adjustComparisonDB;
    private int[] jobSettings;
    private List<JobDetails> jobSettingsFromDBList = new ArrayList<>();
    private JobSettings jobSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustcomparison);

        DBSQLiteHelper dbHelper = new DBSQLiteHelper(AdjustComparisonActivity.this);

        jobSettings = dbHelper.getJobSetting();
        jobSettingsFromDBList = dbHelper.getJobDetails();
        salaryFactorInput = (EditText) findViewById(R.id.yearlySalary);
        bonusFactorInput = (EditText) findViewById(R.id.yearlyBonus);
        vacationFactorInput =(EditText) findViewById(R.id.leaveTime);
        sharesFactorInput = (EditText) findViewById(R.id.numShares);
        homefundFactorInput = (EditText) findViewById(R.id.homeBuyingFund);
        wellnessFactorInput = (EditText) findViewById(R.id.wellnessFund);
        saveAdjustFactorButton = (Button) findViewById(R.id.saveComparison);

        //read value from db
        salaryFactorInput.setText(Integer.toString(jobSettings[0]));
        bonusFactorInput.setText(Integer.toString(jobSettings[1]));
        vacationFactorInput.setText(Integer.toString(jobSettings[2]));
        sharesFactorInput.setText(Integer.toString(jobSettings[3]));
        homefundFactorInput.setText(Integer.toString(jobSettings[4]));
        wellnessFactorInput.setText(Integer.toString(jobSettings[5]));

        //create a new DB class and pass the contents into it
        adjustComparisonDB = new DBSQLiteHelper(AdjustComparisonActivity.this);

        // below line is to add on click listener for save adjust factor
        saveAdjustFactorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String salaryFactor = salaryFactorInput.getText().toString();
                String bonusFactor = bonusFactorInput.getText().toString();
                String vacationFactor = vacationFactorInput.getText().toString();
                String sharesFactor = sharesFactorInput.getText().toString();
                String homefundFactor = homefundFactorInput.getText().toString();
                String wellnessfundFactor = wellnessFactorInput.getText().toString();


                try{
                    jobSetting = new JobSettings(Integer.valueOf(salaryFactor),
                            Integer.valueOf(bonusFactor),
                            Integer.valueOf(vacationFactor),
                            Integer.valueOf(sharesFactor),
                            Integer.valueOf(homefundFactor),
                            Integer.valueOf(wellnessfundFactor));
                } catch(Exception e) {
                    Toast.makeText(AdjustComparisonActivity.this,
                            "Invalid weight entry is not saved. Please update!", Toast.LENGTH_SHORT).show();
                }

                if (jobSetting.validateFields()&&isInteger(salaryFactor)&&isInteger(bonusFactor)&&
                        isInteger(vacationFactor)&&isInteger(sharesFactor)&&
                        isInteger(homefundFactor)&&isInteger(wellnessfundFactor)) {
                    // add new factors to sqlite data and pass all our values to it.
                    adjustComparisonDB.addFactor(salaryFactor, bonusFactor, vacationFactor,
                            sharesFactor, homefundFactor, wellnessfundFactor);

                    // display the factor has been added toast message
                    Toast.makeText(AdjustComparisonActivity.this, "Factors have been added.", Toast.LENGTH_SHORT).show();

                    salaryFactorInput.setText("");
                    bonusFactorInput.setText("");
                    vacationFactorInput.setText("");
                    sharesFactorInput.setText("");
                    homefundFactorInput.setText("");
                    wellnessFactorInput.setText("");

                    //automatically get back to main menu
                    finish();
                } else {
                    Toast.makeText(AdjustComparisonActivity.this, "Please enter valid field values", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public boolean isInteger(String factor){
        boolean val = factor.matches("[-+]?\\d*\\.?\\d+");
        return factor != null && val;
    }
}

