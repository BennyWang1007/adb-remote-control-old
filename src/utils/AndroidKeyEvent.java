package utils;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public enum AndroidKeyEvent {
    UNKNOWN(0),
    KEY_SOFT_LEFT(1),
    KEY_SOFT_RIGHT(2),
    KEY_HOME(3),
    KEY_BACK(4),
    KEY_CALL(5),
    KEY_ENDCALL(6),
    KEY_0(7),
    KEY_1(8),
    KEY_2(9),
    KEY_3(10),
    KEY_4(11),
    KEY_5(12),
    KEY_6(13),
    KEY_7(14),
    KEY_8(15),
    KEY_9(16),
    KEY_STAR(17),
    KEY_POUND(18),
    KEY_DPAD_UP(19),
    KEY_DPAD_DOWN(20),
    KEY_DPAD_LEFT(21),
    KEY_DPAD_RIGHT(22),
    KEY_DPAD_CENTER(23),
    KEY_VOLUME_UP(24),
    KEY_VOLUME_DOWN(25),
    KEY_POWER(26),
    KEY_CAMERA(27),
    KEY_CLEAR(28),
    KEY_A(29),
    KEY_B(30),
    KEY_C(31),
    KEY_D(32),
    KEY_E(33),
    KEY_F(34),
    KEY_G(35),
    KEY_H(36),
    KEY_I(37),
    KEY_J(38),
    KEY_K(39),
    KEY_L(40),
    KEY_M(41),
    KEY_N(42),
    KEY_O(43),
    KEY_P(44),
    KEY_Q(45),
    KEY_R(46),
    KEY_S(47),
    KEY_T(48),
    KEY_U(49),
    KEY_V(50),
    KEY_W(51),
    KEY_X(52),
    KEY_Y(53),
    KEY_Z(54),
    KEY_COMMA(55),
    KEY_PERIOD(56),
    KEY_ALT_LEFT(57),
    KEY_ALT_RIGHT(58),
    KEY_SHIFT_LEFT(59),
    KEY_SHIFT_RIGHT(60),
    KEY_TAB(61),
    KEY_SPACE(62),
    KEY_SYM(63),
    KEY_EXPLORER(64),
    KEY_ENVELOPE(65),
    KEY_ENTER(66),
    KEY_DEL(67),
    KEY_GRAVE(68),
    KEY_MINUS(69),
    KEY_EQUALS(70),
    KEY_LEFT_BRACKET(71),
    KEY_RIGHT_BRACKET(72),
    KEY_BACKSLASH(73),
    KEY_SEMICOLON(74),
    KEY_APOSTROPHE(75),
    KEY_SLASH(76),
    KEY_AT(77),
    KEY_NUM(78),
    KEY_HEADSETHOOK(79),
    KEY_FOCUS(80),
    KEY_PLUS(81),
    KEY_MENU(82),
    KEY_NOTIFICATION(83),
    KEY_SEARCH(84),
    KEY_MEDIA_PLAY_PAUSE(85),
    KEY_MEDIA_STOP(86),
    KEY_MEDIA_NEXT(87),
    KEY_MEDIA_PREVIOUS(88),
    KEY_MEDIA_REWIND(89),
    KEY_MEDIA_FAST_FORWARD(90),
    KEY_MUTE(91),
    KEY_PAGE_UP(92),
    KEY_PAGE_DOWN(93),
    KEY_PICTSYMBOLS(94),
    KEY_FORWARD_DEL(112),
    KEY_MOVE_HOME(122),
    KEY_MOVE_END(123);

    private static final Map<Integer, AndroidKeyEvent> AWT_TO_ANDROID_MAP = new HashMap<>();

    static {
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_ENTER, KEY_ENTER);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_ESCAPE, KEY_BACK);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_INSERT, KEY_HOME);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_CONTEXT_MENU, KEY_POWER);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_BACK_SPACE, KEY_DEL);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_DELETE, KEY_FORWARD_DEL);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_UP, KEY_DPAD_UP);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_DOWN, KEY_DPAD_DOWN);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_LEFT, KEY_DPAD_LEFT);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_RIGHT, KEY_DPAD_RIGHT);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_HOME, KEY_MOVE_HOME);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_END, KEY_MOVE_END);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_PAGE_UP, KEY_PAGE_UP);
        AWT_TO_ANDROID_MAP.put(KeyEvent.VK_PAGE_DOWN, KEY_PAGE_DOWN);
    }

    private final int keycode;

    AndroidKeyEvent(int keycode) {
        this.keycode = keycode;
    }

    public static AndroidKeyEvent fromAwtKeycode(int keycode) {
        return AWT_TO_ANDROID_MAP.getOrDefault(keycode, UNKNOWN);
    }

    public int getKeycode() {
        return keycode;
    }
}
