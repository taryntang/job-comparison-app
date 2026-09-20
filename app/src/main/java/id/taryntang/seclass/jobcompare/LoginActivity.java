package id.taryntang.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import id.taryntang.seclass.jobcompare.dbhelpers.DBSQLiteHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private DBSQLiteHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.enterusername);
        password = findViewById(R.id.enterpassword);
        DB = new DBSQLiteHelper(this);

        findViewById(R.id.loginbutton).setOnClickListener(view -> {
            String user = userName.getText().toString().trim();
            String pass = password.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter your user name and password.", Toast.LENGTH_SHORT).show();
            } else if (DB.usernamePasswordExist(user, pass)) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (DB.usernameExist(user)) {
                Toast.makeText(this, "Password is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User name does not exist. Please create an account.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.signupbutton).setOnClickListener(view ->
                startActivity(new Intent(this, SignUpActivity.class)));

        findViewById(R.id.forgotpasswordbutton).setOnClickListener(view ->
                startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }
}
