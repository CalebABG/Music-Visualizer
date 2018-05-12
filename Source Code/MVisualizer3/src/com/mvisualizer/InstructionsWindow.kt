package com.mvisualizer

import javax.swing.*
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class InstructionsWindow private constructor(parent: JFrame?, w: Int, h: Int, title: String, instructions: String) {

    companion object {
        private var instructionsWindow: InstructionsWindow? = null
        lateinit var frame: JFrame
        private val mvInstructions: String = InstructionsWindow::class.java.getResource("/MVInstructions.html").readText()

        fun getInstance(parent: JFrame?) {
            if (instructionsWindow == null) {
                instructionsWindow = InstructionsWindow(parent, 855, 490, "Music Visualizer Instructions", mvInstructions)
            }
            frame.toFront()
        }
    }

    init {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())} catch (e: Exception) {EException.append(e)}

        frame = JFrame(title)
        frame.iconImage = iconImage
        frame.setSize(w, h)
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE

        frame.addWindowListener(object : WindowAdapter() { override fun windowClosing(e: WindowEvent) { close() } })
        frame.setLocationRelativeTo(parent)
        frame.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent) {
                close(keyEvent = e)
            }
        })

        val panel = JPanel()
        panel.layout = BorderLayout()
        frame.contentPane.add(panel)

        val label = JLabel(instructions)
        label.font = Font(Font.SERIF, Font.PLAIN, 18)
        label.horizontalAlignment = SwingConstants.LEFT

        val scrollPane = JScrollPane()
        scrollPane.viewport.add(label)
        panel.add(scrollPane, BorderLayout.CENTER)

        frame.isVisible = true
    }

    private fun close() {
        frame.dispose()
        instructionsWindow = null
    }

    private fun close(keyEvent: KeyEvent) {
        if (keyEvent.keyCode == KeyEvent.VK_ESCAPE) close()
    }
}
