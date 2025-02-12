package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.level.Player;

/**
 * A panel consisting of a column for each player, with the numbered players on
 * top and their respective scores underneath.
 *
 * @author Jeroen Roosen
 *
 */
public class ScorePanel extends JPanel {

    /**
     * Default serialisation ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map storing players and their UI elements (score & lives labels).
     */
    private final Map<Player, Map<String, JLabel>> playerLabels;


    /**
     * The default way in which the score is shown.
     */
    public static final ScoreFormatter DEFAULT_SCORE_FORMATTER =
        (Player player) -> String.format("Score: %3d", player.getScore());

    /**
     * The way to format the score information.
     */
    private ScoreFormatter scoreFormatter = DEFAULT_SCORE_FORMATTER;

    /**
     * Creates a new score panel with a column for each player.
     *
     * @param players
     *            The players to display the scores of.
     */
    public ScorePanel(List<Player> players) {
        super();
        assert players != null;

        setLayout(new GridLayout(3, players.size()));

        playerLabels = new LinkedHashMap<>();

        for (int i = 1; i <= players.size(); i++) {
            add(new JLabel("Player " + i, JLabel.CENTER));
        }
        for (Player player : players) {
            JLabel scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
            JLabel livesLabel = new JLabel("Lives: " + player.getLives(), JLabel.CENTER);

            // Store labels in a nested map
            Map<String, JLabel> labels = new LinkedHashMap<>();
            labels.put("score", scoreLabel);
            labels.put("lives", livesLabel);
            playerLabels.put(player, labels);

            // Create a panel to hold score & lives labels side by side
            JPanel statsPanel = new JPanel(new GridLayout(1, 2));
            statsPanel.add(scoreLabel);
            statsPanel.add(livesLabel);

            add(statsPanel);
        }
    }

    /**
     * Refreshes the scores of the players.
     */
    protected void refresh() {
        for (Map.Entry<Player, Map<String, JLabel>> entry : playerLabels.entrySet()) {
            Player player = entry.getKey();
            Map<String, JLabel> labels = entry.getValue();

            // Update score
            JLabel scoreLabel = labels.get("score");
            scoreLabel.setText(scoreFormatter.format(player));

            // Update lives
            JLabel livesLabel = labels.get("lives");
            livesLabel.setText("Lives: " + player.getLives());

            // Force UI refresh
            scoreLabel.revalidate();
            scoreLabel.repaint();
        }
    }

    /**
     * Provide means to format the score for a given player.
     */
    public interface ScoreFormatter {

        /**
         * Format the score of a given player.
         * @param player The player and its score
         * @return Formatted score.
         */
        String format(Player player);
    }

    /**
     * Let the score panel use a dedicated score formatter.
     * @param scoreFormatter Score formatter to be used.
     */
    public void setScoreFormatter(ScoreFormatter scoreFormatter) {
        assert scoreFormatter != null;
        this.scoreFormatter = scoreFormatter;
    }
}
