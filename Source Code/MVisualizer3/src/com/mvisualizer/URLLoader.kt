package com.mvisualizer

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import javax.swing.*

class URLLoader private constructor() {
    companion object {
        private var urlLoader: URLLoader? = null

        var frame: JFrame = JFrame("SoundCloud Song")
        var textField: JTextField = JTextField(10)
        var checkBox: JCheckBox = JCheckBox()
        var go: JButton = JButton("Add Song(s)")
        var clearTextBtn: JButton = JButton("Clear Text")

        fun getInstance() {
            if (urlLoader == null) urlLoader = URLLoader()
            frame.toFront()
        }
    }

    init {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e1: Exception) {EException.append(e1)}

        frame.iconImage = iconImage
        frame.setSize(535, 150)
        frame.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(object : WindowAdapter() { override fun windowClosing(e: WindowEvent) { close() } })
        frame.setLocationRelativeTo(null)

        val lblPleaseEnterA = JLabel("Please Enter A SoundCloud URL :D")
        lblPleaseEnterA.font = Font(Font.SERIF, Font.BOLD, 16)
        lblPleaseEnterA.horizontalAlignment = SwingConstants.CENTER
        frame.add(lblPleaseEnterA, BorderLayout.NORTH)

        val panel = JPanel(true)
        frame.add(panel, BorderLayout.SOUTH)


        textField.font = Font(Font.SERIF, Font.PLAIN, 18)
        frame.add(textField, BorderLayout.CENTER)

        checkBox.font = Font(Font.SERIF, Font.PLAIN, 17)
        checkBox.text = "Keep on Add"
        panel.add(checkBox)

        val popupMenu = JPopupMenu()
        val cut = JMenuItem("Cut")
        cut.addActionListener { textField.cut() }
        popupMenu.add(cut)

        val copy = JMenuItem("Copy")
        copy.addActionListener { textField.copy() }
        popupMenu.add(copy)

        val paste = JMenuItem("Paste")
        paste.addActionListener { textField.paste() }
        popupMenu.add(paste)

        addPopup(textField, popupMenu)

        go.addActionListener { handleSoundCloudRequest() }
        go.font = Font(Font.SERIF, Font.BOLD, 14)
        panel.add(go)

        clearTextBtn.addActionListener { textField.text = "" }
        clearTextBtn.font = Font(Font.SERIF, Font.BOLD, 14)
        panel.add(clearTextBtn)

        frame.isVisible = true
    }

    private fun handleSoundCloudRequest() {
        val requestURL = textField.text

        if (!requestURL.isNullOrEmpty()) {
            val sc = ISoundCloud("h4xVW8Xx30tXHqgTtfUxiXFk2XpTWI8I","8tzbr1Q0fFPOre68l9NhAwAXHqTrJO1M")

            val completableFuture = CompletableFuture.supplyAsync ({ sc.getSongs(requestURL) })
            val futureMSongs = completableFuture.get(15, TimeUnit.SECONDS)

            if (futureMSongs.second != null)
                Visualizer.addSongs(futureMSongs.second)
//                Controls.visualizerRef.addSoundCloudTracks(futureMSongs.second)
            else
                Visualizer.showError(ISoundCloud.getErrorCodeString(futureMSongs.first), "Ohh No :(")

            if (!checkBox.isSelected) close()
        }
    }

    private fun close() {
        frame.dispose()
        urlLoader = null
    }

    private fun addPopup(c: Component, p: JPopupMenu) {
        c.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {if (e.isPopupTrigger) p.show(e.component, e.x, e.y)}
            override fun mouseReleased(e: MouseEvent) {if (e.isPopupTrigger) p.show(e.component, e.x, e.y)}
        })
    }
}
