package projekt.ActivityBackend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsPanel extends AppCompatActivity {

    Button button;
    RadioButton radioButton_setTheme1;
    RadioButton radioButton_setTheme2;
    RadioGroup themeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);

        FindViewOfComponents();
        SetListenerOfThemeRadioGroup();
    }

    private void SetListenerOfThemeRadioGroup() {
        themeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    setTheme(android.R.style.Theme);
                    setContentView(R.layout.activity_options);
                } else if (radioGroup.getCheckedRadioButtonId() == 0) {
                    setTheme(android.R.style.Theme_Black);
                    setContentView(R.layout.activity_options);
                }
            }
        });
    }

    private void FindViewOfComponents() {
        button = (Button) findViewById(R.id.button4);
        radioButton_setTheme1 = (RadioButton) findViewById(R.id.radioButton_setTheme1);
        radioButton_setTheme2 = (RadioButton) findViewById(R.id.radioButton_setTheme2);
        themeRadioGroup = (RadioGroup) findViewById(R.id.themeRadioGroup);
    }


    public void SetTheme(View v) {

    }
}
