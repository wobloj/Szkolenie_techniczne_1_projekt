package com.mycompany.szkolenie_techniczne_1_projekt;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    private ComboBox<String> calculateFromList, calculateToList, calculateToTableList;
    @FXML
    private TextField calculateFrom, calculateTo;
    @FXML
    private Label rates;
    @FXML
    private TableView<Currencies> table;
    @FXML
    private TableColumn<Currencies, Integer> tableLp;
    @FXML
    private TableColumn<Currencies, String> tableCurrency;
    @FXML
    private TableColumn<Currencies, BigDecimal> tableRate;    
    @FXML
    private ProgressIndicator progress;
    
    private Task<ObservableList<Currencies>> task;
    
    public String[] currencies = { "JPY", "CNY", "SDG", "RON", "MKD", "MXN", 
        "CAD", "ZAR", "AUD", "NOK", "ILS", "ISK", "SYP", "LYD", "UYU", "YER",
        "THB", "IDR", "LBP", "AED", "BOB", "QAR", "BHD", "HNL", "HRK", "COP",
        "ALL", "DKK", "MYR", "SEK", "RSD", "BGN", "DOP", "KRW", "CZK", "TND",
        "KWD", "VND", "JOD", "NZD", "PAB", "CLP", "PEN", "GBP", "DZD", "CHF",
        "RUB", "UAH", "ARS", "SAR", "EGP", "INR", "PYG", "TWD", "TRY", "BAM", 
        "OMR", "SGD", "MAD", "NIO", "HKD", "GTQ", "BRL", "EUR", "HUF", "IQD",
        "CRC", "PHP", "SVC", "PLN", "USD" };
    
    @FXML
    public void initialize(){
        
        calculateFromList.setItems(FXCollections.observableArrayList(currencies).sorted());
        calculateToList.setItems(FXCollections.observableArrayList(currencies).sorted());
        calculateToTableList.setItems(FXCollections.observableArrayList(currencies).sorted());
        
        tableLp.setCellValueFactory(new PropertyValueFactory<>("lp"));
        tableCurrency.setCellValueFactory(new PropertyValueFactory<>("currencies"));
        tableRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
         
    }
    
    
    @FXML
    private void calculateCurrency() throws IOException {
        
        if(calculateFrom.getText().isEmpty()){
            rates.setText("Uzupełnij pole");
            return;
        }
        if(calculateFromList.getSelectionModel().isEmpty() && calculateToList.getSelectionModel().isEmpty()){
            rates.setText("Wybierz waluty");
            return;
        }
        
        BigDecimal amount = BigDecimal.valueOf(Double.valueOf(calculateFrom.getText()));
        
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
    private void getCurrenciesData() throws IOException {
        if(calculateToTableList.getSelectionModel().isEmpty()){
            return;
        }
        ObservableList<Currencies> list = FXCollections.observableArrayList();

        
        task = new Task<ObservableList<Currencies>>(){

            @Override
            protected ObservableList<Currencies> call() throws Exception{
//                for(int i=0; i < currencies.length; i++){
//                    if(currencies[i].equals(calculateToTableList.getValue())){
//                        continue;
//                    }
//                    
//                    String urlString = "https://api.exchangerate.host/convert?from="+ currencies[i] +"&to="+calculateToTableList.getValue()+"&amount=1";
//
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                        .url(urlString)
//                        .get()
//                        .build();
//
//                    Response response = client.newCall(request).execute();
//                    String stringResponse = response.body().string(); 
//
//                    JSONObject jsonObject = new JSONObject(stringResponse);        
//                    JSONObject infos = jsonObject.getJSONObject("info");
//                    BigDecimal rate = infos.getBigDecimal("rate");
//                    
//                    progress.setProgress(((float)i+1)/currencies.length);
//                    
//                    System.out.println((((float)i+1)/currencies.length)* 100);
//                    list.add(new Currencies(i+1,currencies[i],rate));
//                }

                int i=0, lp=0;
                while(lp < currencies.length){
                    if(currencies[i].equals(calculateToTableList.getValue())){
                        System.out.println("TAKIE SAME!!");
                        i++;
                        continue;
                    }
                    
                    lp++;
                    
                    String urlString = "https://api.exchangerate.host/convert?from="+ currencies[i] +"&to="+calculateToTableList.getValue()+"&amount=1";

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
                    
                    progress.setProgress(((float)lp+1)/currencies.length);
                    
                    System.out.println((((float)lp+1)/currencies.length)* 100);
                    list.add(new Currencies(lp,currencies[i],rate));
                    i++;
                }
                return list;
            }
        };
        tableRate.setText("Aktualna cena w "+calculateToTableList.getValue());
        table.setItems(list);
        
        
        
        new Thread(task).start();
    }
    
    @FXML
    private void back() throws IOException {
        App.setRoot("primary");
    }
}
