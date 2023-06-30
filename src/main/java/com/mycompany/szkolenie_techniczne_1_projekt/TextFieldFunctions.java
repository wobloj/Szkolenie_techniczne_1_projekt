/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szkolenie_techniczne_1_projekt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author Wiktor
 */
public class TextFieldFunctions {
    
    public void setOnlyNumericTextField(TextField textfield){
        textfield.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(!newValue.matches("\\d*")){
                    textfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    public void oneClickCopy(TextField textfield){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textfield.getText());
        clipboard.setContent(content);
    }
}
