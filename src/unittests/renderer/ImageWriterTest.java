package unittests.renderer;


import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.ImageWriter;


public class ImageWriterTest {

    @Test
    public void testConstructor() {

        ImageWriter imageWriter = new ImageWriter("test", 800, 500);

        for(int i = 0; i < 800; i++)
            for(int j = 0; j < 500; j++)
                imageWriter.writePixel(i, j, new Color(255,20,147));

        for(int i = 0; i < 800; i += 50)
            for(int j = 0; j < 500; j ++)
                imageWriter.writePixel(i, j, new Color(255,255,255));

        for(int i = 0; i < 800; i ++)
            for(int j = 0; j < 500; j += 50)
                imageWriter.writePixel(i, j, new Color(255,255,255));
        imageWriter.writeToImage();

    }


}
