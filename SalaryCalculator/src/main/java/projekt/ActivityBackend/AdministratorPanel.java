package projekt.ActivityBackend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AdministratorPanel extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_productName;
    private EditText editText_productPrice;

    private Button btn_addProduct;
    private Button btn_viewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_panel);

        FindViewOfComponents();

        btn_addProduct.setOnClickListener(this);
        btn_viewProduct.setOnClickListener(this);
    }

    private void FindViewOfComponents() {
        editText_productName = (EditText) findViewById(R.id.editTxt_nameOfProduct);
        editText_productPrice = (EditText) findViewById(R.id.editTxt_priceOfProduct);

        btn_addProduct = (Button) findViewById(R.id.btn_addProduct);
        btn_viewProduct = (Button) findViewById(R.id.btn_viewProduct);
    }

    private void AddProduct() {

        final String name = editText_productName.getText().toString().trim();
        final String price = editText_productPrice.getText().toString().trim();

        class AddProduct extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AdministratorPanel.this, "Dodawanie...", "Czekaj...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AdministratorPanel.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(ConfigConnection.KEY_PRD_NAME, name);
                params.put(ConfigConnection.KEY_PRD_PRC, price);

                RequestHandler requestHandler = new RequestHandler();
                String res = requestHandler.sendPostRequest(ConfigConnection.URL_ADD, params);
                return res;
            }
        }

        AddProduct ap = new AddProduct();
        ap.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_addProduct) {
            AddProduct();
        }

        if (view == btn_viewProduct) {
            startActivity(new Intent(this, AdminAllProductsPanel.class));
        }
    }
}