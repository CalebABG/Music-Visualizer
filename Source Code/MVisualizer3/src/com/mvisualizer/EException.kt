package com.mvisualizer

import javax.swing.*
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date

class EException private constructor(parent: JFrame?) {

    companion object {
        private var EException: EException? = null
        lateinit var frame: JFrame
        private val textArea = JTextArea()

        fun getInstance(parent: JFrame?) {
            if (EException == null) {
                EException = EException(parent)
            }
            frame.toFront()
        }

        fun logException(e: Exception): String {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            return sw.toString()
        }

        private fun write(s: String) {
            textArea.append(s + "\n")
        }

        internal fun setText(s: String) {
            textArea.text = s
        }

        internal fun append(except: Exception) {
            textArea.append(SimpleDateFormat("h:mm:ss a").format(Date()) + logException(except) + "\n")
        }

        internal fun set(except: Exception) {
            textArea.text = SimpleDateFormat("h:mm:ss a").format(Date()) + logException(except) + "\n"
        }

        private fun closeWindow() {
            frame.dispose()
            EException = null
        }
    }

    init {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (x: Exception) { x.printStackTrace() }

        frame = JFrame("Exception Log")
        frame.iconImage = iconImage
        frame.setSize(430, 260)
        frame.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(object : WindowAdapter() { override fun windowClosing(e: WindowEvent) { closeWindow() } })
        frame.setLocationRelativeTo(parent)
        frame.addKeyListener(object : KeyAdapter(){
            override fun keyReleased(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ESCAPE)
                    closeWindow()

                if (e.isControlDown && e.keyCode == KeyEvent.VK_C)
                    textArea.text = ""
            }
        })

        val panel = JPanel()
        panel.layout = BorderLayout()
        frame.add(panel)

        textArea.isEditable = false
        textArea.lineWrap = true
        textArea.isDoubleBuffered = true
        textArea.dragEnabled = false
        textArea.isEnabled = false
        textArea.font = Font(Font.SERIF, Font.PLAIN, 18)
        textArea.background = Color.BLACK
        textArea.foreground = Color.WHITE

        val scrollPane = JScrollPane()
        scrollPane.background = Color.BLACK
        scrollPane.viewport.add(textArea)
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.isVisible = true
    }
}
