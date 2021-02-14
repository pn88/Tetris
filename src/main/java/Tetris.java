import java.awt.event.KeyEvent;

public class Tetris {

    private Field field;
    private GamePiece gamePiece;
    private boolean isGameOver;

    public Tetris(int width, int height) {
        field = new Field(width, height);
        gamePiece = null;
    }
    public Field getField() {
        return field;
    }
    public GamePiece getGamePiece() {
        return gamePiece;
    }
    public void run() throws Exception {
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();
        isGameOver = false;
        gamePiece = GamePieceFactory.createRandomGamePiece(field.getWidth()/2, 0);

        while (!isGameOver) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyChar() == 'q') return;
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    gamePiece.left();
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    gamePiece.right();
                else if (event.getKeyCode() == 12)
                    gamePiece.rotate();
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    gamePiece.downMaximum();
            }

            step();
            field.print();
            Thread.sleep(300);
        }

        System.out.println("Game Over");
    }

    public void step() {
        gamePiece.down();
        if (!gamePiece.isCurrentPositionAvailable()) {
            gamePiece.up();
            gamePiece.land();

            isGameOver = gamePiece.getY () <= 1;
            field.removeFullLines();
            gamePiece = GamePieceFactory.createRandomGamePiece(field.getWidth()/2, 0);
        }
    }
    public void setGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }
    public void setField(Field field) {
        this.field = field;
    }

    public static Tetris game;

    public static void main(String[] args) throws Exception {
        game = new Tetris(10, 20);
        game.run();
    }
}