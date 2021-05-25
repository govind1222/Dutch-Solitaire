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
        return Integer.parseInt(cardType.replaceAll("\\D+", ""));
    }

    //returns the point which contains the index of the card
    public Point getPoint(){
        return index;
    }

    //returns cardType
    public String getCardFace(){
        //System.out.println(cardType);
        return cardType.replaceAll("[^A-Za-z.]+", "").replace(".gif", "");
    }

    //sets cardType
    public void setCardType(String newCardType){
        cardType = newCardType;
    }

}
