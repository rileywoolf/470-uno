package components;

/**
 * The {@code SpecialRules} class represents the special rules that can be applied in a game of Uno.
 * These rules customize the gameplay by enabling or disabling specific features.
 *
 * <p>
 * Special rules include options such as stacking, zero card rotation, seven card hand-switching,
 * and allowing players to jump in during other players' turns.
 * </p>
 *
 * <p>
 * The class provides methods to query the status of each rule, allowing game logic to adapt based
 * on the specified special rules.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class SpecialRules {
    /**
     * Flag indicating whether stacking is allowed (true for allowed, false for not allowed).
     */
    private final boolean allowStacking;

    /**
     * Flag indicating whether zero card rotation is enabled (true for enabled, false for disabled).
     */
    private final boolean zerosRotate;

    /**
     * Flag indicating whether seven card hand-switching is enabled (true for enabled, false for disabled).
     */
    private final boolean sevensSwitchHands;

    /**
     * Flag indicating whether players are allowed to jump in during other players' turns
     * (true for allowed, false for not allowed).
     */
    private final boolean allowJumpIn;

    /**
     * Constructs a new SpecialRules object with default settings (all rules set to false).
     */
    public SpecialRules() {
        this.allowStacking = false;
        this.zerosRotate = false;
        this.sevensSwitchHands = false;
        this.allowJumpIn = false;
    }

    /**
     * Constructs a new SpecialRules object with custom settings.
     *
     * @param allowStacking      flag indicating whether stacking is allowed
     * @param zerosRotate        flag indicating whether zero card rotation is enabled
     * @param sevensSwitchHands  flag indicating whether seven card hand-switching is enabled
     * @param allowJumpIn        flag indicating whether players are allowed to jump in during other players' turns
     */
    public SpecialRules(boolean allowStacking, boolean zerosRotate, boolean sevensSwitchHands, boolean allowJumpIn) {
        this.allowStacking = allowStacking;
        this.zerosRotate = zerosRotate;
        this.sevensSwitchHands = sevensSwitchHands;
        this.allowJumpIn = allowJumpIn;
    }

    /**
     * Checks if stacking is allowed.
     *
     * @return true if stacking is allowed, false otherwise
     */
    public boolean isAllowStacking() {
        return allowStacking;
    }

    /**
     * Checks if zero card rotation is enabled.
     *
     * @return true if zero card rotation is enabled, false otherwise
     */
    public boolean isZerosRotate() {
        return zerosRotate;
    }

    /**
     * Checks if seven card hand-switching is enabled.
     *
     * @return true if seven card hand-switching is enabled, false otherwise
     */
    public boolean isSevensSwitchHands() {
        return sevensSwitchHands;
    }

    /**
     * Checks if players are allowed to jump in during other players' turns.
     *
     * @return true if jump-ins are allowed, false otherwise
     */
    public boolean isAllowJumpIn() {
        return allowJumpIn;
    }
}
