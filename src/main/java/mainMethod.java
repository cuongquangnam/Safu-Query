import org.telegram.telegrambots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;
import java.lang.System.*;
public class mainMethod {
	
	
	public static void main(String[] args) {
//		Initializing things
        ApiContextInitializer.init();
//        To get the curent Time for the computer
//        System.currentTimeMillis() must be saved to long 
        long currentTime = System.currentTimeMillis();
        long lastTime = System.currentTimeMillis();
        
        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        
        
        try {
            botsApi.registerBot(new telgramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.print("tesFirst");
        while(true) {
        	currentTime = System.currentTimeMillis();
        	if(currentTime> lastTime+10000) {
        		for(Map.Entry<Integer, Integer> entry : telgramBot.hashMap.entrySet()) {
//        			The entry is the element for the Map<Integer, Integer>
//        			Map<int, int> cannot, Map<Integer, Integer> can
        			if(entry.getValue()>0) {
            			entry.setValue(entry.getValue()+1) ;
        			}
        		}
//        		if(telgramBot.hashMap!= null) {
//        			telgramBot.hashMap.replaceAll((k, v) -> v - 1);
//        		}
        		currentTime = System.currentTimeMillis();
        		lastTime = currentTime;
        	}
        }
    }

}
