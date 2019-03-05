

public class userSpam {
	private int userId;
	private int numOfReq;
	userSpam(int userId, int numOfReq){
		this.userId = userId;
		this.numOfReq = numOfReq;
	}
	public int getId(){
		return userId;
	}
	public int getNum() {
		return numOfReq;
	}
	public void numPlus() {
		numOfReq++;
	}
	public void numMinus() {
		numOfReq--;
	}
	
}
