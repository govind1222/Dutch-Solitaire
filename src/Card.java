import java.awt.Point;

class Card{

    private String cardType;
    private Point index;

    //constructor for card class
    Card(String t, Point index) {
        cardType = t;
        this.index = index;
    }

    //returns cardType
    public String getCardType(){
        return cardType;
    }

    //returns card number
    public int getCardNumber(){
        int number = 0;
        try{
            number = Integer.parseInt(getCardType().substring(58,60)); //2 digit card number
        }catch(Exception e){
            number = Integer.parseInt(getCardType().substring(58,59)); //one digit
        }finally {
            return number;
        }
    }

    //returns the point which contains the index of the card
    public Point getPoint(){
        return index;
    }

    //returns cardType
    public String getCardFace(){
        return cardType.substring(60, cardType.length() - 4);
    }

    //sets cardType
    public void setCardType(String newCardType){
        cardType = newCardType;
    }

}
