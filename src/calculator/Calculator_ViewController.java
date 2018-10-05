//<editor-fold defaultstate="collapsed" desc="Version description">
// ver 0.1.1. Az összeadás működik halmozva is, gombok 0-9 és +,
// ver 0.1.2. gomb_ cleat, +, -, 0=>null
// ver 0.1.3. int => double, button coma
// ver 0.1.4. varálható műveletek, műveletek metódusban, CE button, egyenlő után folytatható, az első ciklus kavarodása javítva
// ver 0.1.5. button M, MR, Back, +-
// ver 0.2.1. fixing code design
// ver 0.2.2. fixed comma, exeptions handled in = and << buttons
// ver 0.2.3. int eqTxpe changed to opType method, lonely zero removed from the end of result, background & design added
// ver 0.2.4. history added
// ver 0.2.5. icon added, input changed to StringBuilder
// ver 0.3.1. double display rounding solved
// ver 0.3.2. M changed to M+, MC added
//</editor-fold>

package calculator;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.swing.KeyStroke;



public class Calculator_ViewController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="Class Variables">
    //the list of the inserted numbers as string
    StringBuilder inputStr = new StringBuilder();
    //input characters, converted to number    
    private double m_dInputNumber = 0; 
    // the previous input number or result
    private double m_dPrevNumber = 0;     
    // calculation result
    private double result = 0; 
    // memory
    private double dMem;    
    // contains the types of the operations in enum
    private opType m_opType;
    // to avoid multiple commas
    boolean commaBoolean = false;
    //a list contains the used operations
    ArrayList <opType> operationList = new ArrayList <> ();     
    // stores history elements
    ArrayList <String> historyList = new ArrayList <> ();      
  
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="FXML inport">
    @FXML
    private Label displayLabel;
    @FXML
    private Label historyLabel;    
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button0;   
    @FXML
    private Button buttonComma;      
    @FXML
    private Button buttonBack;  
    @FXML
    private Button buttonNegPos;    
    @FXML
    private Button buttonPlus;
    @FXML
    private Button buttonMinus;
    @FXML
    private Button buttonMultiply;
    @FXML
    private Button butonDivide;
    @FXML
    private Button buttonClear;
    @FXML
    private Button buttonClearCurrentNum;    
    @FXML
    private Button buttonMem;
    @FXML
    private Button buttonMemRecall;    
    @FXML
    private Button buttonMemClear;      
 
//</editor-fold>
  

//<editor-fold defaultstate="collapsed" desc="Buttons 0-9">
// Number Buttons 1-9
    @FXML  //  1
    private void handle1Action(ActionEvent event) {
        inputNumber('1');
    }
    @FXML  //  2
    private void handle2Action(ActionEvent event) {
        inputNumber('2');
    }
    @FXML  //  3
    private void handle3Action(ActionEvent event) {
        inputNumber('3');
    }
    @FXML  //  4
    private void handle4Action(ActionEvent event) {
        inputNumber('4');;
    }
    @FXML  //  5
    private void handle5Action(ActionEvent event) {
        inputNumber('5');
    }
    @FXML  //  6
    private void handle6Action(ActionEvent event) {
        inputNumber('6');
    }
    @FXML  //  7
    private void handle7Action(ActionEvent event) {
        inputNumber('7');
    }
    @FXML  //  8
    private void handle8Action(ActionEvent event) {
        inputNumber('8');
    }
    @FXML  //  9
    private void handle9Action(ActionEvent event) {
        inputNumber('9');
    }
    @FXML  //  0
    private void handle0Action(ActionEvent event) {
        inputNumber('0');
    }  
//</editor-fold>    
//<editor-fold defaultstate="collapsed" desc="Button: Comma, Back, +-">
    // Buttons: Comma, <=, +-
    @FXML  //  ,
    private void handleCommaAction(ActionEvent event) {
        if (commaBoolean == false){
        inputNumber('.');
        }
        commaBoolean = true;

//        inputNumber(',');
    }
    @FXML  //   <=
    private void handleBackAction(ActionEvent event) {
      if (inputStr.length() != 0){
        inputStr.setLength(inputStr.length()-1);
        displayLabel.setText(inputStr.toString());
        m_dInputNumber = Double.parseDouble(inputStr.toString());
      }else{
          return;
      }
    }
    @FXML  //   -+
    private void handleNegPosAction(ActionEvent event) {
        if (inputStr.charAt(0)!= '-' ){
            inputStr.insert(0, "-");
        }else{
            inputStr.deleteCharAt(0);
        }
        displayLabel.setText(inputStr.toString());
        m_dInputNumber = Double.parseDouble(inputStr.toString());
    }    
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Button +,-,*,/">
    // Buttons +,-,*,/
    @FXML
    private void handlePlusAction(ActionEvent event) {
        m_opType = opType.Plus;
        displayLabel.setText("+");  
        int hLen = historyList.size();
        if(hLen > 2 && historyList.get(hLen-2).contains("=")){
            historyList.add("+");         
        }        
        operationAction();   
        historyList.add("+");
        

     }
    @FXML
    private void handleMinusAction(ActionEvent event) {
        m_opType = opType.Minus;
        displayLabel.setText("-");  
        int hLen = historyList.size();
        if(hLen > 2 && historyList.get(hLen-2).contains("=")){
            historyList.add("-");         
        }
        operationAction();
        historyList.add("-");        

    }
    @FXML
    private void handleMultiplyAction(ActionEvent event) {
        m_opType = opType.Multiply;
        displayLabel.setText("*");
        int hLen = historyList.size();
        if(hLen > 2 && historyList.get(hLen-2).contains("=")){
            historyList.add("*");         
        }        
        operationAction();        
        historyList.add("*");        

    }
    @FXML
    private void handleDivideAction(ActionEvent event) {
        m_opType = opType.Divide; 
        displayLabel.setText("/"); 
        int hLen = historyList.size();
        if(hLen > 2 && historyList.get(hLen-2).contains("=")){
            historyList.add("/");         
        }        
        operationAction();        
        historyList.add("/");        

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Button C, CE, M, MR">
    // Buttons: C, CE, =, M, MR
    @FXML  //  C
    private void handleClearAction(ActionEvent event) {
        commaBoolean = false;        
        historyList.clear();        
        inputStr.setLength(0);
        displayLabel.setText("");
        historyLabel.setText("");
        m_dInputNumber = 0;
        result = 0;
    }
    @FXML  //  CE
    private void handleClearCurrentNumAction(ActionEvent event) {
        commaBoolean = false;   
        inputStr.setLength(0);        
        displayLabel.setText("");
      
    }
    @FXML  //  M
    private void handleMemAction(ActionEvent event) {
        if (result !=0){
        dMem += result;  
        }else{
        dMem += Double.parseDouble(inputStr.toString());   
        }        
    }
    @FXML  //  MR
    private void handleMemRecallAction(ActionEvent event) {
        inputStr.setLength(0); 
//        displayLabel.setText(String.valueOf(dMem));
        doubleDisplay(dMem);
        result = dMem;
    }   
    @FXML  //  MC
    private void handleMemClearAction(ActionEvent event) {
        dMem = 0;
    }     

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="= Button">
    @FXML //  =
    private void handleEqualAction(ActionEvent event) {
        m_dPrevNumber = result;
        m_dInputNumber = Double.parseDouble(inputStr.toString());
//        historyList.add(String.valueOf(m_dInputNumber));
        historyList.add(inputStr.toString());
//        doubleAddHist(m_dInputNumber);
        inputStr.setLength(0);
        
        if (m_dPrevNumber != 0){
            switch (m_opType){
                case Plus: result = m_dPrevNumber + m_dInputNumber;
                break;
                case Minus: result = m_dPrevNumber - m_dInputNumber;
                break;
                case Multiply: result = m_dPrevNumber * m_dInputNumber;
                break;
                case Divide: result = m_dPrevNumber / m_dInputNumber;
                break;
            }
        historyDisplay();
        historyList.add("=");
        doubleAddHist(result);
        doubleDisplay(result);
        
//        historyList.add(String.valueOf(result));
//        displayLabel.setText(String.valueOf(result));
        }else{
            return;
        }
//        System.out.println(calcArray[0] + " <=0 calCarray 1=> " + calcArray[1]);
//        System.out.println("inputNumber1= " + inputNumber);
    }
//</editor-fold>
    
    //buids a string from number buttons   
    public void inputNumber(char num){
        inputStr.append(num);  
        displayLabel.setText(inputStr.toString()); 
    }
    
    // OPERATION BUTTONS ACTION
    private void operationAction(){
        commaBoolean = false;  
        historyList.add(inputStr.toString());    
        operationList.add(m_opType);    
        m_dInputNumber = Double.parseDouble(inputStr.toString());
        inputStr.setLength(0);
        opType lastOperation;

        if (result == 0){
            result = m_dInputNumber;
        historyDisplay();             
        }else{
        lastOperation = operationList.get(operationList.size()-2);               
            m_dPrevNumber = result;
            switch (lastOperation){
                case Plus: result = m_dPrevNumber + m_dInputNumber;
                break;
                case Minus: result = m_dPrevNumber - m_dInputNumber;
                break;
                case Multiply: result = m_dPrevNumber * m_dInputNumber;
                break;
                case Divide: result = m_dPrevNumber / m_dInputNumber;
                break;
        }
        doubleDisplay(result);    
//            displayLabel.setText(String.valueOf(result));  

         historyDisplay();     
        }

//        System.out.println("operationList= " + operationList);  


    }
    
    //stores the operation types
    public enum opType{
        Plus, Minus, Multiply, Divide
    }
    
     //removes the lonely zero after the comma from the double & displays number 
    public void doubleDisplay(Double number){

        int iResult = (int)Math.round(number);
        if(number % iResult == 0.0){
        displayLabel.setText(String.valueOf(iResult));  
        }else{
        displayLabel.setText(String.valueOf(number));              
        }      
 
    }
    
// adds the fixed double value to the history
    public void doubleAddHist(Double number){

        int iResult = (int)Math.round(number);
        if(result % iResult == 0.0){
         historyList.add(String.valueOf(iResult)); 
        }else{        
         historyList.add(String.valueOf(number)); 
        }
    }
    
    //buids and displays a string from history list to the history label   
    public void historyDisplay(){
        StringBuilder historyString = new StringBuilder();            
        for (String i: historyList){
        historyString.append(i);
        }
        historyLabel.setText(historyString.toString());   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
