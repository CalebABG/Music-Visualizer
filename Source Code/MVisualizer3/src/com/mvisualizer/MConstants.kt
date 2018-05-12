package com.mvisualizer

import controlP5.ControlP5
import ddf.minim.AudioPlayer
import ddf.minim.Minim
import ddf.minim.analysis.FFT
import java.awt.*


lateinit var minim: Minim
lateinit var cp5: ControlP5
lateinit var fft: FFT
lateinit var player: AudioPlayer

var iconFile = "logo3.png"
var iconImage = Toolkit.getDefaultToolkit().getImage(Visualizer::class.java.getResource("/$iconFile"))

//  Strings
var nameOfSong = ""
var title = ""

//  Arrays
var fftSmooth = FloatArray(1)
var audioFormats = arrayOf("mp3", "wav", "aiff", "au")

//  Ints
var avgSize = 0
var barStep = 1
var backgroundStep = 50
var bufferSize = 2048
var minBandwidth = 2000
var bandsPerOctave = 300
var transformMode = 0
var visualMode = 0

//  Floats
var minVal = 0.0f
var maxVal = 0.0f
var smoothing = 0.78f
var cAmplitude = 90f
var ellipseR = 0f
var bassKick = 2.5f
var circleRadius = 165.0f
var strokeWeight = 2.05f
var amplitude = 1.2f
var angOffset = 2.18f
var angle = 0.0f
var offset = -0.07014108f /*random value between -PI and PI*/
var maxDrawHeight = 1.0f
var spectrumScaleFactor = 1.0f
var musicGain = 0f

//  Booleans
var MV_DEBUG = false
var initialSongLoaded = false
var playerInitialized = false
var playerNotClosed = true
var isPaused = false
var isMute = false
var drawBackground = false
var isSoundCloudSource = false