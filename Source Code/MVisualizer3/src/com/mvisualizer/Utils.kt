package com.mvisualizer

import java.awt.Font
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.UIManager

open class Utils {
    companion object {
        private var OS = System.getProperty("os.name").toLowerCase()

        fun isWindows(): Boolean = OS.indexOf("win") >= 0
        fun isMac(): Boolean = OS.indexOf("mac") >= 0
        fun isUnix(): Boolean = OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0
        fun isSolaris(): Boolean = OS.indexOf("sunos") >= 0

        fun minValueGuard(min: Float, max: Float, default_val: Float, promptText: String, parent: JFrame?): Float {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (e1: Exception) {EException.append(e1)}

            val label = JLabel(promptText)
            label.font = Font(Font.SERIF, Font.PLAIN, 18)

            val string_amount: String? = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE)
            val float_amount = string_amount?.toFloatOrNull()

            return if (float_amount == null || float_amount !in min..max) default_val
            else float_amount
        }
    }
}