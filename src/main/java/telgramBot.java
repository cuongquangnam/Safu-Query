
import java.io.*;

import java.util.*;
import java.util.concurrent.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.logging.BotLogger;



import java.util.stream.Collectors;


public class telgramBot extends TelegramLongPollingBot {
	
	long startTime;
	// for the stages of the bot
	private static final int START_ = 13;
	private static final int DOMAINORADDRESS = 1;
	private static final int DOMAIN = 2;
	private static final int ADDRESS = 3;
	private static final int TYPEOFDOMAIN = 4;
	private static final int SENDREMARKS = 5;
	private static final int WANTOSENDPROOFS = 6;
	private static final int TYPEOFCRYPTO = 8;
	private static final int HAVEYOUTRANSFER = 9;
	private static final int YOURADDRESS = 10;
	private static final int HASHLINK = 11;
	private static final int SENDPROOFS = 12;
	static protected ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<Integer, Integer>();
	static protected ConcurrentHashMap<Integer,Boolean> firstTime = new ConcurrentHashMap<Integer,Boolean>();
	@Override
    public void onUpdateReceived(Update update) {
        // TODO
    	JSONObject json = null;
    	SendMessage message;
    	Message newMsg = update.getMessage();
    	int usrId = newMsg.getFrom().getId();
//    	//private final ga bisa knp ya?

    	if(!hashMap.containsKey(usrId)) {
    		hashMap.put(usrId, START_);
    	}
    	System.out.println("tes");
    	Long chatId = newMsg.getChatId();
    	switch (hashMap.get(usrId)) {
    	   case START_:
	        	System.out.println("START");	
		    	message = new SendMessage() // Create a SendMessage object with mandatory fields
	        	                    .setChatId(chatId).setText("Please choose report or query")
	        	                    .setReplyMarkup(AddressorDomain());
                exe(message);
                if (newMsg.getText().contentEquals("Report")) {
                	System.out.println("Reporte");
                    hashMap.put(usrId, DOMAINORADDRESS);
                }
            	else if(newMsg.getText().contentEquals("Query")){
            		System.out.println("Query");
            		hashMap.put(usrId, DOMAINORADDRESS);
            	}
                checkCancel(newMsg,usrId);
                break;
    		case DOMAINORADDRESS:
            	 System.out.println("DOMAIN OR ADDRESS");
            	 message = new SendMessage().setChatId(chatId).setText("DOMAIN OR ADDRESS").setReplyMarkup(WebsiteOrAddr());
                 exe(message);
            	 if (newMsg.getText().contentEquals("Website"))
            		  {
            
            		 hashMap.put(usrId,DOMAIN);
            		  }
            	  if (newMsg.getText().contentEquals("Address"))
            		  {
            		  hashMap.put(usrId,ADDRESS);
            		  }
            	  checkCancel(newMsg,usrId);
                  break;
            case DOMAIN:
            	 System.out.println("DOMAIN");
                 message = new SendMessage().setChatId(chatId).setText("Please input website by /web");
                 exe(message);
                 String getWeb = extract(newMsg.getText(),"/web");
                 if (getWeb!="") {
                  hashMap.put(usrId, TYPEOFDOMAIN);
                  }            	  
                  checkCancel(newMsg,usrId);
                  break;
             case TYPEOFDOMAIN:
             	 System.out.println("TYPE OF DOMAIN");
            	 List<String> a = new ArrayList<String>();
            	 a.add("Fraud");
            	 a.add("Phishing");
            	 a.add("Others");
            	 Boolean checkType = a.contains(newMsg.getText());
            	 if (!checkType) {
            	  message = new SendMessage().setChatId(chatId).setText("Please input the type of the website").setReplyMarkup(typeOfDomain());
            	  exe(message);}
            	 else {
            		 hashMap.put(usrId, SENDREMARKS);
            	 }
                  checkCancel(newMsg,usrId);
            	  break;
             case SENDREMARKS:
            	 System.out.println("SEND REMARKS");
            	 String getRemarks = extract(newMsg.getText(),"/remarks");
            	  message = new SendMessage().setChatId(chatId).setText("Please send remarks using /remarks");
            	  exe(message);
            	 if (getRemarks!="")
            		 {
            		 hashMap.put(usrId, WANTOSENDPROOFS);
            		 }
                  checkCancel(newMsg,usrId);
            	  break;
             case WANTOSENDPROOFS:
            	 System.out.println("WANT TO SEND PROOF");
            	 message = new SendMessage().setChatId(chatId).setText("Do you want to provide us the proof?").setReplyMarkup(yesOrNo());
            	 exe(message);
            	 if (newMsg.getText().contentEquals("Yes")) {
                	 hashMap.put(usrId, SENDPROOFS);
            	 }
            	 else if (newMsg.getText().contentEquals("No")) {
            		 hashMap.put(usrId, START_);
            	 }
                 checkCancel(newMsg,usrId);
                 break;
             case SENDPROOFS:
            	 System.out.println("SEND PROOF");
            	 
                	 message = new SendMessage().setChatId(chatId).setText("Send Proofs using /proofs");
            		 exe(message);
            	 

           	     checkCancel(newMsg,usrId);
            	 hashMap.put(usrId,START_);
        	 	break;
             case ADDRESS:
            	 System.out.println("ADDRESS");
            	 message = new SendMessage().setChatId(chatId).setText("Send Address using /address");
            	 hashMap.put(usrId,TYPEOFCRYPTO);

           	  checkCancel(newMsg,usrId);
        	 	break;
             case TYPEOFCRYPTO:
            	 System.out.println("TYPE_OF_CRYPTO");
            	 String getAddress = extract(newMsg.getText(),"/address");
            	 if (getAddress != "") {
            	 message = new SendMessage().setChatId(chatId).setText("please report the cryptocurrency by /crypto"); 
            	 exe(message);
            	 hashMap.put(usrId,HAVEYOUTRANSFER);
            	 }
           	     checkCancel(newMsg,usrId);
        	 	break;
             case HAVEYOUTRANSFER:
            	 System.out.println("HAVEYOUTRANSFER");
            	 String getCrypto = extract(newMsg.getText(),"/crypto");
            	 if (getCrypto != "") {
            	 message = new SendMessage().setChatId(chatId).setText("Have you transferred some cyptocurrency to the scam?").setReplyMarkup(typeOfDomain());
            	 
            	 if (newMsg.getText().contentEquals("Yes")) {
            		
            	 }
            	 else {}
            	 hashMap.put(usrId,YOURADDRESS);}
            	 ///////IMPORTANT
             	 hashMap.put(usrId,WANTOSENDPROOFS);

           	  checkCancel(newMsg,usrId);
             	
            	 break;
             case YOURADDRESS:
            	 System.out.println("YOUR ADDRESS");
            	 message = new SendMessage().setChatId(chatId).setText("Please show your address by /address").setReplyMarkup(typeOfDomain());
            	 
            	 if (newMsg.getText().contentEquals("Please show your address by /addr")) {
            		
            	 }
            	 else {}
            	 hashMap.put(usrId,HASHLINK);

           	  checkCancel(newMsg,usrId);
        	 	break;
             case HASHLINK:
            	 message = new SendMessage().setChatId(chatId).setText("Please show your transactio ID by /trx").setReplyMarkup(typeOfDomain());
            	 System.out.println("HASH LINK");
            	 
            	 if (newMsg.getText().contentEquals("Yes")) {
            		
            	 }
            	 else {}
            	 hashMap.put(usrId,WANTOSENDPROOFS);

           	  checkCancel(newMsg,usrId);
        	 	break;

    	}
  	
    }

    @Override
    public String getBotUsername() {
        // TODO
//    	return "Zumbbbot";
 //   	return "Driibot";
    	return "overtherainbow";
    }

    @Override
    public String getBotToken() {
        // TODO
//        return "666765018:AAG8PWrRSxHjSAaHbmbw1l5j3lWrklISESM";
        return "706324094:AAF286X_x3ZQNIQE5xtecDmdo5QqgkcKtqI";
 //   		return "654943081:AAGblcw3yKW2Cm0TmQvmRan_aq4B_h0bIOs";
    }
    public void exe(SendMessage message) {
    	try {
    		execute(message);
    	}
    	catch (TelegramApiException e){
    		e.printStackTrace();
    	}
    }

    private static ReplyKeyboardMarkup setKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup) {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
    
    private static ReplyKeyboardMarkup AddressorDomain() {
    	ReplyKeyboardMarkup rep = new ReplyKeyboardMarkup();
    	setKeyboardMarkup(rep);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Report");
        keyboardFirstRow.add("Query");
        keyboard.add(keyboardFirstRow);
        rep.setKeyboard(keyboard);
    	
    	return rep;
    }
    private static ReplyKeyboardMarkup WebsiteOrAddr() {
    	ReplyKeyboardMarkup rep = new ReplyKeyboardMarkup();
    	setKeyboardMarkup(rep);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Website");
        keyboardFirstRow.add("Address");
        keyboard.add(keyboardFirstRow);
        rep.setKeyboard(keyboard);
    	
    	return rep;
    }
    
    private static ReplyKeyboardMarkup typeOfDomain() {
    	ReplyKeyboardMarkup rep = new ReplyKeyboardMarkup();
    	setKeyboardMarkup(rep);
    	List<KeyboardRow> keyboard = new ArrayList<>();
    	KeyboardRow keyboardFirstRow = new KeyboardRow();
    	keyboardFirstRow.add("Phishing");
    	keyboardFirstRow.add("Fraud");
    	keyboardFirstRow.add("Others");
    	keyboard.add(keyboardFirstRow);
    	rep.setKeyboard(keyboard);
    	
    	return rep;
    }
    private static ReplyKeyboardMarkup yesOrNo() {
    	ReplyKeyboardMarkup rep = new ReplyKeyboardMarkup();
    	setKeyboardMarkup(rep);
    	List<KeyboardRow> keyboard = new ArrayList<>();
    	KeyboardRow keyboardFirstRow = new KeyboardRow();
    	keyboardFirstRow.add("Yes");
    	keyboardFirstRow.add("No");
    	keyboard.add(keyboardFirstRow);
    	rep.setKeyboard(keyboard);
    	
    	return rep;    	
    }
    private void checkCancel(Message msg, int usrId) {
    	//if (msg.getText().contentEquals("/cancel")) 
    		//{
    		//hashMap.put(usrId,START_);
    	//	}
    }
   // private void String checkDomain(Message msg, String check) {
   // 	msg.getText().split(" ");
   // 	String str[] = msg.getText().split(" ");
   // 	if(check)
    	
    	
   // }
    private String extract(String input,String command) {
    	String[] str = input.split(" ");
    	if (str[0] == command) {
    		return str[1];
    	}
    	else {
    		return "";}
    }
    
}
