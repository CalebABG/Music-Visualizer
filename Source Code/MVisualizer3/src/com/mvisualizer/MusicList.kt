package com.mvisualizer

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JFrame
import javax.swing.JLabel

/*fun main(args: Array<String>) {
    EventQueue.invokeLater { MusicList() }
}*/

open class MusicList {
    companion object {
        lateinit var frame: JFrame
        var visListModel = VisListModel()
        var jList = JList(visListModel)

        val lfont = Font("Segoe UI Symbol", Font.PLAIN, 16)
        var nowPlayingString = "Now Playing:"
        var nowPlayingJLabel = JLabel(nowPlayingString)

        var musicListUI: MusicList? = null

        fun getInstance() {
            if (musicListUI == null) musicListUI = MusicList()
            frame.toFront()
        }

        fun nextSong() {
            if (!visListModel.isEmpty){
                if (!jList.valueIsAdjusting) {
                    val nextSong: MSong = visListModel.poll()
                    Controls.visualizerRef.loadSong(nextSong)
                }
            }
        }

        fun playSongAt(){
            if (!jList.isSelectionEmpty){
                if (!jList.valueIsAdjusting) {
                    val selectedIndex = jList.selectedIndex
                    val selectedSong: MSong = visListModel.remove(selectedIndex)
                    Controls.visualizerRef.loadSong(selectedSong)
                }
            }
        }

        fun cutSong() {
            if (!jList.isSelectionEmpty) {
                if (!jList.valueIsAdjusting) {
                    val selectedIndex = jList.selectedIndex
                    visListModel.remove(selectedIndex)

                    jList.selectedIndex = selectedIndex
                }
            }
        }

        fun moveSongUp() {
            if (!jList.isSelectionEmpty) {
//                val selection = jList.selectedValue
//                val selectedItemIndex = visListModel.indexOf(selection)
                val selectedIndex = jList.selectedIndex
                val itemUpIndex = selectedIndex - 1

                if (itemUpIndex > -1){
                    visListModel.swap(selectedIndex, itemUpIndex)
                    jList.selectedIndex = itemUpIndex
                }
            }
        }

        fun moveSongDown() {
            if (!jList.isSelectionEmpty) {
                //val selection = jList.selectedValue
                //val selectedItemIndex = visListModel.indexOf(selection)
                val selectedIndex = jList.selectedIndex
                val itemDownIndex = selectedIndex + 1

                if (itemDownIndex < visListModel.size){
                    visListModel.swap(selectedIndex, itemDownIndex)
                    jList.selectedIndex = itemDownIndex
                }
            }
        }

        fun setNowPlaying(title: String) { synchronized(nowPlayingJLabel) { nowPlayingJLabel.text = "$nowPlayingString $title" } }
    }

    init {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e1: Exception) {e1.printStackTrace()}

        frame = JFrame("Music List :D")
        frame.iconImage = iconImage
        frame.setSize(535, 380)
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(object : WindowAdapter() { override fun windowClosing(e: WindowEvent) { close() } })
        frame.setLocationRelativeTo(null)

        val scrollPane = JScrollPane()
        scrollPane.isDoubleBuffered = true
        scrollPane.preferredSize = Dimension(2, 60)
        frame.contentPane.add(scrollPane, BorderLayout.SOUTH)

        val panel = JPanel()
        scrollPane.setViewportView(panel)

        val playSongBtn = JButton("Play Song")
        playSongBtn.addActionListener{ playSongAt() }
        playSongBtn.font = lfont
        panel.add(playSongBtn)

        val nextsongBtn = JButton("Next Song")
        nextsongBtn.addActionListener{ nextSong() }
        nextsongBtn.font = lfont
        panel.add(nextsongBtn)

        val cutSongBtn = JButton("✂ Song")
        cutSongBtn.addActionListener{ cutSong() }
        cutSongBtn.font = lfont
        panel.add(cutSongBtn)

        val moveupBtn = JButton("Move ▲")
        moveupBtn.addActionListener { moveSongUp() }
        moveupBtn.font = lfont
        panel.add(moveupBtn)

        val movedownBtn = JButton("Move ▼")
        movedownBtn.addActionListener { moveSongDown() }
        movedownBtn.font = lfont
        panel.add(movedownBtn)

        val scrollPane_1 = JScrollPane()
        frame.contentPane.add(scrollPane_1, BorderLayout.CENTER)

        jList.visibleRowCount -1
        jList.layoutOrientation = JList.VERTICAL
        jList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        jList.font = Font("Segoe UI Symbol", Font.PLAIN, 18)
        scrollPane_1.setViewportView(jList)

        val panel_1 = JPanel()
        scrollPane_1.setColumnHeaderView(panel_1)
        panel_1.layout = BorderLayout(0, 0)

        nowPlayingJLabel.font = Font("Segoe UI Symbol", Font.PLAIN, 18)
        panel_1.add(nowPlayingJLabel, BorderLayout.NORTH)

        frame.isVisible = true
    }

    private fun close() {
        frame.dispose()
        musicListUI = null
    }
}