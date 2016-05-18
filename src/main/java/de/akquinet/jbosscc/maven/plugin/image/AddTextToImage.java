package de.akquinet.jbosscc.maven.plugin.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Tool to add some text to an Image at specified positions with a specified font and color.
 */
public final class AddTextToImage {
    private BufferedImage image;

    private Graphics2D graphics;

    private int imageHeight;

    private int imageWidth;

    private String imageFormat = "jpg";

    private AddTextToImage() {
        // use factory method
    }

    public static AddTextToImage create() {
        return new AddTextToImage();
    }

    public AddTextToImage setImageFormat(final String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public void saveImage(final String targetImg) throws IOException {
        graphics.dispose();
        ImageIO.write(image, imageFormat, new File(targetImg));
    }

    public AddTextToImage addText(
        final String text, final Font font, final Color color, final int offsetX, final int offsetY) {
        System.out.println("Adding Text: text=" + text + ", font=" + font + ", color=" + color + ", offsetX=" + offsetX
            + ", offsetY=" + offsetY);

        graphics.setFont(font);
        graphics.setColor(color);

        int xPos = offsetX;
        if (offsetX < 0) {
            // make the text right-aligned

            // get metrics from the graphics
            FontMetrics metrics = graphics.getFontMetrics(font);
            // get the height of a line of text in this
            // font and render context
            int hgt = metrics.getHeight();
            // get the advance of my text in this font
            // and render context
            int adv = metrics.stringWidth(text);

            // because offsetX is negative the value is subtracted
            xPos = imageWidth + offsetX - adv;
            System.out.println("The offsetX value is expected as right aligned value, new xPos: " + xPos
                + ", calculated width of text: " + adv);
        }

        graphics.drawString(text, xPos, imageHeight - font.getSize() / 2 - offsetY);
        return this;
    }

    public AddTextToImage openImage(final String sourceImagePath) throws IOException {
        File file = new File(sourceImagePath);
        Image srcImage = ImageIO.read(file);
        imageWidth = srcImage.getWidth(null);
        imageHeight = srcImage.getHeight(null);
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.addRenderingHints(rh);
        
        graphics.drawImage(srcImage, 0, 0, imageWidth, imageHeight, null);

        return this;
    }

    public static void main(String[] args) throws IOException {
        /*
         * String srcPath =
         * "C:\\Projekte\\ubi-root\\vs\\vs-config\\vs-config-client-packaging\\src\\main\\resources-binary\\splash.jpg";
         * String targetPath = "C:\\Temp\\splash.png";
         * 
         * AddTextToImage.create() .setImageFormat("png") .openImage(srcPath) .addText("UBI ist toll", new Font("Arial",
         * Font.BOLD, 16), Color.WHITE, 50, 500) .addText("UBI ist gut", new Font("Arial", Font.BOLD, 16), Color.WHITE,
         * 50, 475) .saveImage(targetPath);
         */

        System.out.println(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()));
    }
}
