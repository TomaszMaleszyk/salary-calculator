package projekt.ActivityBackend;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminAllProductsPanel extends AppCompatActivity {

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_products);
        listView = (ListView) findViewById(R.id.listOfProducts);
        GetJSON();
    }

    public void ShowProducts() {
        List<String> productList = new ArrayList<>();
        try {
            productList = ImportProductListFromServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!productList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productList);
            listView.setAdapter(adapter);
        }
    }

    private List<String> ImportProductListFromServer() throws JSONException {
        List<String> productsList = new ArrayList<>();
        JSONObject jsonObject;
        jsonObject = new JSONObject(JSON_STRING);
        JSONArray result = jsonObject.getJSONArray(ConfigConnection.TAG_JSON_ARRAY);

        for (int resultRow = 0; resultRow < result.length(); resultRow++) {
            JSONObject jo = result.getJSONObject(resultRow);
            String name = jo.getString(ConfigConnection.TAG_NAME);
            String price = jo.getString(ConfigConnection.TAG_PRC);

            productsList.add(resultRow + " Produkt: " + name + " Cena: " + price + " zl");
        }
        return productsList;
    }

    private void GetJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AdminAllProductsPanel.this, "Wczytywanie danych", "Proszę czekać...", false, false);
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
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}