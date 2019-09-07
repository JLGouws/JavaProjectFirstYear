import processing.core.*;
// dummy object for backwards compatibility, plus the select methods
import java.awt.Frame;

// before calling settings() to get displayWidth/Height
import java.awt.DisplayMode;
// handleSettings() and displayDensity()
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
// used to present the fullScreen() warning about Spaces on OS X
import javax.swing.JOptionPane;

// inside runSketch() to warn users about headless
import java.awt.HeadlessException;
import java.awt.Toolkit;

// used by loadImage()
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
// allows us to remove our own MediaTracker code
import javax.swing.ImageIcon;

// used by selectInput(), selectOutput(), selectFolder()
import java.awt.EventQueue;
import java.awt.FileDialog;
import javax.swing.JFileChooser;

// set the look and feel, if specified
import javax.swing.UIManager;

// used by link()
import java.awt.Desktop;

// used by desktopFile() method
import javax.swing.filechooser.FileSystemView;

// loadXML() error handling
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.regex.*;
import java.util.zip.*;

import processing.data.*;
import processing.event.*;
import processing.opengl.*;



public class PAppletSource extends PApplet{
    
  
  static public void newRunSketch(final String[] args,final PApplet constructedSketch) {

      System.setProperty("sun.awt.noerasebackground", "true");

      System.setProperty("javafx.animation.fullspeed", "true");

      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) {
          e.printStackTrace();
        }
      });

      

      // Catch any HeadlessException to provide more useful feedback
      try {
        // Call validate() while resize events are in progress
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
      } catch (HeadlessException e) {
        System.err.println("Cannot run sketch without a display. Read this for possible solutions:");
        System.err.println("https://github.com/processing/processing/wiki/Running-without-a-Display");
        System.exit(1);
      }

      // So that the system proxy setting are used by default
      System.setProperty("java.net.useSystemProxies", "true");

      if (args.length < 1) {
        System.err.println("Usage: PApplet [options] <class name> [sketch args]");
        System.err.println("See the Javadoc for PApplet for an explanation.");
        System.exit(1);
      }

      boolean external = false;
      int[] location = null;
      int[] editorLocation = null;

      String name = null;
      int windowColor = 0;
      int stopColor = 0xff808080;
      boolean hideStop = false;

      int displayNum = -1;  // use default
  //    boolean fullScreen = false;
      boolean present = false;
  //    boolean spanDisplays = false;
      int density = -1;

      String param = null, value = null;
      String folder = calcSketchPath();
      final PApplet sketch;
      try {
        Class<?> c =
          Thread.currentThread().getContextClassLoader().loadClass(name);
        sketch = (PApplet) c.getDeclaredConstructor().newInstance();
      } catch (RuntimeException re) {
        // Don't re-package runtime exceptions
        throw re;
      } catch (Exception e) {
        // Package non-runtime exceptions so we can throw them freely
        throw new RuntimeException(e);
      }

      if (platform == MACOSX) {
        try {
          final String td = "processing.core.ThinkDifferent";
          Class<?> thinkDifferent =
            Thread.currentThread().getContextClassLoader().loadClass(td);
          Method method =
            thinkDifferent.getMethod("init", new Class[] { PApplet.class });
          method.invoke(null, new Object[] { sketch });
        } catch (Exception e) {
          e.printStackTrace();  // That's unfortunate
        }
      }

      // Set the suggested display that's coming from the command line
      // (and most likely, from the PDE's preference setting).
      sketch.display = displayNum;

      // Set the suggested density that is coming from command line
      // (most likely set from the PDE based on a system DPI scaling)
      sketch.suggestedDensity = density;

      sketch.present = present;

      // For 3.0.1, moved this above handleSettings() so that loadImage() can be
      // used inside settings(). Sets a terrible precedent, but the alternative
      // of not being able to size a sketch to an image is driving people loopy.
      // A handful of things that need to be set before init/start.
  //    if (folder == null) {
  //      folder = calcSketchPath();
  //    }
      sketch.sketchPath = folder;

      // Don't set 'args' to a zero-length array if it should be null [3.0a8]
      if (args.length != argIndex + 1) {
        // pass everything after the class name in as args to the sketch itself
        // (fixed for 2.0a5, this was just subsetting by 1, which didn't skip opts)
        sketch.args = PApplet.subset(args, argIndex + 1);
      }

 
      sketch.handleSettings();

      sketch.external = false;

      if (windowColor != 0) {
        sketch.windowColor = windowColor;
      }

      final PSurface surface = sketch.initSurface();


      if (present) {
        if (hideStop) {
          stopColor = 0;  // they'll get the hint
        }
        surface.placePresent(stopColor);
      } else {
        surface.placeWindow(location, editorLocation);
      }


      sketch.showSurface();
      sketch.startSurface();
      /*
      if (sketch.getGraphics().displayable()) {
        surface.setVisible(true);
      }
      //sketch.init();
      surface.startThread();
      */
    }
}