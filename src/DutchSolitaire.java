import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.imageio.ImageIO;
import java.io.File;

public class DutchSolitaire {
    public static void main(String... args) {
        JFrame j = new JFrame();  //JFrame is the window; window is a depricated class
        MyPanel m = new MyPanel();
        j.setSize(m.getSize());
        j.setLocationRelativeTo(null);
        j.setContentPane(m); //adds the panel to the frame so that the picture will be drawn
        j.setVisible(true); //allows the frame to be shown
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
    }
}

class MyPanel extends JPanel implements MouseListener {

    private Card[][] imageHolder;

    private ArrayList<File> files;

    private boolean firstCardSelected;
    private boolean secondCardSelected;

    private Card one, two;

    private final int CARDWIDTH = 73;
    private final int CARDHEIGHT = 97;

    private final String filePath = "res\\CardImages";

    //instantiates MyPanel
    MyPanel() {
        setSize(1200, 600);
        setVisible(true);
        addMouseListener(this);
        System.out.println(filePath.length());
        //loads the variables and starts the game
        load();
    }

    //loads game
    private void load() {
        one = two = null;
        firstCardSelected = secondCardSelected = false;
        imageHolder = new Card[4][14];
        files = new ArrayList<>(Arrays.asList(new File(filePath).listFiles()));
        Collections.shuffle(files);
        generateAces(); //loads in aces in the last column
        int index = 0;
        for (int r = 0; r < imageHolder.length; r++) {
            for (int c = 0; c < imageHolder[r].length - 1; c++) {
                if (index < files.size() && imageHolder[r][c] == null) {
                    imageHolder[r][c] = new Card(files.get(index).getAbsolutePath(), new Point(r, c));
                    index++;
                }
            }
        }
    }

    //puts all cards to the screen with a call to drawImages()
    public void paintComponent(Graphics g) {
        if (firstCardSelected && secondCardSelected) {
            swap(one, two);
        }
        drawImages(g);
    }

    //shows selected card to the right
    private void highLightCard(Graphics g) {
        if (firstCardSelected) {
            BufferedImage img;
            img = loadFile(new File(one.getCardType()));
            g.drawImage(img, 1050, 150, null);
        } else g.drawImage(null, 1050, 150, null);
    }

    //loads BufferedImage and saves it to matrix
    private BufferedImage loadFile(File fileName) {
        try {
            return ImageIO.read(fileName);
        } catch (Exception e) {
            return null;
        }
    }

    //draws all the cards
    private void drawImages(Graphics g) {
        highLightCard(g);
        if (!firstCardSelected) {
            g.setColor(Color.gray);
            g.fillRect(0, 0, 1200, 800);
        }
        int xStart = 0, yStart = 0;
        for (Card[] arr : imageHolder) {
            for (Card x : arr) {
                BufferedImage curr = loadFile(new File(x.getCardType()));
                if (curr != null) {
                    g.drawImage(curr, xStart, yStart, null);
                    xStart += CARDWIDTH;
                }
            }
            yStart += CARDHEIGHT;
            xStart = 0;
        }
    }

    //removes aces from the files ArrayList to prevent them from being redrawn again into imageHolder
    private void removeAces() {
        for (int i = 0; i < files.size(); i++) {
            String filePath = files.get(i).getAbsolutePath();
            if (filePath.contains("14") || filePath.contains("Thumbs")) {
                files.remove(i);
                i--;
            }
        }
    }

    //generates all aces into the last row where they cannot be moved again
    private void generateAces() {
        String[] acesCards = "14clubs.gif 14diamonds.gif 14hearts.gif 14spades.gif".split(" ");
        int index = 0;
        for (int r = 0; r < imageHolder.length; r++) {
            if (index < acesCards.length) {
                File ace = new File(filePath + acesCards[index]);
                imageHolder[r][13] = new Card(ace.getAbsolutePath(), new Point(r, 13));
                index++;
            }
        }
        removeAces();
    }

    //swaps two cards if it is a valid swap
    private void swap(Card one, Card two) {
        if (checkValid(one, two)) {
            String cardTypeOne = one.getCardType();
            one.setCardType(two.getCardType());
            two.setCardType(cardTypeOne);
        }
        firstCardSelected = secondCardSelected = false;
        one = null;
        two = null;
    }

    //checks whether the two cards selected can be swapped according to a set of rules
    private boolean checkValid(Card one, Card two) {
        if (one.getCardNumber() != 15 && two.getCardNumber() != 15) {
            return false;
        } else if (one.getCardNumber() == 14 || two.getCardNumber() == 14) {
            return false;
        } else if ((one.getCardType().contains("2") || two.getCardType().contains("2"))) {
            if ((one.getPoint().x == 0 || two.getPoint().x == 0))
                return true;
        } else {
            if (one.getCardType().contains("15")) {
                return validAdjacentCard(one, two);
            } else {
                return validAdjacentCard(two, one);
            }
        }
        return true;
    }

    //checks whether the cards adjacent to the blank
    private boolean validAdjacentCard(Card compare, Card ref) {
        int indexX = compare.getPoint().x, indexY = compare.getPoint().y;
        int valueOfCard = ref.getCardNumber();
        String cardFace = ref.getCardFace();
        System.out.println(cardFace);
        if (indexY - 1 >= 0) {
            Card check = imageHolder[indexX][indexY - 1];
            int valueCheck = check.getCardNumber();
            if (valueOfCard == valueCheck + 1 && cardFace.equals(check.getCardFace()))
                return true;
        }
        if (indexY + 1 < imageHolder[indexX].length) {
            Card check = imageHolder[indexX][indexY + 1];
            int valueCheck = check.getCardNumber();
            return valueOfCard == valueCheck - 1 && cardFace.equals(check.getCardFace());
        }
        return false;
    }

    //controls mouse clicks; the first click selects the card to be moved, second click determines
    //the location to which the other card moves
    public void mouseClicked(MouseEvent e) {
        int xCoord = e.getX();
        int yCoord = e.getY();
        if (xCoord >= 0 && xCoord <= (14 * CARDWIDTH) && yCoord >= 0 && yCoord <= (4 * CARDHEIGHT)) {
            int indexX = (yCoord - (yCoord % CARDHEIGHT)) / CARDHEIGHT;
            int indexY = (xCoord - (xCoord % CARDWIDTH)) / CARDWIDTH;
            if (firstCardSelected) {
                two = imageHolder[indexX][indexY];
                secondCardSelected = firstCardSelected;
            } else {
                one = imageHolder[indexX][indexY];
                firstCardSelected = true;
            }
        }
        repaint();
    }

    //unused methods: required implements
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}