package me.curlpipesh.pipe.util;

import me.curlpipesh.lib.util.Keybind;
import me.curlpipesh.pipe.event.Keypress;

import static org.lwjgl.input.Keyboard.*;

/**
 * Helps with handling key bindings with or without modifiers
 *
 * @author c
 * @since 5/24/15
 */
public class KeypressHelper {
    /**
     * Returns whether or not the given key binding is fully satisfied,
     * including modifiers
     *
     * @param keybind The key binding to test against
     * @param keypress The current keypress event
     * @return True if the key binding is satisfied, false otherwise
     */
    public static boolean isKeyPlusModifiersDown(Keybind keybind, Keypress keypress) {
        if(keypress.getKey() == keybind.getKey()) {
            if(keybind.getModifiers().length > 0) {
                int mod = 0;
                for(int m : keybind.getModifiers()) {
                    if(m == KEY_LCONTROL || m == KEY_RCONTROL) {
                        if(isKeyDown(KEY_LCONTROL) || isKeyDown(KEY_RCONTROL)) {
                            ++mod;
                        }
                    } else if(m == KEY_LSHIFT || m == KEY_RSHIFT) {
                        if(isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT)) {
                            ++mod;
                        }
                    } else if(m == KEY_LMENU || m == KEY_RMENU) {
                        if(isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU)) {
                            ++mod;
                        }
                    } else if(isKeyDown(m)) {
                        ++mod;
                    }
                }
                return mod == keybind.getModifiers().length;
            } else {
                return true;
            }
        }
        return false;
    }
}
