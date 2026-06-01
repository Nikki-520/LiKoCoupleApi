package com.liko.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageUtil {

    private static final int MAX_SIZE = 200 * 1024;
    private static final int MAX_WIDTH = 1200;

    public static String compress(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return base64;
        }

        try {
            String pure = extractBase64(base64);
            byte[] bytes = Base64.getDecoder().decode(pure);

            if (bytes.length <= MAX_SIZE) {
                return base64;
            }

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
            if (image == null) {
                return base64;
            }

            int width = image.getWidth();
            int height = image.getHeight();
            if (width > MAX_WIDTH) {
                double ratio = (double) MAX_WIDTH / width;
                width = MAX_WIDTH;
                height = (int) (height * ratio);
                BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = scaled.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(image, 0, 0, width, height, null);
                g.dispose();
                image = scaled;
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            float quality = 0.6f;
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            try (ImageOutputStream ios = ImageIO.createImageOutputStream(out)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            }
            writer.dispose();

            byte[] compressed = out.toByteArray();

            String prefix = getMimePrefix(base64);
            return prefix + Base64.getEncoder().encodeToString(compressed);

        } catch (Exception e) {
            return base64;
        }
    }

    private static String extractBase64(String dataUri) {
        if (dataUri.startsWith("data:image/")) {
            int idx = dataUri.indexOf(";base64,");
            if (idx > 0) {
                return dataUri.substring(idx + 8);
            }
        }
        return dataUri;
    }

    private static String getMimePrefix(String dataUri) {
        if (dataUri.startsWith("data:image/")) {
            int idx = dataUri.indexOf(";base64,");
            if (idx > 0) {
                return dataUri.substring(0, idx + 8);
            }
        }
        return "data:image/jpeg;base64,";
    }
}
