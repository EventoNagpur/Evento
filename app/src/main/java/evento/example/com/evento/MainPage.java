package evento.example.com.evento;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainPage extends AppCompatActivity {
    TextView UserInfo;
    FirebaseAuth auth;
    EditText et1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        UserInfo = (TextView) findViewById(R.id.user_info);
        auth = FirebaseAuth.getInstance();
        FirebaseUser u = auth.getCurrentUser();
        String j = u.getDisplayName();
        j = j + "\nEMAIL:" + u.getEmail();
        j = j + "\nPROVIDER-ID:" + u.getProviderId();
        j = j + "\nUID:" + u.getUid();
        j = j + "\nURL:" + u.getPhotoUrl();
        UserInfo.setText(j);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(u.getUid()+"");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m1=getMenuInflater();
        m1.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_sign_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                Toast.makeText(getApplicationContext(),"SUCCESSFULLY SIGNED OUT",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainPage.this,MainActivity.class));
                                UserInfo.setText("SUCCESSFULLY SIGNED OUT");

                            }
                        });
            return true;
            case R.id.menu_delete_account:
                AuthUI.getInstance()
                        .delete(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"USER SUCCESSFULLY DELETED",Toast.LENGTH_LONG).show();
                                    UserInfo.setText("USER SUCCESSFULLY DELETED");
                                    startActivity(new Intent(MainPage.this,MainActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(),"SOME ERROR OCCURED WHILE DELETION",Toast.LENGTH_LONG).show();
                                    UserInfo.setText("SOME ERROR OCCURED WHILE DELETION");
                                }
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
