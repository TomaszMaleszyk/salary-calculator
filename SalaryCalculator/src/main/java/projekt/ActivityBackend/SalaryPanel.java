package projekt.ActivityBackend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SalaryPanel extends AppCompatActivity {

    Button button_calculateSalary, button_goToShop;
    TextView txtView_hoursOfWorkMonday, txtView_hoursOfWorkTuesday, txtView_hoursOfWorkWednesday,
            txtView_hoursOfWorkThursday, txtView_hoursOfWorkFriday, txtView_hoursOfWorkSaturday;
    Switch switch_modeOfCalculation;
    EditText editText_paymentPerHour;
    SeekBar seekBar_mondaySalary, seekBar_tuesdaySalary, seekBar_wednesdaySalary, seekBar_thursdaySalary,
            seekBar_fridaySalary, seekBar_saturdaySalary;
    int valueOfEarnings = 0, valueOfPaymentPerHour = 0;
    String salaryPerWeek;
    Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        setTitle("Oblicz tygodniówkę");
        FindViewOfComponents();
        SetHoursOfWork();

        contextOfApplication = getApplicationContext();
    }

    private void FindViewOfComponents() {
        button_calculateSalary = (Button) findViewById(R.id.btn_calculateSalary);
        button_goToShop = (Button) findViewById(R.id.btn_goToShop);
        txtView_hoursOfWorkMonday = (TextView) findViewById(R.id.txt_hoursOfWork1);
        txtView_hoursOfWorkTuesday = (TextView) findViewById(R.id.txt_hoursOfWork2);
        txtView_hoursOfWorkWednesday = (TextView) findViewById(R.id.txt_hoursOfWork3);
        txtView_hoursOfWorkThursday = (TextView) findViewById(R.id.txt_hoursOfWork4);
        txtView_hoursOfWorkFriday = (TextView) findViewById(R.id.txt_hoursOfWork5);
        txtView_hoursOfWorkSaturday = (TextView) findViewById(R.id.txt_hoursOfWork6);
        editText_paymentPerHour = (EditText) findViewById(R.id.editTxt_paymentPerHour);
        seekBar_mondaySalary = (SeekBar) findViewById(R.id.seekBar_MondaySalary);
        seekBar_tuesdaySalary = (SeekBar) findViewById(R.id.seekBar_TuesdaySalary);
        seekBar_wednesdaySalary = (SeekBar) findViewById(R.id.seekBar_WednesdaySalary);
        seekBar_thursdaySalary = (SeekBar) findViewById(R.id.seekBar_ThursdaySalary);
        seekBar_fridaySalary = (SeekBar) findViewById(R.id.seekBar_FridaySalary);
        seekBar_saturdaySalary = (SeekBar) findViewById(R.id.seekBar_SaturdaySalary);
        switch_modeOfCalculation = (Switch) findViewById(R.id.switch_modeOfCalculation);
    }

    private void SetHoursOfWork() {
        txtView_hoursOfWorkMonday.setText("Pracowałeś : " + seekBar_mondaySalary.getProgress() + " h / "
                + seekBar_mondaySalary.getMax() + " h");
        txtView_hoursOfWorkTuesday.setText("Pracowałeś : " + seekBar_tuesdaySalary.getProgress() + " h / "
                + seekBar_tuesdaySalary.getMax() + " h");
        txtView_hoursOfWorkWednesday.setText("Pracowałeś : " + seekBar_wednesdaySalary.getProgress() + " h / "
                + seekBar_wednesdaySalary.getMax() + " h");
        txtView_hoursOfWorkThursday.setText("Pracowałeś : " + seekBar_thursdaySalary.getProgress() + " h / "
                + seekBar_thursdaySalary.getMax() + " h");
        txtView_hoursOfWorkFriday.setText("Pracowałeś : " + seekBar_fridaySalary.getProgress() + " h / "
                + seekBar_fridaySalary.getMax() + " h");
        txtView_hoursOfWorkSaturday.setText("Pracowałeś : " + seekBar_saturdaySalary.getProgress() + " h / "
                + seekBar_saturdaySalary.getMax() + " h");

        seekBar_mondaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkMonday.setText("Pracowałeś : " + progress + " h / "
                                + SalaryPanel.this.seekBar_mondaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= SalaryPanel.this.seekBar_mondaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += SalaryPanel.this.seekBar_mondaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seekBar_tuesdaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkTuesday.setText("Pracowałeś : " + progress + " h / "
                                + seekBar_tuesdaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= seekBar_tuesdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += seekBar_tuesdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seekBar_wednesdaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkWednesday.setText("Pracowałeś : " + progress + " h / "
                                + seekBar_wednesdaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= seekBar_wednesdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += seekBar_wednesdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seekBar_thursdaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkThursday.setText("Pracowałeś : " + progress + " h / "
                                + seekBar_thursdaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= seekBar_thursdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += seekBar_thursdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seekBar_fridaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkFriday.setText("Pracowałeś : "
                                + progress + " h / " + seekBar_fridaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= seekBar_fridaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += seekBar_fridaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seekBar_saturdaySalary.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtView_hoursOfWorkSaturday.setText("Pracowałeś : " + progress + " h / "
                                + seekBar_saturdaySalary.getMax() + " h");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings -= seekBar_saturdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        valueOfEarnings += seekBar_saturdaySalary.getProgress()
                                * Integer.parseInt(editText_paymentPerHour.getText().toString());
                        if (switch_modeOfCalculation.isChecked() == true) {
                            Toast.makeText(contextOfApplication, Integer.toString(valueOfEarnings), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void CalculateSalary(View view) {
        GetValueOfEarningsFromSeekBars();

        salaryPerWeek = Integer.toString(valueOfEarnings * valueOfPaymentPerHour);

        ShowWeekSalaryAlertDialog();

        button_goToShop.setEnabled(true);
    }

    private void GetValueOfEarningsFromSeekBars() {
        valueOfEarnings = seekBar_mondaySalary.getProgress() + seekBar_tuesdaySalary.getProgress()
                + seekBar_wednesdaySalary.getProgress() + seekBar_thursdaySalary.getProgress()
                + seekBar_fridaySalary.getProgress() + seekBar_saturdaySalary.getProgress();
        valueOfPaymentPerHour = Integer.parseInt(editText_paymentPerHour.getText().toString());
    }

    private void ShowWeekSalaryAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("W tym tygodniu zarobiłeś: " + salaryPerWeek + " PLN!!!");
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void GoToShop(View view) {
        Intent intent = new Intent(this, ShopPanel.class);
        intent.putExtra("val1", salaryPerWeek);
        startActivity(intent);
    }
}
