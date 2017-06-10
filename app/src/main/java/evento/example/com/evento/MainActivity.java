package evento.example.com.evento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button SignIn;
    TextView UserInfo;
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainPage.class));
        } else {
            setContentView(R.layout.activity_main);
            SignIn = (Button) findViewById(R.id.sign_in);
            SignIn.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                startActivityForResult(
                        // Get an instance of AuthUI based on the default app
                        AuthUI.getInstance().
                                createSignInIntentBuilder().
                                setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())).
                                setTosUrl("https://superapp.example.com/terms-of-service.html").
                                build(),
                        RC_SIGN_IN);
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(getApplicationContext(), "SUCCESSFULLY SIGNED IN", Toast.LENGTH_LONG).show();

                final FirebaseUser firebaseUser=auth.getCurrentUser();
                final String uid = firebaseUser.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference();
                myRef.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplication(),"Three",Toast.LENGTH_LONG).show();
                        if(dataSnapshot.exists())
                        {Toast.makeText(getApplication(),"Two",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, MainPage.class));
                        }
                            else{
                            Toast.makeText(getApplication(),"One",Toast.LENGTH_LONG).show();
                            //CODE FOR ALERT DIALOG
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Select DOB and Gender");

                            // Set up the input
                            Context context = MainActivity.this;
                            LinearLayout layout = new LinearLayout(context);
                            layout.setOrientation(LinearLayout.VERTICAL);

                            final EditText input = new EditText(context);
                            input.setHint("Gender(Male:0 Female:1)");
                            layout.addView(input);

                            final EditText day = new EditText(context);
                            day.setHint("Date of Birth(1-31)");
                            layout.addView(day);

                            final EditText month = new EditText(context);
                            month.setHint("Month of Birth(1-12)");
                            layout.addView(month);

                            final EditText year = new EditText(context);
                            year.setHint("Year of Birth(1930-2016)");
                            layout.addView(year);

                            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            //  input.setInputType(InputType.TYPE_CLASS_DATETIME);
                            builder.setView(layout);

                            // Set up the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int gender;
                                    String name,email,url;
                                    Taareekh dob;

                                    gender = Integer.parseInt(input.getText().toString());
                                    dob=new Taareekh(Integer.parseInt(day.getText().toString()),
                                            Integer.parseInt(month.getText().toString()),
                                            Integer.parseInt(year.getText().toString()));
                                    name=firebaseUser.getDisplayName();
                                    email=firebaseUser.getEmail();
                                    url=firebaseUser.getPhotoUrl().toString();
                                    User current=new User(name,email,uid,url,gender,dob);
                                    myRef.child("users").child(uid).setValue(current);
                                    startActivity(new Intent(MainActivity.this, MainPage.class));

                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getApplicationContext(), "SIGN IN CANCELLED", Toast.LENGTH_LONG).show();
                    UserInfo.setText("SIGN IN CANCELLED");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "NETWORK IS DOWN", Toast.LENGTH_LONG).show();
                    UserInfo.setText("NETWORK IS DOWN");

                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), "SOME ERROR OCCURED", Toast.LENGTH_LONG).show();
                    UserInfo.setText("SOME ERROR OCCURED");
                    return;
                }
            }

        }
    }


}


