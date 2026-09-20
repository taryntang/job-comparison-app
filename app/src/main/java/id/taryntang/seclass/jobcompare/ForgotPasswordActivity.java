package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;

public class ForgotPasswordActivity extends AppCompatActivity {
    private String verifiedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        DBSQLiteHelper db = new DBSQLiteHelper(this);
        EditText userName = findViewById(R.id.resetusername);
        View resetSection = findViewById(R.id.resetsection);
        TextView questionText = findViewById(R.id.securityquestiontext);
        EditText answer = findViewById(R.id.resetanswer);
        EditText newPassword = findViewById(R.id.resetnewpassword);
        EditText confirm = findViewById(R.id.resetconfirmpassword);

        findViewById(R.id.findaccountbutton).setOnClickListener(view -> {
            String user = userName.getText().toString().trim();
            resetSection.setVisibility(View.GONE);
            verifiedUser = null;
            if (user.isEmpty()) {
                toast("Please enter your user name.");
            } else if (!db.usernameExist(user)) {
                toast("User name does not exist.");
            } else {
                String question = db.getSecurityQuestion(user);
                if (question == null) {
                    toast("This account has no security question, so its password can't be reset.");
                } else {
                    verifiedUser = user;
                    questionText.setText(question);
                    resetSection.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.resetpasswordbutton).setOnClickListener(view -> {
            String pass = newPassword.getText().toString();
            if (verifiedUser == null) {
                toast("Please enter your user name first.");
            } else if (answer.getText().toString().trim().isEmpty() || pass.isEmpty()) {
                toast("Please fill in all fields.");
            } else if (!pass.equals(confirm.getText().toString())) {
                toast("Passwords do not match.");
            } else if (!db.securityAnswerMatches(verifiedUser, answer.getText().toString())) {
                toast("Incorrect answer. Please try again.");
            } else if (db.updatePassword(verifiedUser, pass)) {
                toast("Password updated. Please log in.");
                finish();
            } else {
                toast("Could not update password. Please try again.");
            }
        });

        findViewById(R.id.resetbacktologinbutton).setOnClickListener(view -> finish());
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
