package projekt.ActivityBackend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle("Kalkulator");
    }

    public void ShowSalaryPanel(View view) {
        Intent newActivity = new Intent(getApplicationContext(), SalaryPanel.class);
        startActivity(newActivity);
    }

    public void ShowAlertDialog(View view) {
        AlertDialog.Builder communique = new AlertDialog.Builder(this);
        communique.setMessage("Panel w budowie...");
        communique.setCancelable(true);
        communique.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = communique.create();
        alertDialog.show();
    }

    public void ShowAdministratorPanel(View v) {
        Intent newActivity = new Intent(getApplicationContext(), AdministratorPanel.class);
        startActivity(newActivity);
    }

}
