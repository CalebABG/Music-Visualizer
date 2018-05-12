package com.mvisualizer

import controlP5.*
import controlP5.Button
import controlP5.ControlP5Constants.CENTER
import controlP5.ControlP5
import java.awt.EventQueue


object Controls {
    lateinit var visualizerRef: Visualizer
    lateinit var accordion: Accordion
    lateinit var sliderfont: ControlFont
    lateinit var buttonfont: ControlFont

    fun setupControls() {
        //will need to change all three values
        //if offset is changed dramatically
        val sl_offset = 35f
        val sl_startYLoc = 10f
        val sl_spacing = 10f

        //slider width and height
        val slw = 295f
        //val slw = 265f
        val slh = 20f

        sliderfont = ControlFont(visualizerRef.createFont("Serif", 15f, true))
        buttonfont = ControlFont(visualizerRef.createFont("Serif", 14f, true))


        // group number 1
        val g1 = cp5.addGroup("sliders (press q for help)")
                .setHeight(25)
                .setBackgroundHeight((sl_offset * 9 + sl_spacing).toInt())

        g1.captionLabel.font = sliderfont
        g1.captionLabel.align(CENTER, CENTER)

        var temp_bheight = sl_startYLoc

        //smoothing slider
        val smoothing_slider = createSlider(0.04f, g1, "smoothing", 0f, sl_startYLoc, slw, slh, 0.5f, 1.0f, smoothing)
                .addListener { p0 -> smoothing = p0.value }

        //amplitude slider
        temp_bheight = 1 * sl_offset + sl_spacing
        val amplitude_slider = createSlider(0.02f, g1, "amplitude", 0f, temp_bheight, slw, slh, 1f, 300f, amplitude)
                .addListener { p0 -> amplitude = p0.value }

        //slider buttons
        /*val smoothing_btn = createButton(g1, "smoothing_btn", slw + 10f, temp_bheight, 20f, 20f, buttonfont)
                .addListener { smoothing_slider.value = Utils.minValueGuard(0.5f, 1.0f, smoothing, "Smoothing Value (Float)", null) }
        smoothing_btn.captionLabel.isVisible = false

        val amplitude_btn = createButton(g1, "amplitude_btn", slw + 10f, temp_bheight, 20f, 20f, buttonfont)
                .addListener { amplitude_slider.value = Utils.minValueGuard(1f, 300f, smoothing, "Amplitude Value (Float)", null) }
        amplitude_btn.captionLabel.isVisible = false*/

        //basskick slider and button
        temp_bheight = 2 * sl_offset + sl_spacing
        val basskick_slider = createSlider(0.05f, g1, "bass-kick", 0f, temp_bheight, slw, slh, 0.1f, 120f, bassKick)
                .addListener { p0 -> bassKick = p0.value }

        val radius_slider = createSlider(0.04f, g1, "radius", 0f, 3 * sl_offset + sl_spacing, slw, slh, 50f, 500f, circleRadius)
                .addListener { p0 -> circleRadius = p0.value }

        val barstep_slider = createSlider(0.3f, g1, "bar-step", 0f, 4 * sl_offset + sl_spacing, slw, slh, 1f, 30f, barStep.toFloat())
                .addListener { p0 -> barStep = p0.value.toInt() }

        val strokeweight_slider = createSlider(0.03f, g1, "stroke-weight", 0f, 5 * sl_offset + sl_spacing, slw, slh, 1f, 60f, strokeWeight)
                .addListener { p0 -> strokeWeight = p0.value }

        val angleoffset_slider = createSlider(0.004f, g1, "angle-offset", 0f, 6 * sl_offset + sl_spacing, slw, slh, 1f, 250f, angOffset)
                .addListener { p0 -> angOffset = p0.value }

        val backgroundstep_slider = createSlider(0.05f, g1, "background-step", 0f, 7 * sl_offset + sl_spacing, slw, slh, 10f, 180f, backgroundStep.toFloat())
                .addListener { p0 -> backgroundStep = p0.value.toInt() }

        val musicgain_slider = createSlider(0.02f, g1, "music-gain", 0f, 8 * sl_offset + sl_spacing, slw, slh, -30f, 10f, 0f)
                .addListener { p0 -> musicGain = p0.value; player.gain = musicGain }


        //Button constants
        val btn_offset = 15f
        val btn_startYLoc = 10f
        val bw = 145f //button width
        val btn_spacingx = 5f //button horizontal spacing
        val bh = 25f //button width
        val btn_spacingy = 15f //button vertical spacing

        val g2 = cp5.addGroup("buttons")
                .setHeight(25)
                .setBackgroundHeight(150)

        g2.captionLabel.font = sliderfont
        g2.captionLabel.align(CENTER, CENTER)

        createButton(g2, "add-songs", 0f, btn_startYLoc, bw, bh).addListener {
            run {
                Visualizer.addSongs(Visualizer.fileChooser())
            }
        }

        createButton(g2, "next-visual", bw + btn_spacingx, btn_startYLoc, bw, bh).addListener {
            run {
                Visualizer.changeVisual()
            }
        }

        createButton(g2, "pause-song", 0f, 2 * btn_offset + btn_spacingy, bw, bh).addListener { p0 ->
                    run {
                        Visualizer.togglePlay()
                        p0.controller.setCaptionLabel(if (isPaused) "play-song" else "pause-song")
                    }
                }

        createButton(g2, "mute-song", bw + btn_spacingx, 2 * btn_offset + btn_spacingy, bw, bh).addListener { p0 ->
                    run {
                        Visualizer.toggleMute()
                        p0.controller.setCaptionLabel(if (isMute) "un-mute-song" else "mute-song")
                    }
                }

        createButton(g2, "toggle-dots", 0f, 4 * btn_offset + btn_spacingy + 5, bw, bh).addListener {
            run {
                Visualizer.toggleBackground()
            }
        }

        createButton(g2, "song-queue", bw + btn_spacingx, 4 * btn_offset + btn_spacingy + 5, bw, bh).addListener {
            run {
                EventQueue.invokeLater { MusicList.getInstance() }
            }
        }

        createButton(g2, "add-soundcloud-track", 0f, 6 * btn_offset + btn_spacingy + 10, 295f, bh).addListener {
            run {
                EventQueue.invokeLater { URLLoader.getInstance() }
            }
        }


        //Setup Accordion
        accordion = cp5.addAccordion("acc")
                .setPosition(2f, 20f)
                .setWidth(295)
                .addItem(g1)
                .addItem(g2)

        //accordion.open(0, 1)
        accordion.setCollapseMode(Accordion.MULTI)

    }

    /**
     * Ease of use function for creating sliders from the controlP5 library
     * The last parameter can be omitted as can be seen in the above slider creations because
     * the default PFont for all sliders is the class static font: Font.SERIF, Font.PLAIN, 15
     */
    private fun createSlider(scrollSense: Float, group: Group, name: String, loc_x: Float, loc_y: Float, size_w: Float, size_h: Float, range_min: Float,
                             range_max: Float, init_v: Float, font: ControlFont = sliderfont): Slider {
        // add a vertical slider
        val t_slider = cp5.addSlider(name)
                .setPosition(loc_x, loc_y)
                .setSize(size_w.toInt(), size_h.toInt())
                .setRange(range_min, range_max)
                .setValue(init_v)
                .setScrollSensitivity(scrollSense)
                .setSliderMode(Slider.FLEXIBLE)
                .moveTo(group)

        t_slider.valueLabel.font = font
        t_slider.valueLabel.align(ControlP5.RIGHT, ControlP5.RIGHT)

        t_slider.captionLabel.font = font
        t_slider.captionLabel.align(ControlP5.LEFT, ControlP5.LEFT)

        return t_slider
    }

    /**
     * Ease of use function for creating buttons from the controlP5 library
     * The last parameter can be omitted as can be seen in the above button creations because
     * the default PFont for all buttons is the class static font: Font.SERIF, Font.PLAIN, 15
     */
    private fun createButton(group: Group, name: String, loc_x: Float, loc_y: Float, size_w: Float, size_h: Float, font: ControlFont = buttonfont): Button {
        // add a button
        val t_button = cp5.addButton(name)
                .setPosition(loc_x, loc_y)
                .setSize(size_w.toInt(), size_h.toInt())
                .moveTo(group)

        t_button.captionLabel.font = font

        return t_button
    }
}
