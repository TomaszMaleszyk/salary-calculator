package projekt.ActivityBackend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopPanel extends AppCompatActivity {
    private HashMap<String, String> hashMapProductsAndPrice = new HashMap<>();
    private double priceOfProduct;
    private double quantityOfProduct;
    private double avaibleMoneyToSpend;
    private double costOfShopping;
    private String JSON_STRING;

    private Spinner spinner_ChoiceOfProducts;
    private TextView txt_actualMoneyOnAccountValue, txt_costOfShopping, txt_moneyOnAccountValue;
    private EditText EditText_weightOfProduct;
    private ProgressBar ProgressBar_valueOfAccountCondition;
    private Context applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_panel);
        setTitle("Kalkulator wydatków");
        applicationContext = getApplicationContext();
        FindViewOfComponents();
        SetTextOf_txt_actualMoneyOnAccountValue();
        SetSpinnerListener();
        GetJSON();
    }

    private void FindViewOfComponents() {
        txt_actualMoneyOnAccountValue = (TextView) findViewById(R.id.txt_valueOfAccountBallance);
        txt_costOfShopping = (TextView) findViewById(R.id.txt_shoppingCost);
        txt_moneyOnAccountValue = (TextView) findViewById(R.id.textView_stanKontaProcent);

        ProgressBar_valueOfAccountCondition = (ProgressBar) findViewById(R.id.progressBar_valueOfAccountCondition);
        spinner_ChoiceOfProducts = (Spinner) findViewById(R.id.spinner_choiceProduct);
        EditText_weightOfProduct = (EditText) findViewById(R.id.editText_WagaProduktu);
    }

    private void SetSpinnerListener() {
        spinner_ChoiceOfProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
                Toast.makeText(applicationContext, hashMapProductsAndPrice
                        .get(spinner_ChoiceOfProducts.getSelectedItem().toString())
                        + " za kilogram", Toast.LENGTH_SHORT).show();
                priceOfProduct = Double.parseDouble(hashMapProductsAndPrice
                        .get(spinner_ChoiceOfProducts.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void SetTextOf_txt_actualMoneyOnAccountValue() {
        Intent intent = getIntent();
        String transferedData = intent.getStringExtra("val1");
        avaibleMoneyToSpend = Double.parseDouble(transferedData);
        txt_actualMoneyOnAccountValue.setText(transferedData);
    }

    private void GetJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShopPanel.this, "Wczytywanie danych", "Prosze czekac...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                ShowProducts();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(ConfigConnection.URL_GET_ALL);
                return s;
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void ShowProducts() {
        List<String> listOfProducts = ImportProductListFromServer();

        if (!listOfProducts.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfProducts);
            spinner_ChoiceOfProducts.setAdapter(adapter);
        }
    }

    private List<String> ImportProductListFromServer() {
        List<String> productList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(ConfigConnection.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(ConfigConnection.TAG_NAME);
                String price = jo.getString(ConfigConnection.TAG_PRC);

                hashMapProductsAndPrice.put(name, price);
                productList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void CalculateSalary(View v) {
        String getQuantityOfProductFromInputData = EditText_weightOfProduct.getText().toString();
        if (getQuantityOfProductFromInputData != "") {
            quantityOfProduct = Double.parseDouble(getQuantityOfProductFromInputData);

            costOfShopping = priceOfProduct * quantityOfProduct;
            txt_costOfShopping.setText(Double.toString(costOfShopping));

            if (costOfShopping > avaibleMoneyToSpend) {
                lowResourcesAlarm();
                ProgressBar_valueOfAccountCondition.setProgress(0);
                txt_moneyOnAccountValue.setText(" ");
            } else {
                ProgressBar_valueOfAccountCondition.setMax((int) avaibleMoneyToSpend);
                ProgressBar_valueOfAccountCondition.setProgress((int) costOfShopping);
                double procent = costOfShopping / avaibleMoneyToSpend * 100;
                txt_moneyOnAccountValue.setText("Wydasz: " + Double.toString(Math.round(procent)) + " % zarobionej kwoty");
            }
        } else {
            SetQuantityOfProduct();
            ProgressBar_valueOfAccountCondition.setProgress(0);
            txt_moneyOnAccountValue.setText(" ");
        }
    }

    private void lowResourcesAlarm() {
        AlertDialog.Builder communique = new AlertDialog.Builder(this);
        communique.setMessage("Nie posiada Pan odpowiedniej ilosci środków finansowych, aby zrealizować transakcje.");
        communique.setCancelable(true);

        communique.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txt_costOfShopping.setText("0");
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = communique.create();
        alertDialog.show();
    }

    private void SetQuantityOfProduct() {
        AlertDialog.Builder communique = new AlertDialog.Builder(this);
        communique.setMessage("Proszę wpisać ilość wybranego towaru.");
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
}