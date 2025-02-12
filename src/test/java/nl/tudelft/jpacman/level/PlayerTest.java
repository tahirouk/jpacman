package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        // Mock des sprites pour éviter les dépendances inutiles
        Map<Direction, Sprite> spriteMap = Collections.emptyMap();
        AnimatedSprite deathAnimation = Mockito.mock(AnimatedSprite.class);

        // Création du joueur avec 3 vies initiales
        player = new Player(spriteMap, deathAnimation, 3);
    }

    @Test
    void getLives() {
        assertEquals(3, player.getLives(), "Le joueur devrait commencer avec 3 vies.");
    }

    @Test
    void setLives() {
        player.setLives(5);
        assertEquals(5, player.getLives(), "Le nombre de vies devrait être mis à jour à 5.");
    }

    @Test
    void loseLife() {
        player.loseLife();
        assertEquals(2, player.getLives(), "Après avoir perdu une vie, il devrait rester 2 vies.");
    }

    @Test
    void isDying() {
        player.isDying();
        assertEquals(2, player.getLives(), "Après une mort, il devrait rester 2 vies.");
        assertTrue(player.isAlive(), "Le joueur devrait être encore en vie avec 2 vies restantes.");

        player.isDying();
        player.isDying();
        assertEquals(0, player.getLives(), "Le joueur devrait avoir 0 vies après 3 morts.");
        assertFalse(player.isAlive(), "Le joueur ne devrait plus être en vie après avoir perdu toutes ses vies.");
    }
}
