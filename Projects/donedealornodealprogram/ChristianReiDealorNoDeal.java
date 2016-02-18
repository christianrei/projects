import java.awt.*;
import java.util.Random;      // for choosing random cases
import java.awt.event.*;        // All java packages you need for this program
import javax.swing.*; 
import java.io.*;            //for sound



import javax.sound.sampled.AudioInputStream;       // Needed to put sounds in game
import javax.sound.sampled.Clip;                   //wav files
import javax.sound.sampled.AudioSystem;


public class ChristianReiDealorNoDeal extends JPanel {
  
  private int counter = 0;                      // Counter for counting the round
  private int casesNeeded = 6;                // How many cases you choose in the first round then gets subtracted by one each round
  private int casesClicked = casesNeeded;       // How many times the case is clicked
  private boolean CaseSelected = false;          // Checks if these cases have been selected
  private double roundNum = 1;                // Keeps track of what round it is.
  
  
  // Array of money values for the game
 private double money[]={0.01, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000};
  
 private boolean caseChosen[] = new boolean[26];        // Array of 26 cases that will be chosen
  
private JLabel[] moneyLabel={      // How the money values will appear on their labels in the game.
    new JLabel(" $0.01"),
    new JLabel(" $1"),
    new JLabel(" $5"),
    new JLabel(" $10"),
    new JLabel(" $25"),
    new JLabel(" $50"),
    new JLabel(" $75"),
    new JLabel(" $100"),
    new JLabel(" $200"),
    new JLabel(" $300"),
    new JLabel(" $400"),
    new JLabel(" $500"),
    new JLabel(" $750"),
    new JLabel(" $1000"),
    new JLabel(" $5000"),
    new JLabel(" $10000"),
    new JLabel(" $25000"),
    new JLabel(" $50000"),
    new JLabel(" $75000"),
    new JLabel(" $100000"),
    new JLabel(" $200000"),
    new JLabel(" $300000"),
    new JLabel(" $400000"),
    new JLabel(" $500000"),
    new JLabel(" $750000"),
    new JLabel(" $1000000")
  };
  
  ImageIcon briefcase1 = new ImageIcon("case1.png");   // Pictures for the 26 cases
  ButtonHandler listener1 = new ButtonHandler();
  
  ImageIcon briefcase2 = new ImageIcon("case2.png");
  ButtonHandler listener2 = new ButtonHandler();
  
  ImageIcon briefcase3 = new ImageIcon("case3.png");
  ButtonHandler listener3 = new ButtonHandler();
  
  ImageIcon briefcase4 = new ImageIcon("case4.png");
  ButtonHandler listener4 = new ButtonHandler();
  
  ImageIcon briefcase5 = new ImageIcon("case5.png");
  ButtonHandler listener5 = new ButtonHandler();
  
  ImageIcon briefcase6 = new ImageIcon("case6.png");
  ButtonHandler listener6 = new ButtonHandler();
  
  ImageIcon briefcase7 = new ImageIcon("case7.png");
  ButtonHandler listener7 = new ButtonHandler();
  
  ImageIcon briefcase8 = new ImageIcon("case8.png");
  ButtonHandler listener8 = new ButtonHandler();
  
  ImageIcon briefcase9 = new ImageIcon("case9.png");
  ButtonHandler listener9 = new ButtonHandler();
  
  ImageIcon briefcase10 = new ImageIcon("case10.png");
  ButtonHandler listener10 = new ButtonHandler();
  
  ImageIcon briefcase11 = new ImageIcon("case11.png");
  ButtonHandler listener11 = new ButtonHandler();
  
  ImageIcon briefcase12 = new ImageIcon("case12.png");
  ButtonHandler listener12 = new ButtonHandler();
  
  ImageIcon briefcase13 = new ImageIcon("case13.png");
  ButtonHandler listener13 = new ButtonHandler();
  
  ImageIcon briefcase14 = new ImageIcon("case14.png");
  ButtonHandler listener14 = new ButtonHandler();
  
  ImageIcon briefcase15 = new ImageIcon("case15.png");
  ButtonHandler listener15 = new ButtonHandler();
  
  ImageIcon briefcase16 = new ImageIcon("case16.png");
  ButtonHandler listener16 = new ButtonHandler();
  
  ImageIcon briefcase17 = new ImageIcon("case17.png");
  ButtonHandler listener17 = new ButtonHandler();
  
  ImageIcon briefcase18 = new ImageIcon("case18.png");
  ButtonHandler listener18 = new ButtonHandler();
  
  ImageIcon briefcase19 = new ImageIcon("case19.png");
  ButtonHandler listener19 = new ButtonHandler();
  
  ImageIcon briefcase20 = new ImageIcon("case20.png");
  ButtonHandler listener20 = new ButtonHandler();
  
  ImageIcon briefcase21 = new ImageIcon("case21.png");
  ButtonHandler listener21 = new ButtonHandler();
  
  ImageIcon briefcase22 = new ImageIcon("case22.png");
  ButtonHandler listener22 = new ButtonHandler();
  
  ImageIcon briefcase23 = new ImageIcon("case23.png");
  ButtonHandler listener23 = new ButtonHandler();
  
  ImageIcon briefcase24 = new ImageIcon("case24.png");
  ButtonHandler listener24 = new ButtonHandler();
  
  ImageIcon briefcase25 = new ImageIcon("case25.png");
  ButtonHandler listener25 = new ButtonHandler();
  
  ImageIcon briefcase26 = new ImageIcon("case26.png");
  ButtonHandler listener26 = new ButtonHandler();
  
  
  //Create 26 buttons for the cases
  JButton Case1 = new JButton(briefcase1);   // These are the buttons that the images will be attached to using the briefcase name
  JButton Case2 = new JButton(briefcase2);
  JButton Case3 = new JButton(briefcase3);
  JButton Case4 = new JButton(briefcase4);
  JButton Case5 = new JButton(briefcase5);
  JButton Case6 = new JButton(briefcase6);
  JButton Case7 = new JButton(briefcase7);
  JButton Case8 = new JButton(briefcase8);
  JButton Case9 = new JButton(briefcase9);
  JButton Case10 = new JButton(briefcase10);
  JButton Case11 = new JButton(briefcase11);
  JButton Case12 = new JButton(briefcase12);
  JButton Case13 = new JButton(briefcase13);
  JButton Case14 = new JButton(briefcase14);
  JButton Case15 = new JButton(briefcase15);
  JButton Case16 = new JButton(briefcase16);
  JButton Case17 = new JButton(briefcase17);
  JButton Case18 = new JButton(briefcase18);
  JButton Case19 = new JButton(briefcase19);
  JButton Case20 = new JButton(briefcase20);
  JButton Case21 = new JButton(briefcase21);
  JButton Case22 = new JButton(briefcase22);
  JButton Case23 = new JButton(briefcase23);
  JButton Case24 = new JButton(briefcase24);
  JButton Case25 = new JButton(briefcase25);
  JButton Case26 = new JButton(briefcase26);
  
  private class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {           // In here describes what buttons will do.
      
      JButton buttonPressed = ((JButton) (e.getSource()));      // Says that the button has been pressed
      if(!CaseSelected) {
        if (buttonPressed == Case1) {           // What the buttons will do in the first turn
          Case1.setLocation(100, 650);            // Brings them to the bottom right and dissables them so the cannot be pressed anymore. This case is your case.
          Case1.setEnabled(false);
        }
        else if (buttonPressed == Case2) {
          Case2.setLocation(100, 650);
          Case2.setEnabled(false);
        }
        else if (buttonPressed == Case3) {
          Case3.setLocation(100, 650);
          Case3.setEnabled(false);     
        }
        else if (buttonPressed == Case4) {
          Case4.setLocation(100, 650);
          Case4.setEnabled(false);
        }
        else if (buttonPressed == Case5) {
          Case5.setLocation(100, 650);
          Case5.setEnabled(false);
        }
        else if (buttonPressed == Case6) {
          Case6.setLocation(100, 650);
          Case6.setEnabled(false);;
        }
        else if (buttonPressed == Case7) {
          Case7.setLocation(100, 650);
          Case7.setEnabled(false);
        }
        else if (buttonPressed == Case8) {
          Case8.setLocation(100, 650);
          Case8.setEnabled(false);
        }
        else if (buttonPressed == Case9) {
          Case9.setLocation(100, 650);
          Case9.setEnabled(false);
        }
        else if (buttonPressed == Case10) {
          Case10.setLocation(100, 650);
          Case10.setEnabled(false);
        }
        else if (buttonPressed == Case11) {
          Case11.setLocation(100, 650);
          Case11.setEnabled(false);
        }
        else if (buttonPressed == Case12) {
          Case12.setLocation(100, 650);
          Case12.setEnabled(false);
        }
        else if (buttonPressed == Case13) {
          Case13.setLocation(100, 650);
          Case13.setEnabled(false);
        }
        else if (buttonPressed == Case14) {
          Case14.setLocation(100, 650);
          Case14.setEnabled(false);
        }
        else if (buttonPressed == Case15) {
          Case15.setLocation(100, 650);
          Case15.setEnabled(false);
        }
        else if (buttonPressed == Case16) {
          Case16.setLocation(100, 650);
          Case16.setEnabled(false);
        }
        else if (buttonPressed == Case17) {
          Case17.setLocation(100, 650);
          Case17.setEnabled(false);
        }
        else if (buttonPressed == Case18) {
          Case18.setLocation(100, 650);
          Case18.setEnabled(false);
        }
        else if (buttonPressed == Case19) {
          Case19.setLocation(100, 650);
          Case19.setEnabled(false);
        }
        else if (buttonPressed == Case20) {
          Case20.setLocation(100, 650);
          Case20.setEnabled(false);
        }
        else if (buttonPressed == Case21) {
          Case21.setLocation(100, 650);
          Case21.setEnabled(false);
        }
        else if (buttonPressed == Case22) {
          Case22.setLocation(100, 650);
          Case22.setEnabled(false);
        }
        else if (buttonPressed == Case23) {
          Case23.setLocation(100, 650);
          Case23.setEnabled(false);
        }
        else if (buttonPressed == Case24) {
          Case24.setLocation(100, 650);
          Case24.setEnabled(false);
        }
        else if (buttonPressed == Case25) {
          Case25.setLocation(100, 650);
          Case25.setEnabled(false);
        }
        else if (buttonPressed == Case26) {
          Case26.setLocation(100, 650);
          Case26.setVisible(false);
        }
        CaseSelected = true;  // This realizes a case has been chosen then now is true for the else if statment
      }
      else if (CaseSelected && (casesClicked != 0)) {
        // For choosing 6 cases
        if (buttonPressed == Case1) {         
          Case1.setVisible(false);         // Makes the case dissapear
          casesClicked--;                 // subtracts a case in the counter so it knows how many cases left you have to choose in the round
          caseChosen[0] = true;              // 
          removeMoneyLabel(Double.parseDouble(moneyLabel[0].getText().substring(2)));       // Removes random money value the case is attached to.
        }
        else if (buttonPressed == Case2) {
          Case2.setVisible(false);
          casesClicked--;
          caseChosen[1] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[1].getText().substring(2)));
        }
        else if (buttonPressed == Case3) {
          Case3.setVisible(false);
          casesClicked--;
          caseChosen[2] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[2].getText().substring(2)));
        }
        else if (buttonPressed == Case4) {
          Case4.setVisible(false);
          casesClicked--;
          caseChosen[3] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[3].getText().substring(2)));
        }
        else if (buttonPressed == Case5) {
          Case5.setVisible(false);
          casesClicked--;
          caseChosen[4] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[4].getText().substring(2)));
        }
        else if (buttonPressed == Case6) {
          Case6.setVisible(false);
          casesClicked--;
          caseChosen[5] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[5].getText().substring(2)));
        }
        else if (buttonPressed == Case7) {
          Case7.setVisible(false);
          casesClicked--;
          caseChosen[6] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[6].getText().substring(2)));
        }
        else if (buttonPressed == Case8) {
          Case8.setVisible(false);
          casesClicked--;
          caseChosen[7] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[7].getText().substring(2)));
        }
        else if (buttonPressed == Case9) {
          Case9.setVisible(false);
          casesClicked--;
          caseChosen[8] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[8].getText().substring(2)));
        }
        else if (buttonPressed == Case10) {
          Case10.setVisible(false);
          casesClicked--;
          caseChosen[9] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[9].getText().substring(2)));
        }
        else if (buttonPressed == Case11) {
          Case11.setVisible(false);
          casesClicked--;
          caseChosen[10] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[10].getText().substring(2)));
        }
        else if (buttonPressed == Case12) {
          Case12.setVisible(false);
          casesClicked--;
          caseChosen[11] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[11].getText().substring(2)));
        }
        else if (buttonPressed == Case13) {
          Case13.setVisible(false);
          casesClicked--;
          caseChosen[12] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[12].getText().substring(2)));
        }
        else if (buttonPressed == Case14) {
          Case14.setVisible(false);
          casesClicked--;
          caseChosen[13] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[13].getText().substring(2)));
        }
        else if (buttonPressed == Case15) {
          Case15.setVisible(false);
          casesClicked--;
          caseChosen[14] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[14].getText().substring(2)));
        }
        else if (buttonPressed == Case16) {
          Case16.setVisible(false);
          casesClicked--;
          caseChosen[15] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[15].getText().substring(2)));
        }
        else if (buttonPressed == Case17) {
          Case17.setVisible(false);
          casesClicked--;
          caseChosen[16] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[16].getText().substring(2)));
        }
        else if (buttonPressed == Case18) {
          Case18.setVisible(false);
          casesClicked--;
          caseChosen[17] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[17].getText().substring(2)));
        }
        else if (buttonPressed == Case19) {
          Case19.setVisible(false);
          casesClicked--;
          caseChosen[18] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[18].getText().substring(2)));
        }
        else if (buttonPressed == Case20) {
          Case20.setVisible(false);
          casesClicked--;
          caseChosen[19] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[19].getText().substring(2)));
        }
        else if (buttonPressed == Case21) {
          Case21.setVisible(false);
          casesClicked--;
          caseChosen[20] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[20].getText().substring(2)));
        }
        else if (buttonPressed == Case22) {
          Case22.setVisible(false);
          casesClicked--;
          caseChosen[21] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[21].getText().substring(2)));
        }
        else if (buttonPressed == Case23) {
          Case23.setVisible(false);
          casesClicked--;
          caseChosen[22] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[22].getText().substring(2)));
        }
        else if (buttonPressed == Case24) {
          Case24.setVisible(false);
          casesClicked--;
          caseChosen[23] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[23].getText().substring(2)));
        }
        else if (buttonPressed == Case25) {
          Case25.setVisible(false);
          casesClicked--;
          caseChosen[24] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[24].getText().substring(2)));
        }
        else if (buttonPressed == Case26) {
          Case26.setVisible(false);
          casesClicked--;
          caseChosen[25] = true;
          removeMoneyLabel(Double.parseDouble(moneyLabel[25].getText().substring(2)));
        }
      }
      
      if (casesClicked == 0) {             // When the amount of cases for the round has been chosen the subroutine for deal or no deal will be used
        dealOrNoDeal();
        if(casesNeeded != 1) casesNeeded--;       // Each round it will ask for one less case until it eventually only keeps asking for one.
        casesClicked = casesNeeded;
        roundNum++;                             // Adds a round each time a round is over
      }                                          // if 2 cases left, case selected = how much money you won. show you left with amount of money message. 
    }
  }
  //****************************************************************************************************************************
  public ChristianReiDealorNoDeal() {           // GUI

    setLayout(null); //Create the window for the game play
    JLabel picLabel4 = new JLabel (new ImageIcon("yourcase.png"));     // The label at the bottom left to help the user experience. Says that the case beside it is theirs
    picLabel4.setLocation(25,470);
    picLabel4.setSize(150, 250);
    add(picLabel4);
   
    JLabel picLabel5 = new JLabel (new ImageIcon("welcomemessage.png"));     // Welcome message at the top
    picLabel5.setLocation(575,1);
    picLabel5.setSize(300, 100);
    add(picLabel5);
   
    
    //Each case size and location
    Case1.setLocation(375, 50);
    Case1.setSize(100, 100);
    add(Case1);
    Case1.addActionListener(new ButtonHandler());
    Case1.setVisible(true);
    
    Case2.setLocation(525, 50);
    Case2.setSize(100, 100);
    add(Case2);
    Case2.addActionListener(new ButtonHandler());
    Case2.setVisible(true);
    
    Case3.setLocation(675, 50);
    Case3.setSize(100, 100);
    add(Case3);
    Case3.addActionListener(new ButtonHandler());
    Case3.setVisible(true);
    
    Case4.setLocation(825, 50);
    Case4.setSize(100, 100);
    add(Case4);
    Case4.addActionListener(new ButtonHandler());
    Case4.setVisible(true);
    
    Case5.setLocation(975, 50);
    Case5.setSize(100, 100);
    add(Case5);
    Case5.addActionListener(new ButtonHandler());
    Case5.setVisible(true);
    
    Case6.setLocation(375, 175);
    Case6.setSize(100, 100);
    add(Case6);
    Case6.addActionListener(new ButtonHandler());
    Case6.setVisible(true);
    
    Case7.setLocation(525, 175);
    Case7.setSize(100, 100);
    add(Case7);
    Case7.addActionListener(new ButtonHandler());
    Case7.setVisible(true);
    
    Case8.setLocation(675, 175);
    Case8.setSize(100, 100);
    add(Case8);
    Case8.addActionListener(new ButtonHandler());
    Case8.setVisible(true);
    
    Case9.setLocation(825, 175);
    Case9.setSize(100, 100);
    add(Case9);
    Case9.addActionListener(new ButtonHandler());
    Case9.setVisible(true);
    
    Case10.setLocation(975, 175);
    Case10.setSize(100, 100);
    add(Case10);
    Case10.addActionListener(new ButtonHandler());
    Case10.setVisible(true);
    
    Case11.setLocation(375, 300);
    Case11.setSize(100, 100);
    add(Case11);
    Case11.addActionListener(new ButtonHandler());
    Case11.setVisible(true);
    
    Case12.setLocation(525, 300);
    Case12.setSize(100, 100);
    add(Case12);
    Case12.addActionListener(new ButtonHandler());
    Case12.setVisible(true);
    
    Case13.setLocation(675, 300);
    Case13.setSize(100, 100);
    add(Case13);
    Case13.addActionListener(new ButtonHandler());
    Case13.setVisible(true);
    
    Case14.setLocation(825, 300);
    Case14.setSize(100, 100);
    add(Case14);
    Case14.addActionListener(new ButtonHandler());
    Case14.setVisible(true);
    
    Case15.setLocation(975, 300);
    Case15.setSize(100, 100);
    add(Case15);
    Case15.addActionListener(new ButtonHandler());
    Case15.setVisible(true);
    
    Case16.setLocation(375, 425);
    Case16.setSize(100, 100);
    add(Case16);
    Case16.addActionListener(new ButtonHandler());
    Case16.setVisible(true);
    
    Case17.setLocation(525, 425);
    Case17.setSize(100, 100);
    add(Case17);
    Case17.addActionListener(new ButtonHandler());
    Case17.setVisible(true);
    
    Case18.setLocation(675, 425);
    Case18.setSize(100, 100);
    add(Case18);
    Case18.addActionListener(new ButtonHandler());
    Case18.setVisible(true);
    
    Case19.setLocation(825, 425);
    Case19.setSize(100, 100);
    add(Case19);
    Case19.addActionListener(new ButtonHandler());
    Case19.setVisible(true);
    
    Case20.setLocation(975, 425);
    Case20.setSize(100, 100);
    add(Case20);
    Case20.addActionListener(new ButtonHandler());
    Case20.setVisible(true);
    
    Case21.setLocation(375, 550);
    Case21.setSize(100, 100);
    add(Case21);
    Case21.addActionListener(new ButtonHandler());
    Case22.setVisible(true);
    
    Case22.setLocation(525, 550);
    Case22.setSize(100, 100);
    add(Case22);
    Case22.addActionListener(new ButtonHandler());
    Case22.setVisible(true);
    
    Case23.setLocation(675, 550);
    Case23.setSize(100, 100);
    add(Case23);
    Case23.addActionListener(new ButtonHandler());
    Case23.setVisible(true);
    
    Case24.setLocation(825, 550);
    Case24.setSize(100, 100);
    add(Case24);
    Case24.addActionListener(new ButtonHandler());
    Case24.setVisible(true);
    
    Case25.setLocation(975,550);
    Case25.setSize(100, 100);
    add(Case25);
    Case25.addActionListener(new ButtonHandler());
    Case25.setVisible(true);
    
    Case26.setLocation(675, 675);
    Case26.setSize(100, 100);
    add(Case26);
    Case26.addActionListener(new ButtonHandler());
    Case26.setVisible(true);
    
      for(int i = 0; i < 13; i++) {                  // For loop for where the money labels will be on the left side and what they will look like.
      moneyLabel[i].setLocation(50, 100 + 35 * i);        // X value stays the same but the y value goes up by 35 each time to space them correctly.   
      moneyLabel[i].setSize(100, 30);                     // the size for each label
      moneyLabel[i].setBackground(Color.YELLOW);        // Background behind words is yellow
      moneyLabel[i].setOpaque(true);                 // So you are able to see the yellow
      add(moneyLabel[i]);                       // Adding them onto the GUI
    }
    for(int i = 13; i < 26; i++) {                   // For loop for right side labels. the larger money values.
      moneyLabel[i].setLocation(1200, 100 + 35 * (i % 13));          // Locations for right side values
      moneyLabel[i].setSize(100, 30);
      moneyLabel[i].setBackground(Color.YELLOW);
      moneyLabel[i].setOpaque(true);
      add(moneyLabel[i]);
    }
    
    JLabel picLabel = new JLabel (new ImageIcon("backround.jpg"));     //This represents the picture in the background of the window
    picLabel.setLocation(0,0);           
    picLabel.setSize(1440, 850);
    add(picLabel);
    
    
    Random rgen = new Random();      // Rgen is a new variable of random
    for (int lastMoney = money.length - 1; lastMoney > 0; lastMoney--) {
      // Choose a random location from among 0,1,...,lastPlace.
      
      int randomNum = rgen.nextInt(money.length);
      // Swap items in locations randomNum and lastMoney.
      
      double temp = money[randomNum];          // temporary array
      money[randomNum] = money[lastMoney];
      money[lastMoney] = temp;
    }
  }
  
  public void dealOrNoDeal() {
   // banker's offer = average value * turn number / 10
    double bankersOffer;
    double total = 0;
    int casesLeft = 0;
    for(int i = 0; i < 26; i++) {            
      if(!caseChosen[i]) {           
        total += money[i];      // formulas change for amount of cases
        casesLeft++;

      }
    }
    bankersOffer = (total / casesLeft) * (roundNum / 10);            // Bankers formula
    bankersOffer =((int) (bankersOffer * 100)) / 100;
    String output = ("The Banker Offers $" + bankersOffer);      // Displays the bankers offer
    output = output + "\n Do you want to take the deal?";      // Asks if they want to take the deal
    playSound("PHONERING.wav");         // sound of phone ringing when the deal or no deal window comes up
    int selection = JOptionPane.showOptionDialog(null, output, "Deal or No Deal?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);  // Yes or no option window that is made for if they want to choose deal or no deal
    if(selection == 0) {
      playSound("APPLAUSE.wav");   // Appalause if they choose to take the deal
      int selection2 = JOptionPane.showOptionDialog(null, "You left with $" + bankersOffer + "\n Would you like to play again?" , "Deal Or No Deal?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
      if(selection2 == 0) restartGame();       // If they say they want to play again the restart game subroutine will happen
      else if(selection2 == 1) System.exit(0);      // If they don't want to play again the window closes
    }
    else {
      if((roundNum == 1) || (roundNum == 3) || (roundNum == 5) || (roundNum == 7) || (roundNum == 9)) {  //Every odd number of rounds it will play this sound
      playSound("NODEALHOWIE.wav");   //Responds with a no deal sound
      }
      else {    //Else it plays this sound
        playSound("AINTAMILLION.wav");  //Plays another no deal sound
      }
  }
  }
  
  public void removeMoneyLabel(double moneySelected) {       // Subroutine for removing money values
    for(int i = 0; i < 26; i++) {
      if (moneySelected == money[i]) {            // The money value equal to that value will dissapear
        moneyLabel[i].setVisible(false);         // Makes the label dissapear
      }
    }
  }
  
  public void playSound(String soundName)
  {
    try
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
      Clip clip = AudioSystem.getClip( );
      clip.open(audioInputStream);
      clip.start( );
    }
    catch(Exception ex)               // If music fails to play it will catch and say that there is an error
    {
      System.out.println("Error with playing sound.");
      ex.printStackTrace( );
    }
  }
  
  static JFrame window = new JFrame("Deal Or No Deal");           // Name of frame
  static ChristianReiDealorNoDeal panel = new ChristianReiDealorNoDeal();          // Panel plays the game
  public static void main(String[] args) {     // The main
    window.setContentPane(new ChristianReiDealorNoDeal());
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setSize(1440, 850);
    window.setLocation(0, 0);
    window.setVisible(true);
    try
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("THINKINGMUSIC.wav").getAbsoluteFile());
      Clip clip = AudioSystem.getClip( );
      clip.open(audioInputStream);
      clip.loop(clip.LOOP_CONTINUOUSLY  );    // Music loops continuosly all game
    }
    catch(Exception ex)       // Catches any errors
    {
      System.out.println("Error with playing sound.");
      ex.printStackTrace( );
    }
  
  }
  public void restartGame() {             // Restart the game subroutine
    Case1.setVisible(true);               // Make all the cases visible again
    Case2.setVisible(true);
    Case3.setVisible(true);
    Case4.setVisible(true);
    Case5.setVisible(true);
    Case6.setVisible(true);
    Case7.setVisible(true);
    Case8.setVisible(true);
    Case9.setVisible(true);
    Case10.setVisible(true);
    Case11.setVisible(true);
    Case12.setVisible(true);
    Case13.setVisible(true);
    Case14.setVisible(true);
    Case15.setVisible(true);
    Case16.setVisible(true);
    Case17.setVisible(true);
    Case18.setVisible(true);
    Case19.setVisible(true);
    Case20.setVisible(true);
    Case21.setVisible(true);
    Case22.setVisible(true);
    Case23.setVisible(true);
    Case24.setVisible(true);
    Case25.setVisible(true);
    Case26.setVisible(true);
    
    
    for(int i = 0; i < 26; i++) {              // Makes all the money values show up again
      caseChosen[i] = false;
      moneyLabel[i].setVisible(true);
    }
    Random rgen = new Random();               // Gives all the cases new random vlaues
    for (int lastMoney = money.length - 1; lastMoney > 0; lastMoney--) {
      // Choose a random location from among 0,1,...,lastPlace.
      
      int randomNum = rgen.nextInt(money.length);
      // Swap items in locations randomNum and lastMoney.
      
      double temp = money[randomNum];
      money[randomNum] = money[lastMoney];
      money[lastMoney] = temp;
    }
    casesNeeded = 6;              //Each case needed
    casesClicked = casesNeeded;   //How many times the case is clicked
    CaseSelected = false;     //Checks if these cases have been selected
    roundNum = 1;              //This will determine how many rounds there will be
    
    Case1.setLocation(375, 50);            // Makes all the buttons go back to original positions and be enabled
    Case1.setEnabled(true);
    Case2.setLocation(525, 50);   
    Case2.setEnabled(true);
    Case3.setLocation(675, 50);
    Case3.setEnabled(true);
    Case4.setLocation(825, 50);
    Case4.setEnabled(true);
    Case5.setLocation(975, 50);
    Case5.setEnabled(true);
    Case6.setLocation(375, 175);
    Case6.setEnabled(true);
    Case7.setLocation(525, 175);
    Case7.setEnabled(true);
    Case8.setLocation(675, 175);
    Case8.setEnabled(true);
    Case9.setLocation(825, 175);
    Case9.setEnabled(true);
    Case10.setLocation(975, 175);
    Case10.setEnabled(true);
    Case11.setLocation(375, 300);
    Case11.setEnabled(true);
    Case12.setLocation(525, 300);
    Case12.setEnabled(true);
    Case13.setLocation(675, 300);
    Case13.setEnabled(true);
    Case14.setLocation(825, 300);
    Case14.setEnabled(true);
    Case15.setLocation(975, 300);
    Case15.setEnabled(true);
    Case16.setLocation(375, 425);
    Case1.setEnabled(true);
    Case17.setLocation(525, 425);
    Case17.setEnabled(true);
    Case18.setLocation(675, 425);
    Case18.setEnabled(true);
    Case19.setLocation(825, 425);
    Case19.setEnabled(true);
    Case20.setLocation(975, 425);
    Case20.setEnabled(true);
    Case21.setLocation(375, 550);
    Case21.setEnabled(true);
    Case22.setLocation(525, 550);
    Case22.setEnabled(true);
    Case23.setLocation(675, 550);
    Case23.setEnabled(true);
    Case24.setLocation(825, 550);
    Case24.setEnabled(true);
    Case25.setLocation(975,550);
    Case25.setEnabled(true);
    Case26.setLocation(675, 675);
    Case26.setEnabled(true);
  }
}   // End of the program

