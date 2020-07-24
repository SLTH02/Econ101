//background color: Hex: D7E6DE

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;
import javax.imageio.*;
import java.util.Scanner;

import javax.swing.JOptionPane.*; //for the quiz/quit alert box

public class Econ101
{
    public static void main(String [] args)
    {
        Econ101 econ = new Econ101();
        econ.runIt();
    }

    public void runIt()
    {
        JFrame frame = new JFrame("Econ 101");
		JPanel panel = new JPanel();
		frame.setSize(1200,700);
		frame.setLocation(50,35);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		Econ101Game econ = new Econ101Game(frame);
		frame.getContentPane().add(econ);
		frame.setVisible(true);
    }
}

/**
 * Contains 4 classes: MainPage, GamePage, QuizPage, and ResultPage
 */
class Econ101Game extends JPanel
{
    //3 pages for CardLayout
    private MainPage mp;
    private EasyGamePage ep;
    private HardGamePage hp;
    private ResultPage rp;
    private QuizPage qp;

    private CardLayout cl;

    // 
    private static final int HNUMCARDS = 24;
    private static final int NUMCARDS = 12;

    //if the game level is easy or hard 
    private boolean isEasy;

    //Fonts used thoughout the game
    Font abhBold;
    Font abhRegular;

    //Background Images 
    private String gameBackName; //the simplest background
    private Image gameBackPic;
    private String mainBackName; //back ground image
    private Image mainBackPic;
    private String winBackName;
    private Image winBackPic;

    //keep track if user first time open the game. Need instruction
    private boolean firstPlay = true;

    //name of the instruction dialog box icon 
    private String InstIconName = new String("Money.png");

    //know which type of questions images
    private String questionType;

    public Econ101Game(JFrame frame)
    {
        createFonts();
        isEasy = true;
        questionType = new String();

        //background Images
        gameBackName = new String("GameBackground.png");
        gameBackPic = getImage(gameBackName);

       
        mainBackName = new String("MainBackground.png");
        mainBackPic = getImage(mainBackName);

        winBackName = new String("ResultBackground.png");
        winBackPic = getImage(winBackName);

        mp = new MainPage(this);
        ep = new EasyGamePage(this);
        hp = new HardGamePage(this);

        qp = new QuizPage(this);
        rp = new ResultPage(this);
        
        cl = new CardLayout();
        setLayout(cl);

        add(mp, "MainPage");
        add(ep, "EasyGamePage");
        add(hp, "HardGamePage");
        add(rp, "ResultPage");

        add(qp, "QuizPage");

        cl.show(this, "MainPage");
    }

    /**
     * Create the fonts
     */
    public void createFonts()
    {
        try 
        {
            abhBold = Font.createFont(Font.TRUETYPE_FONT, new File("AbhayaLibre-ExtraBold.ttf")).deriveFont(150f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(abhBold);
        } 
        catch (IOException|FontFormatException e) 
        {
            System.err.println("Cannot use font AbhayaLibre-ExtraBold");
            e.printStackTrace();
        }

        try 
        {
            abhRegular = Font.createFont(Font.TRUETYPE_FONT, new File("AbhayaLibre-Regular.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(abhRegular);
        } 
        catch (IOException|FontFormatException e) 
        {
            System.err.println("Cannot use font AbhayaLibre-Regular.ttf");
            e.printStackTrace();
        }
    }

    /**
     * Helper method. Get the images
     * 
     * @param fileName      the name of the image file 
     * @param image         the image object of the image 
     * 
     * @return the already read image file
     */
    public Image getImage(String fileName)
    {
        File file = new File(fileName);
        try
        {
            Image image = ImageIO.read(file);
            return image;
        }
        catch(IOException e)
        {
            System.err.println("\n\n" + 
                fileName + " cannot be found");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Show dialog box about the game instruction
     */
    public void showInstruction()
    {
        String [] option = {"Go to game"};
        String inst2 = "Turn over pairs of matching cards until all the cards are revealed.";
        String inst3l1 = "Each time you pick two same cards,";
        String inst3l2 = "it will lead to an Economics-related quiz corresponding with that graphic.";

        String inst3l3 = "- Balance Scale: Comparative/Absolute Advantage.";
        String inst3l4 = "- Man: Keynesian Economics.";
        String inst3l5 = "- Money: National Income Accounting.";
        String inst3l6 = "- Helmet: labor force";

        String inst4l1 = "After you get a question right, it will bring you back to the cards game.";
        String inst4l2 = "Repeat these steps until you turn over all the cards.";
        String inst5 = "(You can go back to this instruction by clicking the \"?\" button)";
        JOptionPane.showOptionDialog(null, inst2 + "\n\n" + inst3l1 + "\n" + inst3l2 + "\n\n\t\t\t" + inst3l3 + "\n\t\t\t" + inst3l4 + "\n\t\t\t" + inst3l5 + "\n\t\t\t" + inst3l6 + "\n\n" + inst4l1 + "\n" + inst4l2 + "\n\n" + inst5, "Econ Card Memo Game Direction", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(InstIconName), option, option[0]);
    }

    /**
     * Uses GridLayout
     */
    class MainPage extends JPanel
    {
        private Econ101Game econ101; 

        public MainPage(Econ101Game econ101In)
        {
            setLayout(new GridLayout(3, 1));

            econ101 = econ101In;

            MainTitle title = new MainTitle();
            MainSize size = new MainSize();
            MainPlay play = new MainPlay();

            add(title);
            add(size);
            add(play);
        }
        
        public void paintComponent(Graphics g)
        {
            if (mainBackPic != null)
            {
                g.drawImage(mainBackPic,0,0,1200,700,this);
            }    
        }

        class MainTitle extends JPanel
        {
            public void paintComponent(Graphics g)
            {
                g.setColor(new Color(3, 41, 35));
                g.setFont(abhBold);
                g.drawString("Econ 101", 325, 175);
            }
        }

        class MainSize extends JPanel implements ChangeListener
        {
            private JSlider levels;
            private String levelStr;

            public MainSize()
            {
                setLayout(null);
                setOpaque(false);
                levelStr = new String("Easy");

                levels = new JSlider(JSlider.VERTICAL, 0, 100, 0);
                levels.setBounds(775, 0, 100, 200);
                add(levels);
               
                levels.addChangeListener(this);
            }

            public void stateChanged(ChangeEvent evt)
            {
                int value = levels.getValue();

                if (value < 50)
                {
                    levelStr = "Easy";
                    isEasy = true;
                }
                else
                {
                    levelStr = "Hard";
                    isEasy = false;
                }
                repaint();
            }

            public void paintComponent(Graphics g)
            {
                g.setColor(new Color(20, 64, 62));
                g.setFont(abhBold.deriveFont(50f));
                g.drawString("Step1: Board Size", 250, 100);

                g.setFont(abhRegular.deriveFont(40f));
                g.drawString(levelStr, 675, 100);
            }
        }

        class MainPlay extends JPanel implements ActionListener
        {
            private JButton playButton;
            private String playButtonName;
            public MainPlay()
            {
                setLayout(null);
                this.setOpaque(false);
                playButtonName = new String("PlayIcon.png");
                playButton = new JButton(new ImageIcon(playButtonName));
                playButton.setActionCommand("PLAY");
                playButton.setBounds(940, 10, 130, 78);
                playButton.setFont(abhRegular);
                add(playButton);

                playButton.addActionListener(this);
            }

            public void paintComponent(Graphics g)
            {
                g.setColor(new Color(20, 64, 62));
                g.setFont(abhBold.deriveFont(50f));
                g.drawString("Step2: Start!", 650, 60);
            }

            public void actionPerformed(ActionEvent evt)
            {
                if (evt.getActionCommand().equals("PLAY"))
                {
                    if (isEasy)
                    {
                        cl.show(econ101, "EasyGamePage");
                    }
                    else
                    {
                        cl.show(econ101, "HardGamePage");
                    }
                    
                    if (firstPlay)
                    {
                        showInstruction();
                        firstPlay = false;
                    }
                }
            }
        }
    }

    class HardGamePage extends JPanel
    {
        private Econ101Game econ101;
        private HGameNorth hnorth; 
        private HGameCenter hcenter;
        
        public HardGamePage(Econ101Game econ101In)
        {
           setLayout(new BorderLayout()); 
           econ101 = econ101In;

           hnorth = new HGameNorth();
           hcenter = new HGameCenter();

           add(hnorth, BorderLayout.NORTH);
           add(hcenter, BorderLayout.CENTER);
        } 

        //paint background image
        public void paintComponent(Graphics g)
        {
            if (gameBackPic != null)
            {
                g.drawImage(gameBackPic,0,0,1200,700,this);
            }
        }

        //the quit and help buttons
        class HGameNorth extends JPanel implements ActionListener
        {
            private JButton quitButton;
            private JButton helpButton;
            
            //the sad face icon when user wanna quit game
            private String quitIconName = new String("SadFace.png");
            private String quitButtonName = new String("QuitButton.png");
            private String helpButtonName  = new String("HelpButton.png");

            public HGameNorth()
            {
                setOpaque(false);
                setLayout(new BorderLayout());

                quitButton = new JButton(new ImageIcon(quitButtonName));
                quitButton.setBorderPainted(false);
                quitButton.setActionCommand("X");
                quitButton.setOpaque(false);

                helpButton = new JButton(new ImageIcon(helpButtonName));
                helpButton.setBorderPainted(false);
                helpButton.setActionCommand("?");
                helpButton.setOpaque(false);

                add(quitButton, BorderLayout.WEST);
                add(helpButton, BorderLayout.EAST);

                quitButton.addActionListener(this);
                helpButton.addActionListener(this);
            }

            public void actionPerformed(ActionEvent evt)
            {
                String clicked = evt.getActionCommand();

                if (clicked == "?")
                {
                    showInstruction();
                }
                else if (clicked == "X")
                {
                    String message = new String("Are you sure you want to quit Econ101 -- Hard? Your records will be lost.");
                    String []options = {"YES :(", "NEVERMIND :)"};
                    int returned = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(quitIconName), options, options[1]);

                    if (returned == 0)
                    {
                        for (int i = 0; i < HNUMCARDS; i++)
                        {
                            hcenter.hSetCardBack(i);
                        }
                        hcenter.hRestart(true); //set all related fields and counters to 0
                        cl.show(econ101, "MainPage"); 

                        hcenter.hEnableCards();
                        hcenter.hSetCards();
                    }
                }
            }
        }

        //where the cards are 
        class HGameCenter extends JPanel implements ActionListener
        {
            //the front of the cards. There are 4 types
            private Image advantage;
            private String advName;

            private Image keynes;
            private String keyName;

            private Image labor;
            private String labName;

            private Image gdp;
            private String gdpName;

            private Image cardBack;
            private String bacName;

            JButton [] hardButton; //array of buttons/cards
            JButton [] chosenButtons = new JButton[3]; //only 3 cards can be chosen at a time

            int counter = 0; //count how many cards are chosen per 3 clicks
            //count how many adv, gdp, key, and lab cards are chosen 
            int advCount2 = 0; 
            int gdpCount2 = 0;
            int keyCount2 = 0;
            int labCount2 = 0;
            int tripleCount = 0; //how many triples are found

            public HGameCenter()
            {
                setOpaque(false);
                setLayout(new GridLayout(3, 8, 0, 0));

                //images initializations
                advName = new String("Advantage.jpg");
                keyName = new String("Keynes.jpg");
                labName = new String("Labor.jpg");
                gdpName = new String("GDP.jpg");
                bacName = new String("PokerBack.jpg");

                //get images
                advantage = getImage(advName);
                keynes = getImage(keyName);
                labor = getImage(labName);
                gdp = getImage(gdpName);
                cardBack = getImage(bacName);

                hardButton = new JButton[24];

                hSetCards();   

                //add all the buttons into the grids
                for (int i = 0; i < hardButton.length; i++)
                {
                    add(hardButton[i]);
                }
            }

            /**
             * makes the card looks right, make sure 3 cards/type. set the correct action 
             * command to each card 
             */
            public void hSetCards()
            {
                //these counts count how many cards had been placed 
                int advCount = 0;
                int keyCount = 0;
                int labCount = 0;
                int gdpCount = 0;

                //the name of the 4 types of cards
                String [] chooseCard = {"advantage", "keynes", "labor", "gdp"};
                int randInd = 0;
                boolean set;

                //instantiate & set command for each button
                for (int i = 0; i < hardButton.length; i++)
                {
                    hardButton[i] = new JButton(new ImageIcon(cardBack));

                    hardButton[i].setBorder(BorderFactory.createEmptyBorder());
                    hardButton[i].setContentAreaFilled(false);
                    hardButton[i].addActionListener(this);

                    randInd = (int)(Math.random() * 4);

                    //different random # represents different types of cards
                    set = false;
                    while (!set)
                    {
                        if (randInd == 0 && advCount < 6)
                        {
                            hardButton[i].setActionCommand("advantage");
                            advCount++;
                            set = true;
                        }
                        else if (randInd == 1 && keyCount < 6)
                        {
                            hardButton[i].setActionCommand("keynes");
                            keyCount++;
                            set = true;
                        }
                        else if (randInd == 2 && labCount < 6)
                        {
                            hardButton[i].setActionCommand("labor");
                            labCount++;
                            set = true;
                        }
                        else if (randInd == 3 && gdpCount < 6)
                        {
                            hardButton[i].setActionCommand("gdp");
                            gdpCount++;
                            set = true;
                        }

                        randInd = (int)(Math.random() * 4);
                    }
                }
            }

            public void actionPerformed(ActionEvent evt)
            {
                boolean sameCard = false; //make sure clicking same card multiple
                                            //times doesn't count as more than once
                for (int i = 0; i < 3; i++)
                {
                    if (evt.getSource() == chosenButtons[i])
                    {
                        sameCard = true;
                        break;
                    }
                    
                }

                if (!sameCard)
                {
                    //flip the cards to front side
                    if (evt.getActionCommand() == "advantage")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(advantage));
                        advCount2++;
                    }
                    else if(evt.getActionCommand() == "keynes")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(keynes));
                        keyCount2++;

                    }
                    else if(evt.getActionCommand() == "labor")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(labor));
                        labCount2++;
                    }
                    else if(evt.getActionCommand() == "gdp")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(gdp));
                        gdpCount2++;
                    }
                    chosenButtons[counter] = (JButton)evt.getSource();
                    counter ++;
                }

                if (counter == 3) //after clicking this card, 3 cards are chosen
                { 
                    if (advCount2 == 3)
                    {
                        questionType = "adv";
                        qp.chooseQuesImg();
                        tripleCount++;
                        cl.show(econ101, "QuizPage");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else if (gdpCount2 == 3)
                    {
                        questionType = "gdp";
                        qp.chooseQuesImg();
                        tripleCount++;
                        cl.show(econ101, "QuizPage");

                        System.out.println("hi");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else if (keyCount2 == 3)
                    {
                        questionType = "key";
                        qp.chooseQuesImg();
                        tripleCount++;
                        cl.show(econ101, "QuizPage");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else if (labCount2 ==3)
                    {
                        questionType = "lab";
                        qp.chooseQuesImg();
                        tripleCount++;
                        cl.show(econ101, "QuizPage");

                       // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else 
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            chosenButtons[i].setIcon(new ImageIcon(cardBack));
                        }
                    }
                    hRestart(false);
                } 
                
                //if all the cards are found 
                if (tripleCount == 8)
                {
                    cl.show(econ101, "ResultPage");
                    hEnableCards();

                    for (int i = 0; i < HNUMCARDS; i++)
                    {
                       hSetCardBack(i);
                    }

                    hSetCards();
                    hRestart(true);
                }
            }

         /////HELPER METHODS////////
            /**
             *  Initialize all related fields to 0. 
             *  Cleat out the record. Good for didn't choose the correct 3 cards
             */
            public void hRestart(boolean clearAll)
            {
                advCount2 = 0;
                gdpCount2 = 0;
                keyCount2 = 0;
                labCount2 = 0;
                counter = 0;

                //clear chosenButton
                for (int i = 0; i < 3; i++)
                {
                    chosenButtons[i] = null;
                }

                // if clear tripleCount field variable or not 
                if (clearAll)
                {
                    tripleCount = 0;
                }
            }

            public void hEnableCards()
            {
                // Enable all the buttons/cards
                for (int i = 0; i < hardButton.length; i++)
                {
                    hardButton[i].setEnabled(true);
                }
                repaint();
            }

            /**
             * Set all the cards, or button, to the cardBack image
             * 
             * @param   ind     the index of the card/button array 
             *                  to set to the back
             */
            public void hSetCardBack(int ind)
            {
                if (ind < hardButton.length)
                {
                    hardButton[ind].setIcon(new ImageIcon(cardBack));
                }
                else 
                {
                    System.err.print("Class \"Game Center\" method getButton() argument "
                        + "inappropriate, argument: "  + ind);
                }
            }

            public JButton[] getCardsArray()
            {
                return hardButton;
            }
        }
    }

    class EasyGamePage extends JPanel
    {
        private Econ101Game econ101;
        private GameNorth north; 
        private GameCenter center;
        
        public EasyGamePage(Econ101Game econ101In)
        {
           setLayout(new BorderLayout()); 
           econ101 = econ101In;

           north = new GameNorth();
           center = new GameCenter();

           add(north, BorderLayout.NORTH);
           add(center, BorderLayout.CENTER);
        } 

        public void paintComponent(Graphics g)
        {
            if (gameBackPic != null)
            {
                g.drawImage(gameBackPic,0,0,1200,700,this);
            }
        }

        //the quit and help buttons
        class GameNorth extends JPanel implements ActionListener
        {
            private JButton quitButton;
            private JButton helpButton;
            
            //the sad face icon when user wanna quit game
            private String quitIconName = new String("SadFace.png");
            private String quitButtonName = new String("QuitButton.png");
            private String helpButtonName  = new String("HelpButton.png");

            public GameNorth()
            {
                setLayout(new BorderLayout());
                setOpaque(false);

                quitButton = new JButton(new ImageIcon(quitButtonName));
                quitButton.setBorderPainted(false);
                quitButton.setActionCommand("X");
                quitButton.setOpaque(false);

                helpButton = new JButton(new ImageIcon(helpButtonName));
                helpButton.setBorderPainted(false);
                helpButton.setActionCommand("?");
                helpButton.setOpaque(false);

                add(quitButton, BorderLayout.WEST);
                add(helpButton, BorderLayout.EAST);

                quitButton.addActionListener(this);
                helpButton.addActionListener(this);
            }

            public void actionPerformed(ActionEvent evt)
            {
                String clicked = evt.getActionCommand();

                if (clicked == "?")
                {
                    showInstruction();
                }
                else if (clicked == "X")
                {
                    String message = new String("Are you sure you want to quit Econ101 -- Easy? Your records will be lost.");
                    String []options = {"YES :(", "NEVERMIND :)"};
                    int returned = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(quitIconName), options, options[1]);

                    if (returned == 0)
                    {
                        for (int i = 0; i < NUMCARDS; i++)
                        {
                            center.setCardBack(i);
                        }
                        center.restart(true); //set all related fields and counters to 0
                        center.enableCards();
                        cl.show(econ101, "MainPage"); 
                    }
                }
            }
        }

        //where the cards are 
        class GameCenter extends JPanel implements ActionListener
        {
            //the front of the cards. There are 4 types
            private Image advantage;
            private String advName;

            private Image keynes;
            private String keyName;

            private Image labor;
            private String labName;

            private Image gdp;
            private String gdpName;

            private Image cardBack;
            private String bacName;

            private Image cardBackRed;
            private String backRName;

            JButton [] easyButton; //array of buttons/cards
            JButton [] chosenButtons = new JButton[3]; //only 3 cards can be chosen at a time

            int counter = 0; //count how many cards are chosen per 3 clicks
            //count how many adv, gdp, key, and lab cards are chosen 
            int advCount2 = 0; 
            int gdpCount2 = 0;
            int keyCount2 = 0;
            int labCount2 = 0;
            int tripleCount = 0; //how many triples are found

            public GameCenter()
            {
                setOpaque(false);
                setLayout(new GridLayout(3, 4, 0, 0));

                //images initializations
                advName = new String("Advantage.jpg");
                keyName = new String("Keynes.jpg");
                labName = new String("Labor.jpg");
                gdpName = new String("GDP.jpg");
                bacName = new String("PokerBack.jpg");
                backRName = new String("PokerBackRed.jpg");

                //get images
                advantage = getImage(advName);
                keynes = getImage(keyName);
                labor = getImage(labName);
                gdp = getImage(gdpName);
                cardBack = getImage(bacName);
                cardBackRed = getImage(backRName);

                easyButton = new JButton[12];

                setCards(); 

                //add all the buttons into the grids
                for (int i = 0; i < 12; i++)
                {
                    add(easyButton[i]);
                }
            }

            /**
             * makes the card looks right, make sure 3 cards/type. set the correct action 
             * command to each card 
             */
            public void setCards()
            {
                //these counts count how many cards had been placed 
                int advCount = 0;
                int keyCount = 0;
                int labCount = 0;
                int gdpCount = 0;

                //the name of the 4 types of cards
                String [] chooseCard = {"advantage", "keynes", "labor", "gdp"};
                int randInd = 0;
                boolean set;

                // instantiate & set command for each button
                //  make sure gives each card a randomized symbol
                for (int i = 0; i < easyButton.length; i++)
                {
                    easyButton[i] = new JButton(new ImageIcon(cardBack));

                    easyButton[i].setBorder(BorderFactory.createEmptyBorder());
                    easyButton[i].setContentAreaFilled(false);
                    easyButton[i].addActionListener(this);

                    randInd = (int)(Math.random() * 4);

                    set = false;
                    while (!set)
                    {
                        if (randInd == 0 && advCount < 3)
                        {
                            easyButton[i].setActionCommand("advantage");
                            advCount++;
                            set = true;
                        }
                        else if (randInd == 1 && keyCount < 3)
                        {
                            easyButton[i].setActionCommand("keynes");
                            keyCount++;
                            set = true;
                        }
                        else if (randInd == 2 && labCount < 3)
                        {
                            easyButton[i].setActionCommand("labor");
                            labCount++;
                            set = true;
                        }
                        else if (randInd == 3 && gdpCount < 3)
                        {
                            easyButton[i].setActionCommand("gdp");
                            gdpCount++;
                            set = true;
                        }

                        randInd = (int)(Math.random() * 4);
                    }
                }
            }

            public void actionPerformed(ActionEvent evt)
            {
                boolean sameCard = false; //make sure clicking same card multiple
                                            //times doesn't count as more than once
                for (int i = 0; i < 3; i++)
                {
                    if (evt.getSource() == chosenButtons[i])
                    {
                        sameCard = true;
                        break;
                    }
                    
                }

                if (!sameCard)
                {
                    //flip the cards to front side
                    if (evt.getActionCommand() == "advantage")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(advantage));
                        advCount2++;
                        System.out.println("show");
                    }
                    else if(evt.getActionCommand() == "keynes")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(keynes));
                        keyCount2++;
                        System.out.println("show");
                    }
                    else if(evt.getActionCommand() == "labor")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(labor));
                        labCount2++;
                        System.out.println("show");
                    }
                    else if(evt.getActionCommand() == "gdp")
                    {
                        ((JButton)evt.getSource()).setIcon(new ImageIcon(gdp));
                        gdpCount2++;
                        System.out.println("show");
                    }
                    chosenButtons[counter] = (JButton)evt.getSource();
                    counter ++;
                }

                if (counter == 3) //after clicking this card, 3 cards are chosen
                { 
                    if (advCount2 == 3)
                    {
                        tripleCount++;
                        questionType = "adv";
                        qp.chooseQuesImg();
                        cl.show(econ101, "QuizPage");
                        
                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);

                    }
                    else if (gdpCount2 == 3)
                    {
                        tripleCount++;
                        questionType = "gdp";
                        qp.chooseQuesImg();
                        cl.show(econ101, "QuizPage");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else if (keyCount2 == 3)
                    {
                        tripleCount++;
                        questionType = "key";
                        qp.chooseQuesImg();
                        cl.show(econ101, "QuizPage");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else if (labCount2 ==3)
                    {
                        tripleCount++;
                        questionType = "lab";
                        qp.chooseQuesImg();
                        cl.show(econ101, "QuizPage");

                        // Disable all the 3 buttons
                        chosenButtons[0].setEnabled(false);
                        chosenButtons[1].setEnabled(false);
                        chosenButtons[2].setEnabled(false);
                    }
                    else 
                    {
                         //if three cards are not the same, flip back 
                        for (int i = 0; i < 3; i++)
                        {
                            chosenButtons[i].setIcon(new ImageIcon(cardBack));
                        }

                    }
                    restart(false);
                } 
                
                //if all the cards are found 
                if (tripleCount == 4)
                {
                    cl.show(econ101, "ResultPage");

                    enableCards();

                    for (int i = 0; i < NUMCARDS; i++)
                    {
                        setCardBack(i);
                    }

                    setCards();
                    restart(true);
                }
            }

         /////HELPER METHODS////////
            /**
             *  Initialize all related fields to 0. 
             *  Cleat out the record
             */
            public void restart(boolean clearAll)
            {
                advCount2 = 0;
                gdpCount2 = 0;
                keyCount2 = 0;
                labCount2 = 0;
                counter = 0;

                //clear chosenButton
                for (int i = 0; i < 3; i++)
                {
                    chosenButtons[i] = null;
                }

                if (clearAll)
                {
                    tripleCount = 0;
                }
            }

            public void enableCards()
            {
                // Enable all the buttons/cards
                for (int i = 0; i < easyButton.length; i++)
                {
                    easyButton[i].setEnabled(true);
                }
                repaint();
            }

            /**
             * Set all the cards, or button, to the cardBack image
             * 
             * @param   ind     the index of the card/button array 
             *                  to set to the back
             */
            public void setCardBack(int ind)
            {
                if (ind < easyButton.length)
                {
                    easyButton[ind].setIcon(new ImageIcon(cardBack));
                }
                else 
                {
                    System.err.print("Class \"Game Center\" method getButton() argument "
                        + "inappropriate, argument: "  + ind);
                }
            }

            public JButton[] getCardsArray()
            {
                return easyButton;
            }
        }
    }

    class QuizPage extends JPanel
    {
        private Buttons response; //class that holds the radio-buttons
        private Econ101Game econ101;

        private int randImgInd; //the index of the image
        private Image[] quesImg = new Image[41]; //stores all question images

        private boolean coverExp; //if the explanation should be covered. uncovered when submit is clicked
        private boolean answered = false; //if the user answered the question. "disable" the submit button if not
        private boolean correct = false;
        private boolean showWrong = false;

        private String chosen = new String(); //the string chosen at the time
        private String correctAnswer = new String();

        private String celebrateImg = new String("correctSymbol.png");
        private String wrongImg = new String("wrongSymbol.png");
        public QuizPage(Econ101Game econ101In)
        {
            econ101 = econ101In;

            randImgInd = 0;
            coverExp = true;

            setLayout(null);
            response = new Buttons();
            response.setBounds(700, 80, 300, 200);
            add(response);

            openQuesImg();
            chooseQuesImg();

        }

        class Buttons extends JPanel 
        {
            private SubmitB sb; //also includes the go back to game button
            private RadioB rb;
            public Buttons()
            {
                setOpaque(false);
                setLayout(new GridLayout(1, 2));

                sb = new SubmitB();
                rb = new RadioB();

                add(rb);
                add(sb);
            }

            class SubmitB extends JPanel implements ActionListener
            {
                private JButton changePageButton;
                private JButton switchPage;

                public SubmitB()
                {
                    setOpaque(false);
                    setLayout(new GridLayout(5, 1));
                    changePageButton = new JButton("Submit");

                    //blank cells
                    add(new JLabel());
                    add(new JLabel());
                    add(new JLabel());
                    add(changePageButton, BorderLayout.CENTER);

                    changePageButton.addActionListener(this);
                }

                public void actionPerformed(ActionEvent evt)
                {
                    if (answered && evt.getActionCommand().equals("Submit"))
                    {
                        coverExp = false;
                        qp.repaint();

                        //check if the user choose the right answer
                        if (correctAnswer.equals(chosen))
                        {
                            correct = true;
                            changePageButton.setText("Back to Game");
                        }
                        else
                        {
                            showWrong = true;
                            changePageButton.setText("Next Question");
                        }

                        // Disable all the chocies for this question
                        rb.getA().setEnabled(false);
                        rb.getB().setEnabled(false);
                        rb.getC().setEnabled(false);
                        rb.getD().setEnabled(false);
                    }
                    else if (evt.getActionCommand().equals("Back to Game"))
                    {
                        showWrong = false;
                        if (isEasy)
                        {
                            cl.show(econ101, "EasyGamePage");
                        }
                        else
                        {
                            cl.show(econ101, "HardGamePage");
                        }

                        //cover the expanation and hide the switchPage button
                        coverExp = true;
                        rb.unselectButton();
                        qp.repaint();
                        answered = false;
                        correct = false;
                        changePageButton.setText("Submit");

                        // Enable all the answer choices, originally 
                        // disabled from last question
                        rb.getA().setEnabled(true);
                        rb.getB().setEnabled(true);
                        rb.getC().setEnabled(true);
                        rb.getD().setEnabled(true);
                    }
                    else if (evt.getActionCommand().equals("Next Question"))
                    {
                        showWrong = false;
                        int temp = randImgInd;
                        while (temp == randImgInd)
                        {
                            chooseQuesImg();
                        }

                        //cover the expanation and hide the switchPage button
                        coverExp = true;
                        rb.unselectButton();
                        qp.repaint();
                        answered = false;
                        correct = false;
                        changePageButton.setText("Submit");

                        // Enable all the answer choices, originally 
                        // disabled from last question
                        rb.getA().setEnabled(true);
                        rb.getB().setEnabled(true);
                        rb.getC().setEnabled(true);
                        rb.getD().setEnabled(true);
                    }
                }
            } 

            class RadioB extends JPanel implements ActionListener
            {
                private JRadioButton a;
                private JRadioButton b;
                private JRadioButton c;
                private JRadioButton d;
    
                private ButtonGroup group;

                public RadioB()
                {
                    setOpaque(false);
                    setLayout(new GridLayout(5, 1));

                    a = new JRadioButton("A");
                    b = new JRadioButton("B");
                    c = new JRadioButton("C");
                    d = new JRadioButton("D");

                    group = new ButtonGroup();

                    group.add(a);
                    group.add(b);
                    group.add(c);
                    group.add(d);

                    add(a);
                    add(b);
                    add(c);
                    add(d);
                    add(new JLabel());

                    a.addActionListener(this);
                    b.addActionListener(this);
                    c.addActionListener(this);
                    d.addActionListener(this);
                }

                public void actionPerformed(ActionEvent evt)
                {
                    answered = true;
                    chosen = evt.getActionCommand();

                    //assign the right index to its correct answer choices
                    int lastDig = randImgInd % 10;
                    if (lastDig >= 1 && lastDig <= 3)
                    {
                        correctAnswer = "A";
                    }
                    else if (lastDig >= 4 && lastDig <= 6)
                    {
                        correctAnswer = "B";
                    }
                    else if (lastDig >= 7 && lastDig <= 9)
                    {
                        correctAnswer = "C";
                    }
                    else if (lastDig == 0)
                    {
                        correctAnswer = "D";
                    }                
                }

                //HELPER METHOD
                public void unselectButton()
                {
                    group.clearSelection();
                }

                // Getters 
                public JRadioButton getA()
                {
                    return a;
                }

                public JRadioButton getB()
                {
                    return b;
                }

                public JRadioButton getC()
                {
                    return c;
                }

                public JRadioButton getD()
                {
                    return d;
                }

            }
        }

        public void openQuesImg()
        {
            //0 is null
            for (int i = 1; i <= 40; i++)
            {
                quesImg[i] = getImage("Slide" + i + ".png");
            }
        }

        //gdp: ques1-10
        //adv: ques11-20
        //lab: ques21-30
        //key: ques31-40

        /*last digit:
        1-3: A
        4-6: B
        7-9: C
        0: D
        */
        public void chooseQuesImg()
        {
            int startNum = 0;
            if (questionType.equals("adv"))
            {
                startNum = 10;
            }
            else if (questionType.equals("lab"))
            {
                startNum = 20;
            }
            else if (questionType.equals("key"))
            {
                startNum = 30;
            }

            randImgInd = (int)(Math.random() * 10)+ startNum + 1;
        }

        public void paintComponent(Graphics g)
        {
            //paint question + background image
            if (quesImg[randImgInd] != null)
            {
                g.drawImage(quesImg[randImgInd],0,0,1200,700,this);
            }   

            if (correct && getImage(celebrateImg) != null)
            {
                g.drawImage(getImage(celebrateImg),850,30,150,160,this);
            }
            else if (showWrong && getImage(wrongImg) != null)
            {
                g.drawImage(getImage(wrongImg),870,60,100,100,this);
            }

            if (coverExp)
            {
                g.drawImage(getImage("CoverExp.png"), 600, 300, 700, 250, this);
            }
        }
    }

    class ResultPage extends JPanel 
    {
        private Econ101Game econ101;

        private ResultLeft rl;
        private ResultRight rr;

        public ResultPage(Econ101Game econ101In)
        {
           setLayout(new GridLayout(1, 2));
           econ101 = econ101In;
        
           rl = new ResultLeft();
           rr = new ResultRight();

           add(rl);
           add(rr);
        } 

        public void paintComponent(Graphics g)
        {
            if (gameBackPic != null)
            {
                g.drawImage(winBackPic,0,0,1200,700,this);
            }   

            if (getImage("ResultGraph.png") != null)
            {
                g.drawImage(getImage("ResultGraph.png"),100,50,430,400, this);
            }

            g.setColor(new Color(3, 41, 35));
            g.setFont(abhBold.deriveFont(100f));
            g.drawString("You Won!", 700, 190);
        }

        class ResultLeft extends JPanel
        {
            public ResultLeft()
            {
                setOpaque(false);
            }
        }

        class ResultRight extends JPanel implements ActionListener
        {
            //Buttons
            private JButton mainPageButton;
            private String mainPageButtonName;
            private JButton playAgainButton; 
            private String playAgainButtonName;

            public ResultRight()
            {
                setLayout(null);
                setOpaque(false);
                mainPageButtonName = new String("MainPageButton.png");
                mainPageButton = new JButton(new ImageIcon(mainPageButtonName));
                mainPageButton.setBounds(320, 330, 130, 70);
                mainPageButton.setActionCommand("MainPage");
     
                playAgainButtonName = new String("PlayAgainButton.png");
                playAgainButton = new JButton(new ImageIcon(playAgainButtonName));
                playAgainButton.setBounds(130, 330, 130, 70);
                playAgainButton.setActionCommand("PlayAgain");
     
                add(mainPageButton);
                add(playAgainButton);
     
                mainPageButton.addActionListener(this);
                playAgainButton.addActionListener(this);
            }

            public void actionPerformed(ActionEvent evt)
            {
                String clicked = evt.getActionCommand();
                if (clicked == "MainPage")
                {
                    cl.show(econ101, "MainPage");
                }
                else if (clicked == "PlayAgain")
                {
                    //Go back to the same level the player chose last time
                    if (isEasy)
                    {
                        cl.show(econ101, "EasyGamePage");
                    }
                    else
                    {
                        cl.show(econ101, "HardGamePage");
                    } 
                }
            }
        } 
    }
}