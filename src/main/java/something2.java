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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;

public class something2 extends TelegramLongPollingBot {
  
  
  long startTime;
  static protected ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<Integer, Integer>();
  @Override
    public void onUpdateReceived(Update update) {
        // TODO
//    InlineKeyboardMarkup
    InlineKeyboardButton newButt = new InlineKeyboardButton("newButton1");
    InlineKeyboardButton newButt2 = new InlineKeyboardButton("newButton2");
    
    ArrayList<InlineKeyboardButton> newList = new ArrayList<InlineKeyboardButton>();
    newList.add(newButt);
    newList.add(newButt2);
    ArrayList<List<InlineKeyboardButton>> newList2 = new ArrayList<List<InlineKeyboardButton>>();
    newList2.add(newList);
    
    InlineKeyboardMarkup keyboard_ =new InlineKeyboardMarkup();
//    keyboard_.setKeyboard(
//        new ArrayList<List<InlineKeyboardButton>>().add(newList));
//why cannot like the up?
    keyboard_.setKeyboard(newList2);

        
        
        
        
        
        
      boolean spam = false;
      
      JSONObject json = null;
      SendMessage message;
//      Spam detector----------------------------------------
      Message newMsg = update.getMessage();
      String newArr[] = new String[] {"a","b"};
//      String newArr[] = newMsg.getText().split(" ");
//      int usrId = newMsg.getFrom().getId();
      int usrId =0;
//      //private final ga bisa knp ya?

      if(!hashMap.containsKey(usrId)) {
        hashMap.put(usrId, 0);
      }
      else {
        hashMap.replace(usrId,hashMap.get(usrId)+1);
      }
      
      System.out.println(hashMap.get(usrId));
      if(hashMap.get(usrId)>10) {
        spam = true;
      }
      System.out.print(usrId);
      
//      Spam detector end
      
      if(spam) {
        message = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(newMsg.getChatId())
            .setText("SPAM!!!! @"+ (newMsg.getFrom().getUserName()));
        try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
      }
      if (update.hasInlineQuery()) {
        handleIncomingQuery(update.getInlineQuery());
      }
      else if(newArr[0].equals("/report")&&!spam){
      if (newArr.length==1) {
        System.out.println(newMsg.getFrom().getId());
//        System.out.println(Integer.toString(jsonFromUrlHelper.readUserId(newMsg)));
message = new SendMessage() // Create a SendMessage object with mandatory fields
                      .setChatId(newMsg.getChatId())
                      .setText("pls input the web/ether address to report");
//        message = new SendMessage() // Create a SendMessage object with mandatory fields
//                      .setChatId(newMsg.getUser())
//                      .setText("pls input the web/ether address to report");
            
      }
      else {
        message = new SendMessage() // Create a SendMessage object with mandatory fields
                      .setChatId(newMsg.getChatId())
                      .setText("Let's report " + newArr[1] +" Click this link to report: https://etherscamdb.info/report");
              
      }

            //up is the logic for reporting
            try {      
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
      }
      else if(newArr[0].equals("/query")&&!spam){      
      message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(newMsg.getChatId())
                    .setText("Click this link to query: querylink.com/query");
            //up is the logic for reporting
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
      }
    }

    @Override
    public String getBotUsername() {
        // TODO
//      return "Zumbbbot";

      return "Driibot";
    }

    @Override
    public String getBotToken() {
        // TODO
//        return "666765018:AAG8PWrRSxHjSAaHbmbw1l5j3lWrklISESM";
        return "654943081:AAGblcw3yKW2Cm0TmQvmRan_aq4B_h0bIOs";
    }
    
    public void handleIncomingQuery(InlineQuery inlineQuery) {
      String query = inlineQuery.getQuery();
      if (!query.isEmpty())
        {
          AnswerInlineQuery answer = new AnswerInlineQuery();
            List<InlineQueryResult> l = new ArrayList<InlineQueryResult>();
            InlineQueryResultArticle article = new InlineQueryResultArticle();
            InputTextMessageContent messageContent = new InputTextMessageContent();
            messageContent.disableWebPagePreview();
            messageContent.enableMarkdown(true);
            messageContent.setMessageText("Finally, I have done this shit");
            article.setInputMessageContent(messageContent);
            article.setId("1");
            article.setTitle("sdf");
        l.add(article);
          answer.setInlineQueryId(inlineQuery.getId());
          
          answer.setCacheTime(86400);
          answer.setResults(l);
          System.out.println(answer.getResults());
          try {
            execute(answer);
          }
          catch (TelegramApiException e) {
            e.printStackTrace();
          }
          
        }
      }
    
}
