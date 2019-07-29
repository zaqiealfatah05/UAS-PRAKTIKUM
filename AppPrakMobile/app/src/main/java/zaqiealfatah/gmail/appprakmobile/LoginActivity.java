package zaqiealfatah.gmail.appprakmobile;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    //mendeklarasikan  EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //mendeklarasikan TextInputLayout
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration Button
    Button buttonLogin;

    //mendeklarasikan database  SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        initViews();

        //menyambungkan button click ke layout
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validasi untuk text email dan password
                if (validate()) {

                    //mengambil data email dan password untuk login pada aplikasi
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //validasi pada database
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));
                    // kondisi apabila button login ditekan akan sukses
                    if (currentUser != null) {
                        Snackbar.make(buttonLogin, "Login Sukses", Snackbar.LENGTH_LONG).show();

                        //login user pada login activity
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        // kondisi apabila login tidak sukses maka akan disuruh untuk memasukkan email dan password
                    } else {

                        Snackbar.make(buttonLogin, "Login Tidak BErhasil, Silhakan Ulangi Lagi", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public boolean validate() {
        boolean valid = false;
// mengambil vallue pada emai dan password
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Harus Masukkan Email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Harus Masukkan Password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password sangat Pendek!");
            }
        }

        return valid;
    }


}