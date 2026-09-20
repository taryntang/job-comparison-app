package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;
import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;
import id.taryntang.seclass.jobcompare.models.JobDetails;


public class MainActivity extends AppCompatActivity {

    private Button enterCurrentJobButton;
    private Button enterJobOfferButton;
    private Button adjustComparisonSettingsButton;
    private Button compareJobOffersButton;
    private Button logoutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterCurrentJobButton = (Button) findViewById(R.id.entercurrentjobbutton);
        enterCurrentJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JobDetailsActivity.class);
                intent.putExtra("isCurrentJob", true);
                startActivity(intent);
            }
        });
        enterJobOfferButton = (Button) findViewById(R.id.enterjoboffersbutton);
        enterJobOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JobDetailsActivity.class);
                intent.putExtra("isCurrentJob", false);
                startActivity(intent);
            }
        });
        adjustComparisonSettingsButton = (Button) findViewById(R.id.adjustcomparisonsettingsbutton);
        adjustComparisonSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdjustComparisonActivity.class);
                startActivity(intent);
            }
        });
        compareJobOffersButton = (Button) findViewById(R.id.comparejoboffersbutton);
        compareJobOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBSQLiteHelper dbHelper = new DBSQLiteHelper(MainActivity.this);
                List<JobDetails> jobDetails = dbHelper.getJobDetails();
                //Toast.makeText(MainActivity.this, String.valueOf(jobDetails.size()), Toast.LENGTH_SHORT).show();
                if(jobDetails.size() >= 2)
                {
                    Intent intent = new Intent(MainActivity.this, JobComparisonSelectionActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Total number of job offers is less than 2.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logoutButton = (Button) findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        DBSQLiteHelper settingsDbHelper = new DBSQLiteHelper(MainActivity.this);
        settingsDbHelper.addFactor("1","1","1","1","1", "1");
    }




}