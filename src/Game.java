import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;

import javalib.worldimages.*;

// main class
class Game extends World {
    int width;
    int height;
    int cardWidth;
    int cardHeight;
    int counter;
    Posn difCursor;
    int level;
    boolean player1turn;
    boolean end;
    String result;
    ArrayList<Card> cardsInPlay;
    ArrayList<Card> player1Cards;
    ArrayList<Card> player2Cards;
    ArrayList<Card> alreadyFlipped;

    Game() {
        this.width = 9;
        this.height = 6;
        this.cardWidth = 100;
        this.cardHeight = 140;
        this.counter = 0;
        this.difCursor = new Posn(this.width * this.cardWidth / 2 + 500 + 2 -
                (this.cardHeight / 2 - 2) * 4 - (this.cardHeight / 2 - 2) / 2, 
                this.height * this.cardHeight / 2 + 50 + 1);
        this.level = 1;
        this.player1turn = true;
        this.end = true;
        this.result = "Start Menu";
        this.cardsInPlay = this.createCards();
        this.player1Cards = new ArrayList<Card>();
        this.player2Cards = new ArrayList<Card>();
        this.alreadyFlipped = new ArrayList<Card>();
    }

    // renders the game
    public WorldScene makeScene() {

        WorldScene game = new WorldScene(this.width * this.cardWidth,
                this.height * this.cardHeight);

        WorldImage bg = new RectangleImage(this.width * this.cardWidth + 1000,
                this.height * this.cardHeight + 110, OutlineMode.SOLID, 
                Color.cyan);

        WorldImage player1 = new TextImage("Player 1", 72, Color.BLUE);

        WorldImage player1Score = 
                new TextImage(Integer.toString(this.score(this.player1Cards)), 64,
                        Color.BLUE);

        WorldImage player2Score = 
                new TextImage(Integer.toString(this.score(this.player2Cards)), 64,
                        Color.MAGENTA);

        WorldImage player2 = new TextImage("Player 2", 72, Color.MAGENTA);

        WorldImage cursor = new RectangleImage(300, 86, 
                OutlineMode.SOLID, Color.GREEN);

        WorldImage startBg = new RectangleImage(this.width * this.cardWidth,
                this.height * this.cardHeight, OutlineMode.SOLID, Color.BLUE);

        WorldImage memoryGame = new TextImage("Memory Game", 84, Color.CYAN);

        WorldImage singlePlayer = new TextImage("Single Player",
                72, Color.WHITE);
        
        WorldImage difficulty = new TextImage("Select Difficulty", 72, Color.WHITE);
        
        WorldImage difBg = new RectangleImage(
                this.width * this.cardWidth * 3 / 40, this.cardHeight / 2 - 2,
                OutlineMode.SOLID, Color.BLACK);
        
        WorldImage difCurs = new RectangleImage(
                this.width * this.cardWidth * 3 / 40 + 4, this.cardHeight / 2 + 2,
                OutlineMode.OUTLINE, Color.CYAN);
        
        WorldImage dif1 = new RectangleImage(
                this.width * this.cardWidth * 3 / 40 - 5, this.cardHeight / 2 - 7,
                OutlineMode.SOLID, Color.GREEN);
        WorldImage dif2 = new RectangleImage(
                this.width * this.cardWidth * 3 / 40 - 5, this.cardHeight / 2 - 7,
                OutlineMode.SOLID, Color.YELLOW);
        WorldImage dif3 = new RectangleImage(
                this.width * this.cardWidth * 3 / 40 - 5, this.cardHeight / 2 - 7,
                OutlineMode.SOLID, Color.ORANGE);
        WorldImage dif4 = new RectangleImage(
                this.width * this.cardWidth * 3 / 40 - 5, this.cardHeight / 2 - 7,
                OutlineMode.SOLID, Color.RED);
        WorldImage diff1 = new OverlayImage(dif1, difBg);
        WorldImage diff2 = new OverlayImage(dif2, difBg);
        WorldImage diff3 = new OverlayImage(dif3, difBg);
        WorldImage diff4 = new OverlayImage(dif4, difBg);
        
        WorldImage play = new TextImage("Play", 72, Color.WHITE);

        WorldImage multiPlayer = new TextImage("Multiplayer",
                72, Color.WHITE);
        
        WorldImage howToPlay = new TextImage("How to Play", 72, Color.WHITE);
        
        WorldImage howToPlayText1 = new TextImage(
                "Flip two cards. If they are the same,", 48, Color.YELLOW);
        WorldImage howToPlayText2 = new TextImage(
                "you keep the two cards and you get to", 
                48, Color.YELLOW);
        WorldImage howToPlayText3 = new TextImage(
                "choose two new cards. If the cards are",
                48, Color.YELLOW);
        WorldImage howToPlayText4 = new TextImage(
                "not the same, the other player will go.",
                48, Color.YELLOW);
        WorldImage howToPlayText5 = new TextImage(
                "Each player takes turns until there are",
                48, Color.YELLOW);
        WorldImage howToPlayText6 = new TextImage(
                "no cards left on the board",
                48, Color.YELLOW);
        WorldImage howToPlayText7 = new TextImage("Point values", 48, Color.YELLOW);
        WorldImage howToPlayText8 = new TextImage("Pair of Jokers = 2", 
                48, Color.YELLOW);
        WorldImage howToPlayText9 = new TextImage("Any other Pair = 1", 
                48, Color.YELLOW);
        
        WorldImage back = new TextImage("Back", 72, Color.WHITE);

        WorldImage resume = new TextImage("Resume",
                72, Color.WHITE);

        WorldImage restart = new TextImage("Restart",
                72, Color.WHITE);

        WorldImage mainMenu = new TextImage("Main Menu",
                72, Color.WHITE);
        

        game.placeImageXY(bg, this.width * this.cardWidth / 2 + 500,
                this.height * this.cardHeight / 2 + 55);

        if (this.player1turn) {
            game.placeImageXY(cursor, 250, 58);
        }
        else {
            game.placeImageXY(cursor, 1650, 58);
        }

        game.placeImageXY(player1, 250, 50);

        game.placeImageXY(player1Score, 250, 150);

        game.placeImageXY(player2, 1650, 50);

        game.placeImageXY(player2Score, 1650, 150);


        for (Card c : this.cardsInPlay) {

            if (c.isFlipped) {

                this.placeFlipped(c, c.x * this.cardWidth + 
                        this.cardWidth / 2 + 500, c.y * this.cardHeight + 
                        this.cardHeight / 2 + 50, game);
            }
            else {

                game.placeImageXY(new FromFileImage("card-back.png"), 
                        c.x * this.cardWidth + this.cardWidth / 2 + 500,
                        c.y * this.cardHeight + this.cardHeight / 2 + 50);
            }
        }

        // places each player's cards on the appropriate side
        for (Card c : this.player1Cards) {
            int pos = this.player1Cards.indexOf(c) / 2;
            if (this.player1Cards.indexOf(c) % 2 == 0) {
                this.placeFlipped(c, pos % 4 * this.cardWidth * 5 / 4
                        + 50, (pos / 4) * 150 + 250, game);
            }
            else {
                this.placeFlipped(c, pos % 4 * this.cardWidth * 5 / 4
                        + 80, (pos / 4) * 150 + 260, game);
            }
        }

        // places each player's cards on the appropriate side
        for (Card c : this.player2Cards) {
            int pos = this.player2Cards.indexOf(c) / 2;
            if (this.player2Cards.indexOf(c) % 2 == 0) {
                this.placeFlipped(c, pos % 4 * this.cardWidth * 5 / 4
                        + 1445, (pos / 4) * 150 + 250, game);
            }
            else {
                this.placeFlipped(c, pos % 4 * this.cardWidth * 5 / 4
                        + 1475, (pos / 4) * 150 + 260, game);
            }
        }

        // place the start menu
        if (this.result.equals("Start Menu")) {
            game.placeImageXY(startBg, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(memoryGame, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 6 + 50);
            game.placeImageXY(singlePlayer, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 3 + 50);
            game.placeImageXY(multiPlayer, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(howToPlay, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 2 / 3 + 50);


        }

        // place the main pause menu
        if (this.result.equals("PausedSP") || this.result.equals("PausedMP")) {
            game.placeImageXY(startBg, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(memoryGame, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 6 + 50);
            game.placeImageXY(resume, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 3 + 50);
            game.placeImageXY(restart, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(mainMenu, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 2 / 3 + 50);
        }
        
        // place how to play
        if (this.result.equals("How to Play")) {
            game.placeImageXY(startBg, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(howToPlay, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 11 + 50);
            game.placeImageXY(howToPlayText1, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 3 / 16 + 50);
            game.placeImageXY(howToPlayText2, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 4 / 16 + 50);
            game.placeImageXY(howToPlayText3, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 5 / 16 + 50);
            game.placeImageXY(howToPlayText4, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 6 / 16 + 50);
            game.placeImageXY(howToPlayText5, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 7 / 16 + 50);
            game.placeImageXY(howToPlayText6, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 8 / 16 + 50);
            game.placeImageXY(howToPlayText7, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 10 / 16 + 50);
            game.placeImageXY(howToPlayText8, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 12 / 16 + 50);
            game.placeImageXY(howToPlayText9, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 13 / 16 + 50);
            
            game.placeImageXY(back, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight * 11 / 12 + 50);
        }
        
        // if you select single player
        if (this.result.equals("Single Player Options")) {
            game.placeImageXY(startBg, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 2 + 50);
            game.placeImageXY(difficulty, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 6 + 50);
            game.placeImageXY(diff1, this.width * this.cardWidth / 2 + 500 + 2 -
                    (this.cardHeight / 2 - 2) * 4 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff1, this.width * this.cardWidth / 2 + 500 -
                    (this.cardHeight / 2 - 2) * 3 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff2, this.width * this.cardWidth / 2 + 500 - 2 -
                    (this.cardHeight / 2 - 2) * 2 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff2, this.width * this.cardWidth / 2 + 500 - 4 -
                    (this.cardHeight / 2 - 2) - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff2, this.width * this.cardWidth / 2 + 500 - 6 -
                    (this.cardHeight / 2 - 2) * 0 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff3, this.width * this.cardWidth / 2 + 500 - 8 +
                    (this.cardHeight / 2 - 2) * 1 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff3, this.width * this.cardWidth / 2 + 500 - 10 +
                    (this.cardHeight / 2 - 2) * 2 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff3, this.width * this.cardWidth / 2 + 500 - 12 +
                    (this.cardHeight / 2 - 2) * 3 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff4, this.width * this.cardWidth / 2 + 500 - 14 +
                    (this.cardHeight / 2 - 2) * 4 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(diff4, this.width * this.cardWidth / 2 + 500 - 16 +
                    (this.cardHeight / 2 - 2) * 5 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1);
            game.placeImageXY(difCurs, this.difCursor.x, this.difCursor.y);
            WorldImage levelNum = new TextImage(Integer.toString(this.level),
                    64, this.whatColorLevel());
            game.placeImageXY(levelNum, this.difCursor.x, this.difCursor.y + 75);
            game.placeImageXY(play, this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 6 + 50 + 500);
        }

        return game;
    }
    
    // determines which color the level's number should be below the cursor
    // on the Single Player Options (Difficulty) menu
    public Color whatColorLevel() {
        if (this.level < 3) {
            return Color.GREEN;
        }
        else if (this.level < 6) {
            return Color.YELLOW;
        }
        else if (this.level < 9) {
            return Color.ORANGE;
        }
        else {
            return Color.RED;
        }
    }

    // places a given card on a given position
    public void placeFlipped(Card c, int x, int y, WorldScene game) {

        WorldImage cardFlippedBg = new RectangleImage(this.cardWidth - 10, 
                this.cardHeight - 10, OutlineMode.SOLID, Color.BLACK);

        WorldImage cardFlipped = new RectangleImage(this.cardWidth - 10 - 2, 
                this.cardHeight - 10 - 2, OutlineMode.SOLID, Color.WHITE);

        WorldImage cardVal = new TextImage(c.symbol(), 36, c.whatColor());

        game.placeImageXY(cardFlippedBg, x, y);

        game.placeImageXY(cardFlipped, x, y);

        if (c.value == 10) {
            game.placeImageXY(cardVal, x - this.cardWidth / 4,
                    y - 3 * this.cardHeight / 10);
            game.placeImageXY(cardVal, x + 3 * this.cardWidth / 16,
                    y + 3 * this.cardHeight / 10);
        }

        else if (c.suit == 4) {
            game.placeImageXY(new FromFileImage("joker.png"), x, y);
        }

        else {
            game.placeImageXY(cardVal, x - 3 * this.cardWidth / 10,
                    y - 3 * this.cardHeight / 10);
            game.placeImageXY(cardVal, x + 3 * this.cardWidth / 10,
                    y + 3 * this.cardHeight / 10);
        }

        if (c.suit == 0) {
            game.placeImageXY(new FromFileImage("heart.png"), x, y);
        }

        if (c.suit == 1) {
            game.placeImageXY(new FromFileImage("diamond.png"), x, y);
        }

        if (c.suit == 2) {
            game.placeImageXY(new FromFileImage("club.png"), x, y);
        }

        if (c.suit == 3) {
            game.placeImageXY(new FromFileImage("spade.png"), x, y);
        }
    }

    // determines the score of the given team
    public int score(ArrayList<Card> crds) {
        int score = crds.size() / 2;
        boolean jokers = false;

        for (Card c : crds) {
            if (c.value == 14) {
                jokers = true;
            }
        }

        if (jokers) {
            score = score + 1;
        }

        return score;
    }

    // creates the cards on the table to start the game
    public ArrayList<Card> createCards() {

        ArrayList<Card> arr = new ArrayList<Card>();
        ArrayList<Posn> posns = new ArrayList<Posn>();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                posns.add(new Posn(x, y));
            }
        }
        Collections.shuffle(posns);

        for (int j = 0; j < 2; j++) {
            arr.add(new Card(14, 4, false, posns.get(0).x, posns.get(0).y));
            posns.remove(0);
        }

        for (int n = 1; n < 14; n++) {
            for (int s = 0; s < 4; s++) {
                arr.add(new Card(n, s, false, posns.get(0).x, posns.get(0).y));
                posns.remove(0);
            }
        }
        return arr;
    }

    // changes the game based on the key pressed
    public void onMouseClicked(Posn pos) {

        // make a list with the flipped cards on the board
        ArrayList<Card> flippedCards = this.cardsFlipped();

        // if there's two cards left on the board and one is already flipped over
        if (this.cardsInPlay.size() == 2 && this.countFlipped() == 1 && !this.end
                &&
                (this.result.equals("Multiplayer") || 
                        (this.result.equals("Single Player") && 
                                this.player1turn))) {
            // move them to the appropriate player's side
            this.match(this.cardsInPlay.get(0), this.cardsInPlay.get(1));
            this.end = true;
            this.cardsInPlay.addAll(this.player1Cards);
            this.cardsInPlay.addAll(this.player2Cards);
        }

        Card secondCard = null;

        // if the user clicked on a card on the board
        if (this.isCard(pos).x > -1 && !this.end &&
                (this.result.equals("Multiplayer") || 
                        (this.result.equals("Single Player") && 
                                this.player1turn))) {
            // set the card to equal to secondCard
            for (Card c : this.cardsInPlay) {
                if (this.isCard(pos).x == c.x &&
                        this.isCard(pos).y == c.y) {
                    secondCard = c;
                }
            }
        }
        
        // change the player's turn
        if (this.countFlipped() == 1 && this.isCard(pos).x > -1 && !this.end &&
                (this.result.equals("Multiplayer") || 
                        (this.result.equals("Single Player") && 
                                this.player1turn))) {
            
            Card firstCard = flippedCards.get(0);
            if (firstCard.value != secondCard.value) {
                this.player1turn = !this.player1turn;
            }
        }

        // if there are two cards flipped over already
        if (this.countFlipped() == 2 && this.isCard(pos).x > -1 && !this.end
                &&
                (this.result.equals("Multiplayer") || 
                        (this.result.equals("Single Player") && 
                                this.player1turn))) {

            // the first flipped card
            Card first = flippedCards.get(0);
            // the second flipped card
            Card second = flippedCards.get(1);

            // if the cards have the same value, move them to the appropriate side
            if (first.value == second.value) {
                this.match(first, second);
            }

            // ... otherwise flip them back over
            else {
                for (Card c : this.cardsInPlay) {
                    if ((first.x == c.x && first.y == c.y) ||
                            (second.x == c.x && second.y == c.y)) {
                        c.isFlipped = false;
                    }
                }
            }
        }

        // if the user clicked on a card on the board
        if (this.isCard(pos).x > -1 && !this.end &&
                (this.result.equals("Multiplayer") || 
                        (this.result.equals("Single Player") && 
                                ((this.player1turn) ||
                                        (!this.player1turn && 
                                                this.countFlipped() == 1 &&
                                                this.counter == 0))
                                ))) {
            // set the card to flipped
            for (Card c : this.cardsInPlay) {
                if (this.isCard(pos).x == c.x &&
                        this.isCard(pos).y == c.y) {
                    c.isFlipped = true;
                    this.alreadyFlipped.add(0, c);
                }
            }
        }

        int tlx = this.width * this.cardWidth / 2 + 325;
        int tly = this.height * this.cardHeight / 2 + 20;
        int brx = this.width * this.cardWidth / 2 + 675;
        int bry = this.height * this.cardHeight / 2 + 80;

        // using the start menu to select multiplayer
        if (this.result.equals("Start Menu") && this.proceed(pos, 
                new Posn(tlx, tly), new Posn(brx, bry))) {
            this.end = false;
            this.result = "Multiplayer";
        }

        // using the start menu to select single player
        if (this.result.equals("Start Menu") && this.proceed(pos, 
                new Posn(tlx - 32, tly - 135), new Posn(brx + 32, bry - 135))) {
            this.end = false;
            this.result = "Single Player Options";
        }
        
        // select difficulty
        if (this.result.equals("Single Player Options") && this.proceed(pos, 
                new Posn(tlx - 160, tly - 6), new Posn(brx + 150, bry + 6))) {
            ArrayList<Posn> diffs = new ArrayList<Posn>();
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 + 2 -
                    (this.cardHeight / 2 - 2) * 4 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 -
                    (this.cardHeight / 2 - 2) * 3 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 2 -
                    (this.cardHeight / 2 - 2) * 2 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 4 -
                    (this.cardHeight / 2 - 2) * 1 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 6 -
                    (this.cardHeight / 2 - 2) * 0 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 8 +
                    (this.cardHeight / 2 - 2) * 1 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 10 +
                    (this.cardHeight / 2 - 2) * 2 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 12 +
                    (this.cardHeight / 2 - 2) * 3 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 14 +
                    (this.cardHeight / 2 - 2) * 4 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            diffs.add(new Posn(this.width * this.cardWidth / 2 + 500 - 16 +
                    (this.cardHeight / 2 - 2) * 5 - (this.cardHeight / 2 - 2) / 2, 
                    this.height * this.cardHeight / 2 + 50 + 1));
            
            for (Posn p : diffs) {
                if (this.withinRange(p, pos, 
                        (this.width * this.cardWidth * 3 / 40 - 5) / 2,
                        (this.cardHeight / 2 - 7) / 2)) {
                    this.difCursor = p;
                    this.level = diffs.indexOf(p) + 1;
                }
            }
        }
        
        // start the single player game after selecting the difficulty level
        if (this.result.equals("Single Player Options") &&
                this.withinRange(new Posn(this.width * this.cardWidth / 2 + 500,
                    this.height * this.cardHeight / 6 + 50 + 500), pos, 
                        65, 29)) {
            this.result = "Single Player";
            this.end = false;
        }
        
        // using the start menu to select how to play
        if (this.result.equals("Start Menu") && this.proceed(pos, 
                new Posn(tlx - 14, tly + 135), new Posn(brx + 14, bry + 135))) {
            this.result = "How to Play";
        }
        
        // go back to the main menu from how to play
        if (this.result.equals("How to Play") && this.proceed(pos, 
                new Posn(tlx, tly + 343), new Posn(brx, bry + 343))) {
            this.result = "Start Menu";
        }

        // using the pause menu to select restart
        if ((this.result.equals("PausedSP") || this.result.equals("PausedMP"))
                && this.proceed(pos, new Posn(tlx, tly), new Posn(brx, bry))) {
            this.player1turn = true;
            this.end = false;
            if (this.result.equals("PausedSP")) {
                this.result = "Single Player";
            }
            else {
                this.result = "Multiplayer";
            }
            this.cardsInPlay = this.createCards();
            this.player1Cards = new ArrayList<Card>();
            this.player2Cards = new ArrayList<Card>();
        }

        // using the pause menu to select resume
        if ((this.result.equals("PausedSP") || this.result.equals("PausedMP"))
                && this.proceed(pos, new Posn(tlx, tly - 135), 
                        new Posn(brx, bry - 135))) {
            this.end = false;
            if (this.result.equals("PausedSP")) {
                this.result = "Single Player";
            }
            else {
                this.result = "Multiplayer";
            }
        }

        // using the pause menu to select start menu
        if ((this.result.equals("PausedSP") || this.result.equals("PausedMP"))
                && this.proceed(pos, new Posn(tlx, tly + 135), 
                        new Posn(brx, bry + 135))) {
            this.player1turn = true;
            this.end = true;
            this.result = "Start Menu";
            this.cardsInPlay = this.createCards();
            this.player1Cards = new ArrayList<Card>();
            this.player2Cards = new ArrayList<Card>();
        }
    }
    
    // determines if the pos is within x of m and within y of m
    public boolean withinRange(Posn m, Posn pos, int x, int y) {
        return Math.abs(m.x - pos.x) < x && Math.abs(m.y - pos.y) < y;
    }

    // determines if the click was on an option in the menu given the top left
    // and bottom right of the area to click
    public boolean proceed(Posn pos, Posn tl, Posn br) {
        if (pos.x < br.x && pos.x > tl.x && pos.y < br.y && pos.y > tl.y) {
            return true;
        }
        else {
            return false;
        }
    }

    // make a list with the flipped cards on the board
    public ArrayList<Card> cardsFlipped() {
        ArrayList<Card> flippedCards = new ArrayList<Card>();
        for (Card c : this.cardsInPlay) {
            if (c.isFlipped) {
                flippedCards.add(c);
            }
        }
        return flippedCards;
    }

    // if a match is found, add it to the appropriate side
    public void match(Card first, Card second) {

        // remove both from the board
        this.cardsInPlay.remove(first);
        this.cardsInPlay.remove(second);
        this.alreadyFlipped.remove(first);
        this.alreadyFlipped.remove(second);
        first.isFlipped = true;
        second.isFlipped = true;
        // if player1 flipped them
        if (player1turn) {
            // add them to player1's side
            this.player1Cards.add(first);
            this.player1Cards.add(second);
        }
        else {
            // otherwise add them to player2's side
            this.player2Cards.add(first);
            this.player2Cards.add(second);
        }
    }

    // determines if there is a card at the given position
    public Card isCard(Posn posn) {

        ArrayList<Card> notFlipped = new ArrayList<Card>();
        Card noCard = new Card(1, 0, false, -1, -1);

        for (Card c : this.cardsInPlay) {
            if (!c.isFlipped) {
                notFlipped.add(c);
            }
        }

        for (Card c : notFlipped) {
            if (posn.x - (c.x * this.cardWidth + 500 + 5) < this.cardWidth - 10 &&
                    posn.x - (c.x * this.cardWidth + 500 + 5) >= 0 &&
                    posn.y - (c.y * this.cardHeight + 50 + 5) < this.cardHeight - 10
                    && posn.y - (c.y * this.cardHeight + 50 + 5) >= 0) {
                noCard = c;
            }
        }

        return noCard;
    }

    // changes the game every tick
    public void onTick() {
        if (this.result.equals("Single Player") && !this.player1turn &&
                this.countFlipped() == 2 && this.counter < 150 && !this.end) {
            // continue
            this.counter = this.counter + 1;
        }
        
        // unflip the user's last two flipped cards and continue ...or...
        if (this.counter == 150 && this.alreadyFlipped.get(0).value !=
                this.alreadyFlipped.get(1).value) {
            this.counter = this.counter + 1;
            this.alreadyFlipped.get(0).isFlipped = false;
            this.alreadyFlipped.get(1).isFlipped = false;
        }
        
        // end the game
        if (this.counter == 150 && this.cardsInPlay.size() == 2 && !this.player1turn) {
            this.match(this.cardsInPlay.get(0), this.cardsInPlay.get(1));
            this.end = true;
            this.counter = 0;
            this.cardsInPlay.addAll(this.player1Cards);
            this.cardsInPlay.addAll(this.player2Cards);
        }
        
        // remove the previously matched cards from the game and continue
        if (this.counter == 150 && this.alreadyFlipped.get(0).value ==
                this.alreadyFlipped.get(1).value && this.cardsInPlay.size() !=2) {
            this.counter = this.counter + 1;
            Card first = this.alreadyFlipped.get(0);
            Card second = this.alreadyFlipped.get(1);
            this.cardsInPlay.remove(first);
            this.cardsInPlay.remove(second);
            this.player2Cards.add(first);
            this.player2Cards.add(second);
        }
        
        // continue
        if (this.counter < 200 & this.counter > 150) {
            this.counter = this.counter + 1;
        }
        
        // FIRST FLIP
        if (this.counter == 200) {
            // continue
            this.counter = this.counter + 1;
            ArrayList<Card> known = this.pair(this.level);
            
            // if the AI sees a match before the flip, flip one of the cards 
            // in the match
            if (known.size() != 0) {
                known.get(0).isFlipped = true;
                this.alreadyFlipped.add(0, known.get(0));
            }
            
            else {
                // else flip a random card that hasn't been flipped yet
                ArrayList<Card> haventFlipped = new ArrayList<Card>();
                
                for (Card c : this.cardsInPlay) {
                    if (!this.aiMemory(this.level).contains(c)) {
                        haventFlipped.add(c);
                    }
                }
                
                for (Card c : this.alreadyFlipped) {
                    if (haventFlipped.contains(c)) {
                        haventFlipped.remove(c);
                    }
                }
                
                Card firstOne = null;
                
                if (haventFlipped.size() != 0) {
                firstOne = haventFlipped.get(
                        this.randInt(0, haventFlipped.size() - 1));
                }
                else {
                    firstOne = this.cardsInPlay.get(
                            this.randInt(0, this.cardsInPlay.size() - 1));
                }
                firstOne.isFlipped = true;
                this.alreadyFlipped.add(0, firstOne);
            }
        } 
        
        // continue
        if (this.counter > 200 & this.counter < 300) {
            this.counter = this.counter + 1;
        }
        
        // SECOND FLIP
        if (this.counter == 300) {
            
            ArrayList<Card> memo = new ArrayList<Card>();
            for (Card c : this.aiMemory(this.level + 1)) {
                memo.add(this.aiMemory(this.level + 1).indexOf(c), c);
            }
            memo.remove(0);
            
            // if the computer knows where the matching card to the first card
            // it flipped is
            
            for (Card c : memo) {
                if (this.alreadyFlipped.get(0).value
                        == c.value && this.cardsInPlay.contains(c) &&
                        this.countFlipped() == 1) {
                    c.isFlipped = true;
                    this.alreadyFlipped.add(0, c);
                    this.counter = 650;
                }
            }
            
            // if the AI doesn't know where any pairs are
            if (this.counter != 650) {
                ArrayList<Card> haventFlipped = new ArrayList<Card>();
                
                for (Card c : this.cardsInPlay) {
                    haventFlipped.add(c);
                }
                
                for (Card c : this.alreadyFlipped) {
                    if (haventFlipped.contains(c)) {
                        haventFlipped.remove(c);
                    }
                }
                
                Card secondOne = null;
                
                // if all cardsInPlay haven't already been flipped
                if (haventFlipped.size() != 0) {
                secondOne = haventFlipped.get(
                        this.randInt(0, haventFlipped.size() - 1));
                }
                // if all cardsInPlay have already been flipped
                else {
                    
                    ArrayList<Card> cardsLeft = new ArrayList<Card>();
                    for (Card c : this.cardsInPlay) {
                        if (!c.isFlipped) {
                            cardsLeft.add(c);
                        }
                    }
                    
                    secondOne = cardsLeft.get(
                            this.randInt(0, cardsLeft.size() - 1));
                }
                
                secondOne.isFlipped = true;
                this.alreadyFlipped.add(0, secondOne);
                
                if (secondOne.value == this.alreadyFlipped.get(1).value) {
                    this.counter = 650;
                }
                else {
                    this.counter = this.counter + 1;
                }
            }
        }
        
        if (this.counter > 300 & this.counter < 450) {
            this.counter = this.counter + 1;
        }
        
        if (this.counter == 450) {
            this.player1turn = true;
            this.counter = 0;
            this.alreadyFlipped.get(0).isFlipped = false;
            this.alreadyFlipped.get(1).isFlipped = false;
        }
        
        if (this.counter >= 650 && this.counter < 750) {
            this.counter = this.counter + 1;
        }
        
        if (this.counter == 750) {
            this.counter = 0;
            this.player1turn = false;
        }
    }
    
    // is there a pair in the AI's memory? if so what is it 
    // (if none, return empty ArrayList)
    public ArrayList<Card> pair(int level) {
        ArrayList<Card> pair = new ArrayList<Card>();
        for (int i = 0; i < this.aiMemory(level).size(); i++) {
             ArrayList<Card> without = this.aiMemory(level);
             without.remove(this.aiMemory(level).get(i));
             for (Card c : without) {
                 if (c.value == this.aiMemory(level).get(i).value &&
                         this.cardsInPlay.contains(c) &&
                         this.cardsInPlay.contains(this.aiMemory(level).get(i))) {
                     pair.add(0, this.aiMemory(level).get(i));
                     pair.add(0, c);
                 }
             }
        }
        return pair;
    }
    
    
    // make a list of the cards the AI can remember in alreadyFlipped at the
    // given difficulty level
    public ArrayList<Card> aiMemory(int level) {
        ArrayList<Card> mem = new ArrayList<Card>();
        for (Card c : this.noRepeats()) {
            if (this.noRepeats().indexOf(c) < level) {
                mem.add(this.noRepeats().indexOf(c), c);
            }
        }
        return mem;
    }
    
    
    // make a list of cards that have been flipped with no repeats
    public ArrayList<Card> noRepeats() {
        ArrayList<Card> noRep = new ArrayList<Card>();
        for (Card c : this.alreadyFlipped) {
            if (!noRep.contains(c)) {
                noRep.add(0, c);
            }
        }
        
        // since noRep puts the latest cards flipped at the end, we need to
        // reverse it
        ArrayList<Card> noRepFinal = new ArrayList<Card>();
        for (Card c : noRep) {
            noRepFinal.add(0, c);
        }
        
        return noRepFinal;
    }
    

    // counts how many of the cards on the board are flipped
    public int countFlipped() {

        int count = 0;

        for (Card c : this.cardsInPlay) {
            if (c.isFlipped) {
                count = count + 1;
            }
        }

        return count;
    }

    // changes the game based on the key pressed
    public void onKeyEvent(String ke) {
        if (ke.equals("p") && this.result.equals("Single Player")) {
            this.end = true;
            this.result = "PausedSP";
        }

        if (ke.equals("p") && this.result.equals("Multiplayer")) {
            this.end = true;
            this.result = "PausedMP";
        }
    }
    
    // returns a random integer from the minimum to maximum
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}

class Card {
    // symbol on the card (J=11, Q=12, K=13, A=1)
    int value;
    // is the card's suite Red?
    int suit;
    boolean isFlipped;
    int x;
    int y;

    Card(int value, int suit, boolean isFlipped, int x, int y) {
        this.value = value;
        this.suit = suit;
        this.isFlipped = isFlipped;
        this.x = x;
        this.y = y;
    }
    
    // how long ago was the card flipped over
    public int howLongAgo(ArrayList<Card> flipped) {
        int howLong = 0;
        int howLongFinal = 0;
        if (flipped.contains(this)) {
            for (Card c : flipped) {
                howLong = howLong + 1;
                if (c.equals(this)) {
                    howLong = howLongFinal;
                }
            }
        }
        return howLongFinal;
    }

    // tells which symbol the card has based on its value
    public String symbol() {
        if (this.value == 1) {
            return "A";
        }
        else if (this.value < 11) {
            return Integer.toString(this.value);
        }
        else if (this.value == 11) {
            return "J";
        }
        else if (this.value == 12) {
            return "Q";
        }
        else if (this.value == 13){
            return "K";
        }
        else {
            return "j";
        }
    }

    // determines the color
    public Color whatColor() {
        if (this.suit < 2 || this.suit == 4) {
            return Color.RED;
        }
        else {
            return Color.BLACK;
        }
    }

}

class Examples {

    Game w1;

    void initData() {
        w1 = new Game();
    }

    // runs the program
    void testBigBang(Tester t) {
        initData();
        w1.bigBang(1900, 950, .001);
    }
}