package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DBSQLiteHelper db = new DBSQLiteHelper(this);
        EditText userName = findViewById(R.id.signupusername);
        EditText password = findViewById(R.id.signuppassword);
        EditText confirm = findViewById(R.id.signupconfirmpassword);
        EditText answer = findViewById(R.id.securityanswer);
        Spinner questions = findViewById(R.id.securityquestionspinner);
        questions.setAdapter(ArrayAdapter.createFromResource(this, R.array.security_questions,
                android.R.layout.simple_spinner_dropdown_item));

        findViewById(R.id.createaccountbutton).setOnClickListener(view -> {
            String user = userName.getText().toString().trim();
            String pass = password.getText().toString();
            String ans = answer.getText().toString().trim();
            if (user.isEmpty() || pass.isEmpty() || ans.isEmpty()) {
                toast("Please fill in all fields.");
            } else if (!pass.equals(confirm.getText().toString())) {
                toast("Passwords do not match.");
            } else if (db.usernameExist(user)) {
                toast("That user name is taken. Please choose another or log in.");
            } else if (db.userSignup(user, pass, (String) questions.getSelectedItem(), ans)) {
                toast("Account created. Please log in.");
                finish();
            } else {
                toast("Could not create account. Please try again.");
            }
        });

        findViewById(R.id.backtologinbutton).setOnClickListener(view -> finish());
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
