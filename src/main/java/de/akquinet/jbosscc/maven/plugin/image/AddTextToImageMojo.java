package de.akquinet.jbosscc.maven.plugin.image;

import static java.util.Arrays.asList;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which adds text to an image file. The source file is left untouched. The target file will be saved to a
 * configured folder under the Maven target directory.
 */
@Mojo(name = "add-text", defaultPhase = LifecyclePhase.PROCESS_RESOURCES, threadSafe = true)
public class AddTextToImageMojo extends AbstractMojo {
    /**
     * Path to the source image file; relative to the module's base dir.
     */
	@Parameter(required = true)
    private File sourceImage;

    /**
     * Path for the target image file; relative to the module's base dir.
     */
	@Parameter(required = true)
    private File targetImage;

    /**
     * The format of the target image. Will be passed on to javax.imageio.ImageIO.write() as parameter formatName.
     */
	@Parameter(defaultValue = "jpg")
    private String targetFormat;

    /**
     * Default values for the Font properties.
     */
	@Parameter
    private Default defaults = new Default();

    /**
     * The Text and its properties.
     */
	@Parameter(required = true)
    private Text[] texts;

	@Override
    public void execute() throws MojoExecutionException {
        logParameters();

        targetImage.getParentFile().mkdirs();

        // -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("awt.aatext", "true");

        try {
            AddTextToImage textToImage =
                AddTextToImage.create().openImage(sourceImage.getPath()).setImageFormat(targetFormat);

            for (Text text : texts) {
                textToImage
                    .addText(text.getText(), getFont(text), getFontColor(text), text.getOffsetX(), text.getOffsetY());
            }

            textToImage.saveImage(targetImage.getPath());
        }
        catch (IOException e) {
            throw new MojoExecutionException("Error working with image", e);
        }
        catch (IllegalAccessException e) {
            throw new MojoExecutionException("Error working with image", e);
        }

    }

    private Color getFontColor(final Text text) throws IllegalAccessException {
        return ColorUtil.fromRgbOrName(text.getFontColor() == null ? defaults.getFontColor() : text.getFontColor());
    }

    private Font getFont(final Text text) {
        String fontName = text.getFontName() == null ? defaults.getFontName() : text.getFontName();
        int fontSize = text.getFontSize() == null ? defaults.getFontSize() : text.getFontSize();
        int fontStyle = text.getFontStyle() == null ? defaults.getFontStyle() : text.getFontStyle();
        return new Font(fontName, fontStyle, fontSize);
    }

    private void logParameters() {
        getLog().info("sourceImage=" + sourceImage);
        getLog().info("targetImage=" + targetImage);
        getLog().info("defaults=" + defaults);
        if (texts != null) {
            getLog().info("texts=" + asList(texts));
        }
    }
}
