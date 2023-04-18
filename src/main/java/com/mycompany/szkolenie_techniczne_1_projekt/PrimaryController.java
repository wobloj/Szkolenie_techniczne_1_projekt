package com.mycompany.szkolenie_techniczne_1_projekt;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class PrimaryController {

    @FXML
    private ComboBox<String> calculateFromList, calculateToList;
    @FXML
    private TextField calculateFrom, calculateTo;
    @FXML
    private Label rates;
    @FXML
    private TableView table;
    @FXML
    private TableColumn tableCurrency, tableRate;    
    
    @FXML
    public void initialize(){
        String[] isoCurrencies = { "JPY", "CNY", "SDG", "RON", "MKD", "MXN", "CAD",
        "ZAR", "AUD", "NOK", "ILS", "ISK", "SYP", "LYD", "UYU", "YER", "CSD",
        "EEK", "THB", "IDR", "LBP", "AED", "BOB", "QAR", "BHD", "HNL", "HRK",
        "COP", "ALL", "DKK", "MYR", "SEK", "RSD", "BGN", "DOP", "KRW", "LVL",
        "VEF", "CZK", "TND", "KWD", "VND", "JOD", "NZD", "PAB", "CLP", "PEN",
        "GBP", "DZD", "CHF", "RUB", "UAH", "ARS", "SAR", "EGP", "INR", "PYG",
        "TWD", "TRY", "BAM", "OMR", "SGD", "MAD", "BYR", "NIO", "HKD", "LTL",
        "SKK", "GTQ", "BRL", "EUR", "HUF", "IQD", "CRC", "PHP", "SVC", "PLN",
        "USD" };
        
        
        calculateFromList.setItems(FXCollections.observableArrayList(isoCurrencies).sorted());
        calculateToList.setItems(FXCollections.observableArrayList(isoCurrencies).sorted());
    }
    

    
    @FXML
    private void calculateCurrency() throws IOException {
        BigDecimal amount = BigDecimal.valueOf(Double.valueOf(calculateFrom.getText()));
        
        if(calculateFrom.getText().isEmpty()){
            rates.setText("Uzupełnij pole");
            return;
        }
        
        
        String listConvertTo = calculateToList.getValue();        
        String listConvertFrom = calculateFromList.getValue();
        
        String urlString = "https://api.exchangerate.host/convert?from="+ listConvertFrom +"&to="+ listConvertTo +"&amount="+ amount;
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(urlString)
            .get()
            .build();

        
        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string(); 
        
        JSONObject jsonObject = new JSONObject(stringResponse);        
        JSONObject infos = jsonObject.getJSONObject("info");
        BigDecimal rate = infos.getBigDecimal("rate");
        BigDecimal calculatedCurrency = jsonObject.getBigDecimal("result");
        
        rates.setText("1 " + listConvertFrom + " = "+String.format("%.2f",rate)+ " " + listConvertTo);
        
        calculateTo.setText(String.format("%.2f",calculatedCurrency));
    }
    
    @FXML
    private void back() throws IOException {
        App.setRoot("primary");
    }
}
